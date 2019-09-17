import java.io.IOException;

public class Parser {
    private Lexer lexer;
    private Lexeme current;

    public Parser(Lexer lexer) {
        this.lexer = lexer;
    }

    public int calculate() throws IOException, ParserException, LexerException {
        getNextLexeme();
        return parseExpr();
    }

    private int parseExpr() throws IOException, ParserException, LexerException {

        int expressionSum = parseTerm();

        while (current.getLexemeType() == Lexeme.LexemeType.PLUS || current.getLexemeType() == Lexeme.LexemeType.MINUS) {
            int sign = defineSign();
            getNextLexeme();
            expressionSum += sign * parseTerm();
        }
        return expressionSum;
    }

    private int parseTerm() throws IOException, ParserException, LexerException {
        int termSum = parseUnaryMinus();

        while (current.getLexemeType() == Lexeme.LexemeType.DIV || current.getLexemeType() == Lexeme.LexemeType.MUL) {
            if (current.getLexemeType() == Lexeme.LexemeType.DIV) {
                getNextLexeme();
                termSum /= parseUnaryMinus();
            } else {
                getNextLexeme();
                termSum *= parseUnaryMinus();
            }
        }
        return termSum;
    }

    private int parseUnaryMinus() throws IOException, ParserException, LexerException {
        int powerSum;
        if (current.getLexemeType() == Lexeme.LexemeType.UNARY_MINUS) {
            getNextLexeme();
            powerSum = -1 * parseAtom();
        } else {
            powerSum = parseAtom();
        }
        return powerSum;
    }

    private int parseAtom() throws IOException, ParserException, LexerException {
        switch (current.getLexemeType()) {
            case NUMBER: {
                int temp = Integer.parseInt(current.getText());
                getNextLexeme();
                return temp;
            }
            case OPEN_BRACE: {
                getNextLexeme();
                int temp = parseExpr();
                if (current.getLexemeType() == Lexeme.LexemeType.CLOSE_BRACE) {
                    getNextLexeme();
                    return temp;
                } else {
                    throw new ParserException("no closing brace");
                }
            }
            default: {
                throw new ParserException("incorrect lexeme");
            }
        }
    }

    private int defineSign() throws ParserException, LexerException {
        int sign;
        switch (current.getLexemeType()) {
            case MINUS: {
                sign = -1;
                break;
            }
            case PLUS: {
                sign = 1;
                break;
            }
            default: {
                throw new ParserException("incorrect sign");
            }
        }
        return sign;
    }

    private void getNextLexeme() throws IOException, LexerException {
        current = lexer.getCurrent();
    }
}
