package hardware;

public class Cpu {
    private Ram ram;
    private Register register;

    public Cpu(Ram ram) {
        this.ram = ram;
        this.register = new Register();
    }


    // Executes the CPU by fetching instructions from RAM and executing them
    public void executeCpu() {
        ALU alu = new ALU(register);
        int endProgramIndex = ram.getP1_SIZE();  // The index where the program ends
        int currentGH;

        // Execute instructions until the end of the program is reached
        do {
            currentGH = register.getRegisterGH();  // Get the current instruction index (GH)
            byte[] cell = ram.getInstructionMachineCode(currentGH);  // Fetch the machine code for the instruction
            alu.execute(cell, ram); // Execute the instruction using the ALU
        } while (endProgramIndex > currentGH + 3);  // Continue until the end of the program is reached

        // Print the values of registers
        System.out.println("Print registers Values");
        register.dump_register();

        // Print the values of memory
        System.out.println("Print memory Values");
        ram.dump_memory();
    }
}

