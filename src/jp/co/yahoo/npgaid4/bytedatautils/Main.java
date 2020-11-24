package jp.co.yahoo.npgaid4.bytedatautils;

public class Main {

    public static void main(String[] args) {
	// write your code here
	            // Passport DG15 公開鍵を取得する
        if (tagResponse.containsKey("DG15_READ")) {
            Log.d(TAG, "DG15_READ");
            byte[] AAPublickeyBytes = null;
            PublicKey publicKey;

            final byte[] b = tagResponse.get("DG15_READ");
            if (b == null) {
                Log.d(TAG, "DG15_READ returned null from NFC");
                return NFCPassPortData;
            }
            AAPublickeyBytes = nfcMyNumberUtils.getTagValue(b, (byte)0x6F);//0x6F
            activeAuthenticatePublicKey = new ActiveAuthenticatePublicKey(AAPublickeyBytes);
        }

        // Passport INTERNAL_AUTHENTICATEのレスポンスを取得し、Active Authenticationの結果を確認する
        if (tagResponse.containsKey("INTERNAL_AUTHENTICATE")) {
            Log.d(TAG, "INTERNAL_AUTHENTICATE");

            // チャレンジ乱数
            byte[] aaRandomIFD = utilsForPassPort.getAaRandomIFD();

            final byte[] b = tagResponse.get("INTERNAL_AUTHENTICATE");

            // RSA暗号化公開鍵のみサポート
            ActiveAuthenticateCipher aaCipher = new ActiveAuthenticateCipher(activeAuthenticatePublicKey, b);


    }
}
