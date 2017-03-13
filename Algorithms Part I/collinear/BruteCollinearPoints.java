
import edu.princeton.cs.algs4.*;

public class BruteCollinearPoints {

    int segmentCount = 0;
    LineSegment[] segments;


    public BruteCollinearPoints(Point[] points) {

        if (points == null) {
            throw new java.lang.NullPointerException("the points array can't be null");
        }

        int total = points.length;

        segmentNode first = new segmentNode();
        segmentNode current = first;

        for (int i = 0; i < total; i++) {
            Point one = points[i];
            validate(one);
            for (int j = i + 1; j < total; j++) {
                Point two = points[j];
                validate(two);
                double slope = one.slopeTo(two);
                validateSlope(slope);
                for (int k = j + 1; k < total; k++) {
                    Point three = points[k];
                    validate(three);
                    double slope2 = one.slopeTo(three);
                    validateSlope(slope2);
                    if (slope == slope2) {
                        for (int l = k + 1; l < total; l++) {
                            Point four = points[l];
                            validate(four);
                            double slope3 = one.slopeTo(four);
                            validateSlope(slope3);
                            if (slope == slope3) {
                                Point[] line = {one, two, three, four};
                                current.value = generateSegment(line);
                                segmentNode next = new segmentNode();
                                current.next = next;
                                current = next;
                                segmentCount++;
                            }
                        }
                    }
                }

            }
        }

        current = first;
        segments = new LineSegment[segmentCount];

        for (int i = 0; i < segmentCount; i++) {
            segments[i] = current.value;
            current = current.next;
        }

    }
    public int numberOfSegments() {

        return segmentCount;

    }
    public LineSegment[] segments() {

        return segments;

    }

    private LineSegment generateSegment(Point[] points) {

        Quick.sort(points);

        int length = points.length;

        return new LineSegment(points[0], points[length - 1]);

    }

    private void validate(Point point) {
        if (point == null) {
            throw new java.lang.NullPointerException("the point can't be null");
        }
    }

    private void validateSlope(double slope) {
        if (slope == Double.NEGATIVE_INFINITY) {
            throw new java.lang.IllegalArgumentException("repeated point");
        }
    }

    private class segmentNode {
        LineSegment value;
        segmentNode next;
    }

    public static void main(String[] args) {
        /* YOUR CODE HERE */

        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();


    }

}