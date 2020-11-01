import chisel3._
import chisel3.util._
import chisel3.iotesters
import chisel3.iotesters.PeekPokeTester
import java.util

object Programs{
  val program1 = Array(
    "b00010011000000100000000000000111".U(32.W)
  )

  val program2 = Array(
    "b00010011000000100000000000000111".U(32.W)
  )
}