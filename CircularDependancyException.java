package test;

/**
 * DOCUMENT ME!
 *
 * @version  $Revision$, $Date$
 */
public class CircularDependancyException extends Exception {
    /**
     * Creates a new CircularDependancyException object.
     *
     * @param  string
     */
    public CircularDependancyException(final String string) {
        super(string);
    }
}
