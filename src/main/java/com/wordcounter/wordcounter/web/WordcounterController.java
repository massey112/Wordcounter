package com.wordcounter.wordcounter.web;

import com.wordcounter.wordcounter.business.WordcounterService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class WordcounterController {

    private final WordcounterService wordcounterService;

    @PostMapping(value = "/count-words-as-string")
    public String getDataAsString(@RequestParam("file") MultipartFile file) throws IOException {
        return wordcounterService.countWordsToReadableString(file);
    }

    @PostMapping(value = "/count-words")
    public String getData(@RequestParam("file") MultipartFile file) throws IOException {
        return wordcounterService.countWordsToJson(file);
    }

}
