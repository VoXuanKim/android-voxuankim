package com.example.lab5;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class TaskDetailsActivity extends AppCompatActivity {

    private TaskDetailsViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_details);

        EditText descriptionEditText = findViewById(R.id.descriptionEditText);
        Button saveButton = findViewById(R.id.saveButton);

        viewModel = new ViewModelProvider(this).get(TaskDetailsViewModel.class);

        saveButton.setOnClickListener(v -> {
            String editedDescription = descriptionEditText.getText().toString();
            viewModel.setTaskDescription(editedDescription);
        });

        // Populate the description field with the data from the ViewModel
        descriptionEditText.setText(viewModel.getTaskDescription());
    }
}

