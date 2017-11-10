/**
 * Created by daipei on 2017/11/10.
 */

import edu.princeton.cs.algs4.Picture;

import java.awt.*;

public class SeamCarver {

    private Picture picture;
    private static double BORDER_ENERGY = 1000;

    public SeamCarver(Picture picture) {
        this.picture = picture;
    }

    public Picture picture() {
        return picture;
    }

    public int width() {
        return picture.width();
    }

    public int height() {
        return picture.height();
    }

    public double energy(int x, int y) {
        validateCol(x);
        validateRow(y);
        if (isBorder(x, y)) {
            return BORDER_ENERGY;
        }
        Color left = picture.get(x - 1, y);
        Color top = picture.get(x, y - 1);
        Color focus = picture.get(x, y);
        double d1 = Math.pow(focus.getRed() - left.getRed(), 2);
        double d2 = Math.pow(focus.getGreen() - left.getGreen(), 2);
        double d3 = Math.pow(focus.getBlue() - left.getBlue(), 2);
        double d4 = Math.pow(focus.getRed() - top.getRed(), 2);
        double d5 = Math.pow(focus.getGreen() - top.getGreen(), 2);
        double d6 = Math.pow(focus.getBlue() - top.getBlue(), 2);
        return Math.sqrt(d1 + d2 + d3 + d4 + d5 + d6);
    }

    public int[] findHorizontalSeam() {
        int[] seams;

        return seams;
    }

    public int[] findVerticalSeam() {
        int[] seams;

        return seams;
    }

    public void removeHorizontalSeam(int[] seam) {

    }

    public void removeVerticalSeam(int[] seam) {

    }

    private void validateRow(int row) {
        if (row < 0 || row >= picture.height()) {
            throw new IllegalArgumentException();
        }
    }

    private void validateCol(int col) {
        if (col < 0 || col >= picture.width()) {
            throw new IllegalArgumentException();
        }
    }

    private boolean isBorder(int x, int y) {
        if (x == 0 || y == 0 || x == picture.width() - 1 || y == picture.height() - 1) {
            return true;
        }
        return false;
    }

}
