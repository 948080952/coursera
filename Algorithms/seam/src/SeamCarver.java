/**
 * Created by daipei on 2017/11/10.
 */

import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stopwatch;
import java.awt.Color;
import java.util.ArrayList;

public class SeamCarver {

    private static double BORDER_ENERGY = 1000;
    private ArrayList<ArrayList<Integer>> colorInfo;
    private int width;
    private int height;

    public SeamCarver(Picture picture) {
        if (picture == null) {
            throw new IllegalArgumentException();
        }
        width = picture.width();
        height = picture.height();
        colorInfo = new ArrayList<>();
        for (int i = 0; i < width; i++) {
            colorInfo.add(new ArrayList<Integer>());
            for (int j = 0; j < height; j++) {
                Color color = picture.get(i, j);
                colorInfo.get(i).add(color.hashCode());
            }
        }
    }

    public Picture picture() {
        return updatePicture();
    }

    private Picture updatePicture() {
        Picture carvedPic = new Picture(width, height);
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                Color color = new Color(colorInfo.get(i).get(j));
                carvedPic.set(i, j, color);
            }
        }
        return carvedPic;
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
        Color left = new Color(colorInfo.get(x - 1).get(y));
        Color top = new Color(colorInfo.get(x).get(y - 1));
        Color bottom = new Color(colorInfo.get(x).get(y + 1));
        Color right = new Color(colorInfo.get(x + 1).get(y));
        double d1 = Math.pow(right.getRed() - left.getRed(), 2);
        double d2 = Math.pow(right.getGreen() - left.getGreen(), 2);
        double d3 = Math.pow(right.getBlue() - left.getBlue(), 2);
        double d4 = Math.pow(bottom.getRed() - top.getRed(), 2);
        double d5 = Math.pow(bottom.getGreen() - top.getGreen(), 2);
        double d6 = Math.pow(bottom.getBlue() - top.getBlue(), 2);
        return Math.sqrt(d1 + d2 + d3 + d4 + d5 + d6);
    }

    public int[] findHorizontalSeam() {
        int[] seams;
        int[][] edgeTo = new int[width][height];
        double[][] energyMatrix = energyMatrix();
        for (int i = 1; i < width; i++) {
            for (int j = 0; j < height; j++) {
                relax(i, j, edgeTo, energyMatrix, false);
            }
        }
        seams = seam(edgeTo, energyMatrix, false);
        return seams;
    }

    public int[] findVerticalSeam() {
        int[] seams;
        int[][] edgeTo = new int[width][height];
        double[][] energyMatrix = energyMatrix();
        for (int j = 1; j < height; j++) {
            for (int i = 0; i < width; i++) {
                relax(i, j, edgeTo, energyMatrix, true);
            }
        }
        seams = seam(edgeTo, energyMatrix, true);
        return seams;
    }

    private double[][] energyMatrix() {
        double[][] energy = new double[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                energy[i][j] = energy(i, j);
            }
        }
        return energy;
    }

    private void relax(int x, int y, int[][] edgeTo, double[][] energyMatrix, boolean vertical) {
        double minEnergy = Double.MAX_VALUE;
        int neighborID = 0;
        int[] neighbors = neighbor(vertical ? x : y, vertical);
        if (vertical) {
            for (int id : neighbors) {
                if (energyMatrix[id][y - 1] < minEnergy) {
                    minEnergy = energyMatrix[id][y - 1];
                    neighborID = id;
                }
            }
        } else {
            for (int id : neighbors) {
                if (energyMatrix[x - 1][id] < minEnergy) {
                    minEnergy = energyMatrix[x - 1][id];
                    neighborID = id;
                }
            }
        }
        energyMatrix[x][y] += minEnergy;
        edgeTo[x][y] = neighborID;
    }

    private int[] seam(int[][] edgeTo, double[][] energyMatrix, boolean vertical) {
        int[] seam;
        int index = 0;
        if (vertical) {
            seam = new int[height];
            for (int i = 1; i < width; i++) {
                if (energyMatrix[i][height - 1] < energyMatrix[index][height - 1]) {
                    index = i;
                }
            }
            seam[height - 1] = index;
            for (int j = height - 2; j >= 0; j--) {
                seam[j] = edgeTo[index][j + 1];
                index = seam[j];
            }
        } else  {
            seam = new int[width];
            for (int j = 1; j < height; j++) {
                if (energyMatrix[width - 1][j] < energyMatrix[width - 1][index]) {
                    index = j;
                }
            }
            seam[width - 1] = index;
            for (int i = width - 2; i >= 0; i--) {
                seam[i] = edgeTo[i + 1][index];
                index = seam[i];
            }
        }
        return seam;
    }

    private int[] neighbor(int key, boolean vertical) {
        int[] neighbor;
        if ((vertical && width == 1) || (!vertical && height == 1)) {
            neighbor = new int[1];
            neighbor[0] = key;
        } else if (key == 0) {
            neighbor = new int[2];
            neighbor[0] = key;
            neighbor[1] = key + 1;
        } else if ((key == width - 1 && vertical) || (key == height - 1 && !vertical)) {
            neighbor = new int[2];
            neighbor[0] = key - 1;
            neighbor[1] = key ;
        } else {
            neighbor = new int[3];
            neighbor[0] = key - 1;
            neighbor[1] = key;
            neighbor[2] = key + 1;
        }
        return neighbor;
    }

    public void removeHorizontalSeam(int[] seam) {
        if (seam == null) {
            throw new IllegalArgumentException();
        }
        if (seam.length != width) {
            throw new IllegalArgumentException();
        }
        for (int row : seam) {
            validateRow(row);
        }
        validateSeam(seam);
        for (int i = 0; i < width; i++) {
            int target = seam[i];
            colorInfo.get(i).remove(target);
        }
        height--;
    }

    public void removeVerticalSeam(int[] seam) {
        if (seam == null) {
            throw new IllegalArgumentException();
        }
        if (seam.length != height) {
            throw new IllegalArgumentException();
        }
        for (int col : seam) {
            validateCol(col);
        }
        validateSeam(seam);
        for (int i = 0; i < width - 1; i++) {
            for (int j = 0; j < height; j++) {
                int target = seam[j];
                if (i < target) {
                    continue;
                }
                colorInfo.get(i).set(j, colorInfo.get(i + 1).get(j));
            }
        }
        width--;
        colorInfo.remove(width);
    }

    private void validateSeam(int[] seam) {
        for (int i = 1; i < seam.length; i++) {
           if (Math.abs(seam[i] - seam[i - 1]) > 1) {
               throw new IllegalArgumentException();
           }
        }
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

    public static void main(String[] args) {
        if (args.length != 3) {
            StdOut.println("Usage:\njava ResizeDemo [image filename] [num cols to remove] [num rows to remove]");
            return;
        }

        Picture inputImg = new Picture(args[0]);
        int removeColumns = Integer.parseInt(args[1]);
        int removeRows = Integer.parseInt(args[2]);

        StdOut.printf("image is %d columns by %d rows\n", inputImg.width(), inputImg.height());
        SeamCarver sc = new SeamCarver(inputImg);

        Stopwatch sw = new Stopwatch();

        for (int i = 0; i < removeRows; i++) {
            int[] horizontalSeam = sc.findHorizontalSeam();
            sc.removeHorizontalSeam(horizontalSeam);
        }

        for (int i = 0; i < removeColumns; i++) {
            int[] verticalSeam = sc.findVerticalSeam();
            sc.removeVerticalSeam(verticalSeam);
        }
        Picture outputImg = sc.picture();

        StdOut.printf("new image size is %d columns by %d rows\n", sc.width(), sc.height());

        StdOut.println("Resizing time: " + sw.elapsedTime() + " seconds.");
        inputImg.show();
        outputImg.show();
    }

}
