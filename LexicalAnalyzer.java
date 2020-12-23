import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;

class LexicalAnalyzer {
    private static final String FOR_KEY_WORD = "for";

    private InputStream is;
    private int curChar;
    private int curPos;
    private Token curToken;
    private StringBuilder curTokenValue;
    private boolean appendValue = false;

    LexicalAnalyzer(InputStream is) throws ParseException {
        this.is = is;
        this.curPos = -1;
        nextChar();
        nextToken();
    }

    private boolean isBlank(int c) {
        return c == ' ' || c == '\n' || c == '\r' || c == '\t';
    }

    private void nextChar() throws ParseException {
        curPos++;

        try {
            if (appendValue) {
                curTokenValue.append((char) curChar);
            }

            curChar = is.read();
        } catch (IOException e) {
            throw new ParseException(e.getMessage(), curPos);
        }
    }

    void nextToken() throws ParseException {
        curTokenValue = new StringBuilder();
        appendValue = false;

        while (isBlank(curChar)) {
            nextChar();
        }

        appendValue = true;

        if (Character.isAlphabetic(curChar) || curChar == '_') {
            StringBuilder value = new StringBuilder();

            while (Character.isDigit(curChar) || Character.isAlphabetic(curChar) || curChar == '_') {
                value.append((char) Character.toLowerCase(curChar));
                nextChar();
            }

            curToken = Token.IDENT;


            if (value.toString().equals(FOR_KEY_WORD)) {
                curToken = Token.FOR;
            }
        } else if (Character.isDigit(curChar)) {
            StringBuilder value = new StringBuilder();

            while (Character.isDigit(curChar)) {
                value.append((char) Character.toLowerCase(curChar));
                nextChar();
            }

            curToken = Token.VAL;
        } else {
            switch (curChar) {
                case '(': {
                    nextChar();
                    curToken = Token.LEFT_BRACKET;
                    break;
                }
                case ')': {
                    nextChar();
                    curToken = Token.RIGHT_BRACKET;
                    break;
                }
                case ';': {
                    nextChar();
                    curToken = Token.SEMICOLON;
                    break;
                }
                case '<':
                case '>': {
                    curToken = Token.COMP;

                    nextChar();

                    if (curChar == '=') {
                        nextChar();
                    }
                    break;
                }
                case '!': {
                    nextChar();

                    if (curChar == '=') {
                        curToken = Token.COMP;
                        nextChar();
                    } else {
                        throw new ParseException(
                            "Unexpected character " + (char) curChar + " at position: " + curPos,
                            curPos
                        );
                    }
                    break;
                }
                case '=': {
                    nextChar();

                    if (curChar == '=') {
                        curToken = Token.COMP;
                        nextChar();
                    } else {
                        curToken = Token.EQUAL;
                    }
                    break;
                }
                case '+': {
                    nextChar();

                    if (curChar == '+') {
                        curToken = Token.OP;
                        nextChar();
                    } else {
                        throw new ParseException(
                            "Unexpected character " + (char) curChar + " at position: " + curPos,
                            curPos
                        );
                    }
                    break;
                }
                case '-': {
                    nextChar();

                    if (curChar == '-') {
                        curToken = Token.OP;
                        nextChar();
                    } else {
                        throw new ParseException(
                            "Unexpected character " + (char) curChar + " at position: " + curPos,
                            curPos
                        );
                    }
                    break;
                }
                case -1: {
                    curToken = Token.END;
                    break;
                }
                default: {
                    throw new ParseException(
                        "Unexpected character " + (char) curChar + " at position: " + curPos,
                        curPos
                    );
                }
            }
        }
    }

    int getCurChar() {
        return curChar;
    }

    Token getCurToken() {
        return curToken;
    }

    int getCurPos() {
        return curPos;
    }

    String getCurTokenValue() {
        return curTokenValue.toString();
    }
}
