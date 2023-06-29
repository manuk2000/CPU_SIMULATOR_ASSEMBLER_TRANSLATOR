import OC.OS;
import hardware.Cpu;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello");
        OS os = new OS();
        os.execute("/home/levon/IdeaProjects/CPU_SIMULATOR/src/code.txt");
    }
}