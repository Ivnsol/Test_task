package com.example;

import javax.swing.*;
import java.awt.*;
import java.util.Set;

public class PictureService extends JPanel {

    private Object[][] matrix;
    private Set<String> winningSymbols;

    public PictureService(String[][] matrix, Set<String> winningSymbols) {
        this.matrix = matrix;
        this.winningSymbols = winningSymbols;
        setPreferredSize(new Dimension(400, 400));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int rows = matrix.length;
        int cols = matrix[0].length;
        int cellWidth = getWidth() / cols;
        int cellHeight = getHeight() / rows;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                g.setColor(Color.WHITE);
                g.fillRect(j * cellWidth, i * cellHeight, cellWidth, cellHeight);

                if (winningSymbols != null && winningSymbols.contains(String.valueOf(matrix[i][j]))) {
                    g.setColor(Color.YELLOW);
                } else {
                    g.setColor(Color.BLACK);
                }

                g.drawRect(j * cellWidth, i * cellHeight, cellWidth, cellHeight);
                g.drawString(String.valueOf(matrix[i][j]), j * cellWidth + cellWidth / 4, i * cellHeight + cellHeight / 2);
            }
        }

        if (winningSymbols != null) {
            g.setColor(Color.BLUE);
            g.drawString("Won with: " + String.join(", ", winningSymbols), 10, getHeight() - 20);
        }
    }

}
