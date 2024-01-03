/*
--- Day 5: Doesn't He Have Intern-Elves For This? ---
Santa needs help figuring out which strings in his text file are naughty or nice.

A nice string is one with all of the following properties:

It contains at least three vowels (aeiou only), like aei, xazegov, or aeiouaeiouaeiou.
It contains at least one letter that appears twice in a row, like xx, abcdde (dd), or aabbccdd (aa, bb, cc, or dd).
It does not contain the strings ab, cd, pq, or xy, even if they are part of one of the other requirements.
For example:

ugknbfddgicrmopn is nice because it has at least three vowels (u...i...o...), a double letter (...dd...), and none of the disallowed substrings.
aaa is nice because it has at least three vowels and a double letter, even though the letters used by different rules overlap.
jchzalrnumimnmhp is naughty because it has no double letter.
haegwjzuvuyypxyu is naughty because it contains the string xy.
dvszwmarrgswjxmb is naughty because it contains only one vowel.
How many strings are nice?

Your puzzle answer was 236.

--- Part Two ---
Realizing the error of his ways, Santa has switched to a better model of determining whether a string is naughty or nice. None of the old rules apply, as they are all clearly ridiculous.

Now, a nice string is one with all of the following properties:

It contains a pair of any two letters that appears at least twice in the string without overlapping, like xyxy (xy) or aabcdefgaa (aa), but not like aaa (aa, but it overlaps).
It contains at least one letter which repeats with exactly one letter between them, like xyx, abcdefeghi (efe), or even aaa.
For example:

qjhvhtzxzqqjkmpb is nice because is has a pair that appears twice (qj) and a letter that repeats with exactly one letter between them (zxz).
xxyxx is nice because it has a pair that appears twice and a letter that repeats with one between, even though the letters used by each rule overlap.
uurcxstgmygtbstg is naughty because it has a pair (tg) but no repeat with a single letter between them.
ieodomkazucvgmuy is naughty because it has a repeating letter with one between (odo), but no pair that appears twice.
How many strings are nice under these new rules?

Your puzzle answer was 51.
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.regex.Pattern;

public class Day5{

    public static void main(String[] args){

        String inputPath = "./input.txt";

        int niceCount = NaughtyNiceParser(inputPath);

        System.out.println("Total Nice Strings: " + niceCount);

    }

    /**
     * Reads the input file and checks if Naughty or Nice
     * @param input - String path for input file (Santa's List)
     * @return - The Nice Count
     */
    private static int NaughtyNiceParser(String input){

        int niceCount = 0;
        BufferedReader reader;

        //Regex
        //Part 1
        Pattern vowelsPattern = Pattern.compile(".*[aeiou].*[aeiou].*[aeiou].*"); //Matches any num of characters, but expects 3 instances of either aeiou
        Pattern repeatingCharPattern = Pattern.compile(".*(.)\\1.*"); //First capture group (.) matches next character \1 
        Pattern forbiddenSubStringPattern = Pattern.compile(".*(?:ab|cd|pq|xy).*"); //Pattern for the forbidden sequences.

        //Part 2
        Pattern overlapPatter = Pattern.compile(".*(.{2}).*\\1.*"); //Letters should appear at least twice without overlap. i.e. xyxy or aabbcc
        Pattern repeat1CharPattern = Pattern.compile(".*(.).\\1.*"); //A character should repeat with exactly 1 other character between. i.e. xyx, aba

        //Read the file
        try{
            reader = new BufferedReader(new FileReader(input));
            String line = reader.readLine();

            while(line != null){

                //Part 1
                //if(IsNiceString(line, vowelsPattern, repeatingCharPattern, forbiddenSubStringPattern)){
                //    niceCount++;
                //}

                //Part 2
                if(IsNiceStringPart2(line, overlapPatter, repeat1CharPattern)){
                    niceCount++;
                }

                line = reader.readLine();

            }

            reader.close();

        } catch (Exception e){
            e.printStackTrace();
            return -1;
        }


        return niceCount;
    }

    /**
     * Compares the line with the given patterns.
     * @param line - Line to check.
     * @param vowelsPattern - The vowel pattern
     * @param repeatingPattern - The repeating char pattern
     * @param forbiddenPattern - The pattern for the forbidden character combos
     * @return - True if Nice, False if Naughty
     */
    private static boolean IsNiceString(String line, Pattern vowelsPattern, Pattern repeatingPattern, Pattern forbiddenPattern)
    {
        return vowelsPattern.matcher(line).matches() && repeatingPattern.matcher(line).matches() && !forbiddenPattern.matcher(line).matches();
    }

    /**
     * Compares the line with the given patterns.
     * @param line - Line to check.
     * @param overlapPattern - Pattern for 2 letters that appear at least 2 times but dont overlap
     * @param repeat1CharPattern - Pattern for letters that repeat with exactly 1 letter between them
     * @return - True if Nice, False if Naughty
     */
    private static boolean IsNiceStringPart2(String line, Pattern overlapPattern, Pattern repeat1CharPattern)
    {
        return overlapPattern.matcher(line).matches() && repeat1CharPattern.matcher(line).matches();
    }

}