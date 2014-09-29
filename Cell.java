package test;

import java.util.ArrayList;
import java.util.Stack;
import java.util.StringTokenizer;


/**
 * DOCUMENT ME!
 *
 * @version $Revision$, $Date$
 */
public class Cell {
    volatile int edgeCount;
    boolean evaluated;

    double evaluatedValue;

    String name;

    ArrayList<String> references = new ArrayList<String>();

    SpreadSheet spreadSheet;
    ArrayList<Token> tokens = new ArrayList<Token>();

    /**
     * Creates a new Cell object.
     *
     * @param spreadSheet
     */
    public Cell(final SpreadSheet spreadSheet, final int row, final int col, final String input) {
        this.spreadSheet = spreadSheet;
        this.name = (char) (65 +  row) + String.valueOf(col + 1);
        setInput(input);
    }

    /**
     * DOCUMENT ME!
     *
     * @return double
     * @throws CircularDependancyException
     */
    public double evaluate() throws SpreadSheet.CircularReferenceException {
        if (evaluated) {
            return evaluatedValue;
        }

        final Stack<Double> stack = new Stack<Double>();
        double eval;
        double arg2;
        double arg1;

        for (final Token token : tokens) {
            switch (token.type) {
                case VALUE: {
                    stack.push(token.value);

                    break;
                }

                case REFERENCE: {
                    final Object reference =
                            spreadSheet.get(getName(token.referenceRow, token.referenceColumn));

                    if (reference instanceof Cell) {
                        eval = ((Cell) reference).evaluate();
                    } else {
                        eval = (Double) reference;
                    }

                    stack.push(eval);

                    break;
                }

                case SUM: {
                    arg1 = stack.pop();
                    arg2 = stack.pop();
                    eval = arg2 + arg1;
                    stack.push(eval);

                    break;
                }

                case MINUS: {
                    arg1 = stack.pop();
                    arg2 = stack.pop();
                    eval = arg2 - arg1;
                    stack.push(eval);

                    break;
                }

                case MUL: {
                    arg1 = stack.pop();
                    arg2 = stack.pop();
                    eval = arg2 * arg1;
                    stack.push(eval);

                    break;
                }

                case DIV: {
                    arg1 = stack.pop();
                    arg2 = stack.pop();
                    eval = arg2 / arg1;
                    stack.push(eval);

                    break;
                }

                case PLUSPLUS: {
                    arg1 = stack.pop();
                    eval = arg1 + 1;
                    stack.push(eval);

                    break;
                }

                case MINUSMINUS: {
                    arg1 = stack.pop();
                    eval = arg1 - 1;
                    stack.push(eval);

                    break;
                }
            }
        }

        evaluatedValue = stack.pop();
        evaluated = true;

        return evaluatedValue;
    }

    /**
     * Returns the name value.
     *
     * @return name value.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the inputFile value.
     *
     * @param input
     */
    public void setInput(final String input) {

        final StringTokenizer st = new StringTokenizer(input);

        while (st.hasMoreTokens()) {
            final String token = st.nextToken();

            if ("+".equals(token)) {
                tokens.add(new Token(TokenType.SUM));
            } else if ("-".equals(token)) {
                tokens.add(new Token(TokenType.MINUS));
            } else if ("*".equals(token)) {
                tokens.add(new Token(TokenType.MUL));
            } else if ("/".equals(token)) {
                tokens.add(new Token(TokenType.DIV));
            } else if ("++".equals(token)) {
                tokens.add(new Token(TokenType.PLUSPLUS));
            } else if ("--".equals(token)) {
                tokens.add(new Token(TokenType.MINUSMINUS));
            } else {
                final char c = token.charAt(0);

                if (('A' <= c) && (c <= 'Z')) {
                    // reference
                    tokens.add(new Token(token));
                    references.add(token);
                } else {
                    // integer
                    final double d = Double.valueOf(token);
                    tokens.add(new Token(d));
                }
            }
        }
        edgeCount = references.size();
    }

    /**
     * Sets the name value.
     *
     * @param name
     */
    public void setName(final String name) {
        this.name = name;
    }

    // get name for a cell
    public static String getName(final int row, final int col) {
        return (char) (65 +  row) + String.valueOf(col + 1);
    }

}
