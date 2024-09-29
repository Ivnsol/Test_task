package com.example;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Setter
@Getter
public class Config {
    private Integer columns; // OPTIONAL
    private Integer rows; // OPTIONAL
    private Map<String, Symbol> symbols;
    private Probabilities probabilities;
    @JsonProperty("win_combinations")
    private WinCombinations winCombinations;
}

@Setter
@Getter
class Symbol {
    @JsonProperty("reward_multiplier")
    private Integer rewardMultiplier;
    private String type;
    private Integer extra;
    private String impact;
}

@Setter
@Getter
class Probabilities {
    @JsonProperty("standard_symbols")
    private List<StandardSymbol> standardSymbols;
    @JsonProperty("bonus_symbols")
    private BonusSymbols bonusSymbols;
}

@Getter
@Setter
class StandardSymbol {
    private int column;
    private int row;
    private Map<String, Integer> symbols;

}

@Setter
@Getter
class BonusSymbols {
    private Map<String, Integer> symbols;
}

@Setter
@Getter
class WinCombinations {
    @JsonProperty("same_symbol_3_times")
    private WinCombination sameSymbol3Times;
    @JsonProperty("same_symbol_4_times")
    private WinCombination sameSymbol4Times;
    @JsonProperty("same_symbol_5_times")
    private WinCombination sameSymbol5Times;
    @JsonProperty("same_symbol_6_times")
    private WinCombination sameSymbol6Times;
    @JsonProperty("same_symbol_7_times")
    private WinCombination sameSymbol7Times;
    @JsonProperty("same_symbol_8_times")
    private WinCombination sameSymbol8Times;
    @JsonProperty("same_symbol_9_times")
    private WinCombination sameSymbol9Times;

    @JsonProperty("same_symbols_horizontally") // OPTIONAL
    private WinCombination sameSymbolsHorizontally;

    @JsonProperty("same_symbols_vertically") // OPTIONAL
    private WinCombination sameSymbolsVertically;

    @JsonProperty("same_symbols_diagonally_left_to_right") // OPTIONAL
    private WinCombination sameSymbolsDiagonallyLeftToRight;

    @JsonProperty("same_symbols_diagonally_right_to_left") // OPTIONAL
    private WinCombination sameSymbolsDiagonallyRightToLeft;
}

@Getter
@Setter
class WinCombination {
    @JsonProperty("reward_multiplier")
    private double rewardMultiplier;
    private String when;
    private int count;
    private String group;

    @JsonProperty("covered_areas")
    private List<List<String>> coveredAreas;
}

