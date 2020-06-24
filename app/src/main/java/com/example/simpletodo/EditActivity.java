package com.example.simpletodo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditActivity extends AppCompatActivity {

    EditText etEdit;
    Button btnEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        etEdit = findViewById(R.id.etEdit);
        btnEdit = findViewById(R.id.btnEdit);

        getSupportActionBar().setTitle("Edit Item");

        final Intent in = getIntent();

        etEdit.setText(in.getStringExtra(MainActivity.KEY_ITEM_TEXT));

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create an intent with the results
                Intent out = new Intent();
                // Pass the edited data
                out.putExtra(MainActivity.KEY_ITEM_TEXT, etEdit.getText().toString());
                out.putExtra(MainActivity.KEY_ITEM_POSITION, in.getIntExtra(MainActivity.KEY_ITEM_POSITION, 0));
                // Set the result of the intent
                setResult(RESULT_OK, out);
                // Finish current intent (close the Activity)
                finish();
            }
        });
    }
}