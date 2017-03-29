/**
 * Created by daipei on 2017/3/26.
 */
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.SET;

import java.awt.*;

public class KdTree {

    private TreeNode rootNode;
    private int size;

    private class TreeNode {

        public Point2D point = null;
        public boolean isVertical;
        public TreeNode left = null;
        public TreeNode right = null;

        public TreeNode(boolean isVertical) {
            this.isVertical = isVertical;
        }
    }

    public KdTree() {
        this.size = 0;
    }

    public boolean isEmpty() {
        if (size == 0) {
            return true;
        }
        return false;
    }

    public int size() {
        return size;
    }

    public void insert(Point2D p) {
        if (rootNode == null) {
            rootNode = new TreeNode(true);
            rootNode.point = p;
        } else {
            insertCore(rootNode, p);
        }
    }

    private void insertCore(TreeNode node, Point2D p) {
        if (node.point.equals(p)) {
            return;
        } else {
            if (node.isVertical) {
                if (p.x() <= node.point.x()) {
                    if (node.left == null) {
                        size++;
                        node.left = new TreeNode(false);
                        node.left.point = p;
                        return;
                    } else {
                        insertCore(node.left, p);
                    }
                } else {
                    if (node.right == null) {
                        size++;
                        node.right = new TreeNode(false);
                        node.right.point = p;
                        return;
                    } else {
                        insertCore(node.right, p);
                    }
                }
            } else {
                if (p.y() <= node.point.y()) {
                    if (node.left == null) {
                        size++;
                        node.left = new TreeNode(true);
                        node.left.point = p;
                        return;
                    } else {
                        insertCore(node.left, p);
                    }
                } else {
                    if (node.right == null) {
                        size++;
                        node.right = new TreeNode(true);
                        node.right.point = p;
                        return;
                    } else {
                        insertCore(node.right, p);
                    }
                }
            }
        }
    }

    public boolean contains(Point2D p) {
        TreeNode pNode = rootNode;
        while (pNode != null) {
            if (p.equals(pNode.point)) {
                return true;
            }
            if (pNode.isVertical) {
                if (p.x() <= pNode.point.x()) {
                    pNode = pNode.left;
                } else {
                    pNode = pNode.right;
                }
            } else {
                if (p.y() <= pNode.point.y()) {
                    pNode = pNode.left;
                } else {
                    pNode = pNode.right;
                }
            }
        }
        return false;
    }

    public void draw() {

        RectHV border = new RectHV(0, 0, 1, 1);
        drawCore(rootNode, null, border);

    }

    private void drawCore(TreeNode child, TreeNode parent, RectHV border) {

        if (child == null) {
            return;
        }

        StdDraw.setPenColor(Color.BLACK);
        StdDraw.setPenRadius(0.01);
        child.point.draw();

        Point2D point1 = null, point2 = null;
        RectHV leftBorder = null;
        RectHV rightBorder = null;

        StdDraw.setPenRadius();
        if (parent == null) {

            double x = child.point.x();
            point1 = new Point2D(x, border.ymin());
            point2 = new Point2D(x, border.ymax());

            StdDraw.setPenColor(Color.red);

            leftBorder = new RectHV(border.xmin(), border.ymin(), child.point.x(), border.ymax());
            rightBorder = new RectHV(child.point.x(), border.ymin(), border.xmax(), border.ymax());

        } else {

            if (parent.isVertical) {

                double x1 = parent.point.x();
                double y1 = child.point.y();
                double x2 = 0, y2 = y1;

                if (child.point.x() < parent.point.x()) {
                    x2 = border.xmin();
                } else {
                    x2 = border.xmax();
                }
                point1 = new Point2D(x1, y1);
                point2 = new Point2D(x2, y2);

                StdDraw.setPenColor(Color.blue);

                leftBorder = new RectHV(border.xmin(), border.ymin(), border.xmax(), child.point.y());
                rightBorder = new RectHV(border.xmin(), child.point.y(), border.xmax(), border.ymax());

            } else {

                double x1 = child.point.x();
                double y1 = parent.point.y();
                double x2 = x1, y2 = 0;

                if (child.point.y() < parent.point.y()) {
                    y2 = border.ymin();
                } else {
                    y2 = border.ymax();
                }
                point1 = new Point2D(x1, y1);
                point2 = new Point2D(x2, y2);

                StdDraw.setPenColor(Color.red);

                leftBorder = new RectHV(border.xmin(), border.ymin(), child.point.x(), border.ymax());
                rightBorder = new RectHV(child.point.x(), border.ymin(), border.xmax(), border.ymax());
            }
        }

        point1.drawTo(point2);

        drawCore(child.left, child, leftBorder);
        drawCore(child.right, child, rightBorder);

    }

    public Iterable<Point2D> range(RectHV rect) {

        SET<Point2D> range = new SET<>();

        rangeSearch(rect, rootNode, range);

        return range;
    }

    private void rangeSearch(RectHV rect, TreeNode node, SET<Point2D> range) {

        if (node == null) {
            return;
        }

        if (rect.contains(node.point)) {
            range.add(node.point);
        }

        if (node.isVertical) {

            if (rect.xmin() <= node.point.x()) {
                rangeSearch(rect, node.left, range);
            }

            if (rect.xmax() > node.point.x()) {
                rangeSearch(rect, node.right, range);
            }

        } else {

            if (rect.ymin() <= node.point.y()) {
                rangeSearch(rect, node.left, range);
            }

            if (rect.ymax() > node.point.y()) {
                rangeSearch(rect, node.right, range);
            }

        }

    }


    public Point2D nearest(Point2D p) {

        return findNearest(p, rootNode.point, rootNode);

    }

    private Point2D findNearest(Point2D p, Point2D near, TreeNode node) {

        if (node == null) {
            return near;
        }

        if (p.distanceTo(node.point) < p.distanceTo(near)) {
            near = node.point;
        }

        Point2D near1 = near, near2 = near;

        if (node.isVertical) {

            if (p.x() <= node.point.x()) {
                near1 = findNearest(p, near, node.left);
            } else {
                near1 = findNearest(p, near, node.right);
            }

            if (p.distanceTo(near1) > p.distanceTo(new Point2D(node.point.x(), p.y()))) {
                if (p.x() > node.point.x()) {
                    near2 = findNearest(p, near, node.left);
                } else {
                    near2 = findNearest(p, near, node.right);
                }
            }

        } else {

            if (p.y() <= node.point.y()) {
                near1 = findNearest(p, near, node.left);
            } else {
                near1 = findNearest(p, near, node.right);
            }

            if (p.distanceTo(near1) > p.distanceTo(new Point2D(p.x(), node.point.y()))) {
                if (p.y() > node.point.y()) {
                    near2 = findNearest(p, near, node.left);
                } else {
                    near2 = findNearest(p, near, node.right);
                }
            }

        }

        return p.distanceTo(near1) < p.distanceTo(near2) ? near1 : near2;

    }

    public static void main(String[] args) {

    }
}
