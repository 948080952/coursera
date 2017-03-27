/**
 * Created by daipei on 2017/3/26.
 */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;

public class PointSET {

    private SET<Point2D> point2DS;

    public PointSET() {
        point2DS = new SET<>();
    }

    public boolean isEmpty() {
        return point2DS.isEmpty();
    }

    public int size() {
        return point2DS.size();
    }

    public void insert(Point2D p) {
        point2DS.add(p);
    }

    public boolean contains(Point2D p) {
        return point2DS.contains(p);
    }

    public void draw() {
        for (Point2D point :
                point2DS) {
            point.draw();
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        SET<Point2D> rangeSet = new SET<>();

        for (Point2D point :
                point2DS) {
            if (point.x() >= rect.xmin() &&
                    point.x() <= rect.xmax() &&
                    point.y() >= rect.ymin() &&
                    point.y() <= rect.ymax()) {
                rangeSet.add(point);
            }
        }
        return rangeSet;
    }

    public Point2D nearest(Point2D p) {

        Point2D nearestPoint = null;
        double minDistance = 0;

        for (Point2D point :
                point2DS) {
            double distance = p.distanceTo(point);
            if (distance < minDistance) {
                nearestPoint = point;
            }
        }
        return nearestPoint;
    }

    public static void main(String[] args) {



    }

}
