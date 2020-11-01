import chisel3._
import chisel3.util._

class RegisterFile extends Module {
  val io = IO(new Bundle {
    //Define the module interface here (inputs/outputs)
    val aSel = Input(UInt(4.W))
    val bSel = Input(UInt(4.W))
    val writeData = Input(UInt(32.W))
    val writeSel = Input(UInt(4.W))
    val writeEnable = Input(Bool())
    val a = Output(UInt(32.W))
    val b = Output(UInt(32.W))
  })

  val R1 = Reg(UInt(32.W))
  val R2 = Reg(UInt(32.W))
  val R3 = Reg(UInt(32.W))
  val R4 = Reg(UInt(32.W))
  val R5 = Reg(UInt(32.W))

  io.a := 1.U
  io.b := 1.U

  when (io.writeEnable) {
    switch (io.writeSel) {
      is(0.U) { R1 := io.writeData }
      is(1.U) { R2 := io.writeData }
      is(2.U) { R3 := io.writeData }
      is(3.U) { R4 := io.writeData }
      is(4.U) { R5 := io.writeData }
    }
  }

  switch (io.aSel) {
    is(0.U) { io.a := R1 }
    is(1.U) { io.a := R2 }
    is(2.U) { io.a := R3 }
    is(3.U) { io.a := R4 }
    is(4.U) { io.a := R5 }
  }

  switch (io.bSel) {
    is(0.U) { io.b := R1 }
    is(1.U) { io.b := R2 }
    is(2.U) { io.b := R3 }
    is(3.U) { io.b := R4 }
    is(4.U) { io.b := R5 }
  }

}