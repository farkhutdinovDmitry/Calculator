import java.io.IOException;
import java.io.Reader;
import java.util.regex.Pattern;

public class Lexer {
    private static final int EOF = -1;
    private static final String WHITESPACE = " ";
    private Reader reader;
    private Lexeme lastLexeme;
    private StringBuilder current;

    public Lexer(Reader reader) {
        current = new StringBuilder();
        this.reader = reader;
    }

    public Lexeme getCurrent() throws LexerException {
        Lexeme.LexemeType lexemeType = null;
        current.setLength(0);

        try {
            readNext();
            while (current.toString().replaceAll("\\s+", " ").equals(WHITESPACE)) {
                current.delete(0, 1);
                readNext();
            }

            if (Pattern.matches("[0-9]", current.toString())) {
                while (isNextChar()) {
                    reader.reset();
                    readNext();
                }
                reader.reset();
                lexemeType = Lexeme.LexemeType.NUMBER;
            } else if (Lexeme.LexemeType.CLOSE_BRACE.getValue().equals(current.toString())) {
                lexemeType = Lexeme.LexemeType.CLOSE_BRACE;
            } else if (Lexeme.LexemeType.OPEN_BRACE.getValue().equals(current.toString())) {
                lexemeType = Lexeme.LexemeType.OPEN_BRACE;
            } else if (Lexeme.LexemeType.MUL.getValue().equals(current.toString())) {
                lexemeType = Lexeme.LexemeType.MUL;
            } else if (Lexeme.LexemeType.DIV.getValue().equals(current.toString())) {
                lexemeType = Lexeme.LexemeType.DIV;
            } else if (Lexeme.LexemeType.PLUS.getValue().equals(current.toString())) {
                lexemeType = Lexeme.LexemeType.PLUS;
            } else if (isMinus()) {
                lexemeType = Lexeme.LexemeType.MINUS;
            } else if (Lexeme.LexemeType.UNARY_MINUS.getValue().equals(current.toString())) {
                lexemeType = Lexeme.LexemeType.UNARY_MINUS;
            }
        } catch (IOException | LexerException e) {
            lastLexeme = new Lexeme(Lexeme.LexemeType.EOF, "");
            return lastLexeme;
        }

        if (lexemeType == null) {
            throw new LexerException("unknown current");
        }

        lastLexeme = new Lexeme(lexemeType, current.toString());
        return lastLexeme;


    }

    private void readNext() throws IOException {
        char[] buffer = new char[1];
        int read = reader.read(buffer);

        if (read == EOF) {
            throw new LexerException("EOF found, need to stop");
        }

        current.append(buffer);
    }

    private boolean isNextChar() throws IOException {
        char[] buf = new char[1];
        reader.mark(1);
        int read = reader.read(buf);
        return (read != EOF) && (new String(buf).matches("[0-9]"));
    }

    private boolean isMinus() {
        return current.toString().equals(Lexeme.LexemeType.MINUS.getValue()) &&
                lastLexeme != null &&
                (lastLexeme.getLexemeType() == Lexeme.LexemeType.NUMBER ||
                        lastLexeme.getLexemeType() == Lexeme.LexemeType.CLOSE_BRACE);
    }

}

