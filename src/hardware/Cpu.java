package hardware;

import java.util.HashMap;
import java.util.concurrent.RecursiveTask;

public class Cpu {
    private Ram ram;
    private Register register;

    public Cpu(Ram ram) {
        this.ram = ram;
        this.register = new Register();
    }

    //reg     -> 0
    //lable   -> 1
    //[]      -> 2
    //literal -> 3

    public void executeCpu() {
        ALU alu = new ALU(register);
        int endProgramIndex = ram.getP1_SIZE();
        int currentGH;

        do {
            currentGH = register.getRegisterGH();
            byte[] cell = ram.getInstructionMachineCode(currentGH);
            alu.execute(cell, ram);
        }while (endProgramIndex > currentGH + 3); /////////////// popoxutyun
        System.out.println("Print registers Values");
        register.dump_register();
        System.out.println("Print memory Values");
        ram.dump_memory();
    }
}

