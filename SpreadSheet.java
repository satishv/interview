package test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.TreeMap;
import java.util.Map;

import java.util.Scanner;
import java.util.Set;
import java.util.jar.Attributes;


/**
 * DOCUMENT ME!
 *
 * It will scale beyond 3x2 spreadsheet. I can optimize it for larger spreadsheet (either for space or speed).
 * It does not assume any static limits. I tried to use maps and linkedlist where ever possible.
 * There is totally room for improvement.
 *
 * TODO: define an interface and this class can implement that interface
 *
 * @version  $Revision$, $Date$
 */
public class SpreadSheet  {

    private int columns; // column (width)  : 1..columns
    private int rows; // rows (height)  : A..Z

    // TODO: set an initial value. e.g. 10000
    private HashMap<String, Set<Cell>> dependancyMap = new HashMap<String, Set<Cell>>();

    private LinkedList<Cell> topologicalSortS = new LinkedList<Cell>();

    // index is like cell name (e.g. A1)
    private Map<String, Cell> values;

    public SpreadSheet(Scanner sc) {
        this.values = new TreeMap<String, Cell>();

        columns = sc.nextInt();
        rows = sc.nextInt();
        sc.nextLine();

        for (int row = 0; row < rows; row++) {

            for (int col = 0; col < columns; col++) {
                final String input = sc.nextLine().toUpperCase();
                final Cell cell = new Cell(this, row, col, input);
                // populate the spreadsheet with cell
                set(cell);

                if (cell.edgeCount == 0) {
                    topologicalSortS.add(cell);
                } else {
                    final ArrayList<String> references = cell.references;

                    for (final String string : references) {
                        addDependancyMap(string, cell);
                    }
                }
            }
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param   args
     *
     * @throws  Exception
     */
    public static void main(final String[] args) throws Exception {

        SpreadSheet sheet = new SpreadSheet(new Scanner(System.in));
        sheet.dump();
    }

    public void set(final Cell cell) {
        values.put(cell.getName(), cell);
    }

    public Cell get(final String name) {
        return values.get(name);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  dOCUMENT ME!
     *
     * @throws  Exception
     * @throws  CircularDependancyException
     * @throws  CircularReferenceException
     */
    public void dump() {
        // do topological sort
        int countEvaluated = 0;

        while (topologicalSortS.size() > 0) {
            final Cell cell = topologicalSortS.removeFirst();
            cell.evaluate();
            countEvaluated++;

            // get every cell x that refer cell y
            final Set<Cell> list = dependancyMap.get(cell.name);

            if (null == list) {
                continue;
            }

            for (final Cell cellx : list) {
                cellx.edgeCount--;

                if (cellx.edgeCount == 0) {
                    topologicalSortS.add(cellx);
                }
            }
        }


        if (countEvaluated < (columns * rows)) {
            throw new CircularReferenceException(
                "circular dependency detected: " + countEvaluated +
                " cells evaluated");
        }

        System.out.println(String.format("%d %d", columns, rows));
        for(Cell cell : values.values()) {
            System.out.println(String.format("%.5f", cell.evaluatedValue));
        }
    }

    /**
     * Returns the columns value.
     *
     * @return  columns value.
     */
    public int getColumns() {
        return columns;
    }

    /**
     * Returns the rows value.
     *
     * @return  rows value.
     */
    public int getRows() {
        return rows;
    }


    /**
     * DOCUMENT ME!
     *
     * @param  string
     * @param  cell
     */
    private void addDependancyMap(final String string, final Cell cell) {
        Set<Cell> list = dependancyMap.get(string);

        if (null == list) {
            list = new HashSet<Cell>();
            dependancyMap.put(string, list);
        }

        list.add(cell);
    }

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    public class CircularReferenceException extends RuntimeException {
        private static final long serialVersionUID = 1L;

        /**
         * Creates a new CircularReferenceException object.
         *
         * @param  msg
         */
        public CircularReferenceException(final String msg) {
            super(msg);
        }
    }
}
