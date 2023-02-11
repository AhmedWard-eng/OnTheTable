package com.mad.iti.onthetable;

import org.junit.Test;

import static org.junit.Assert.*;

import com.mad.iti.onthetable.formatters.GetIdFromYoutubeUrl;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
       String actualResult = GetIdFromYoutubeUrl.getId("https://www.youtube.com/watch?v=p3u8eiy3yHI");
       String expected = "p3u8eiy3yHI";
        assertEquals(expected, actualResult);
    }
}