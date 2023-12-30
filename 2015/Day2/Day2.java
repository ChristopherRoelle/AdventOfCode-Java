/*
--- Day 2: I Was Told There Would Be No Math ---
The elves are running low on wrapping paper, and so they need to submit an order for more. They have a list of the dimensions (length l, width w, and height h) of each present, and only want to order exactly as much as they need.

Fortunately, every present is a box (a perfect right rectangular prism), which makes calculating the required wrapping paper for each gift a little easier: find the surface area of the box, which is 2*l*w + 2*w*h + 2*h*l. The elves also need a little extra paper for each present: the area of the smallest side.

For example:

A present with dimensions 2x3x4 requires 2*6 + 2*12 + 2*8 = 52 square feet of wrapping paper plus 6 square feet of slack, for a total of 58 square feet.
A present with dimensions 1x1x10 requires 2*1 + 2*10 + 2*10 = 42 square feet of wrapping paper plus 1 square foot of slack, for a total of 43 square feet.
All numbers in the elves' list are in feet. How many total square feet of wrapping paper should they order?

Your puzzle answer was 1586300.

--- Part Two ---
The elves are also running low on ribbon. Ribbon is all the same width, so they only have to worry about the length they need to order, which they would again like to be exact.

The ribbon required to wrap a present is the shortest distance around its sides, or the smallest perimeter of any one face. Each present also requires a bow made out of ribbon as well; the feet of ribbon required for the perfect bow is equal to the cubic feet of volume of the present. Don't ask how they tie the bow, though; they'll never tell.

For example:

A present with dimensions 2x3x4 requires 2+2+3+3 = 10 feet of ribbon to wrap the present plus 2*3*4 = 24 feet of ribbon for the bow, for a total of 34 feet.
A present with dimensions 1x1x10 requires 1+1+1+1 = 4 feet of ribbon to wrap the present plus 1*1*10 = 10 feet of ribbon for the bow, for a total of 14 feet.
How many total feet of ribbon should they order?

Your puzzle answer was 3737498.
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class Day2{

    public static void main(String args[]){

        String inputFilePath = "./input.txt";
        BufferedReader reader;

        int runningTotalWrapping = 0;
        int runningTotalRibbon = 0;

        try{
            reader = new BufferedReader(new FileReader(inputFilePath));
            String line = reader.readLine();

            while(line != null)
            {
                //Lines are lxwxh, separated with 'x'
                String[] contents = line.split("x");

                try{
                    int length = Integer.parseInt(contents[0]);
                    int width = Integer.parseInt(contents[1]);
                    int height = Integer.parseInt(contents[2]);

                    runningTotalWrapping += CalculateWrappingPaper(length, width, height);
                    runningTotalRibbon += CalculateRibbon(length, width, height);

                } catch (Exception parseError) {
                    parseError.printStackTrace();
                }

                line = reader.readLine();
            }

        } catch (IOException e){
            e.printStackTrace();
        }

        System.out.println("Total Sq Ft of Wrapping Paper Needed: " + runningTotalWrapping + " sqFt");
        System.out.println("Total Sq Ft of Ribbon Needed: " + runningTotalRibbon + " sqFt");

    }

    /**
     * Calculates total square footage of a present.
     * Then adds extra wrapping paper equal to the smallest side's square footage to the total.
     * @param length - Length dimension
     * @param width - Width dimension
     * @param height - Height dimension
     * @return - The total square footage plus the smallest side's square footage for extra.
     */
    public static int CalculateWrappingPaper(int length, int width, int height){

        int total = 0;
        int smallestSide = Integer.MAX_VALUE;
        int side[] = {0, 0, 0};

        //Calculate a front, side and top face
        side[0] = SideCalculator(length, width);
        side[1] = SideCalculator(width, height);
        side[2] = SideCalculator(height, length);

        //Combine the sides (2 of each, since rectangular prism)
        total = (side[0] * 2) + (side[1] * 2) + (side[2] * 2);

        //Determine what the smallest side was for the extra paper
        for(int i = 0; i < side.length; i++){

            //Set current side as smallest side if smaller than current stored value
            if(smallestSide > side[i])
                smallestSide = side[i];
        }

        //Add the smallest side to the total
        total += smallestSide;

        //Return the total
        return total;

    }

    /**
     * Calculates total square footage of ribbon needed.
     * Then adds extra ribbon paper equal to the cubic feet of the package.
     * @param length - Length dimension
     * @param width - Width dimension
     * @param height - Height dimension
     * @return - The shorest distance around its sides plus the cubic footage of the package.
     */
    public static int CalculateRibbon(int length, int width, int height){

        int dimensionsSorted[] = {length, width, height};
        Arrays.sort(dimensionsSorted);

        int totalRibbon = 0;

        //Calculate the ribbon to encompass the package
        totalRibbon = (dimensionsSorted[0] * 2) + (dimensionsSorted[1] * 2);

        //Add on the cubic feet of the package
        totalRibbon += (length * width * height);

        return totalRibbon;
    }

    /**
     * Calculates the square footage of a side.
     * @param measure1 - First dimension
     * @param measure2 - Second dimension
     * @return - measure1 * measure2
     */
    public static int SideCalculator(int measure1, int measure2){

        int sideTotal = 0;

        //Determine square feet of side
        sideTotal = measure1 * measure2;

        return sideTotal;
    }

}