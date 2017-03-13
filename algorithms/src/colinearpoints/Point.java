package colinearpoints;
import java.util.Comparator;

import edu.princeton.cs.algs4.StdDraw;

public class Point implements Comparable<Point> {

    private final int x;     // x-coordinate of this point
    private final int y;     // y-coordinate of this point
    
    // constructs the point (x, y)
    public Point(int x, int y) {
        if(x<0 ||x>32767) {
            throw new IllegalArgumentException();
        }
        if(y<0 ||y>32767) {
            throw new IllegalArgumentException();
        }
        this.x = x;
        this.y = y;
    }

    // draws this point
    public void draw() {
        StdDraw.point(x, y);
    }

    // draws the line segment from this point to that point
    public void drawTo(Point that) {
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    // string representation
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    // compare two points by y-coordinates, breaking ties by x-coordinates
    public int compareTo(Point that) {
        if(x == that.x && y == that.y) {
            return 0;
        } else if(y < that.y || y==that.y && x < that.x) {
            return -1;
        } else {
            return 1;
        }
    }

    // the slope between this point and that point
    public double slopeTo(Point that) {
        return (that.y-y)/(double)(that.x-x);
    }

    // compare two points by slopes they make with this point
    public Comparator<Point> slopeOrder() {
        return new Comparator<Point>() {

            @Override
            public int compare(Point o1, Point o2) {
                return new Double(slopeTo(o1)).compareTo(slopeTo(o2));
            }
        
        };
    }

    /**
     * Unit tests the Point data type.
     */
    public static void main(String[] args) {
        /* YOUR CODE HERE */
    }
    
}