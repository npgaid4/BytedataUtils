package jp.co.yahoo.npgaid4.bytedatautils;

public class Decode {
    public byte[] decode(byte[] input){
        byte c = input[0];
        byte[] decodedDo87 = new byte[0];

        for(int i = 0 ; i < input.length;){
            if(c == 0x87){
                decodedDo87 = decodeDo87(input);
            }
        }
    }
    public byte[] decodeDo87(byte[] input){

    }
}
