/*
--- Day 1: Not Quite Lisp ---
Santa was hoping for a white Christmas, but his weather machine's "snow" function is powered by stars, and he's fresh out! To save Christmas, he needs you to collect fifty stars by December 25th.

Collect stars by helping Santa solve puzzles. Two puzzles will be made available on each day in the Advent calendar; the second puzzle is unlocked when you complete the first. Each puzzle grants one star. Good luck!

Here's an easy puzzle to warm you up.

Santa is trying to deliver presents in a large apartment building, but he can't find the right floor - the directions he got are a little confusing. He starts on the ground floor (floor 0) and then follows the instructions one character at a time.

An opening parenthesis, (, means he should go up one floor, and a closing parenthesis, ), means he should go down one floor.

The apartment building is very tall, and the basement is very deep; he will never find the top or bottom floors.

For example:

(()) and ()() both result in floor 0.
((( and (()(()( both result in floor 3.
))((((( also results in floor 3.
()) and ))( both result in floor -1 (the first basement level).
))) and )())()) both result in floor -3.
To what floor do the instructions take Santa?

Your puzzle answer was 74.

--- Part Two ---
Now, given the same instructions, find the position of the first character that causes him to enter the basement (floor -1). The first character in the instructions has position 1, the second character has position 2, and so on.

For example:

) causes him to enter the basement at character position 1.
()()) causes him to enter the basement at character position 5.
What is the position of the character that causes Santa to first enter the basement?

Your puzzle answer was 1795.
*/

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class NotQuiteLisp {

    public static void main(String args[])
    {
        try {
            System.out.println("Floor: " + InstructionReader("./instructions.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int InstructionReader(String instructionsPath) throws IOException
    {
        int floorNumber = 0;
        String instructions;

        Path pathToFile = Paths.get(instructionsPath);
        

        try {
            instructions = Files.readAllLines(pathToFile).get(0);
        } catch (Exception e) {
            System.out.println("ERROR READING FILE!");
            return -1;
        }
        
        if(instructions != null || instructions == ""){

            //Parse the instructions
            for(int c = 0; c < instructions.length(); c++){

                if(instructions.charAt(c) == '('){
                    floorNumber++;
                } else if(instructions.charAt(c) == ')'){
                    floorNumber--;
                }

                //Part 2: Find position of the first character that causes him to enter the basement (floor -1)
                if(floorNumber == -1){
                    System.out.println("Entered the basement on instruction number: " + (c + 1));
                }
            }
        } 
        else 
        {
            System.out.println("NO VALID INSTRUCTIONS!");
            return -1;
        }

        return floorNumber;
    }
    
}
