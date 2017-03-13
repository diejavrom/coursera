package colinearpoints;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FastCollinearPoints {

    private LineSegment[] segments;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        if (points == null) {
            throw new NullPointerException(" points is null");
        }
        for (Point p : points) {
            if (p == null) {
                throw new NullPointerException("exists a point that is null");
            }
        }
        List<LineSegment> result = new ArrayList<>();
        for (int i=0; i< points.length; i++) {
            Point pi = points[i];
            List<Point> othersL = new ArrayList<Point>();
//            Double[] odouble = new Double[points.length-1];
            for(int j=i+1; j<points.length; j++) {
                Point pj = points[j];
                othersL.add(pj);
//                odouble[k] = pi.slopeTo(pj);
            }
            
            Point[] others = othersL.toArray(new Point[othersL.size()]);
            Arrays.sort(others, pi.slopeOrder());
//            Arrays.sort(odouble);
            int cant = 0;
            int desde = 0;
            List<Point> colinears = new ArrayList<>();
            colinears.add(pi);
            for(int j=desde; j < others.length-1; j++) {
                if(pi.slopeTo(others[j]) == pi.slopeTo(others[j+1])) {
                    cant++;
                    if(j==desde) {
                        colinears.add(others[j]);
                    }
                    colinears.add(others[j+1]);
                } else {
                    desde = j+1;
                    if(cant >= 2) {
                        Point[] colArray = colinears.toArray(new Point[colinears.size()]);
                        Arrays.sort(colArray);
                        result.add(new LineSegment(colArray[0], colArray[colArray.length-1]));
                    }
                    cant=0;
                    colinears.clear();
                    colinears.add(pi);
                }
            }
        }
        
        this.segments = result.toArray(new LineSegment[result.size()]);
        System.out.println(result);
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