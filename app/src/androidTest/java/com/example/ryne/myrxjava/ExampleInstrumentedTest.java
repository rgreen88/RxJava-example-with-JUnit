package com.example.ryne.myrxjava;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

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

    //Simple subscription to to a fix value
    @Test
    public void returnAValue(){
        final String[] result = {""};
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                try {
                    result[0] = "hello";
                    emitter.onComplete();
                    assertTrue(result[0].equalsIgnoreCase("Hello"));
                    //observer completed as it can't find "Hello" is empty String result
                } catch (Exception e) {
                    emitter.onError(e);
                }
            }
        });
    }
}