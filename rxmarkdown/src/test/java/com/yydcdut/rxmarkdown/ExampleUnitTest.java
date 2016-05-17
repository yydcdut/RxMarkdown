package com.yydcdut.rxmarkdown;

import org.junit.Test;

import java.util.regex.Pattern;


import static org.junit.Assert.assertEquals;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    private static final String KEY = "```";

    @Test
    public void testCodeGrammar() {
        String text = "111```\n" +
                "nnnnn\n" +
                "```\n";
        String[] splits = text.split(KEY);
        int number = splits.length;
        System.out.println("number-->" + number);
        for (String s : splits) {
            System.out.println(s);
        }
    }

    boolean isMatch(String text) {
        String newText = text.replace("\n", "*--*++*$$*");
        Pattern pattern = Pattern.compile(".*[```]{3}.*[```]{3}.*");
        if (!pattern.matcher(newText).matches()) {
            return false;
        }
        String[] lines = text.split("\n");
        int number = 0;
        for (int i = 0; i < lines.length; i++) {
            number += lines[i].equals(KEY) ? 1 : 0;
        }
//        if (number >= 3 && number % 2 == 1) {//应该是奇数才能正常匹配
//
//        }
        //但是大于2就OK了
        return number >= 2;
    }
}