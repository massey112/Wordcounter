package com.wordcounter.wordcounter;

import com.wordcounter.wordcounter.business.WordcounterService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class WordcounterServiceTest {

    @Autowired
    private WordcounterService classUnderTest;

    @Test
    public void testCountWordsExampleStringToJson() throws IOException {
        MultipartFile multipartFile = getMockMultipartFile("example_string.txt");
        String output = classUnderTest.countWordsToJson(multipartFile);
        assertEquals("{\"lengthCounts\":{\"1\":1,\"2\":1,\"3\":1,\"4\":2,\"5\":2,\"7\":1,\"10\":1}," +
                "\"wordCount\":9,\"averageLength\":4.555555555555555,\"mostOccurringLengthInstances\":2,\"mostOccurringLengths\":[4,5]}", output);
    }

    @Test
    public void testCountWordsLongFileToJson() throws IOException {
        MultipartFile multipartFile = getMockMultipartFile("bible_daily.txt");
        String output = classUnderTest.countWordsToJson(multipartFile);
        assertEquals("{\"lengthCounts\":{\"0\":6474,\"1\":18639,\"2\":131846,\"3\":221258,\"4\":175439,\"5\":95572,\"6\":52923," +
                "\"7\":40788,\"8\":25287,\"9\":16834,\"10\":7564,\"11\":3884,\"12\":1715,\"13\":877,\"14\":353,\"15\":91,\"16\":17,\"17\":4,\"18\":3}" +
                ",\"wordCount\":799568,\"averageLength\":4.051716677005583,\"mostOccurringLengthInstances\":221258,\"mostOccurringLengths\":[3]}", output);
    }

    @Test
    public void testCountWordsEmptyFileToJson() throws IOException {
        MultipartFile multipartFile = getMockMultipartFile("empty_file.txt");
        String output = classUnderTest.countWordsToJson(multipartFile);
        assertEquals("{\"lengthCounts\":{},\"wordCount\":0,\"averageLength\":0.0,\"mostOccurringLengthInstances\":0,\"mostOccurringLengths\":[]}", output);
    }

    @Test
    public void testCountWordsExampleStringToReadable() throws IOException {
        MultipartFile multipartFile = getMockMultipartFile("example_string.txt");
        String output = classUnderTest.countWordsToReadableString(multipartFile);
        assertEquals("Word count = 9\n" +
                "Average word length = 4.556\n" +
                "Number of words of length 1 is 1\n" +
                "Number of words of length 2 is 1\n" +
                "Number of words of length 3 is 1\n" +
                "Number of words of length 4 is 2\n" +
                "Number of words of length 5 is 2\n" +
                "Number of words of length 7 is 1\n" +
                "Number of words of length 10 is 1\n" +
                "The most frequently occurring word length is 2, for word lengths of 4 & 5\n", output);
    }

    @Test
    public void testCountWordsLongFileToReadable() throws IOException {
        MultipartFile multipartFile = getMockMultipartFile("bible_daily.txt");
        String output = classUnderTest.countWordsToReadableString(multipartFile);
        assertNotNull(output);
    }

    @Test
    public void testCountWordsEmptyFileToReadable() throws IOException {
        MultipartFile multipartFile = getMockMultipartFile("empty_file.txt");
        String output = classUnderTest.countWordsToReadableString(multipartFile);
        assertEquals("This passed file was empty or did not contain any words.", output);
    }

    private MultipartFile getMockMultipartFile(String fileName) throws IOException {
        Path path = Paths.get("src/test/resources/" + fileName);
        String contentType = "text/plain";
        byte[] content = Files.readAllBytes(path);
        return new MockMultipartFile(fileName,
                fileName, contentType, content);
    }

}
