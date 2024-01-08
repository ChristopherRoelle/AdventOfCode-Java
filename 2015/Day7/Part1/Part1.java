package Part1;
/**
--- Day 7: Some Assembly Required ---
This year, Santa brought little Bobby Tables a set of wires and bitwise logic gates! Unfortunately, little Bobby is a little under the recommended age range, and he needs help assembling the circuit.

Each wire has an identifier (some lowercase letters) and can carry a 16-bit signal (a number from 0 to 65535). A signal is provided to each wire by a gate, another wire, or some specific value. Each wire can only get a signal from one source, but can provide its signal to multiple destinations. A gate provides no signal until all of its inputs have a signal.

The included instructions booklet describes how to connect the parts together: x AND y -> z means to connect wires x and y to an AND gate, and then connect its output to wire z.

For example:

123 -> x means that the signal 123 is provided to wire x.
x AND y -> z means that the bitwise AND of wire x and wire y is provided to wire z.
p LSHIFT 2 -> q means that the value from wire p is left-shifted by 2 and then provided to wire q.
NOT e -> f means that the bitwise complement of the value from wire e is provided to wire f.
Other possible gates include OR (bitwise OR) and RSHIFT (right-shift). If, for some reason, you'd like to emulate the circuit instead, almost all programming languages (for example, C, JavaScript, or Python) provide operators for these gates.

For example, here is a simple circuit:

123 -> x
456 -> y
x AND y -> d
x OR y -> e
x LSHIFT 2 -> f
y RSHIFT 2 -> g
NOT x -> h
NOT y -> i
After it is run, these are the signals on the wires:

d: 72
e: 507
f: 492
g: 114
h: 65412
i: 65079
x: 123
y: 456
In little Bobby's kit's instructions booklet (provided as your puzzle input), what signal is ultimately provided to wire a?

Your puzzle answer was 3176.
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import java.util.List;

public class Part1{

    private static Map<String, Integer> wireMap = new HashMap<>();
    private static List<Instruction> instructionsList = new ArrayList<Instruction>();
    private static List<Instruction> processedInstructionsList = new ArrayList<Instruction>();

    public static void main(String[] args){

        String input = "./input.txt";

        ReadInstructions(input);
        IterateInstructionsList();

        PrintMap();

        System.out.println();
        System.out.println("Wire A: " + wireMap.get("a"));

    }

    /**
     * Prints the known wire map
     */
    private static void PrintMap(){

        System.out.println();
        System.out.println("WR  |  Value");
        for (Map.Entry<String, Integer> entry : wireMap.entrySet()) {
            System.out.println(entry.getKey() + "  |  " + entry.getValue());
        }

    }

    /**
     * Prints the instructions in the given list<Instruction>
     * @param list - The list to print. Must be list<Instruction>
     */
    private static void PrintInstructionsList(List<Instruction> list){

        System.out.println();

        for (Instruction instruction : list) {
            
            System.out.println("InputA: " + instruction.inputA);
            System.out.println("InputB: " + instruction.inputB);
            System.out.println("Type: " + instruction.operationType);
            System.out.println("Output: " + instruction.outputWire);
            System.out.println("Processed: " + instruction.processed);
            System.out.println();

        }

    }

    /**
     * Reads the instructions into the Instruction Object and appends them to the Instruction List.
     * @param inputPath - Path to read the instructions from.
     */
    private static void ReadInstructions(String inputPath){

        try{
            BufferedReader reader = new BufferedReader(new FileReader(inputPath));

            String line = reader.readLine();

            //Parse the instructions to the List
            while(line != null){

                //Do Something
                Instruction curInstruction = new Instruction(line);
                instructionsList.add(curInstruction);

                line = reader.readLine();
            }

            reader.close();
        }
        catch(Exception e){
            e.printStackTrace();
            return;
        }
    }

    /**
     * Iterates over the instructionList until there are no records left.
     * If an instruction is processed, the instruction is added to the 
     * processedInstructions list. At the end of the loop, we clear all records
     * from the instructionList that have moved to the processedList.
     */
    private static void IterateInstructionsList(){
        while(instructionsList.size() > 0)
        {
            //Process the instruction list
            for (Instruction instruction : instructionsList) {
                ProcessInstructions(instruction);
            }
            //Remove all processed
            instructionsList.removeAll(processedInstructionsList);
        }
    }

    /**
     * Reads in an instruction object.
     * If the instruction was processed and for some reason wasnt pushed to the other list, we add it, 
     * so that the instruction can be cleared from this list.
     * For all others, the instruction must be used on valid inputWires if used, otherwise the instruction is
     * skipped for now.
     * As a valid instruction is processed, the outputWire will be added to the mapping, which can then be used as
     * a valid input, allowing more instructions to be valid.
     * The instruction will have its processed flag set (to prevent accidental refires on the instruction).
     * The instruction will be copied to the processedInstruction list, where at the end of looping, will have it cleared
     * from the instructionList.
     * @param instruction - Instruction Object
     */
    private static void ProcessInstructions(Instruction instruction){

        //Instruction was already processed, skip.
        if(instruction.processed){
            processedInstructionsList.add(instruction);
            return;
        }

        //SHOULD BE UPDATED TO USE A SWITCH FOR PERFORMANCE

        //Highest Precedence, add the output wire to the list, set the numeric to its output signal
        if(instruction.operationType == OperationType.NUM_SET){
            wireMap.put(instruction.outputWire, Integer.parseInt(instruction.inputA));
            instruction.processed = true;
            processedInstructionsList.add(instruction);
            return;
        }

        //SET - Check if the inputA exists in the wiremap, if not, skip this op
        if(instruction.operationType == OperationType.SET){
            if(wireMap.containsKey(instruction.inputA)){
                int signalValue = wireMap.get(instruction.inputA);
                wireMap.put(instruction.outputWire, signalValue);
                instruction.processed = true;
                processedInstructionsList.add(instruction);
                return;
            } else {
                return;
            }
        }

        //NOT - Check if inputA exists in the wiremap, if not, skip this op
        if(instruction.operationType == OperationType.NOT){
            if(wireMap.containsKey(instruction.inputA)){
                int signalValue = LogicNOT(wireMap.get(instruction.inputA));
                wireMap.put(instruction.outputWire, signalValue);
                instruction.processed = true;
                processedInstructionsList.add(instruction);
                return;
            } else {
                return;
            }
        }

        //OR - Check both inputs exist in the wiremap, if not, skip this op
        //inputA may be a digit
        if(instruction.operationType == OperationType.OR){
            if((wireMap.containsKey(instruction.inputA) || instruction.inputA.matches("\\d+")) && wireMap.containsKey(instruction.inputB)){
                int signalA = wireMap.containsKey(instruction.inputA) ? wireMap.get(instruction.inputA) : Integer.parseInt(instruction.inputA);
                int signalB = wireMap.get(instruction.inputB);
                int signalValue = LogicOR(signalA, signalB);
                wireMap.put(instruction.outputWire, signalValue);
                instruction.processed = true;
                processedInstructionsList.add(instruction);
                return;
            } else {
                return;
            }
        }

        //AND - Check both inputs exist in the wiremap, if not, skip this op
        //inputA may be a digit
        if(instruction.operationType == OperationType.AND){
            if((wireMap.containsKey(instruction.inputA) || instruction.inputA.matches("\\d+")) && wireMap.containsKey(instruction.inputB)){
                int signalA = wireMap.containsKey(instruction.inputA) ? wireMap.get(instruction.inputA) : Integer.parseInt(instruction.inputA);
                int signalB = wireMap.get(instruction.inputB);
                int signalValue = LogicAND(signalA, signalB);
                wireMap.put(instruction.outputWire, signalValue);
                instruction.processed = true;
                processedInstructionsList.add(instruction);
                return;
            } else {
                return;
            }
        }

        //RSHIFT - Check both inputA exist in the wiremap, if not, skip this op
        if(instruction.operationType == OperationType.RSHIFT){
            if(wireMap.containsKey(instruction.inputA)){
                int signalA = wireMap.get(instruction.inputA);
                int shiftAmount = Integer.parseInt(instruction.inputB);
                int signalValue = LogicRSHIFT(signalA, shiftAmount);
                wireMap.put(instruction.outputWire, signalValue);
                instruction.processed = true;
                processedInstructionsList.add(instruction);
                return;
            } else {
                return;
            }
        }

        //LSHIFT - Check both inputA exist in the wiremap, if not, skip this op
        if(instruction.operationType == OperationType.LSHIFT){
            if(wireMap.containsKey(instruction.inputA)){
                int signalA = wireMap.get(instruction.inputA);
                int shiftAmount = Integer.parseInt(instruction.inputB);
                int signalValue = LogicLSHIFT(signalA, shiftAmount);
                wireMap.put(instruction.outputWire, signalValue);
                instruction.processed = true;
                processedInstructionsList.add(instruction);
                return;
            } else {
                return;
            }
        }
    }

    /**
     * Returns ~inputSignal (16-bytes)
     * @param inputSignal - Signal value
     * @return - 16-bytes of the complement of inputSignal.
     */
    private static int LogicNOT(int inputSignal){
        return (~inputSignal) & 0xFFFF;
    }

    /**
     * Returns bitwise OR of inputSignal A and B
     * @param inputSignalA - first signal
     * @param inputSignalB - second signal
     * @return - inputSignalA | inputSignalB
     */
    private static int LogicOR(int inputSignalA, int inputSignalB){
        return (inputSignalA | inputSignalB);
    }

    /**
     * Returns bitwise AND of inputSignal A and B
     * @param inputSignalA - First Signal
     * @param inputSignalB - Second Signal
     * @return - inputSignalA & inputSignalB
     */
    private static int LogicAND(int inputSignalA, int inputSignalB){
        return (inputSignalA & inputSignalB);
    }

    /**
     * Returns a Right Bitshift on inputSignal
     * @param inputSignalA - Signal
     * @param shiftAmount - Number of bits to shift.
     * @return - inputSignalA >> shiftAmount
     */
    private static int LogicRSHIFT(int inputSignalA, int shiftAmount){
        return (inputSignalA >> shiftAmount);
    }

    /**
     * Returns a Left Bitshift on inputSignal.
     * @param inputSignalA - Signal
     * @param shiftAmount - Number of bits to shift
     * @return - inputSignalA << shiftAmount
     */
    private static int LogicLSHIFT(int inputSignalA, int shiftAmount){
        return (inputSignalA << shiftAmount);
    }
}

enum OperationType {
    NUM_SET, //Highest Precedence. Setting to exact value
    SET, //Sets a wire directly to another's value
    NOT, //Negates the value
    OR, //Bitwise OR
    AND, //Bitwise AND
    RSHIFT, //Shift Bits Right
    LSHIFT //Shift Bits Left
};

class Instruction{
        
    String inputA;
    String inputB;
    String outputWire;
    OperationType operationType;

    boolean processed = false;

    public Instruction(String line){

        //Split the expression from the output
        String[] expression = line.split(" -> ");
        outputWire = expression[1];

        //Split the expression
        String tokens[] = expression[0].split(" ");

        //PARSE OPS

        //SET/NUM_SET
        //Set is just size 1, set the token to inputA, leave B as null
        if(tokens.length == 1){
            //Set the input
            inputA = tokens[0];
            
            //Check if the token is a number
            if(inputA.matches("\\d+")){
                operationType = OperationType.NUM_SET;
            } else {
                operationType = OperationType.SET;
            }

            return;
        }

        //NOT
        //2 tokens, first is NOT, second is an input
        if(tokens.length == 2 && tokens[0].equals("NOT")){
            inputA = tokens[1];
            operationType = OperationType.NOT;
            return;
        }

        //OR, AND, R/LShift
        //3 tokens, inputA, op, inputB
        if(tokens.length == 3){
            inputA = tokens[0];
            inputB = tokens[2];

            switch(tokens[1]){

                case "OR":
                    operationType = OperationType.OR;
                    break;
                case "AND":
                    operationType = OperationType.AND;
                    break;
                case "RSHIFT":
                    operationType = OperationType.RSHIFT;
                    break;
                case "LSHIFT":
                    operationType = OperationType.LSHIFT;
                    break;
                default:
                    System.out.println("INVALID OP CODE!!");
                    break;

            }
            return;
        }

    }

}