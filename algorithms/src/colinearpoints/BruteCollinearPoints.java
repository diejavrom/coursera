package colinearpoints;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BruteCollinearPoints {

    private LineSegment[] segments;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new NullPointerException(" points is null");
        }
        for (Point p : points) {
            if (p == null) {
                throw new NullPointerException("exists a point that is null");
            }
        }
        List<LineSegment> result = new ArrayList<>();
        Point[] line = new Point[4];
        for (int i = 0; i < points.length; i++) {
            Point pi = points[i];
            line[0] = pi;
            for (int j = i + 1; j < points.length; j++) {
                Point pj = points[j];
                line[1] = pj;
                double slope_i_j = pi.slopeTo(pj);
                for (int k = j + 1; k < points.length; k++) {
                    Point pk = points[k];
                    line[2] = pk;
                    double slope_i_k = pi.slopeTo(pk);
                    for (int l = k + 1; l < points.length; l++) {
                        Point pl = points[l];
                        line[3] = pl;
                        double slope_i_l = pi.slopeTo(pl);
                        if (slope_i_j == slope_i_k && slope_i_k == slope_i_l) {
                            Arrays.sort(line);
                            result.add(new LineSegment(line[0], line[3]));
                        }
                    }
                }
            }
        }
        this.segments = result.toArray(new LineSegment[result.size()]);
    }

    // the number of line segments
    public int numberOfSegments() {
        return segments.length;
    }

    // the line segments
    public LineSegment[] segments() {
        return segments;
    }

}