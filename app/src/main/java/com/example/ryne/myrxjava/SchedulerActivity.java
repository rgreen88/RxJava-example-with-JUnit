package com.example.ryne.myrxjava;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/** Demonstrates a long running operation of the main thread
 * during which a  progressbar is shown
 *
 */
public class SchedulerActivity extends AppCompatActivity {

    private Disposable subscription;
    private ProgressBar progressBar;
    private TextView messagearea;
    private View button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        configureLayout();
        createObservable();
    }

    private void createObservable() {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (subscription != null && !subscription.isDisposed()) {
            subscription.dispose();
        }
    }

    private void configureLayout() {
        setContentView(R.layout.activity_scheduler);
        progressBar = findViewById(R.id.progressBar);
        messagearea = findViewById(R.id.messagearea);
        button  = findViewById(R.id.scheduleLongRunningOperation);
        button.setOnClickListener((View v) -> {
            progressBar.setVisibility(View.VISIBLE);
            Observable.fromCallable(callable).
                    subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).
                    doOnSubscribe(disposable ->
                            {
                                progressBar.setVisibility(View.VISIBLE);
                                button.setEnabled(false);

                                //string format with android resources to appease textview rules
                                //and localization
                                messagearea.setText(String.format("%s%s%s", messagearea.getText()
                                        .toString(), getString(R.string.space),
                                        getString(R.string.progress_bar_visible)));
                            }
                    ).subscribe(getDisposableObserver());
        });
    }

    Callable<String> callable = this::doSomethingLong;

    public String doSomethingLong(){
        SystemClock.sleep(1000);
        return "Hello";
    }

    /**
     * Observer
     * Handles the stream of data:
     */
    private DisposableObserver<String> getDisposableObserver() {
        return new DisposableObserver<String>() {

            @Override
            public void onComplete() {
                messagearea.setText(String.format("%s\nOnComplete", messagearea.getText().toString()));
                progressBar.setVisibility(View.INVISIBLE);
                button.setEnabled(true);
                messagearea.setText(String.format("%s\nHidding Progressbar", messagearea.getText().toString()));
            }

            @Override
            public void onError(Throwable e) {
                messagearea.setText(String.format("%s\nOnError", messagearea.getText().toString()));
                progressBar.setVisibility(View.INVISIBLE);
                button.setEnabled(true);
                messagearea.setText(String.format("%s\nHidding Progressbar", messagearea.getText().toString()));
            }

            @Override
            public void onNext(String message) {
                messagearea.setText(String.format("%s\nonNext %s", messagearea.getText().toString(), message));
            }
        };
    }
    //going back to main activity change button
    public void goBack(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        Toast.makeText(this, "Activity change. ", Toast.LENGTH_SHORT).show();
    }
}