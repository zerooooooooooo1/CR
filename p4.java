import java.util.*;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import javax.crypto.*;
import javax.crypto.spec.*;
 
public class Main {
    static SecretKey key(String password, byte[] salt) throws Exception {
        PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
        byte[] bytes = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256")
                        .generateSecret(spec).getEncoded();
        spec.clearPassword();
        return new SecretKeySpec(bytes, "AES");
    }
 
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter plaintext: ");
        String msg = sc.nextLine();
        System.out.print("Enter password: ");
        String password = sc.nextLine();
 
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16], ivBytes = new byte[16];
        random.nextBytes(salt);
        random.nextBytes(ivBytes);
        SecretKey k = key(password, salt);
        IvParameterSpec iv = new IvParameterSpec(ivBytes);
        Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
        c.init(Cipher.ENCRYPT_MODE, k, iv);
        String encrypted = Base64.getEncoder().encodeToString(
            c.doFinal(msg.getBytes(StandardCharsets.UTF_8)));
        String ivText = Base64.getEncoder().encodeToString(ivBytes);
        String saltText = Base64.getEncoder().encodeToString(salt);
        System.out.println("Ciphertext (Base64): " + encrypted);
        System.out.println("IV (Base64): " + ivText);
        System.out.println("Salt (Base64): " + saltText);
 
        c.init(Cipher.DECRYPT_MODE, key(password, salt), iv);
        String recovered = new String(c.doFinal(Base64.getDecoder().decode(encrypted)),
                                      StandardCharsets.UTF_8);
        System.out.println("Recovered Plaintext: " + recovered);
        sc.close();
    }
}
