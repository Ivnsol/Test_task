package com.example;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;


public class MatrixService {
    int DEFAULT_ROWS = 3;
    int DEFAULT_COLUMNS = 3;

    public String[][] generateSymbolMatrix(Config config) {

        int rows = (config.getRows() != null) ? config.getRows() : DEFAULT_ROWS;
        int columns = (config.getColumns() != null) ? config.getColumns() : DEFAULT_COLUMNS;


        String[][] matrix = new String[rows][columns];

        Map<String, Integer> standardSymbolProbabilities = new HashMap<>();
        Map<String, Integer> bonusSymbolProbabilities = new HashMap<>();

        for (Map.Entry<String, Symbol> entry : config.getSymbols().entrySet()) {
            Symbol symbol = entry.getValue();
            if (symbol.getType().equals("standard")) {
                standardSymbolProbabilities.put(entry.getKey(), symbol.getRewardMultiplier().intValue());
            } else if (symbol.getType().equals("bonus")) {
                if (symbol.getRewardMultiplier() != null) {
                    bonusSymbolProbabilities.put(entry.getKey(), symbol.getRewardMultiplier().intValue());
                } else if (symbol.getExtra() != null) {
                    bonusSymbolProbabilities.put(entry.getKey(), symbol.getExtra());
                } else {
                    bonusSymbolProbabilities.put(entry.getKey(), 1);
                }
            }
        }

        // Генерируем матрицу с символами
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                matrix[row][col] = selectSymbolByType(standardSymbolProbabilities, bonusSymbolProbabilities);
            }
        }

        return matrix;
    }

    private String selectSymbolByType(Map<String, Integer> standardSymbolProbabilities,
                                            Map<String, Integer> bonusSymbolProbabilities) {
        Random random = new Random();

        boolean isStandard = random.nextBoolean();

        if (isStandard) {
            return selectSymbol(standardSymbolProbabilities);
        } else {
            return selectSymbol(bonusSymbolProbabilities);
        }
    }

    private String selectSymbol(Map<String, Integer> symbolProbabilities) {
        int totalWeight = 0;
        for (int weight : symbolProbabilities.values()) {
            totalWeight += weight;
        }

        Random random = new Random();
        int randomNumber = random.nextInt(totalWeight) + 1;

        int currentWeightSum = 0;
        for (Map.Entry<String, Integer> entry : symbolProbabilities.entrySet()) {
            currentWeightSum += entry.getValue();
            if (randomNumber <= currentWeightSum) {
                return entry.getKey();
            }
        }

        throw new RuntimeException("Не удалось выбрать символ.");
    }
}
