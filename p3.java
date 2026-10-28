import java.util.*;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import javax.crypto.*;
import javax.crypto.spec.*;
 
public class Main {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter plaintext: ");
        String msg = sc.nextLine();
        System.out.print("Enter password (at least 8 characters): ");
        String pass = sc.nextLine();
        if (pass.getBytes(StandardCharsets.UTF_8).length < 8) {
            System.out.println("Password must contain at least 8 bytes.");
            sc.close();
            return;
        }
 
        byte[] keyBytes = Arrays.copyOf(pass.getBytes(StandardCharsets.UTF_8), 8);
        SecretKey key = SecretKeyFactory.getInstance("DES")
            .generateSecret(new DESKeySpec(keyBytes));
        byte[] ivBytes = new byte[8];
        new SecureRandom().nextBytes(ivBytes);
        IvParameterSpec iv = new IvParameterSpec(ivBytes);
 
        Cipher c = Cipher.getInstance("DES/CBC/PKCS5Padding");
        c.init(Cipher.ENCRYPT_MODE, key, iv);
        String encrypted = Base64.getEncoder().encodeToString(
            c.doFinal(msg.getBytes(StandardCharsets.UTF_8)));
        String ivText = Base64.getEncoder().encodeToString(ivBytes);
        System.out.println("Ciphertext (Base64): " + encrypted);
        System.out.println("IV (Base64): " + ivText);
 
        c.init(Cipher.DECRYPT_MODE, key, iv);
        byte[] plain = c.doFinal(Base64.getDecoder().decode(encrypted));
        System.out.println("Recovered Plaintext: " +
            new String(plain, StandardCharsets.UTF_8));
        sc.close();
    }
}
