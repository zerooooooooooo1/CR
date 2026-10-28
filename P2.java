import java.util.*;
 
public class Main {
    static char[][] m = new char[5][5];
 
    static void makeMatrix(String key) {
        LinkedHashSet<Character> set = new LinkedHashSet<>();
        String s = (key + "ABCDEFGHIJKLMNOPQRSTUVWXYZ").toUpperCase()
                   .replaceAll("[^A-Z]", "").replace('J', 'I');
        for (char c : s.toCharArray()) if (c != 'J') set.add(c);
        Iterator<Character> it = set.iterator();
        for (int i = 0; i < 5; i++)
            for (int j = 0; j < 5; j++) m[i][j] = it.next();
    }
 
    static int[] pos(char c) {
        if (c == 'J') c = 'I';
        for (int i = 0; i < 5; i++)
            for (int j = 0; j < 5; j++)
                if (m[i][j] == c) return new int[]{i, j};
        throw new IllegalArgumentException("Invalid character");
    }
 
    static String encrypt(String input) {
        String s = input.toUpperCase().replaceAll("[^A-Z]", "").replace('J', 'I');
        StringBuilder p = new StringBuilder(), out = new StringBuilder();
        for (int i = 0; i < s.length();) {
            char a = s.charAt(i++);
            char b = (i < s.length()) ? s.charAt(i) : 'X';
            if (i < s.length() && a != b) i++;
            else b = 'X';
            p.append(a).append(b);
        }
        for (int i = 0; i < p.length(); i += 2) {
            int[] a = pos(p.charAt(i)), b = pos(p.charAt(i + 1));
            if (a[0] == b[0]) {
                out.append(m[a[0]][(a[1] + 1) % 5]).append(m[b[0]][(b[1] + 1) % 5]);
            } else if (a[1] == b[1]) {
                out.append(m[(a[0] + 1) % 5][a[1]]).append(m[(b[0] + 1) % 5][b[1]]);
            } else {
                out.append(m[a[0]][b[1]]).append(m[b[0]][a[1]]);
            }
        }
        return out.toString();
    }
 
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter keyword: ");
        makeMatrix(sc.nextLine());
        System.out.println("Playfair Matrix:");
        for (char[] row : m) {
            for (char c : row) System.out.print(c + " ");
            System.out.println();
        }
        System.out.print("Enter plaintext: ");
        System.out.println("Encrypted Text: " + encrypt(sc.nextLine()));
        sc.close();
    }
}
