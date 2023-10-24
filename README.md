# CPU_SIMULATOR_ASSEMBLER_TRANSLATOR


## Table of Contents
- Introduction
- Features
- Getting Started
- Prerequisites
- File Structure
- Contributing
- License
- Contact

## Introduction

The CPU Simulator is a Java-based program that simulates the behavior of a Central Processing Unit (CPU) based on a simplified instruction set architecture.This repository contains code snippets for a simple operating system implemented in Java. The code demonstrates the functionality of various components such as CPU, RAM, registers, ALU, and a compiler.These components work together to execute machine code instructions and manage memory.It reads MASM Assembly code from a file, translates it into machine code, and loads the machine code into RAM. The CPU’s Arithmetic Logic Unit (ALU) fetches instructions from RAM and executes them. The simulator provides a set of instructions for performing various operations on an abstract machine, including arithmetic and logical operations, data transfers, condition checks, and jump operations. The CPU operations are controlled by an Operating System (OS). This README provides an overview of the CPU Simulator and instructions on how to use it.

Examples you can run following codes
```
1. 
MOV BEN, 24
MOV AYB, 0
ADD AYB, BEN
MOV [5], AYB

```
```
2. 
MOV BEN, 0
MOV AYB, 6
L1:ADD BEN, 1
CMP BEN, AYB
JE L2
JMP L1
L2:ADD BEN, 1

```
```
3. 
MOV GIM, 9
MOV AYB, 127
L1:ADD GIM, 1
CMP AYB, GIM
JE L2
JMP L1
L2: ADD AYB, 0

```
```
4. 
MOV BEN, 4
MOV AYB, 6
CMP BEN, AYB
JL L2
MUL BEN, 1
L2:MOV [5], AYB
```


## Features

- CPU simulation with instruction execution
- Memory management using RAM
- Register management for storing and manipulating data
- Arithmetic and logical operations supported by the ALU
- Compilation of high-level code to machine code

## Usage

To run the code, follow the steps below:

1. Clone the repository:

   ```shell
   git clone https://github.com/manuk2000/CPU_SIMULATOR_ASSEMBLER_TRANSLATOR.git

2. Compile the program:

   ```shell
   javac Main.java
3. Run the program:

   ```shell
   java Main
