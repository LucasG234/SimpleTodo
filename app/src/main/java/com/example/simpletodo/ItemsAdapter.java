package com.example.simpletodo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

// Responsible for taking data from the model and putting it into the recycler view
public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ViewHolder>{

    public interface OnClickListener {
        void onItemClicked(int position);
    }

    public interface OnLongClickListener {
        void onItemLongClicked(int position);
    }

    private List<String> mItems;
    private OnLongClickListener mLongClickListener;
    private OnClickListener mClickListener;

    public ItemsAdapter(List<String> items, OnLongClickListener longClickListener, OnClickListener onClickListener) {
        this.mItems = items;
        this.mLongClickListener = longClickListener;
        this.mClickListener = onClickListener;
    }

    // Responsible for creating ViewHolders
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Use layout inflater to inflate an android default view
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View todoView = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);

        // Wrap it inside of a ViewHolder and return it
        return new ViewHolder(todoView);
    }

    // Responsible for binding data to a particular ViewHolder
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Grab the item at the position
        String item = mItems.get(position);
        // Bind the item into the specified ViewHolder
        holder.bind(item);
    }

    // Tells the RecyclerView how many items are in the list
    @Override
    public int getItemCount() {
        return mItems.size();
    }

    // Container to provide easy access to views
    // Each ViewHolder represents one row in the list
    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mListText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mListText = itemView.findViewById(android.R.id.text1);
            mListText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Notify the listener of the position which was [short] clicked
                    mClickListener.onItemClicked(getAdapterPosition());
                }
            });
            mListText.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    // Notify the listener of the position which was long clicked

                    mLongClickListener.onItemLongClicked(getAdapterPosition());
                    // Return true because callback is consuming the long click
                    return false;
                }
            });
        }

        // Responsible for updating the View inside of the ViewHolder with new data
        public void bind(String item) {
            mListText.setText(item);
        }
    }
}
