import chisel3._
import chisel3.util._

class ALU extends Module {
  val io = IO(new Bundle {
    val a = Input(UInt(32.W))
    val b = Input(UInt(32.W))
    val sel = Input(UInt(3.W))
    val result = Output(UInt(32.W))
    val less = Output(Bool())
    val equal = Output(Bool())
  })

  io.result := 1.U

  switch (io.sel) {
    is(0.U) { io.result := io.a + io.b }
    is(1.U) { io.result := io.a - io.b }
    is(2.U) { io.result := io.a * io.b }
    is(3.U) { io.result := io.b }
    is(4.U) { io.result := io.a }
  }

  io.equal := 0.U
  when (io.a === io.b) {
    io.equal := 1.U
  }

  io.less := 0.U
  when (io.a < io.b) {
    io.less := 1.U
  }


}