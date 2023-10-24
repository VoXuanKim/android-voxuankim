package com.example.lab5;

import androidx.lifecycle.ViewModel;

public class TaskDetailsViewModel extends ViewModel {
    private String taskDescription;

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }
}

