package graphics;
/**
 * @author Hadas Neuman
 *
 */
public class LineEquation {
    private double m;
    private double n;

    /**
     * Constructor: build a new LineEquation from a slope and a y-intersection point.
     * @param m is the slope
     * @param n is the y-intersection
     */
    public LineEquation(double m, double n) {
        this.m = m;
        this.n = n;
    }

    /**
     * Accessor: get the slope of the line.
     *
     * @return the line slope
     */
    public double getM() {
        return this.m;
    }

    /**
     * Accessor: get the y-intersection of the line.
     *
     * @return the line  y-intersection point
     */
    public double getN() {
        return this.n;
    }
}
