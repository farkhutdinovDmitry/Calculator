import java.io.IOException;
import java.io.StringReader;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        StringReader reader = new StringReader(scanner.next());
        Lexer lexer = new Lexer(reader);
        Parser parser = new Parser(lexer);
        try {
            System.out.println(parser.calculate());
        } catch (LexerException | ParserException | IOException e) {
            System.out.println("Bad input: " + e.getMessage());
        }
    }
}
