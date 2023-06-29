package hardware;


public class Register {
    private final byte REG_COUNT = 5;

    private  byte registerGH; //EIP
    private byte registerDA;   //EFLAGS

    private byte[] registersMemory;

    public Register () {
        registersMemory  = new byte[REG_COUNT];
    }


    public byte  getRegisterValue (int index) {
        return registersMemory[index];
    }

    public void setRegisterValue (int index, byte value) {
        registersMemory[index] = value;
    }

    public void defaultIncGH() {
        registerGH += 3;
    }

    public void setGH(byte numberLine) {
        registerGH = numberLine;
    }

    public byte getRegisterGH() {
        return registerGH;
    }

    void setZF(byte value) {     // firstIndex of DA register
        if (value == 1) {
            registerDA |= 1; // binary 00000001
        }else {
            registerDA &= 0xFE;
        }
    }

    void setSF(byte value) {     // second Index of DA register
        if (value == 1) {
            registerDA |= 2; // binary 00000010
        }else {
            registerDA &= 0xFD;  //11111101
        }
    }

    void setOF(byte value) {     // third Index of DA register
        if (value == 1) {
            registerDA |= 4;// binary 00000100
        }else {
            registerDA &= 0xFB;   //11111011
        }
    }

    public byte getZF() {
        return (byte)(registerDA & 1);
    }

    public byte getSF() {
        return (byte)((registerDA>>1) & 1);
    }

    public byte getOF() {
        return (byte)((registerDA>>2) & 1);
    }

    public void dump_register() {
        for (byte b : registersMemory) {
            System.out.println(b);
        }
    }


}
