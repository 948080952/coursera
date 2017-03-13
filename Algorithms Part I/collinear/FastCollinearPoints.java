
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Quick;
import edu.princeton.cs.algs4.StdOut;
import java.util.Arrays;

public class FastCollinearPoints {

    int segmentCount = 0;
    LineSegment[] segments;

    public FastCollinearPoints(Point[] points) {

        int length = points.length;
        Point[] sortedPoint = new Point[length];

        for (int i = 0; i < length; i++) {
            sortedPoint[i] = points[i];
        }

        segmentNode firstNode = new segmentNode();
        segmentNode current = firstNode;

        for (int i = 0; i < length; i++) {

            Point origin = points[i];

            Arrays.sort(sortedPoint, origin.slopeOrder());

            int startIndex = 1;
            double slope = origin.slopeTo(sortedPoint[startIndex]);

            for (int j = 2; j < length; j++) {

                if (slope != origin.slopeTo(sortedPoint[j])) {
                    if (j - startIndex > 1) {
                        Point[] linearPoints = new Point[j - startIndex + 1];
                        for (int k = startIndex; k < j - startIndex; k++) {
                            linearPoints[k] = sortedPoint[k];
                        }
                        linearPoints[j - startIndex] = origin;
                        current.value = generateSegment(linearPoints);
                        current.next = new segmentNode();
                        current = current.next;
                        segmentCount++;
                    }
                    slope = origin.slopeTo(sortedPoint[j]);
                    startIndex = j;
                }
            }
        }

        current = firstNode;
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();


    }



}