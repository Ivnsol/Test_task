package com.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameResultService {
    private static String bet_amount;
    private static String[][] matrix;
    private static int reward;
    private static Map<String, List<String>> applied_winning_combinations = new HashMap<>();
    private static String applied_bonus_symbols;

    public static void setBetAmount(String betAmount) {
        GameResultService.bet_amount = betAmount;
    }

    public static void setMatrix(String[][] inputMatrix) {
        GameResultService.matrix = inputMatrix;
    }

    public static void setReward(int newReward) {
        GameResultService.reward = newReward;
    }

    public static void setAppliedWinningCombinations(String symbol, String combination) {
        applied_winning_combinations.putIfAbsent(symbol, new ArrayList<>());
        applied_winning_combinations.get(symbol).add(combination);
    }

    public static void setAppliedBonusSymbols(String bonusSymbols) {
        GameResultService.applied_bonus_symbols = bonusSymbols;
    }

    public static String getResultFormatted() {
        StringBuilder result = new StringBuilder();
        result.append("{\n");
        result.append("  \"bet_amount\": ").append(bet_amount).append(",\n");
        result.append("  \"matrix\": [\n");

        // Форматирование матрицы
        for (int i = 0; i < matrix.length; i++) {
            result.append("    [");
            for (int j = 0; j < matrix[i].length; j++) {
                result.append("\"").append(matrix[i][j]).append("\"");
                if (j < matrix[i].length - 1) {
                    result.append(", ");
                }
            }
            result.append("]");
            if (i < matrix.length - 1) {
                result.append(",\n");
            }
        }
        result.append("\n  ],\n");

        if (reward == 0) {
            result.append("  \"reward\": 0\n");
            result.append("}");
        } else {
            result.append("  \"reward\": ").append(reward).append(",\n");

            result.append("  \"applied_winning_combinations\": {\n");
            for (Map.Entry<String, List<String>> entry : applied_winning_combinations.entrySet()) {
                result.append("    \"").append(entry.getKey()).append("\": ").append(entry.getValue()).append("\n");
            }
            result.append("  },\n");

            result.append("  \"applied_bonus_symbol\": \"").append(applied_bonus_symbols).append("\"\n");
            result.append("}");
        }

        return result.toString();
    }
}
