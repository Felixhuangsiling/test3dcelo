import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class tree {
    public static void main(String[] args) {
        Scanner sc = null;
        try {
            sc = new Scanner(new File("treemap"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        List<List<Integer>> grid = new ArrayList<>();
        while (sc.hasNextLine()) {
            String row = sc.nextLine();
            if (row.isEmpty()) {
                break;
            }
            List<Integer> rowList = new ArrayList<>();
            for (char c : row.toCharArray()) {
                rowList.add(Character.getNumericValue(c));
            }
            grid.add(rowList);
        }
        int visibleTrees = countVT(grid);
        System.out.println("Total visible trees: " + visibleTrees);
        int highScore = findHighScore(grid);
        System.out.println("Hightest scenic score: " + highScore);
        sc.close();
    }

    public static int countVT(List<List<Integer>> grid) {
        int rows = grid.size();
        int cols = grid.get(0).size();
        int count = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int currentHeight = grid.get(i).get(j);
                if (i == 0 || i == rows - 1 || j == 0 || j == cols - 1) {
                    count++;
                    continue;
                }
                boolean leftVisible = true;
                boolean rightVisible = true;
                boolean upVisible = true;
                boolean downVisible = true;

                // left
                for (int k = 0; k < j; k++) {
                    if (grid.get(i).get(k) >= currentHeight) {
                        leftVisible = false;
                        break;
                    }
                }
                // right
                for (int k = j + 1; k < cols; k++) {
                    if (grid.get(i).get(k) >= currentHeight) {
                        rightVisible = false;
                        break;
                    }
                }
                // up
                for (int k = 0; k < i; k++) {
                    if (grid.get(k).get(j) >= currentHeight) {
                        upVisible = false;
                        break;
                    }
                }
                // down
                for (int k = i + 1; k < rows; k++) {
                    if (grid.get(k).get(j) >= currentHeight) {
                        downVisible = false;
                        break;
                    }
                }
                if (leftVisible || rightVisible || upVisible || downVisible) {
                    count++;
                }
            }
        }

        return count;
    }
    public static int findHighScore(List<List<Integer>> grid) {
        int rows = grid.size();
        int cols = grid.get(0).size();
        int highestScore = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int currentHeight = grid.get(i).get(j);
                int scenicScore = calculScore(grid, i, j, currentHeight);
                highestScore = Math.max(highestScore, scenicScore);
            }
        }
        return highestScore;
    }
    public static int calculScore(List<List<Integer>> grid, int row, int col, int height) {
        if (row == 0 || row == grid.size() - 1 || col == 0 || col == grid.get(0).size() - 1) {
            return 0;
        }
        int rows = grid.size();
        int cols = grid.get(0).size();
        int left = 0;
        int right = 0;
        int up = 0;
        int down = 0;
        for (int i = col - 1; i >= 0; i--) {
            left++;
            if (grid.get(row).get(i) >= height) {
                break;
            }
        }
        for (int i = col + 1; i < cols; i++) {
            right++;
            if (grid.get(row).get(i) >= height) {
                break;
            }
        }
        for (int i = row - 1; i >= 0; i--) {
            up++;
            if (grid.get(i).get(col) >= height) {
                break;
            }
        }
        for (int i = row + 1; i < rows; i++) {
            down++;
            if (grid.get(i).get(col) >= height) {
                break;
            }
        }
        return left*right*up*down;
    }
}
