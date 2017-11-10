/**
 * Created by daipei on 2017/11/10.
 */

import edu.princeton.cs.algs4.Picture;

public class SeamCarver {

    private Picture picture;

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

        return 0;
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
