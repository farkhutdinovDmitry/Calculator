
public class Lexeme {

    private LexemeType lexemeType;
    private String text;

    public Lexeme(LexemeType lexemeType, String lexemeText) {
        this.lexemeType = lexemeType;
        this.text = lexemeText;
    }

    public LexemeType getLexemeType() {
        return lexemeType;
    }


    public String getText() {
        return text;
    }

    public enum LexemeType {
        NUMBER(""),
        OPEN_BRACE("("),
        CLOSE_BRACE(")"),
        MUL("*"),
        DIV("/"),
        PLUS("+"),
        MINUS("-"),
        UNARY_MINUS("-"),
        EOF("-1");
        private String value;

        LexemeType(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }


    }
}

