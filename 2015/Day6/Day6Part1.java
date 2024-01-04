/*
--- Day 6: Probably a Fire Hazard ---
--- Part One ---
Because your neighbors keep defeating you in the holiday house decorating contest year after year, you've decided to deploy one million lights in a 1000x1000 grid.

Furthermore, because you've been especially nice this year, Santa has mailed you instructions on how to display the ideal lighting configuration.

Lights in your grid are numbered from 0 to 999 in each direction; the lights at each corner are at 0,0, 0,999, 999,999, and 999,0. The instructions include whether to turn on, turn off, or toggle various inclusive ranges given as coordinate pairs. Each coordinate pair represents opposite corners of a rectangle, inclusive; a coordinate pair like 0,0 through 2,2 therefore refers to 9 lights in a 3x3 square. The lights all start turned off.

To defeat your neighbors this year, all you have to do is set up your lights by doing the instructions Santa sent you in order.

For example:

turn on 0,0 through 999,999 would turn on (or leave on) every light.
toggle 0,0 through 999,0 would toggle the first line of 1000 lights, turning off the ones that were on, and turning on the ones that were off.
turn off 499,499 through 500,500 would turn off (or leave off) the middle four lights.
After following the instructions, how many lights are lit?

Your puzzle answer was 543903.
 */

import java.io.BufferedReader;
import java.io.FileReader;

public class Day6Part1{

    static int lightArrayWidth = 1000;
    static int lightArrayHeight = 1000;
    static boolean[][] lightArray;

    public static void main(String args[])
    {
        String input = "./input.txt";

        GenerateLightArray();
        //PrintLightArray();

        ProcessLightInstructions(input);

        int litLights = CountOnLights();

        System.out.println("Number of lit lights: " + litLights);
    }

    /**
     * Generates the light array.
     */
    private static void GenerateLightArray(){
        lightArray = new boolean[lightArrayWidth][lightArrayHeight];
    }

    /**
     * Prints the light array to console.
     */
    private static void PrintLightArray(){
        for(int x = 0; x < lightArrayWidth; x++){

            for(int y = 0; y < lightArrayHeight; y++){
                System.out.print(lightArray[x][y] ? 1 : 0);
            }
            System.out.print("\n");
        }
    }

    private static int CountOnLights(){
        int onCount = 0;

        for(int x = 0; x < lightArrayWidth; x++){
            for(int y = 0; y < lightArrayHeight; y++){
                if(lightArray[x][y])
                    onCount++;
            }
        }

        return onCount;
    }

    /**
     * Reads the input file and performs the actions.
     * @param input - Input Instructions path
     */
    private static void ProcessLightInstructions(String input){

        try{
            BufferedReader reader = new BufferedReader(new FileReader(input));
            String line = reader.readLine();

            while(line != null)
            {
                ParseInstruction(line); 
                line = reader.readLine();
            }

            reader.close();
        }
        catch (Exception e){
            e.printStackTrace();
            return;
        }
    }

    /**
     * Parses the instruction line.
     * @param instruction - Instruction line
     */
    private static void ParseInstruction(String instruction){

        String[] instructions = instruction.split(" ");
        String operation;
        String[] lowLightIndexes;

        if(instructions[0].equals("turn")){
            operation = instructions[0] + " " + instructions[1];
            lowLightIndexes = instructions[2].split(",");
        } else {
            operation = instructions[0];
            lowLightIndexes = instructions[1].split(",");
        }

        //System.out.println(instructions[0]);
        //System.out.println(operation);

        String[] highLightIndexes = instructions[instructions.length - 1].split(",");

        int lowLightX  = 0;
        int lowLightY  = 0;
        int highLightX = 0;
        int highLightY = 0;

        //Parse the light indexes
        try{
            lowLightX = Integer.parseInt(lowLightIndexes[0]);
            lowLightY = Integer.parseInt(lowLightIndexes[1]);
            highLightX = Integer.parseInt(highLightIndexes[0]);
            highLightY = Integer.parseInt(highLightIndexes[1]);
        } catch (Exception e){
            e.printStackTrace();
            return;
        }

        //Perform operation
        switch (operation) {
            case "toggle":
                //System.out.println("TOGGLING");
                Toggle(lowLightX, lowLightY, highLightX, highLightY);
                break;

            case "turn off":
                //System.out.println("TURNING OFF");
                TurnOff(lowLightX, lowLightY, highLightX, highLightY);
                break;

            case "turn on":
                //System.out.println("TURNING ON");
                TurnOn(lowLightX, lowLightY, highLightX, highLightY);
                break;

            default:
                System.out.println("ERROR::INVALID OPERATION CODE!");
                System.exit(1);
                break;
        } 
    }

    /**
     * Toggles the in-range light's value
     * @param lowX - low X index
     * @param lowY - low y index
     * @param highX - high x index
     * @param highY - high y index
     */
    private static void Toggle(int lowX, int lowY, int highX, int highY)
    {
        //Loop the light array, starting at low up to the high
        for(int x = lowX; x <= highX; x++){
            for(int y = lowY; y <= highY; y++){
                //Flip the bit
                lightArray[x][y] = !lightArray[x][y];
            }
        }
    }

    /**
     * Sets the lights in the range to off
     * @param lowX - low X index
     * @param lowY - low y index
     * @param highX - high x index
     * @param highY - high y index
     */
    private static void TurnOff(int lowX, int lowY, int highX, int highY)
    {
        //Loop the light array, starting at low up to the high
        for(int x = lowX; x <= highX; x++){
            for(int y = lowY; y <= highY; y++){
                //Set to Off
                lightArray[x][y] = false;
            }
        }
    }

    /**
     * Sets the lights in the range to on
     * @param lowX - low X index
     * @param lowY - low y index
     * @param highX - high x index
     * @param highY - high y index
     */
    private static void TurnOn(int lowX, int lowY, int highX, int highY)
    {
        //Loop the light array, starting at low up to the high
        for(int x = lowX; x <= highX; x++){
            for(int y = lowY; y <= highY; y++){
                //Set to On
                lightArray[x][y] = true;
            }
        }
    }

}