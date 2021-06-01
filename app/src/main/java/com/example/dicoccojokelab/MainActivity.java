package com.example.dicoccojokelab;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private JokeViewModel jokeViewModel;
    private Button callButton;
    private TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        callButton = findViewById(R.id.callButton);
        text = findViewById(R.id.callTextview);

        // create Joke View Model Instance Here
        jokeViewModel = new ViewModelProvider(this, new
                ViewModelProvider.AndroidViewModelFactory(getApplication())).get(JokeViewModel.class);

        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //call the web service using this method on JokeViewModel
                jokeViewModel.loadData();
            }
        });

        final Observer<String> resultJSONObserver = new Observer<String>() {
            @Override
            public void onChanged(@Nullable final String newResultJSON) {
// Update the UI, in this case, a TextView.
                text.setText(newResultJSON);
            }
        };
        jokeViewModel.getResultJSON().observe(this, resultJSONObserver);
    }
}