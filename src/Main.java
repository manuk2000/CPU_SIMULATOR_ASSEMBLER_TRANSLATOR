import OC.OS;

public class Main {
    public static void main(String[] args) {
        OS os = new OS();   // Create an instance of the OS class
        os.execute("src/code.txt");  // Execute the OS by providing the file path of the source code
    }
}