import java.util.Scanner;

public class Main {
    static char charOffset;
    static int indexOffset = 0;
    static String encodedText = "";
    static char[] referenceTableArray = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I',
            'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R',
            'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0',
            '1', '2', '3', '4', '5', '6', '7', '8', '9',
            '(', ')', '*', '+', ',', '-', '.', '/'};
    static char[] cloneTable = referenceTableArray.clone();

    public static void main(String[] args) {
        int offset = 0;
        String plaintext = "HELLO WORLD";

        System.out.println("Enter Offset Character: ");
        Scanner scanner = new Scanner(System.in);
        // Read characters
        char input = scanner.next().charAt(0);

        for(int i=0; i<referenceTableArray.length; i++) {
            // Store offset character
            if(input == referenceTableArray[i]) {
                offset = i;
            }
        }

        // Store character offset
        charOffset = referenceTableArray[offset];
        indexOffset = offset;

        Main object = new Main();

        System.out.println("Encoded Text: " + input + object.encode(plaintext));
        System.out.println("Decoded Text: " + object.decode(encodedText));
    }

    public String encode(String plainText) {
        int numOfSpaces = 0;
        int position = 0;
        char[] plainTextArray = plainText.toCharArray();
        int[] indexArray = new int[plainTextArray.length];
        String et = "";
        boolean elementIsPresent = false;

        for(int i=0; i<plainTextArray.length; i++) {
            // Find number of spaces
            if(plainTextArray[i] == ' ') {
                numOfSpaces++;
            }
        }

        for(int j=0; j<indexOffset; j++) {
            // Offset table
            char lastElement = referenceTableArray[referenceTableArray.length - 1];
            int lastLocation = referenceTableArray.length - 1;

            for(int k=referenceTableArray.length-2; k>=0; k--) {
                // Shift table by 1 element down
                referenceTableArray[lastLocation] = referenceTableArray[k];
                lastLocation--;
            }

            // Store last element to first location
            referenceTableArray[0] = lastElement;
        }

        for(int l=0; l<plainTextArray.length; l++) {
            for(int m=0; m<cloneTable.length; m++) {
                if(plainTextArray[l] == cloneTable[m]) {
                    // If element is present, add position into array
                    elementIsPresent = true;
                    indexArray[position] = m;
                    position++;
                }
            }

            if(elementIsPresent == false && plainTextArray[l] != ' ') {
                // If element is not present, type cast character to integer and add position into array
                indexArray[position] = (int) plainTextArray[l];
                position++;
            }

            elementIsPresent = false;
            if(plainTextArray[l] == ' ') {
                // If space is present in plainText, store 0 to index array
                indexArray[position] = 0;
                position++;
            }
        }

        for(int n=0; n<indexArray.length; n++) {
            // If 0 is present in index array, add space to encoded text
            if(indexArray[n] == 0) {
                et = et + " ";
            }

            if((indexArray[n] != 0) && (indexArray[n] >= 0) && (indexArray[n] <= 43)) {
                // Encode element from table
                et = et + referenceTableArray[indexArray[n]];
            }
        }

        encodedText = encodedText + et;
        return et;
    }

    public String decode(String encodedText) {
        char[] encodedTextArray = encodedText.toCharArray();
        int[] indexArray = new int[encodedText.length()];
        String pt = "";
        boolean elementIsPresent = false;

        for(int i=0; i<encodedTextArray.length; i++) {
            for(int j=0; j<referenceTableArray.length; j++) {
                if(encodedTextArray[i] == referenceTableArray[j]) {
                    // If encoded text matches offset table, add offset to decoded text
                    elementIsPresent = true;
                    pt = pt + referenceTableArray[j + indexOffset];
                }

                if(encodedTextArray[i] == ' ') {
                    // Add space if space is present in encoded text
                    pt = pt + "";
                }
            }

            if(elementIsPresent == false) {
                // If no element is present in table, reprint the message
                pt = pt + encodedTextArray[i];
            }

            elementIsPresent = false;
        }

        return pt;
    }
}
