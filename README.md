# Wordcounter

This was written as part of a Java programming test.

It runs as a standard Spring Boot application. 

### Assumptions

A word is seperated by whitespace. 

There is only ever one piece of punctuation at the end of a word, and the punctuation at the end of a word does not count as part of the word. Any special characters in the word do count as part of the word.

A JSON format is the preferred return. However, I've also added an endpoint that returns a 'human readable' string as per the example.

As per the example, numbers and dates can make words.


### To Use

To use in postman, open a new POST request with the following URL:

```localhost:8080/count-words```

Then set the body to ```form-data``` and add a key ```file``` of type file and set the value as the file that you want to count the words for. 

Then send the request and you'll get back the data in JSON format. 

To get the data back as in the same string format as the example, do the same as above but instead to the following URL:

```localhost:8080/count-words-as-string```