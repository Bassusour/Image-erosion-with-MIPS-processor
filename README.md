# Image-erosion-with-MIPS-processor
In this project, me and my team developed a way to 'erode' the cells of the outer layer of an black-and-white image, by using a custom MIPS processor, and design our own Instruction Set Architecture (ISA) and opcode for it.  
  
  ![image](https://user-images.githubusercontent.com/54844410/160682395-13a4e15a-1f44-4594-9511-9336e62cf3cd.png)

  
Our ISA only uses a single type of command, instead of the R-, I-, and J-type of commands an ordinary MIPS. It is a 32-bit command with the following order:  
| Opcode (4-bits)      | Register 1 (4-bits) | Register 2 (4-bits)      | Register 0 (4-bits) | Immediate (16-bits) |
| ----------- | ----------- | ----------- | ----------- | ----------- |
  
The table for our opcode is the following  
| INSTRUCTION      | SYNTAX | MEANING      | OPCODE |
| ----------- | ----------- | ----------- | ----------- |
| Addition      | ADD R0, R1, R2       | R0 = R1 + R2       | 0000       |
| Addition with immediate   | ADDI R0, R1, IMMEDIATE        |  R0 = R1 + Immediate     | 0001        |
| Subtraction      | SUB R0, R1, R2       | R0 = R1 - R2       | 0010       |
| Subtraction with immediate   | SUBI R0, R1, IMMEDIATE        | R0 = R1 - IMMEDIATE   | 0011        |
| Multiplication      | MULT R0, R1, R2       | R0 = R1 * R2      | 0100       |
| Load immediate   | LI R0, IMMEDIATE        |  R0 = IMMEDIATE   | 0101        |
| Load data      | LD R0, R2       |  R0 = memory(R2)      | 0110       |
| Store data   | SD R2, R1        |  memory(R1) = R2   | 0111        |
| Jump      | JR IMMEDIAT       | Jump to IMMEDIATE      | 1000       |
| Jump if equal   | JEQ IMMEDIATE, R1, R2        | Jump to IM if R1 = R2   | 1001        |
| Jump if less than      |  JLT IMMEDIATE, R1, R2       | Jump to IM if R1 < R2      | 1010       |
| Stop   | STP        | Stop execution     | 1011        |  
  
The `Control`-unit interpreter has the following syntax. The X means the value doesn't matter. 
| Opcode | JumpEq | MemToReg | ALUop | MemWrite | ALUSrc | RegWrite | JumpImm | JumpNeg | Stop |
|--------|--------|----------|-------|----------|--------|----------|---------|---------|------|
| 0000   | 0      | 0        | 000   | 0        | 0      | 1        | 0       | 0       | 0    |
| 0001   | 0      | 0        | 000   | 0        | 1      | 1        | 0       | 0       | 0    |
| 0010   | 0      | 0        | 001   | 0        | 0      | 1        | 0       | 0       | 0    |
| 0011   | 0      | 0        | 001   | 0        | 1      | 1        | 0       | 0       | 0    |
| 0100   | 0      | 0        | 010   | 0        | 0      | 1        | 0       | 0       | 0    |
| 0101   | 0      | 0        | 011   | 0        | 1      | 1        | 0       | 0       | 0    |
| 0110   | 0      | 1        | 011   | 0        | 0      | 1        | 0       | 0       | 0    |
| 0111   | 0      | 0        | 100   | 1        | X      | 0        | 0       | 0       | 0    |
| 1000   | 0      | 0        | X     | 0        | X      | 0        | 1       | 0       | 0    |
| 1001   | 1      | 0        | 001   | 0        | 0      | 0        | 0       | 0       | 0    |
| 1010   | 0      | 0        | 001   | 0        | 0      | 0        | 0       | 1       | 0    |
| 1011   | 0      | 0        | 000   | 0        | 0      | 0        | 0       | 0       | 1    |  
  
The ALU in the diagram is dependent on the 3-bit output from the `Control`-unit. The schema of operations is listed below.  
| Instruction    | Output | ALU operation code |
|----------------|--------|--------------------|
| Addition       | A + B  | 000                |
| Subtraction    | A - B  | 001                |
| Multiplication | A * B  | 010                |
| output=B       | B      | 011                |
| output=A       | A      | 100                |  

Finally a blockdiagram for the processor  

![image](https://user-images.githubusercontent.com/54844410/160682010-cf5add96-7cf9-4858-8455-660008e203cb.png)
