import chisel3._
import chisel3.util._

class ProgramCounter extends Module {
  val io = IO(new Bundle {
    val stop = Input(Bool())
    val jump = Input(Bool())
    val run = Input(Bool())
    val programCounterJump = Input(UInt(16.W))
    val programCounter = Output(UInt(16.W))
  })

  val reg = Reg(UInt(16.W));

  val runSelect = io.stop || !io.run;
  val muxOutput = 0;

  val adder = reg + 1.U;
  val counterMux = Mux(io.jump, io.programCounterJump,adder);

  reg := Mux(runSelect, reg, counterMux);

  io.programCounter := reg
}