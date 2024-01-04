/*
--- Day 6: Probably a Fire Hazard ---
--- Part Two ---
You just finish implementing your winning light pattern when you realize you mistranslated Santa's message from Ancient Nordic Elvish.

The light grid you bought actually has individual brightness controls; each light can have a brightness of zero or more. The lights all start at zero.

The phrase turn on actually means that you should increase the brightness of those lights by 1.

The phrase turn off actually means that you should decrease the brightness of those lights by 1, to a minimum of zero.

The phrase toggle actually means that you should increase the brightness of those lights by 2.

What is the total brightness of all lights combined after following Santa's instructions?

For example:

turn on 0,0 through 0,0 would increase the total brightness by 1.
toggle 0,0 through 999,999 would increase the total brightness by 2000000.
Your puzzle answer was 14687245.
 */

import java.io.BufferedReader;
import java.io.FileReader;

public class Day6Part2{

    static int lightArrayWidth = 1000;
    static int lightArrayHeight = 1000;
    static int[][] lightArray;

    public static void main(String args[])
    {
        String input = "./input.txt";

        GenerateLightArray();
        //PrintLightArray();

        ProcessLightInstructions(input);

        int totalBrightness = CountTotalBrightness();

        System.out.println("Total Brightness of the Lights: " + totalBrightness);
    }

    /**
     * Generates the light array.
     */
    private static void GenerateLightArray(){
        lightArray = new int[lightArrayWidth][lightArrayHeight];
    }

    /**
     * Prints the light array to console.
     */
    private static void PrintLightArray(){
        for(int x = 0; x < lightArrayWidth; x++){

            for(int y = 0; y < lightArrayHeight; y++){
                System.out.print(lightArray[x][y]);
            }
            System.out.print("\n");
        }
    }

    private static int CountTotalBrightness(){
        int totalBrightness = 0;

        for(int x = 0; x < lightArrayWidth; x++){
            for(int y = 0; y < lightArrayHeight; y++){
                    totalBrightness += lightArray[x][y];
            }
        }

        return totalBrightness;
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
     * Increase light brightness by 2 for all lights in range.
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
                lightArray[x][y] += 2;
            }
        }
    }

    /**
     * Decrease brightness of all lights in range by 1
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
                lightArray[x][y]--;

                if(lightArray[x][y] < 0){
                    lightArray[x][y] = 0;
                }
            }
        }
    }

    /**
     * Incease brightness of all lights in range by 1
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
                lightArray[x][y]++;
            }
        }
    }

}