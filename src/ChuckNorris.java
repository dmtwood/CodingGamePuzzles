import java.util.*;
import java.io.*;
import java.math.*;

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/
class ChuckNorris {

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        String MESSAGE = in.nextLine();

        char[] message = MESSAGE.toCharArray();

        StringBuilder binary = ChuckNorris.toBinary(message);


        int i = 0;
        char currChar;

        while ( i < binary.length() ){

            if ( binary.charAt(i) == '0' ) {
                System.out.print("00 ");
                currChar = '0';
            }
            else {
                System.out.print("0 ");
                currChar = '1';
            }
            while (binary.charAt(i) == currChar) {
                System.out.print("0");
                i++;
                if ( i >= binary.length() ) {
                    break;
                }
            }
            if ( i < binary.length() ) {
                System.out.print(" ");
            }
            in.close();
        }






        // Write an answer using System.out.println()
        // To debug: System.err.println("Debug messages...");

        // System.out.println("answer");
    }

    public static StringBuilder toBinary(char[] chars) {

        StringBuilder bin = new StringBuilder();

        for (char c : chars) {
            String result = Integer.toBinaryString(c);

            while( result.length() < 7){
                result = '0' + result;
            }

            bin.append(result);
        }
        return bin;

    }
}