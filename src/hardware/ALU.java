package hardware;

public class ALU {
    private Register register;

    public ALU(Register register) {
        this.register = register;
    }

    // Get the value of the operand based on its type
    byte getValueOperand(int operandType, int index, Ram ram, Register register) {
        if (operandType == 0) {
            return register.getRegisterValue(index);  // Retrieve value from register
        } else if (operandType == 2) {
            return ram.getElementStack(index);   // Retrieve value from RAM stack
        }
        return (byte) index;  // Operand is a literal value
    }


    // Extract the instruction type from the cell
    byte getInstructionType(byte cell) {
        return (byte) ((cell & 0xF0) >>> 4);
    }

    // Extract the first operand type from the cell
    byte getOperandOneType(byte cell) {
        return (byte) ((cell & 0x0C) >> 2);
    }

    // Extract the second operand type from the cell
    byte getOperandTwoType(byte cell) {
        return (byte) (cell & 0x03);
    }

    // Execute the instruction based on the given cell and RAM
    void execute(byte[] cell, Ram ram) {

        byte instruction = getInstructionType(cell[0]);
        byte op1Type = getOperandOneType(cell[0]);
        byte op2Type = getOperandTwoType(cell[0]);

        byte storagePlaceIndex = cell[1];
        byte opOneValue = getValueOperand(op1Type, cell[1], ram, register);
        byte opTwoValue = getValueOperand(op2Type, cell[2], ram, register);

        if (instruction < 7) {
            // Perform arithmetic or logical operation
            byte res = switch (instruction) {
                case 0 -> {
                    initDA(opTwoValue);
                    yield opTwoValue;
                }
                case 1 -> add(opOneValue, opTwoValue);
                case 2 -> sub(opOneValue, opTwoValue);
                case 3 -> mul(opOneValue, opTwoValue);
                case 4 -> div(opOneValue, opTwoValue);
                case 5 -> or(opOneValue, opTwoValue);
                case 6 -> not(opOneValue);
                default -> -11;  // Invalid instruction
            };
            if (op1Type == 0) {
                register.setRegisterValue(storagePlaceIndex, res);  // Store result in a register

            } else if (op1Type == 2) {
                ram.setElementStack(storagePlaceIndex, res);   // Store result in RAM stack
            }
            register.defaultIncGH();   // Increment the instruction pointer
        } else {
            // Perform jump or comparison operation
            switch (instruction) {
                case 7: {
                    cmp(opOneValue, opTwoValue);
                    register.defaultIncGH();
                    break;
                }
                case 8:
                    JMP(opOneValue);
                    break;
                case 9:
                    JG(opOneValue);
                    break;
                case 10:
                    JL(opOneValue);
                    break;
                case 11:
                    JE(opOneValue);
                    break;
            }
        }
    }

    // Subtract two values and update the flags register (DA)
    private byte sub(byte a, byte b) {
        initDA(a - b);
        return (byte) (a - b);
    }

    // Add two values and update the flags register (DA)
    private byte add(byte a, byte b) {
        initDA(a + b);
        return (byte) (a + b);
    }

    // Multiply two values and update the flags register (DA)
    private byte mul(byte a, byte b) {
        initDA(a * b);
        return (byte) (a * b);
    }

    // Divide two values and update the flags register (DA)
    private byte div(byte a, byte b) {
        if (b == 0) {
            return -1;
        }
        initDA(a / b);
        return (byte) (a / b);
    }

    // Perform bitwise AND operation and update the flags register (DA)
    private byte and(byte a, byte b) {
        initDA(a & b);
        return (byte) (a & b);
    }

    // Perform bitwise OR operation and update the flags register (DA)
    private byte or(byte a, byte b) {
        initDA(a | b);
        return (byte) (a | b);
    }

    // Perform bitwise NOT operation and update the flags register (DA)
    private byte not(byte a) {
        initDA(~a);
        return (byte) ~a;
    }

    // Compare two values by subtracting them and update the flags register (DA)
    private void cmp(byte a, byte b) {
        sub(a, b);
    }

    // Jump to the specified index (update instruction pointer GH)
    private void JMP(byte index) {
        register.setGH(index);
    }

    // Jump if greater than (JG) condition is met
    private void JG(byte index) {
        if (register.getOF() == register.getSF() && register.getZF() == 0) {
            JMP(index);
        } else {
            register.defaultIncGH();
        }
    }

    // Jump if less than (JL) condition is met
    private void JL(byte index) {
        if (register.getOF() != register.getSF()) {
            JMP(index);
        } else {
            register.defaultIncGH();
        }
    }

    // Jump if equal (JE) condition is met
    private void JE(byte index) {
        if (register.getZF() == 1) {
            JMP(index);
        } else {
            register.defaultIncGH();
        }
    }

    // Initialize the flags register (DA) based on the given value
    private void initDA(int value) {
        if (value == 0) {
            register.setZF((byte) 1);
        } else {
            register.setZF((byte) 0);
        }

        if (value < 0) {
            register.setSF((byte) 1);
        } else {
            register.setSF((byte) 0);
        }

        if (value < -128 || value > 127) {
            register.setOF((byte) 1);
        } else {
            register.setOF((byte) 0);
        }
    }
}

