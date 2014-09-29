package test;

/**
 * Created by sviswanatham on 9/9/14.
 *
 * @version  $Revision$, $Date$
 */
public enum TokenType {
    DIV(5, "Division"), MINUS(3, "Minus"), MINUSMINUS(7, "Minus Minus"),
    MUL(4, "Multiply"), PLUSPLUS(6, "Plus Plus"), REFERENCE(1, "Reference"),
    SUM(2, "Sum"), VALUE(0, "Value");

    private int id;
    private String name;

    /**
     * Creates a new TokenType object.
     *
     * @param  id
     * @param  name
     */
    private TokenType(final int id, final String name) {
        this.id = id;
        this.name = name;
    }
}
