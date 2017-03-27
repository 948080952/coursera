/**
 * Created by daipei on 2017/3/26.
 */
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

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

        return null;
    }

    public Point2D nearest(Point2D p) {

        return null;
    }

    public static void main(String[] args) {
        
    }
}
