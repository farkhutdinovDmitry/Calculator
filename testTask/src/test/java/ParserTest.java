import org.junit.Test;

import java.io.IOException;
import java.io.StringReader;

import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertEquals;

public class ParserTest {
    @Test
    public void simpleExpressionTest() {
        StringReader text = new StringReader("6 - 6 + 707 - 707");
        StringReader text1 = new StringReader("1 - 0 -0 +                                    0");

        Lexer l = new Lexer(text);
        Lexer l1 = new Lexer(text1);

        try {
            Parser p = new Parser(l);
            assertEquals(0, p.calculate());

            Parser p1 = new Parser(l1);
            assertEquals(1, p1.calculate());
        } catch (IOException | ParserException | LexerException e) {
            String error = "notNull";
            assertNull(error);
            e.printStackTrace();

        }
    }

    @Test
    public void simpleTermTest() {
        StringReader text = new StringReader("10 * 6/6");
        StringReader text1 = new StringReader("2 + 2 * 2");

        Lexer lexer1 = new Lexer(text);
        Lexer lexer2 = new Lexer(text1);

        try {
            Parser parser1 = new Parser(lexer1);
            assertEquals(10, parser1.calculate());

            Parser parser2 = new Parser(lexer2);
            assertEquals(6, parser2.calculate());
        } catch (IOException | ParserException | LexerException e) {
            String err = "notNull";
            assertNull(err);
            e.printStackTrace();

        }
    }
}
