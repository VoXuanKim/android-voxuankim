package com.example.lab6;

import android.util.Log;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class WordFirebaseDAO {
    private DatabaseReference databaseReference;

    public WordFirebaseDAO() {
        // Initialize the Firebase Realtime Database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("words");
    }

    public DatabaseReference getDatabaseReference() {
        return databaseReference;
    }

    public void saveWord(Word word) {
        String wordId = databaseReference.push().getKey();
        if (wordId != null) {
            databaseReference.child(wordId).setValue(word, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                    if (databaseError != null) {
                        String errorMessage = "Data could not be saved. " + databaseError.getMessage();
                        Log.e("Firebase", errorMessage);
                    } else {
                        System.out.println("Data Saved Successfully");
                    }
                }
            });
        }
    }
}

