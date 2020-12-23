import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.text.ParseException;

public class Tests {
    private static final Parser parser = new Parser();
    private static int testsNumber;
    private static int testsSucceed;

    private static void test(String text, boolean expectError) {
        testsNumber++;

        ByteArrayInputStream bais = new ByteArrayInputStream(text.getBytes());
        BufferedInputStream bis = new BufferedInputStream(bais);

        Tree node = new Tree("Empty");
        boolean actual = false;

        try {
            node = parser.parse(bis);
        } catch (ParseException e) {
            actual = true;

            System.out.println(e.getMessage() + " Offset: " + e.getErrorOffset());
        }

        if (expectError != actual) {
            System.out.println("Test with 'text' = " + text + " failed!");
        } else {
            testsSucceed++;
        }
    }

    public static void main(String[] args) {
        test("for (int i = 0; i < 10; i++)", false);
        test("while (int i = 0; i < 10; i++)", true);
        test("for [int i = 0; i < 10; i++)", true);
        test("for (1int i = 0; i < 10; i++)", true);
        test("for (int 9402 i = 0; i < 10; i++)", true);
        test("for (int _i = 0; _i < 10; _i++)", false);
        test("for (int i != 0; i < 10; i++)", true);
        test("for (int i = a; i < 10; i++)", true);
        test("for (int i = 0: i < 10; i++)", true);
        test("for (int i = 0; 10 < 10; i++)", true);
        test("for (int i = 0; i /= 10; i++)", true);
        test("for (int i = 0; i != j; i++)", true);
        test("for (int i = 0; i != 10, i++)", true);
        test("for (int i = 0; i != 10; 1i++)", true);
        test("for (int i = 0; i != 10; i += 1)", true);
        test("for (int i = 0; i != 10; i++}", true);
        test("for (int i = 0; i != 10; i--)", false);
        test("for (uint64_t i = 0; i != 10; i--)", false);
        test("for (uint64_t __test_i = 0; __test_i != 10; __random_j2--)", false);
        test("for\t\n\t\t\t\t\r\n\r (uint64_t __test_i = 0; __test_i != 10; __random_j2--)", false);
        test("for\t\n\t\t\r (uint64_t \t\t\r\n__test_i = 0; __test_i != \t\t\r\n10; __random_j2--)", false);
        test("for (   int i =   0; i    <   10;   i++)", false);
        test("for (   int i =  1; i    <   101;   i++)", false);

        if (testsNumber == testsSucceed) {
            System.out.println("All tests passed successfully!");
        }
    }
}
z