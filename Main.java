import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.text.ParseException;

public class Main {
    public static void main(String[] args) {
        String text = "for (int i = 0; i < 10; i++)";
        ByteArrayInputStream bais = new ByteArrayInputStream(text.getBytes());
        BufferedInputStream bis = new BufferedInputStream(bais);

        System.out.println(text);
        System.out.println("============================");

        try {
            LexicalAnalyzer la = new LexicalAnalyzer(bis);
            while (la.getCurToken() != Token.END) {
                System.out.println(la.getCurToken());
                la.nextToken();
            }
        } catch (ParseException e) {
            System.out.println(e.getMessage() + " Offset: " + e.getErrorOffset());
        }
    }
}
