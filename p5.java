import java.util.*;
import java.nio.charset.StandardCharsets;
import java.security.*;
import javax.crypto.Cipher;
 
public class Main {
    static String hex(byte[] data) {
        StringBuilder s = new StringBuilder();
        for (byte b : data) s.append(String.format("%02X", b));
        return s.toString();
    }
 
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter message (short text): ");
        String msg = sc.nextLine();
 
        KeyPairGenerator gen = KeyPairGenerator.getInstance("RSA");
        gen.initialize(2048);
        KeyPair pair = gen.generateKeyPair();
 
        Cipher c = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding");
        c.init(Cipher.ENCRYPT_MODE, pair.getPublic());
        byte[] encrypted = c.doFinal(msg.getBytes(StandardCharsets.UTF_8));
        System.out.println("Encrypted Message (HEX): " + hex(encrypted));
 
        c.init(Cipher.DECRYPT_MODE, pair.getPrivate());
        String recovered = new String(c.doFinal(encrypted), StandardCharsets.UTF_8);
        System.out.println("Decrypted Message: " + recovered);
        sc.close();
    }
}
