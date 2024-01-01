/*
 --- Day 3: Perfectly Spherical Houses in a Vacuum ---
Santa is delivering presents to an infinite two-dimensional grid of houses.

He begins by delivering a present to the house at his starting location, and then an elf at the North Pole calls him via radio and tells him where to move next. Moves are always exactly one house to the north (^), south (v), east (>), or west (<). After each move, he delivers another present to the house at his new location.

However, the elf back at the north pole has had a little too much eggnog, and so his directions are a little off, and Santa ends up visiting some houses more than once. How many houses receive at least one present?

For example:

> delivers presents to 2 houses: one at the starting location, and one to the east.
^>v< delivers presents to 4 houses in a square, including twice to the house at his starting/ending location.
^v^v^v^v^v delivers a bunch of presents to some very lucky children at only 2 houses.
Your puzzle answer was 2572.

--- Part Two ---
The next year, to speed up the process, Santa creates a robot version of himself, Robo-Santa, to deliver presents with him.

Santa and Robo-Santa start at the same location (delivering two presents to the same starting house), then take turns moving based on instructions from the elf, who is eggnoggedly reading from the same script as the previous year.

This year, how many houses receive at least one present?

For example:

^v delivers presents to 3 houses, because Santa goes north, and then Robo-Santa goes south.
^>v< now delivers presents to 3 houses, and Santa and Robo-Santa end up back where they started.
^v^v^v^v^v now delivers presents to 11 houses, with Santa going one direction and Robo-Santa going the other.
Your puzzle answer was 2631.
 */

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

public class Day3{

    public static void main(String[] args){
        ReadInstructions();
    }

    /**
     * Reads the instructions and handles moving Santa and Robo-Santa
     */
    public static void ReadInstructions(){

        String pathToInstructions = "./input.txt";
        String instructions = "";

        Set<String> visitedHouses = new HashSet<>();
        int santaX = 0;
        int santaY = 0;
        int roboSantaX = 0;
        int roboSantaY = 0;

        Path instructionsFile = Paths.get(pathToInstructions);


        try{
            instructions = Files.readAllLines(instructionsFile).get(0);
        }
        catch(Exception e){
            System.out.println(e.getStackTrace());
            return;
        }

        if(instructions != null && instructions != ""){

            //Add origin house
            visitedHouses.add(santaX + ", " + santaY);

            //Parse the instructions
            //Santa goes first, then robo santa.
            for(int c = 0; c < instructions.length(); c++){

                System.out.println(santaX + ", " + santaY);

                char dir = instructions.charAt(c);

                //Santa
                if(c % 2 == 0)
                {
                    int[] newPos = Move(santaX, santaY, dir);
                    santaX = newPos[0];
                    santaY = newPos[1];
                    visitedHouses.add(santaX + ", " + santaY);
                }
                else //Robo Santa
                {
                    int[] newPos = Move(roboSantaX, roboSantaY, dir);
                    roboSantaX = newPos[0];
                    roboSantaY = newPos[1];
                    visitedHouses.add(roboSantaX + ", " + roboSantaY);
                }          
            }

            //Print the size of the hashSet, this only contains uniques.
            System.out.println("Houses with at least one present: " + visitedHouses.size());
            return;

        }

    }


    /**
     * Handles parsing the direction to move Santa/Robo-Santa
     * @param x - Current X Position
     * @param y - Current Y Position
     * @param direction - Character code of the direction
     * @return - Returns an X,Y array of the new position.
     */
    private static int[] Move(int x, int y, char direction){
        switch (direction) {
            case '^': //North
                y++;
                break;
            case '>': //East
                x++;
                break;
            case 'v': //South
                y--;
                break;
            case '<': //West
                x--;
                break;
        
            default:
                break;
        }

        int[] retVal = {x, y};

        return retVal;
    }

}