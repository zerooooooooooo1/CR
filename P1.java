import java.util.*;
 
public class Main {
    static String shift(String s, int k) {
        k = ((k % 26) + 26) % 26;
        StringBuilder out = new StringBuilder();
        for (char c : s.toCharArray()) {
            if (c >= 'A' && c <= 'Z')
                out.append((char)('A' + (c - 'A' + k) % 26));
            else if (c >= 'a' && c <= 'z')
                out.append((char)('a' + (c - 'a' + k) % 26));
            else out.append(c);
        }
        return out.toString();
    }
 
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the message: ");
        String msg = sc.nextLine();
        System.out.print("Enter shift value: ");
        int k = sc.nextInt();
        String enc = shift(msg, k);
        System.out.println("Encrypted Text: " + enc);
        System.out.println("Decrypted Text: " + shift(enc, -k));
        sc.close();
    }
}
