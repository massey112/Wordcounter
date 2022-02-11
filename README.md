# Wordcounter

This was written as part of a Java programming test.

It runs as a standard Spring Boot application. 

### Assumptions

A word is seperated by whitespace. 

There is only ever one piece of punctuation at the end of a word, and the punctuation at the end of a word does not count as part of the word. Any special characters in the word do count as part of the word.

A JSON format is the preferred return. However, I've also added an endpoint that returns a 'human readable' string as per the example.

As per the example, numbers and dates can make words. 