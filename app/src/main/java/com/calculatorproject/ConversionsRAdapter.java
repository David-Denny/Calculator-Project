package com.calculatorproject;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;


import java.util.ArrayList;

public class ConversionsRAdapter extends RecyclerView.Adapter<ConversionsRAdapter.ViewHolder> {

    private ArrayList<String> mContentArray;
    private RecyclerView mRecyclerView;
    private View.OnClickListener mOnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {

            // get position for switch-case statement
            int position = mRecyclerView.getChildLayoutPosition(v);

            switch (position) {

            }
        }

    };

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public RelativeLayout mRow;
        public TextView mContent;

        public ViewHolder(View itemView) {
            super(itemView);

            // set member variables
            mRow = itemView.findViewById(R.id.default_recycler_row);
            mContent = itemView.findViewById(R.id.default_recycler_content);
        }
    }

    public ConversionsRAdapter(Context context) {

        mContentArray = new ArrayList<>();

        // add content to array to be later used to populate the RecyclerView
        mContentArray.add("Denary <-> Binary");
        mContentArray.add("Denary <-> Hexadecimal");
        mContentArray.add("Binary <-> Hexadecimal");
    }

    @NonNull
    @Override
    public ConversionsRAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // inflates each row of the RecyclerView
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.recycler_default_row, parent, false);

        // sets the onClickListener on each row
        view.setOnClickListener(mOnClickListener);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ConversionsRAdapter.ViewHolder holder, int position) {

        // set text for each element of the RecyclerView
        TextView contentTextView = holder.mContent;
        contentTextView.setText(mContentArray.get(position));
    }

    @Override
    public int getItemCount() {

        // returns size of RecyclerView
        return mContentArray.size();
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

        // set member variable to RecyclerView so it can be used to find the position
        mRecyclerView = recyclerView;
    }
}
