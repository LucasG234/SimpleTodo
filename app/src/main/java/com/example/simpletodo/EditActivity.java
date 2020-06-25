package com.example.simpletodo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Objects;

public class EditActivity extends AppCompatActivity {

    private EditText mTodoEditText;
    private Button mTodoEditButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        mTodoEditText = findViewById(R.id.etEdit);
        mTodoEditButton = findViewById(R.id.btnEdit);

        Objects.requireNonNull(getSupportActionBar()).setTitle(getString(R.string.edit_bar_title));

        final Intent in = getIntent();

        mTodoEditText.setText(in.getStringExtra(MainActivity.KEY_ITEM_TEXT));

        mTodoEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create an intent with the results
                Intent out = new Intent();

                // Pass the edited data
                out.putExtra(MainActivity.KEY_ITEM_TEXT, mTodoEditText.getText().toString());
                out.putExtra(MainActivity.KEY_ITEM_POSITION, in.getIntExtra(MainActivity.KEY_ITEM_POSITION, 0));

                // Set the result of the intent
                setResult(RESULT_OK, out);

                // Finish current intent (close the Activity)
                finish();
            }
        });
    }
}