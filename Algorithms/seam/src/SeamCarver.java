/**
 * Created by daipei on 2017/11/10.
 */

import edu.princeton.cs.algs4.Picture;

import java.awt.*;
import java.util.ArrayList;

public class SeamCarver {

    private Picture picture;
    private static double BORDER_ENERGY = 1000;
    private ArrayList<ArrayList<Integer>> colorInfo;
    private int width;
    private int height;

    public SeamCarver(Picture picture) {
        this.picture = picture;
        width = picture.width();
        height = picture.height();
        colorInfo = new ArrayList<>();
        for (int i = 0; i < width; i++) {
            colorInfo.add(new ArrayList<Integer>());
            for (int j = 0; j < 0; j++) {
                Color color = picture.get(i, j);
                colorInfo.get(i).add(color.hashCode());
            }
        }
    }

    public Picture picture() {
        if (picture.width() != width || picture.height() != height) {
            updatePicture();
        }
        return picture;
    }

    private void updatePicture() {
        Picture carvedPic = new Picture(width, height);
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                Color color = new Color(colorInfo.get(i).get(j));
                carvedPic.set(i, j, color);
            }
        }
    }

    public int width() {
        return width;
    }

    public int height() {
        return height;
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
        int[] seams = new int[width];
        for (int i = 0; i < width; i++) {
            for (int j = 1; j < height; j++) {
                if (energy(i, j) < energy(seams[i], j)) {
                    seams[i] = j;
                }
            }
        }
        if (seams.length > 2) {
            seams[0] = seams[1] - 1;
            seams[seams.length - 1] = seams[seams.length - 2] - 1;
        }
        return seams;
    }

    public int[] findVerticalSeam() {
        int[] seams = new int[height];
        for (int j = 0; j < height; j++) {
            for (int i = 1; i < height; i++) {
                if (energy(i, j) < energy(seams[j], j)) {
                    seams[j] = i;
                }
            }
        }
        if (seams.length > 2) {
            seams[0] = seams[1] - 1;
            seams[seams.length - 1] = seams[seams.length - 2] - 1;
        }
        return seams;
    }

    public void removeHorizontalSeam(int[] seam) {

    }

    public void removeVerticalSeam(int[] seam) {

    }

    private void validateRow(int row) {
        if (row < 0 || row >= height) {
            throw new IllegalArgumentException();
        }
    }

    private void validateCol(int col) {
        if (col < 0 || col >= width) {
            throw new IllegalArgumentException();
        }
    }

    private boolean isBorder(int x, int y) {
        if (x == 0 || y == 0 || x == width - 1 || y == height - 1) {
            return true;
        }
        return false;
    }

}
