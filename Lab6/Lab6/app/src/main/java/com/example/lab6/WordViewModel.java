package com.example.lab6;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class WordViewModel extends ViewModel {
    private WordFirebaseDAO wordFirebaseDAO;
    private LiveData<List<Word>> wordListLiveData;

    public WordViewModel() {
        wordFirebaseDAO = new WordFirebaseDAO();
        wordListLiveData = new FirebaseQueryLiveData();
    }

    public LiveData<List<Word>> getWordListLiveData() {
        return wordListLiveData;
    }

    private class FirebaseQueryLiveData extends LiveData<List<Word>> {
        private final ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Word> words = new ArrayList<>();
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    Word word = childSnapshot.getValue(Word.class);
                    if (word != null) {
                        words.add(word);
                    }
                }
                setValue(words);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle errors
                String errorMessage = "Error occurred: " + databaseError.getMessage();
                // Log the error or perform error handling as needed
                Log.e("Firebase", errorMessage);
            }
        };

        @Override
        protected void onActive() {
            super.onActive();
            wordFirebaseDAO.getDatabaseReference().addValueEventListener(valueEventListener);
        }

        @Override
        protected void onInactive() {
            super.onInactive();
            wordFirebaseDAO.getDatabaseReference().removeEventListener(valueEventListener);
        }
    }
}

