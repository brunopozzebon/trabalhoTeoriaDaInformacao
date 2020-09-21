package redunduncy;

import expections.WrongFormatExpection;

import java.util.HashMap;

public class Hamming implements Redunduncy {

    private final int OUT_LENGTH = 7;
    private final int IN_LENGTH = 4;

    private final HashMap<String, Integer> zTable = new HashMap<String, Integer>(){
        {
            put("000", 0);
            put("001", 7);
            put("010", 6);
            put("011", 4);
            put("100", 5);
            put("101", 1);
            put("110", 2);
            put("111", 3);
        }
    };

    @Override
    public String introduceRedunduncy(String bytes) throws WrongFormatExpection {
        if(bytes.length()!=IN_LENGTH){
            return bytes;
        }

        char firstBit = bytes.charAt(0);
        char secondBit = bytes.charAt(1);
        char thirdBit = bytes.charAt(2);
        char fourthBit = bytes.charAt(3);

        if(verifyBits(firstBit, secondBit, thirdBit, fourthBit)){
            throw new WrongFormatExpection("A mensagem não estava em binário");
        }

        int fifthBit = (firstBit+secondBit+thirdBit)%2;
        int sixthBit = (secondBit+thirdBit+fourthBit)%2;
        int seventhBit = (firstBit+thirdBit+fourthBit)%2;

        return bytes + fifthBit + sixthBit + seventhBit;
    }

    private boolean verifyBits(char ...args) {
        for (char bit : args)
            if(bit!='0' && bit!='1')
                return true;
        return false;
    }

    @Override
    public String getValue(String bytes) throws WrongFormatExpection {
        if(bytes.length()!=OUT_LENGTH){
            return bytes;
        }

        char firstBit = bytes.charAt(0);
        char secondBit = bytes.charAt(1);
        char thirdBit = bytes.charAt(2);
        char fourthBit = bytes.charAt(3);
        char fifthBit = bytes.charAt(4);
        char sixth = bytes.charAt(5);
        char seventh = bytes.charAt(6);

        if(verifyBits(firstBit, secondBit, thirdBit, fourthBit, fifthBit, sixth, seventh)){
            throw new WrongFormatExpection("A mensagem não estava em binário");
        }

        boolean fifthIsRight = String.valueOf((firstBit + secondBit + thirdBit)%2).equals(String.valueOf(fifthBit));
        boolean sixthIsRight = String.valueOf((secondBit + thirdBit + fourthBit)%2).equals(String.valueOf(sixth));
        boolean seventhIsRight = String.valueOf((firstBit + thirdBit+ fourthBit)%2).equals(String.valueOf(seventh));

        String firstZ = fifthIsRight ? "0" : "1";
        String secondZ = sixthIsRight ? "0" : "1";
        String thirdZ = seventhIsRight ? "0" : "1";

        String z = firstZ + secondZ + thirdZ;

        if(!z.equals("000")){
            throw new WrongFormatExpection("Mensagem Corrompida",zTable.get(z));
        }

        return String.valueOf(new char[]{firstBit,secondBit,thirdBit,fourthBit});
    }

}