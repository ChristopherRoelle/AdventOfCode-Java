package Part1;

/*
--- Day 8: Matchsticks ---
--- Part 1 ---
Space on the sleigh is limited this year, and so Santa will be bringing his list as a digital copy. He needs to know how much space it will take up when stored.

It is common in many programming languages to provide a way to escape special characters in strings. For example, C, JavaScript, Perl, Python, and even PHP handle special characters in very similar ways.

However, it is important to realize the difference between the number of characters in the code representation of the string literal and the number of characters in the in-memory string itself.

For example:

"" is 2 characters of code (the two double quotes), but the string contains zero characters.
"abc" is 5 characters of code, but 3 characters in the string data.
"aaa\"aaa" is 10 characters of code, but the string itself contains six "a" characters and a single, escaped quote character, for a total of 7 characters in the string data.
"\x27" is 6 characters of code, but the string itself contains just one - an apostrophe ('), escaped using hexadecimal notation.
Santa's list is a file that contains many double-quoted string literals, one on each line. The only escape sequences used are \\ (which represents a single backslash), \" (which represents a lone double-quote character), and \x plus two hexadecimal characters (which represents a single character with that ASCII code).

Disregarding the whitespace in the file, what is the number of characters of code for string literals minus the number of characters in memory for the values of the strings in total for the entire file?

For example, given the four strings above, the total number of characters of string code (2 + 5 + 10 + 6 = 23) minus the total number of characters in memory for string values (0 + 3 + 7 + 1 = 11) is 23 - 11 = 12.

Your puzzle answer was 1342.
 */

import java.io.BufferedReader;
import java.io.FileReader;

public class Part1{

    static int codeCharCount = 0;
    static int memCharCount  = 0;
    static int doubleQuoteCount = 2;

    public static void main(String[] args){

        String inputFilePath = "./input.txt";
        ReadInputFile(inputFilePath);

        System.out.println();
        System.out.println("Num Characters in Code: " + codeCharCount);
        System.out.println("Num Characters in Memory: " + memCharCount);
        System.out.println("Difference: " + (codeCharCount - memCharCount));

    }

    private static void ReadInputFile(String inputFile){

        try{
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));

            String line = reader.readLine();

            while(line != null){

                int charCount = 0;
                int curIndex = 0;

                while(curIndex < line.length()){
                    char curCharacter = line.charAt(curIndex);

                    if(curCharacter == '\\' && curIndex < line.length() - 1)
                    {
                        //If next char is escaped backslash or quotes, then skip cur and next.
                        if(line.charAt(curIndex + 1) == '\\' || line.charAt(curIndex + 1) == '\"')
                            curIndex += 2;
                        else if (line.charAt(curIndex + 1) == 'x'){
                            //Hex char, skip 4 characters \xDD
                            curIndex += 4;
                        }
                    }
                    else {
                        curIndex++;
                    }

                    charCount++;
                }

                charCount -= doubleQuoteCount;
                
                codeCharCount += line.length();
                memCharCount += charCount;

                line = reader.readLine();
            }

            reader.close();

        }
        catch(Exception e){
            e.printStackTrace();
            return;
        }

    }


}