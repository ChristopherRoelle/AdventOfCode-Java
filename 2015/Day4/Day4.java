/*
--- Day 4: The Ideal Stocking Stuffer ---
Santa needs help mining some AdventCoins (very similar to bitcoins) to use as gifts for all the economically forward-thinking little girls and boys.

To do this, he needs to find MD5 hashes which, in hexadecimal, start with at least five zeroes. The input to the MD5 hash is some secret key (your puzzle input, given below) followed by a number in decimal. To mine AdventCoins, you must find Santa the lowest positive number (no leading zeroes: 1, 2, 3, ...) that produces such a hash.

For example:

If your secret key is abcdef, the answer is 609043, because the MD5 hash of abcdef609043 starts with five zeroes (000001dbbfa...), and it is the lowest such number to do so.
If your secret key is pqrstuv, the lowest number it combines with to make an MD5 hash starting with five zeroes is 1048970; that is, the MD5 hash of pqrstuv1048970 looks like 000006136ef....
Your puzzle answer was 117946.

--- Part Two ---
Now find one that starts with six zeroes.

Your puzzle answer was 3938038.

Both parts of this puzzle are complete! They provide two gold stars: **

At this point, you should return to your Advent calendar and try another puzzle.

Your puzzle input was ckczppom.
 */

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Day5{

    public static void main(String[] args)
    {
        String key = "ckczppom"; //Secret Key
        int resultNumber = MineAdventCoin(key);
        System.out.println("The lowest positive number is: " + resultNumber);
    }

    /**
     * Finds the key + number combo that results in a hash that starts with 5-0's
     * @param key - Secret Key
     * @return - Number that results in hash starting with 5-0's
     */
    private static int MineAdventCoin(String key){
        int number = 1;

        String hash;

        while(true){
            hash = CalculateMD5(key + number);

            if(hash.startsWith("000000")){
                return number;
            }

            number++;
        }
    }

    /**
     * Takes in an input string and generates a Hash Key using the MD5 algorithm
     * @param input - key
     * @return - encrypted
     */
    private static String CalculateMD5(String input){
        try{
            MessageDigest md = MessageDigest.getInstance("MD5"); //Using MD5
            byte[] digest = md.digest(input.getBytes());

            //Convert the byte array to hex string
            StringBuilder result = new StringBuilder();
            for(byte b : digest){
                result.append(String.format("%02x", b));
            }

            return result.toString();
        }
        catch(NoSuchAlgorithmException e){
            e.printStackTrace();
            return null;
        }
    }

}