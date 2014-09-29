package test;

/**
 * DOCUMENT ME!
 *
 * @version $Revision$, $Date$
 */
public class Token {
    static final int SIXTY_FIVE = 65;

    int referenceColumn;
    int referenceRow;

    TokenType type;
    double value;

    /**
     * Creates a new Token object.
     *
     * @param type
     */
    public Token(final TokenType type) {
        this.type = type;
    }

    /**
     * Creates a new Token object.
     *
     * @param reference
     */
    public Token(final String reference) {
        type = TokenType.REFERENCE;
        referenceRow = reference.charAt(0) - SIXTY_FIVE;
        referenceColumn = Integer.valueOf(reference.substring(1)) - 1;
    }

    /**
     * Creates a new Token object.
     *
     * @param d
     */
    public Token(final double d) {
        type = TokenType.VALUE;
        value = d;
    }

    /* 0 = value 1 = reference 2 = operator+ 3 = operator- 4 = operator* 5 =
     * operator/ 6 = operator ++ 7 = operator --*/

    @Override
    public String toString() {
        switch (type) {
            case VALUE: {
                return String.valueOf(value);
            }

            case REFERENCE: {
                return String.valueOf((char) (referenceRow + SIXTY_FIVE) +
                        referenceColumn);
            }

            case SUM: {
                return "+";
            }

            case MINUS: {
                return "-";
            }

            case MUL: {
                return "*";
            }

            case DIV: {
                return "/";
            }

            case PLUSPLUS: {
                return "++";
            }

            case MINUSMINUS: {
                return "--";
            }
        }

        return super.toString();
    }
}
