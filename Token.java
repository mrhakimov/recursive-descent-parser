public enum Token {
    LEFT_BRACKET("'('"),
    RIGHT_BRACKET("')'"),
    SEMICOLON("';'"),
    EQUAL("'='"),
    FOR("'for'"),
    IDENT("'ident'"),
    VAL("'val"),
    COMP("'comp'"),
    OP("'op'"),
    END("$");

    private String value;

    Token(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
