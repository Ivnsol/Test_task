package com.example;

import java.util.*;


public class CountCombinationService {

    int MIN_SEQUENCE_LENGTH = 3;
    private final int[][] DIRECTIONS = {
            {0, 1},
            {1, 0},
            {1, 1},
            {1, -1}
    };

    public Set<String> findSequences(String[][] matrix, String bet, Config config) {
        Integer betAsInt = Integer.parseInt(bet);
        Integer reward = betAsInt;

        int rows = matrix.length;
        int cols = matrix[0].length;
        boolean[][] visited = new boolean[rows][cols];
        Map<String, Integer> bonusCounts = new HashMap<>();
        Map<Integer, WinCombination>  winCombinations = initializeRewardMap(config);
        Map<String, String> winningSymbolsWithCombination = new HashMap<>();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (!visited[i][j]) {
                    if (isBonusSymbol(matrix[i][j])) {
                        String bonusSymbol = matrix[i][j];
                        bonusCounts.put(bonusSymbol, bonusCounts.getOrDefault(bonusSymbol, 0) + 1);
                    } else {
                        int[] result = exploreSequence(matrix, i, j, visited);
                        int totalLength = result[0] > 9 ? 9 : result[0];


                        if (totalLength >= MIN_SEQUENCE_LENGTH) {
                            winningSymbolsWithCombination.put(matrix[i][j], getCombination(totalLength));

                            if (reward != betAsInt) {
                                reward = reward +
                                        betAsInt * config.getSymbols().get(matrix[i][j]).getRewardMultiplier().intValue()
                                                * winCombinations.get(totalLength).getCount();
                            } else {
                                reward = betAsInt * config.getSymbols().get(matrix[i][j]).getRewardMultiplier().intValue()
                                        * winCombinations.get(totalLength).getCount();
                            }
                        }
                    }
                }
            }
        }

        if (reward > betAsInt) {
            StringBuilder st = new StringBuilder();
            if (!bonusCounts.isEmpty()) {

                for (Map.Entry<String, Integer> entry : bonusCounts.entrySet()) {
                    if (entry.getKey().contains("x")){
                        st.append(entry.getKey()).append(" applied for ").append(entry.getValue()).append(" times; ");
                        reward = reward * (config.getSymbols().get(entry.getKey()).getRewardMultiplier() * entry.getValue());
                    } else if (entry.getKey().contains("+")) {
                        st.append(entry.getKey()).append(" applied for ").append(entry.getValue()).append(" times; ");
                        reward = reward + (config.getSymbols().get(entry.getKey()).getExtra() * entry.getValue());
                    }
                }
            }

            GameResultService.setReward(reward);

            for (Map.Entry<String, String> entry: winningSymbolsWithCombination.entrySet()) {
                GameResultService.setAppliedWinningCombinations(entry.getKey(), entry.getValue());
            }

            GameResultService.setAppliedBonusSymbols(st.toString());

            return bonusCounts.keySet();
        } else {
            GameResultService.setReward(0);
            return null;
        }

    }

    private int[] exploreSequence(Object[][] matrix, int row, int col, boolean[][] visited) {
        Object value = matrix[row][col];
        int totalLength = 0;
        int rows = matrix.length;
        int cols = matrix[0].length;

        Stack<int[]> stack = new Stack<>();
        stack.push(new int[]{row, col});

        while (!stack.isEmpty()) {
            int[] current = stack.pop();
            int curRow = current[0];
            int curCol = current[1];

            if (curRow < 0 || curRow >= rows || curCol < 0 || curCol >= cols || visited[curRow][curCol] ||
                    !matrix[curRow][curCol].equals(value)) {
                continue;
            }

            visited[curRow][curCol] = true;
            totalLength++;

            for (int[] dir : DIRECTIONS) {
                int newRow = curRow + dir[0];
                int newCol = curCol + dir[1];
                stack.push(new int[]{newRow, newCol});
            }
        }

        return new int[]{totalLength};
    }

    private boolean isBonusSymbol(Object element) {
        return element instanceof String && (
                element.equals("10x") || element.equals("5x") ||
                        element.equals("+1000") || element.equals("+500") ||
                        element.equals("MISS"));
    }

    public Map<Integer, WinCombination> initializeRewardMap(Config config) {
        Map<Integer, WinCombination> rewardMap = new HashMap<>();

        rewardMap.put(3, config.getWinCombinations().getSameSymbol3Times());
        rewardMap.put(4, config.getWinCombinations().getSameSymbol4Times());
        rewardMap.put(5, config.getWinCombinations().getSameSymbol5Times());
        rewardMap.put(6, config.getWinCombinations().getSameSymbol6Times());
        rewardMap.put(7, config.getWinCombinations().getSameSymbol7Times());
        rewardMap.put(8, config.getWinCombinations().getSameSymbol8Times());
        rewardMap.put(9, config.getWinCombinations().getSameSymbol9Times());

        return rewardMap;
    }

    private String getCombination (int count) {
        return "same_symbol_" + count + "_time";
    }
}
