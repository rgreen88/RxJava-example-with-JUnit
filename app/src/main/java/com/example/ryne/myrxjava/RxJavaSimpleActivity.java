package com.example.ryne.myrxjava;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import io.reactivex.disposables.CompositeDisposable;

public class RxJavaSimpleActivity extends AppCompatActivity {

    RecyclerView colorViewList;
    SimpleStringAdapter simpleStringAdapter;
    CompositeDisposable disposable = new CompositeDisposable(); //disposable to destroy subscriber
    public int value = 0;
}
