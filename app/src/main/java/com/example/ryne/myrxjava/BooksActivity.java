package com.example.ryne.myrxjava;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class BooksActivity extends AppCompatActivity{

    private Disposable bookSubscription;
    private RecyclerView booksRecyclerView;
    private ProgressBar progressBar;
    private SimpleStringAdapter stringAdapter;
    private RestClient restClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        restClient = new RestClient(this);
        configureLayout();
        createObservable();
    }

    //create observables to keep track of list of books
    private void createObservable(){
        Observable<List<String>> booksObservable = Observable.fromCallable(() -> restClient
                .getFavoriteBooks());
        bookSubscription = booksObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(strings -> displayBooks(strings));
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (bookSubscription != null && !bookSubscription.isDisposed()) {
            bookSubscription.dispose();
        }
    }
    //books using simple adapter class (multi-use adapter!!)
    private void displayBooks(List<String> books) {
        stringAdapter.setStrings(books);
        progressBar.setVisibility(View.GONE);
        booksRecyclerView.setVisibility(View.VISIBLE);
    }
    private void configureLayout() {
        setContentView(R.layout.activity_books);
        progressBar = findViewById(R.id.loader);
        booksRecyclerView = findViewById(R.id.books_list);
        booksRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        stringAdapter = new SimpleStringAdapter(this);
        booksRecyclerView.setAdapter(stringAdapter);
    }
    @Override
    protected void onStop() {
        super.onStop();
        if (bookSubscription!=null && !bookSubscription.isDisposed()) {
            bookSubscription.dispose();
        }
    }
    //going back to main activity change button
    public void goBack(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        Toast.makeText(this, "Activity change. ", Toast.LENGTH_SHORT).show();
    }
}
