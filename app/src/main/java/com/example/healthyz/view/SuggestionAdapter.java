package com.example.healthyz.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.healthyz.R;
import java.util.ArrayList;

public class SuggestionAdapter extends RecyclerView.Adapter<SuggestionAdapter.SuggestionViewHolder> {
    private ArrayList<SuggestionItem> suggestionList;

    public static class SuggestionViewHolder extends RecyclerView.ViewHolder {
        public TextView text;

        public SuggestionViewHolder(@NonNull View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.suggestion_item_text);
        }
    }

    public SuggestionAdapter(ArrayList<SuggestionItem> listOfSuggestions){
        suggestionList = listOfSuggestions;
    }

    @NonNull
    @Override
    public SuggestionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.suggestion_item, parent,false);
        SuggestionViewHolder svh = new SuggestionViewHolder(v);
        return svh;
    }

    @Override
    public void onBindViewHolder(@NonNull SuggestionViewHolder holder, int position) {
        SuggestionItem curr = suggestionList.get(position);
        holder.text.setText(curr.getText());
    }

    @Override
    public int getItemCount() {
        return suggestionList.size();
    }
}
