package graphics;
import java.util.List;


/**
 * Line class.
 *
 * @author HadasNeuman
 */
public class Line {
    private Point start;
    private Point end;

    /**
     * A constructor - creates a Line between two specified points.
     *
     * @param start is the start point
     * @param end is the end point
     */
    public Line(Point start, Point end) {
        this.start = start;
        this.end = end;
    }

    /**
     * A constructor - creates a Line between four specified coordinates.
     *
     * @param x1 is the x coordinate of the start point
     * @param y1 is the y coordinate of the start point
     * @param x2 is the x coordinate of the end point
     * @param y2 is the y coordinate of the end point
     */
    public Line(double x1, double y1, double x2, double y2) {

        Point start1 = new Point(x1, y1);
        Point end1 = new Point(x2, y2);
        this.start = start1;
        this.end = end1;
    }

    /**
     * Accessor - gets the private variable start.
     *
     * @return the value of the start point
     */
    public Point getStart() {
        return this.start;
    }

    /**
     *  Accessor - gets the private variable end.
     *
     * @return the value of the end point
     */
    public Point getEnd() {
        return this.end;
    }


    /**
     * Calculates the distance between the start and the end points.
     *
     * @return the length of the line
     */
    public double length() {
        double dist = start.distance(end);
        return dist;
    }

    /**
     * Calculates the middle point between the start and the end points.
     *
     * @return the middle point of the line
     */
    public Point middle() {
        double middleX = ((this.start.getX() + this.end.getX()) / 2);
        double middleY = ((this.start.getY() + this.end.getY()) / 2);
        return new Point(middleX, middleY);
    }

    /**
     * Gets the start point of the line.
     *
     * @return the start point of the line
     */
    public Point start() {
        return this.start;
    }

    /**
     * Gets the end point of the line.
     *
     * @return the end point of the line
     */
    public Point end() {
        return this.end;
    }

    /**
     * Find if a given coordinate is in two given limit.
     *
     * @return true if the x coordinate is on the line range, false otherwise
     * @param coor is a coordinate that might be on the line
     * @param limit1 is the first limit
     * @param limit2 is the second limit
     */
    public boolean isCoorOnLimits(double coor, double limit1, double limit2) {

        double max;
        double min;
        double d = 0.000005; /* Double calculation mistake */

        if (limit1 > limit2) {
            max = limit1;
            min = limit2;
        } else {
            min = limit1;
            max = limit2;
        }
        return (((min - d) <= coor) && (coor <= (max + d)));
    }

    /**
     * Find if a given point is on the line range (but not the line).
     *
     * @return true if the point is on the line range, false otherwise
     * @param p is the point that might be on the line
     */
    public boolean isPointOnLineRange(Point p) {
        return (this.isCoorOnLimits(p.getX(), this.start.getX(), this.end.getX())
                && (this.isCoorOnLimits(p.getY(), this.start.getY(), this.end.getY())));
    }

    /**
     * Calculates the line equation.
     *
     * @return an array containing the line slope and the y-intercept
     */
    public LineEquation findLineEq() {
        double m;
        double n;
        //the slop
        if ((this.end.getX() -  this.start.getX()) == 0) {
            m = Double.POSITIVE_INFINITY;
            n = this.start.getX();
        } else {
            m = (this.end.getY() -  this.start.getY()) / (this.end.getX() -  this.start.getX());

            //the y-intercept
            n = this.end.getY() - (m * this.end.getX());
        }

        //System.out.println("m " + lineEq[0] + "   n  " + lineEq[1]);

        return new LineEquation(m, n);
    }

    /**
     * Find if there is an intersection point between two lines.
     *
     * @return true if the lines intersect, false otherwise
     * @param other is another line that might intersect our line
     */

    public boolean isIntersecting(Line other) {
        return (this.intersectionWith(other) != null);
    }

    /**
     * Calculates the intersection point if the lines intersect, and null otherwise.
     *
     * @return the intersection point if the lines intersect, and null otherwise.
     * @param other is another line that might intersect our line
     */
    public Point intersectionWith(Line other) {
        //Finding the lines equation

        Point ret = null;

        if (!this.equals(other)) {
            LineEquation lineEq1 = this.findLineEq();
            LineEquation lineEq2 = other.findLineEq();

            //Finding the the intercept point between the lines
            if (lineEq1.getM() != lineEq2.getM()) {
                double xIntersect;
                double yIntersect;
                if ((lineEq1.getM() == Double.POSITIVE_INFINITY) || (lineEq2.getM() == Double.POSITIVE_INFINITY)) {
                    if (lineEq1.getM() == Double.POSITIVE_INFINITY) {
                        xIntersect = lineEq1.getN();
                        yIntersect = (xIntersect * lineEq2.getM()) + lineEq2.getN();
                    } else {
                        xIntersect = lineEq2.getN();
                        yIntersect = (xIntersect * lineEq1.getM()) + lineEq1.getN();
                    }
                } else {
                    xIntersect = (lineEq2.getN() - lineEq1.getN()) / (lineEq1.getM() - lineEq2.getM());
                    yIntersect = (xIntersect * lineEq1.getM()) + lineEq1.getN();
                }

                //Finding if the point is in the lines
                Point point = new Point(xIntersect, yIntersect);
                if ((this.isPointOnLineRange(point)) && (other.isPointOnLineRange(point))) {
                    ret = point;
                }
            }
        }
        return ret;
    }

    /**
     * Checks if two lines are equal.
     *
     * @return true is the lines are equal, false otherwise
     * @param other is another line that might be equal to our line
     */
    public boolean equals(Line other) {
        return ((this.start.equals(other.start)) && (this.end.equals(other.end)));
    }

    /**
     * Checks if this line intersect with a given rectangle.
     *
     * @return the closest intersection point to the start of the line,
     *  or null if this line does not intersect with the rectangle
     * @param rect is the rectangle that may intersect the line
     */
    public Point closestIntersectionToStartOfLine(Rectangle rect) {
        List<Point> pointList = rect.intersectionPoints(this);
        Point closestPoint = null;
        if (pointList != null) {
            double dist = 100000; // a minimum value
            double tempDist; // a minimum value

            for (Point currPoint : pointList) {
                tempDist = currPoint.distance(this.start);
                if (tempDist < dist) {
                    dist = tempDist;
                    closestPoint = currPoint;
                }
            }
        }
        return closestPoint;
    }
}
