package jp.co.yahoo.npgaid4.bytedatautils;

import java.nio.ByteBuffer;

public class Decode {
    public byte[] APDUCommandBuilder(byte[] apduHeader, byte[] apduData,int Le){
        byte[] apduCommand;
        int Lc;
        if(Le > 65536){
            throw new Exception("Leが65536よりも大きいです。");
        }
        if(Le < 0){
            throw new Exception("Leがマイナスです。0以上です。");
        }
        Lc = apduData.length;
        if(Lc == 0){
            if(Le == 0){
                //CASE1
                apduCommand = new byte[apduHeader.length];
                System.arraycopy(apduHeader,0, apduCommand,0, apduHeader.length);
            }else{
                if(Le <= 256){
                    //CASE2
                    apduCommand = new byte[apduHeader.length + 1];
                    System.arraycopy(apduHeader,0, apduCommand,0, apduHeader.length);
                    apduCommand[4] = (Le != 256) ? (byte)Le : 0;
                }else{
                    //CASE2E
                    apduCommand = new byte[apduHeader.length + 2];
                    System.arraycopy(apduHeader,0, apduCommand,0, apduHeader.length);
                    if(Le == 65536){
                        apduCommand[4] = 0x00;
                        apduCommand[5] = 0x00;
                    }else {
                        short sLe = (short) Le;
                        byte[] bsLe = ByteBuffer.allocate(2).putShort(sLe).array();
                        System.arraycopy(bsLe,0, apduCommand,4, bsLe.length);
                    }
                }
            }
        }else{
            if(Le == 0){
                if(Lc <= 255){
                    //CASE3
                    apduCommand = new byte[apduHeader.length + 1 + Lc];
                    System.arraycopy(apduHeader,0, apduCommand,0, apduHeader.length);
                    apduCommand[4] = (byte)Lc;
                    System.arraycopy(apduData,0, apduCommand, 5, Lc);
                }else{
                    //CASE3E
                    apduCommand = new byte[apduHeader.length + 3 + Lc];
                    System.arraycopy(apduHeader,0, apduCommand,0, apduHeader.length);
                    apduCommand[4] = (byte)0x00;
                    short sLc = (short) Lc;
                    byte[] bsLc = ByteBuffer.allocate(2).putShort(sLc).array();
                    System.arraycopy(bsLc,0, apduCommand,5, bsLc.length);
                    System.arraycopy(apduData,0, apduCommand, 7, Lc);
                }
            }else {
                if ((Lc <= 255) && (Le <= 256)) {
                    //CASE4
                    apduCommand = new byte[apduHeader.length + 2 + Lc];
                    System.arraycopy(apduHeader,0, apduCommand,0, apduHeader.length);
                    apduCommand[4] = (byte)Lc;
                    System.arraycopy(apduData,0, apduCommand, 5, Lc);
                    apduCommand[apduCommand.length - 1] = (Le != 256) ? (byte)Le : 0;
                }else{
                    //CASE4E
                    apduCommand = new byte[apduHeader.length + 5 + Lc];
                    System.arraycopy(apduHeader,0, apduCommand,0, apduHeader.length);
                    apduCommand[4] = (byte)0x00;
                    short sLc = (short) Lc;
                    byte[] bsLc = ByteBuffer.allocate(2).putShort(sLc).array();
                    System.arraycopy(bsLc,0, apduCommand,5, bsLc.length);
                    System.arraycopy(apduData,0, apduCommand, 7, Lc);
                    if(Le != 65536){
                        short sLe = (short) Le;
                        byte[] bsLe = ByteBuffer.allocate(2).putShort(sLe).array();
                        System.arraycopy(bsLe,0, apduCommand,apduCommand.length - 2, bsLe.length);
                    }else{
                        apduCommand[apduCommand.length - 2] = 0x00;
                        apduCommand[apduCommand.length - 1] = 0x00;
                    }
                }
            }
        }
        return apduCommand;
    }
}
