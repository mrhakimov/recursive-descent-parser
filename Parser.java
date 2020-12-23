import java.io.InputStream;
import java.text.ParseException;

class Parser {
    private LexicalAnalyzer lex;

    private void checkToken(Token expectedToken) throws ParseException {
        if (lex.getCurToken() != expectedToken) {
            throw new ParseException(
                "Expected token: " + expectedToken.getValue() + ", actual: " + lex.getCurToken() + ".",
                lex.getCurPos()
            );
        }
    }

    private void unexpectedTokenError() throws ParseException {
        throw new ParseException(
            "Unexpected token " + lex.getCurToken().getValue() + ".",
            lex.getCurPos());
    }

    private Tree S() throws ParseException {
        checkToken(Token.FOR);

        lex.nextToken();
        checkToken(Token.LEFT_BRACKET);

        lex.nextToken();
        Tree parameters = P();

        checkToken(Token.RIGHT_BRACKET);

        lex.nextToken();
        checkToken(Token.END);

        return new Tree("S",
                    new Tree("for"),
                    new Tree("("),
                    parameters,
                    new Tree(")"));
    }

    private Tree P() throws ParseException {
        Tree decl = D();

        checkToken(Token.SEMICOLON);
        lex.nextToken();

        Tree cond = C();

        checkToken(Token.SEMICOLON);
        lex.nextToken();

        Tree move = M();

        return new Tree("P",
                    decl,
                    new Tree(";"),
                    cond,
                    new Tree(";"),
                    move);
    }

    private Tree D() throws ParseException {
        String type = lex.getCurTokenValue();
        checkToken(Token.IDENT);
        lex.nextToken();

        String ident = lex.getCurTokenValue();
        checkToken(Token.IDENT);
        lex.nextToken();

        checkToken(Token.EQUAL);
        lex.nextToken();

        String val = lex.getCurTokenValue();
        checkToken(Token.VAL);
        lex.nextToken();

        return new Tree("D",
                    new Tree(type),
                    new Tree(ident),
                    new Tree("="),
                    new Tree(val));
    }

    private Tree C() throws ParseException {
        String ident = lex.getCurTokenValue();
        checkToken(Token.IDENT);
        lex.nextToken();

        String comp = lex.getCurTokenValue();
        checkToken(Token.COMP);
        lex.nextToken();

        String val = lex.getCurTokenValue();
        checkToken(Token.VAL);
        lex.nextToken();

        return new Tree("C",
                    new Tree(ident),
                    new Tree(comp),
                    new Tree(val));
    }

    private Tree M() throws ParseException {
        String ident = lex.getCurTokenValue();
        checkToken(Token.IDENT);
        lex.nextToken();

        String op = lex.getCurTokenValue();
        checkToken(Token.OP);
        lex.nextToken();

        return new Tree("M",
                    new Tree(ident),
                    new Tree(op));
    }

    Tree parse(InputStream is) throws ParseException {
        lex = new LexicalAnalyzer(is);
        return S();
    }
}
