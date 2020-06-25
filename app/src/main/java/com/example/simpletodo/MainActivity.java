package com.example.simpletodo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String KEY_ITEM_TEXT = "item_text";
    public static final String KEY_ITEM_POSITION = "item_position";
    public static final int EDIT_TEXT_CODE = 30;

    private List<String> items;
    private Button mTodoAddButton;
    private EditText mTodoAddText;
    private RecyclerView mTodoRecyclerView;
    private ItemsAdapter mItemsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Retrieve instances of all views
        mTodoAddButton = findViewById(R.id.btnAdd);
        mTodoAddText = findViewById(R.id.etAdd);
        mTodoRecyclerView = findViewById(R.id.rvItems);

        loadItems();

        // Initialize ItemsAdapter
        ItemsAdapter.OnLongClickListener longClickListener = new ItemsAdapter.OnLongClickListener() {
            @Override
            public void onItemLongClicked(int position) {
                // Delete the item from the model
                items.remove(position);

                // Notify the adapter
                mItemsAdapter.notifyItemRemoved(position);
                Toast.makeText(getApplicationContext(), getString(R.string.remove_toast), Toast.LENGTH_SHORT).show();

                //Persist the changes
                saveItems();
            }
        };
        ItemsAdapter.OnClickListener clickListener = new ItemsAdapter.OnClickListener() {
            @Override
            public void onItemClicked(int position) {
                // Create the new EditActivity
                Intent i = new Intent(MainActivity.this, EditActivity.class);

                // Pass the data to be added
                i.putExtra(KEY_ITEM_TEXT, items.get(position));
                i.putExtra(KEY_ITEM_POSITION, position);

                // Display the activity
                startActivityForResult(i, EDIT_TEXT_CODE);
            }
        };
        mItemsAdapter = new ItemsAdapter(items, longClickListener, clickListener);
        mTodoRecyclerView.setAdapter(mItemsAdapter);
        mTodoRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mTodoAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strAdd = mTodoAddText.getText().toString();

                // Add new item to model
                items.add(strAdd);

                // Notify adapter of item insertion
                mItemsAdapter.notifyItemInserted(items.size()-1);

                // Clear the edit text
                mTodoAddText.setText("");
                Toast.makeText(getApplicationContext(), getString(R.string.add_toast), Toast.LENGTH_SHORT).show();

                // Persist the changes
                saveItems();
            }
        });
    }

    // Handle the result of the edit activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == EDIT_TEXT_CODE && data != null) {
            // Retrieve updated text value and original position
            String updatedText = data.getStringExtra(KEY_ITEM_TEXT);
            int updatedPosition = data.getIntExtra(KEY_ITEM_POSITION, 0);

            // Update the model
            items.set(updatedPosition, updatedText);

            // Notify the adapter of changes
            mItemsAdapter.notifyItemChanged(updatedPosition);

            // Persist the changes
            saveItems();

            Toast.makeText(getApplicationContext(), getString(R.string.edit_toast), Toast.LENGTH_SHORT).show();
        } else {
            Log.w("MainActivity", "Unknown call to onActivityResult");
        }
    }

    private File getDataFile() {
        return new File(getFilesDir(), "data.txt");
    }

    // Loads items from the data.txt file
    private void loadItems() {
        try {
            items = new ArrayList<>(FileUtils.readLines(getDataFile(), Charset.defaultCharset()));
        }catch (IOException e) {
            Log.e("MainActivity", "Error reading items", e);
            items = new ArrayList<>();
        }
    }

    // Writes items into the data.txt file
    private void saveItems() {
        try {
            FileUtils.writeLines(getDataFile(), items);
        } catch (IOException e) {
            Log.e("MainActivity", "Error writing items", e);
        }
    }
}