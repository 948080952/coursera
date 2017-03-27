/**
 * Created by daipei on 2017/3/26.
 */
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

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
