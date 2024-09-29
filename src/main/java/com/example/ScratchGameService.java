package com.example;

import javax.swing.*;
import java.util.Set;

public class ScratchGameService {
    public void playGame(Config config, String bet){

        GameResultService.setBetAmount(bet);
        String[][] matrix = createMatrix(config);
        GameResultService.setMatrix(matrix);

        Set<String> winningBonus = countBetCombination(matrix, bet, config);
        popUpPicture(matrix, winningBonus);

        System.out.println(GameResultService.getResultFormatted());
    }

    private void popUpPicture(String[][] matrix, Set<String> winningBonus) {
        PictureService pictureService = new PictureService(matrix, winningBonus);

        JFrame frame = new JFrame("Betting Game Visualization");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(pictureService);
        frame.pack();
        frame.setVisible(true);
    }

    private String[][] createMatrix(Config config) {
        MatrixService matrixService = new MatrixService();

        return matrixService.generateSymbolMatrix(config);
    }

    private Set<String> countBetCombination(String[][] matrix, String bet, Config config) {
        CountCombinationService countCombinationService = new CountCombinationService();

        return countCombinationService.findSequences(matrix, bet, config);
    }
}
