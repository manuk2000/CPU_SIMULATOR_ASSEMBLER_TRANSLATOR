package compiler;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Compiler {
    private int CELL_SIZE = 3;
    private List<String> code;
    private byte[] instractions;
    private int instructionIndex = 0;
    private MapingSection maping;


    public Compiler(String codePath) {
        this.code = readeCodeOfFile(codePath);
        instractions = new byte[code.size() * 3];
        maping = new MapingSection();
    }

    // Read the code from a file and store it in a list of strings
    private List<String> readeCodeOfFile(String codePath) {
        List<String> lineCode = new ArrayList<>();
        File file = new File(codePath);
        if (!file.canRead())
            throw new IllegalArgumentException("Not found file of file path: " + codePath);
        try (Scanner code = new Scanner(file);) {
            while (code.hasNextLine()) {
                lineCode.add(code.nextLine());
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return lineCode;
    }

    // Tokenize each line of code by splitting on whitespace, colons, and commas
    private List<List<String>> tokenising() {
        List<List<String>> splitCode = new ArrayList<>();
        String regex = "[,:\\s]+";
        for (String s : code) {
            splitCode.add(Arrays.asList(s.split(regex)));
        }
        return splitCode;
    }

    // Generate a mapping of labels to their corresponding indices in the instructions array
    public Map<String, Integer> generateLableMap(List<List<String>> lineTokens) {
        Map<String, Integer> lables = new HashMap<>();

        for (int i = 0; i < lineTokens.size(); i++) {
            if (lineTokens.get(i).size() == 4) {
                lables.put(lineTokens.get(i).get(0), i * 3);
                lineTokens.set(i, Arrays.asList(lineTokens.get(i).get(1), lineTokens.get(i).get(2), lineTokens.get(i).get(3)));
            }
        }
        return lables;
    }


    // Left-shift the instruction, operand1, and operand2 to form a single byte
    private byte instractionLeftShift(int instruction, int operand1, int operand2) {
        int tmp = instruction << 4;
        tmp |= (operand1 << 2);
        tmp |= operand2;
        return (byte) tmp;
    }

    //reg     -> 0
    //lable   -> 1
    //[]      -> 2
    //literal -> 3

    // Detect the type of operand (register, label, array index, or literal)
    // and return its corresponding index or value
    private int operandDetector(String operator, Map<String, Integer> lables) {
        if (maping.getRegistrIndex(operator) != null) {
            return 0;
        } else if (lables.get(operator) != null) {
            return 1;
        } else if (operator.charAt(0) == '[') {
            return 2;
        }
        return 3;
    }

    // Determine the index of the operand based on its type
    private int operandIndex(String opt, Map<String, Integer> lables) {
        int operandType = operandDetector(opt, lables);
        return switch (operandType) {
            case 0 -> maping.getRegistrIndex(opt);
            case 1 -> lables.get(opt);
            case 2 -> Integer.parseInt(opt.substring(1, opt.length() - 1));
            case 3 -> Integer.parseInt(opt);
            default -> throw new IllegalStateException("Unexpected value: " + operandType);
        };
    }

    // Convert the instruction cell to machine code and add it to the instructions array
    private void instructionToMachineCode(byte[] cell) {
        for (int i = 0; i < 3; i++) {
            instractions[instructionIndex++] = cell[i];
        }
    }

    // Create the instruction cell and convert it to machine code
    private byte creatingInstractionCell(List<String> line, Map<String, Integer> lables) {
        int res = 0;
        int typeOpt1;
        int typeOpt2;
        if (line.size() == 3) {
            String s = line.get(0);
            int instructionIndex = maping.getInstractionIndex(line.get(0).toUpperCase());
            typeOpt1 = operandDetector(line.get(1), lables);
            typeOpt2 = operandDetector(line.get(2), lables);
            res = instractionLeftShift(instructionIndex, typeOpt1, typeOpt2);
        } else if (line.size() == 2) {
            int instructionIndex = maping.getInstractionIndex(line.get(0).toUpperCase());
            typeOpt1 = operandDetector(line.get(1), lables);
            res = instractionLeftShift(instructionIndex, typeOpt1, 0);
        }
        return (byte) res;
    }

    // Generate the machine code by converting each line of code to instructions
    private void generateMachinCode(Map<String, Integer> lables, List<List<String>> codeLine) {

        String opt;
        for (List<String> line : codeLine) {
            byte[] cell = new byte[3];

            cell[0] = creatingInstractionCell(line, lables);

            opt = line.get(1);
            cell[1] = (byte) operandIndex(opt, lables);

            if (line.size() == 3)
                opt = line.get(2);
            else {
                opt = "0";
            }
            cell[2] = (byte) operandIndex(opt, lables);

            instructionToMachineCode(cell);
        }
    }

    public byte[] toCompiler() {
        List<List<String>> lineTokens = tokenising();  // Tokenize the lines of code
        Map<String, Integer> lables = generateLableMap(lineTokens);      // Generate a mapping of labels to their corresponding instruction indices
        generateMachinCode(lables, lineTokens);         // Generate the machine code instructions
        return instractions;                            // Return the compiled instructions
    }

}
