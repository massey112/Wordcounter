package com.wordcounter.wordcounter.business;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wordcounter.wordcounter.dto.WordCountDto;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.*;

@Service
public class WordcounterService {

    public String countWordsToJson(MultipartFile file) throws IOException {
        Map<Integer, Integer> countMap = countWords(file);
        int mostCommonLength = 0;
        List<Integer> mostCommonLengths = new ArrayList<>();
        int sum = 0;
        int wordCount = 0;
        for (Map.Entry<Integer, Integer> entry : countMap.entrySet()) {
            wordCount = wordCount + entry.getValue();
            sum = sum + (entry.getKey() * entry.getValue());
            if (entry.getValue() == mostCommonLength) {
                mostCommonLengths.add(entry.getKey());
            } else if (entry.getValue() > mostCommonLength) {
                mostCommonLength = entry.getValue();
                mostCommonLengths = new ArrayList<>();
                mostCommonLengths.add(entry.getKey());
            }
        }

        WordCountDto wordCountDto = new WordCountDto();
        wordCountDto.setLengthCounts(countMap);
        wordCountDto.setWordCount(wordCount);
        if (wordCount == 0) {
            wordCountDto.setAverageLength(0.0);
        } else {
            wordCountDto.setAverageLength((double)sum/wordCount);
        }
        wordCountDto.setMostOccurringLengthInstances(mostCommonLength);
        wordCountDto.setMostOccurringLengths(mostCommonLengths);

        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(wordCountDto);
    }

    public String countWordsToReadableString(MultipartFile file) throws IOException {
        return countMapToString(countWords(file));
    }

    private Map<Integer, Integer> countWords(MultipartFile file) throws IOException {
        Map<Integer, Integer> countMap = new HashMap<>();
        InputStream is = file.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String line;
        while ((line = br.readLine()) != null) {
            for (String word : getWords(line)) {
                // strip punctuation at end
                word = word.replaceAll("[^A-Za-z0-9&]$", "");
                updateCountMap(countMap, word);
            }
        }
        return countMap;
    }

    private String[] getWords(String string) {
        return string.split("[\\s*]");
    }

    private void updateCountMap(Map<Integer, Integer> countMap, String word) {
        int wordLength = word.length();
        if (countMap.containsKey(wordLength)) {
            countMap.put(wordLength, countMap.get(wordLength) + 1);
        } else {
            countMap.put(wordLength, 1);
        }
    }

    private String countMapToString(Map<Integer, Integer> countMap) {
        if (countMap.isEmpty()) {
            return "This passed file was empty or did not contain any words.";
        }
        Map<Integer, Integer> orderedCountMap = new TreeMap<>(countMap);
        StringBuilder output = new StringBuilder();
        int mostCommonLength = 0;
        List<Integer> mostCommonLengths = new ArrayList<>();
        int sum = 0;
        int wordCount = 0;
        for (Map.Entry<Integer, Integer> entry : orderedCountMap.entrySet()) {
            wordCount = wordCount + entry.getValue();
            sum = sum + (entry.getKey() * entry.getValue());
            output.append("Number of words of length ").append(entry.getKey()).append(" is ").append(entry.getValue()).append("\n");
            if (entry.getValue() == mostCommonLength) {
                mostCommonLengths.add(entry.getKey());
            } else if (entry.getValue() > mostCommonLength) {
                mostCommonLength = entry.getValue();
                mostCommonLengths = new ArrayList<>();
                mostCommonLengths.add(entry.getKey());
            }
        }
        DecimalFormat decimalFormat = new DecimalFormat("#.###");
        return "Word count = " + wordCount + "\n"
                + "Average word length = " + decimalFormat.format((double)sum / wordCount) + "\n"
                + output
                + buildMostFrequentString(mostCommonLength, mostCommonLengths) + "\n";
    }

    private String buildMostFrequentString(int mostCommonLength, List<Integer> mostCommonLengths) {
        StringBuilder lengths = new StringBuilder();
        while (mostCommonLengths.size() > 2) {
            lengths.append(mostCommonLengths.remove(0)).append(", ");
        }
        if (mostCommonLengths.size() == 2) {
            lengths.append(mostCommonLengths.remove(0)).append(" & ");
        }
        return "The most frequently occurring word length is " + mostCommonLength + ", for word lengths of " + lengths + mostCommonLengths.get(0);
    }

}
