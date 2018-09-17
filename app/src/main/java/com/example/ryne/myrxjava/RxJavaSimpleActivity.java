package com.example.ryne.myrxjava;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class RxJavaSimpleActivity extends AppCompatActivity {

    RecyclerView colorViewList;
    SimpleStringAdapter simpleStringAdapter;
    CompositeDisposable disposable = new CompositeDisposable(); //disposable to destroy subscriber
    public int value = 0;

    final Observable<Integer> serverDownloadObservable = Observable.create(emitter -> {
        SystemClock.sleep(10000);//simulate delay
        emitter.onNext(5);//returns the number 5 after simulated 10 seconds
        emitter.onComplete();
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxjavasimple);
        View view = findViewById(R.id.button);
        view.setOnClickListener(v -> {
            v.setEnabled(false);//disables button until simulation finished

            //Disposable object creation to manage existing subscribers
            Disposable subscribe = serverDownloadObservable
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(integer -> {
                       updateTheUserInterface(integer);//method updates ui
                       v.setEnabled(true);//enables button after simulation
                    });
            disposable.add(subscribe);
        });
    }
    //ui update method
    private void updateTheUserInterface(int integer){
        TextView view = findViewById(R.id.resultView);
        view.setText(String.valueOf(integer));
    }
}
