package com.example.ryne.myrxjava;

import android.content.Context;
import android.database.Observable;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.example.ryne.myrxjava", appContext.getPackageName());
    }

    //RxJava unit test using JUnit4 test
    String result = "";

    //Simple subscription to to a fix value
    @Test
    public void returnAValue(){
        result = "";

    }
}
