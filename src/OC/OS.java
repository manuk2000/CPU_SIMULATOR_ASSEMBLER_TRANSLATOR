package OC;
import compiler.Compiler;
import hardware.Cpu;
import hardware.Ram;
import hardware.Register;

public class OS {
    private  String filePath;
    public void execute(String filePath) {
        Compiler compiler = new Compiler(filePath);
        byte[] machineCode = compiler.toCompiler();
        Ram ram = new Ram(machineCode);
        Cpu cpu = new Cpu(ram);
        cpu.executeCpu();
    }
}
