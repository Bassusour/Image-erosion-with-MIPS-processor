import chisel3._
import chisel3.util._

class CPUTop extends Module {
  val io = IO(new Bundle {
    val done = Output(Bool ())
    val run = Input(Bool ())
    //This signals are used by the tester for loading and dumping the memory content, do not touch
    val testerDataMemEnable = Input(Bool ())
    val testerDataMemAddress = Input(UInt (16.W))
    val testerDataMemDataRead = Output(UInt (32.W))
    val testerDataMemWriteEnable = Input(Bool ())
    val testerDataMemDataWrite = Input(UInt (32.W))
    //This signals are used by the tester for loading and dumping the memory content, do not touch
    val testerProgMemEnable = Input(Bool ())
    val testerProgMemAddress = Input(UInt (16.W))
    val testerProgMemDataRead = Output(UInt (32.W))
    val testerProgMemWriteEnable = Input(Bool ())
    val testerProgMemDataWrite = Input(UInt (32.W))
  })

  //Creating components
  val programCounter = Module(new ProgramCounter())
  val dataMemory = Module(new DataMemory())
  val programMemory = Module(new ProgramMemory())
  val registerFile = Module(new RegisterFile())
  val controlUnit = Module(new ControlUnit())
  val alu = Module(new ALU())


  //Connecting the modules
  programCounter.io.run := io.run
  //programMemory.io.address := programCounter.io.programCounter

  // ???????????????????
  io.done := 0.U
  programCounter.io.stop := 0.U


  ////////////////////////////////////////////
  //Continue here with your connections
  ////////////////////////////////////////////

  // Jump condition
  val equalCond = controlUnit.io.jumpEq & alu.io.equal
  val lessCond = controlUnit.io.jumpNeg & alu.io.less
  val jumpOrNot = equalCond | lessCond | controlUnit.io.jumpImm

  // PC
  programCounter.io.programCounterJump := programMemory.io.instructionRead(15, 0)
  programCounter.io.jump := jumpOrNot

  // ALU
  alu.io.a := registerFile.io.a
  val sign = 0.U(32.W) + programMemory.io.instructionRead(15, 0)
  val bRes = Mux( controlUnit.io.ALUsrc , sign, registerFile.io.b)
  alu.io.b := bRes
  alu.io.sel := controlUnit.io.ALUop

  // ControlUnit
  controlUnit.io.opcode := programMemory.io.instructionRead(31, 28)

  // ProgramMemory
  programMemory.io.address := programCounter.io.programCounter

  // DataMemory
  dataMemory.io.address := alu.io.result
  dataMemory.io.dataWrite := alu.io.b
  dataMemory.io.writeEnable := controlUnit.io.memWrite

  // Output (ALU or DataMemory)
  val outALUOrDataMemory = Mux( controlUnit.io.memToReg, dataMemory.io.dataRead, alu.io.result)

  // RegisterFile
  registerFile.io.aSel := programMemory.io.instructionRead(27, 24)
  registerFile.io.bSel := programMemory.io.instructionRead(23, 20)
  registerFile.io.writeSel := programMemory.io.instructionRead(19, 16)
  registerFile.io.writeData := outALUOrDataMemory
  registerFile.io.writeEnable := controlUnit.io.regWrite


  //This signals are used by the tester for loading the program to the program memory, do not touch
  programMemory.io.testerAddress := io.testerProgMemAddress
  io.testerProgMemDataRead := programMemory.io.testerDataRead
  programMemory.io.testerDataWrite := io.testerProgMemDataWrite
  programMemory.io.testerEnable := io.testerProgMemEnable
  programMemory.io.testerWriteEnable := io.testerProgMemWriteEnable
  //This signals are used by the tester for loading and dumping the data memory content, do not touch
  dataMemory.io.testerAddress := io.testerDataMemAddress
  io.testerDataMemDataRead := dataMemory.io.testerDataRead
  dataMemory.io.testerDataWrite := io.testerDataMemDataWrite
  dataMemory.io.testerEnable := io.testerDataMemEnable
  dataMemory.io.testerWriteEnable := io.testerDataMemWriteEnable
}
