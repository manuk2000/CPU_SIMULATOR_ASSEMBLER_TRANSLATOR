package hardware;

import java.util.Arrays;

public class Ram {

    // The main memory of the RAM
    private final byte[] main_memory = new byte[96];

    // Size of Program 1 (P1)
    private final int P1_SIZE;

    // End pointer for Program 1 (P1)
    private int P1_endPtr;

    // Constructor to initialize the RAM with machine code
    public Ram(byte[] machineCode) {
        // Load the machine code into the main memory
        loadMachineCode(machineCode);
        // Set the size of Program 1 (P1)
        P1_SIZE = machineCode.length;
        // Set the end pointer of Program 1 (P1) to its size initially
        P1_endPtr = P1_SIZE;
    }

    // Get the size of Program 1 (P1)
    public int getP1_SIZE() {
        return P1_SIZE;
    }

    // Get the value of an element in the stack memory
    public byte getElementStack(int index) {
        return main_memory[index + P1_SIZE];
    }

    // Set the value of an element in the stack memory
    public void setElementStack(int index, byte value) {
        main_memory[index + P1_SIZE] = value;
        // Update the end pointer if necessary
        if (index + P1_SIZE > P1_endPtr) {
            P1_endPtr = index + P1_SIZE;
        }
    }

    // Get a clone of the main memory array
    public byte[] getMain_memory() {
        return main_memory.clone();
    }

    // Load the machine code into the main memory
    private void loadMachineCode(byte[] array) {
        System.arraycopy(array, 0, main_memory, 0, array.length);
    }


    // Get the machine code of an instruction at a given index
    public byte[] getInstructionMachineCode(int indexGH) {
        int startIndex = indexGH;
        int endIndex = startIndex + 3;
        return Arrays.copyOfRange(main_memory, startIndex, endIndex);
    }

    // Dump the contents of memory (excluding the program code)
    public void dump_memory() {
        for (int i = P1_SIZE; i < P1_endPtr + 1; i++) {
            System.out.println(main_memory[i]);
        }
    }

}