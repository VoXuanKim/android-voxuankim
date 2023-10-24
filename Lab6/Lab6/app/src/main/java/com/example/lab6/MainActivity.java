package com.example.lab6;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private WordViewModel wordViewModel;
    private RecyclerView recyclerView;
    private WordAdapter wordAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the RecyclerView and its adapter
        recyclerView = findViewById(R.id.recyclerView);
        wordAdapter = new WordAdapter();
        recyclerView.setAdapter(wordAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        wordViewModel = new ViewModelProvider(this).get(WordViewModel.class);

        wordViewModel.getWordListLiveData().observe(this, new Observer<List<Word>>() {
            @Override
            public void onChanged(List<Word> words) {
                wordAdapter.submitList(words);
            }
        });

        // Handle any database errors
        wordViewModel.getWordListLiveData().observe(this, new Observer<List<Word>>() {
            @Override
            public void onChanged(List<Word> words) {
                wordAdapter.submitList(words);
            }
        });

        wordViewModel.getWordListLiveData().observe(this, new Observer<List<Word>>() {
            @Override
            public void onChanged(List<Word> words) {
                wordAdapter.submitList(words);
            }
        });
    }
}