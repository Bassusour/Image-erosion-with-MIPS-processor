import chisel3._
import chisel3.util._

class ControlUnit extends Module {
  val io = IO(new Bundle {
    val opcode = Input(UInt(4.W))
    val jumpNeg = Output(Bool())
    val jumpEq = Output(Bool())
    val jumpImm = Output(Bool())
    val memToReg = Output(Bool())
    val ALUop = Output(UInt(3.W))
    val memWrite = Output(Bool())
    val ALUsrc = Output(Bool())
    val regWrite = Output(Bool())
  })

  io.jumpEq := 0.U
  io.jumpNeg := 0.U
  io.jumpImm := 0.U
  io.memToReg := 0.U
  io.ALUop := 0.U
  io.memWrite := 0.U
  io.ALUsrc := 0.U
  io.regWrite := 0.U

  switch (io.opcode) {
    is(0.U) { io.regWrite := 1.U }
    is(1.U) { io.ALUsrc := 1.U; io.regWrite := 1.U}
    is(2.U) { io.ALUop := 1.U; io.regWrite := 1.U}
    is(3.U) { io.ALUop := 1.U;  io.ALUsrc := 1.U; io.regWrite := 1.U}
    is(4.U) { io.ALUop := 2.U; io.regWrite := 1.U}
    is(5.U) { io.ALUop := 3.U; io.ALUsrc := 1.U; io.regWrite := 1.U}
    is(6.U) { io.memToReg := 1.U; io.ALUop := 3.U; io.regWrite := 1.U}
    is(7.U) { io.memWrite := 1.U;  io.ALUop := 4.U }
    is(8.U) { io.jumpImm := 1.U }
    is(9.U) { io.jumpEq:= 1.U;  io.ALUop := 1.U}
    is(10.U) { io.jumpNeg:= 1.U;  io.ALUop := 1.U}
  }
}