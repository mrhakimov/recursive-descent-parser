import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.text.ParseException;

public class MainParser {
    public static void main(String[] args) {
        String text = "for (uint64_t __test_i = 0; __test_i != 10; __random_j2--)";
        ByteArrayInputStream bais = new ByteArrayInputStream(text.getBytes());
        BufferedInputStream bis = new BufferedInputStream(bais);

        Parser parser = new Parser();
        Tree node = new Tree("Empty");

        try {
            node = parser.parse(bis);
        } catch (ParseException e) {
            System.out.println(e.getMessage() + " Offset: " + e.getErrorOffset());
        }

        TreeEncoder te = new TreeEncoder(node);
        System.out.println(te.getResult());
    }
}
