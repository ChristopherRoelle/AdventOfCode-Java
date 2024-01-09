package Part2;

/*
--- Day 8: Matchsticks ---
--- Part 2 ---
Now, let's go the other way. In addition to finding the number of characters of code, you should now encode each code representation as a new string and find the number of characters of the new encoded representation, including the surrounding double quotes.

For example:

"" encodes to "\"\"", an increase from 2 characters to 6.
"abc" encodes to "\"abc\"", an increase from 5 characters to 9.
"aaa\"aaa" encodes to "\"aaa\\\"aaa\"", an increase from 10 characters to 16.
"\x27" encodes to "\"\\x27\"", an increase from 6 characters to 11.
Your task is to find the total number of characters to represent the newly encoded strings minus the number of characters of code in each original string literal. For example, for the strings above, the total encoded length (6 + 9 + 16 + 11 = 42) minus the characters in the original code representation (23, just like in the first part of this puzzle) is 42 - 23 = 19.

Your puzzle answer was 2074.
 */

import java.io.BufferedReader;
import java.io.FileReader;

public class Part2{

    static int codeCharCount = 0;
    static int memCharCount  = 0;
    static int encodedCharCount = 0;
    static int doubleQuoteCount = 2;

    public static void main(String[] args){

        String inputFilePath = "./input.txt";
        ReadInputFile(inputFilePath);

        System.out.println();
        System.out.println("Part 1:");
        System.out.println("Num Characters in Code: " + codeCharCount);
        System.out.println("Num Characters in Memory: " + memCharCount);
        System.out.println("Difference: " + (codeCharCount - memCharCount));

        System.out.println();
        System.out.println("Part 2:");
        System.out.println("Num Characters in Encode: " + encodedCharCount);
        System.out.println("Difference: " + (encodedCharCount - codeCharCount));

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

                //Part 2
                encodedCharCount += EncodeStringLiteral(line);
                
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

    private static int EncodeStringLiteral(String input)
    {

        String encodedString = "\"";
        
        for (char character : input.toCharArray()) {
            
            if(character == '"')
                encodedString += "\\\"";
            else if(character == '\\')
                encodedString += "\\\\";
            else
                encodedString += character;

        }

        encodedString += "\"";

        return encodedString.length();
    }


}