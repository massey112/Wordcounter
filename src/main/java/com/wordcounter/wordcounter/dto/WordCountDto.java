package com.wordcounter.wordcounter.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
public class WordCountDto implements Serializable {

    private Map<Integer, Integer> lengthCounts;
    private Integer wordCount;
    private Double averageLength;
    private Integer mostOccurringLengthInstances;
    private List<Integer> mostOccurringLengths;

}
