package com.example.ryne.myrxjava;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

public class ColorsActivity extends AppCompatActivity {

    RecyclerView colorListView;
    SimpleStringAdapter simpleStringAdapter;
    private Disposable disposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        configureLayout();
        createObservable();
    }

    private void createObservable(){
        Observable<List<String>> listObservable = Observable.just(getColorList());
        disposable = listObservable.subscribe(colors -> simpleStringAdapter.setStrings(colors));
    }

    //RV configuration
    private  void configureLayout(){
        setContentView(R.layout.activity_colors);
        colorListView = findViewById(R.id.color_list);
        colorListView.setLayoutManager(new LinearLayoutManager(this));
        simpleStringAdapter = new SimpleStringAdapter(this);
        colorListView.setAdapter(simpleStringAdapter);
    }

    //ist of colors in rv to view
    public List<String> getColorList(){
        ArrayList<String> colors = new ArrayList<>();
        colors.add("red");
        colors.add("green");
        colors.add("blue");
        colors.add("pink");
        colors.add("brown");
        simpleStringAdapter.setStrings(colors);
        colorListView.setVisibility(View.VISIBLE);
        return colors;
    }

    //destroying disposables
    @Override
    protected void onStop() {
        super.onStop();
        if (disposable!=null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }

    //going back to main activity change button
    public void goBack(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        Toast.makeText(this, "Activity change. ", Toast.LENGTH_SHORT).show();
    }
}
