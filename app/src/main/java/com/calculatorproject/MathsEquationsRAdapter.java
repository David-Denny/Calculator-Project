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

public class MathsEquationsRAdapter extends RecyclerView.Adapter<MathsEquationsRAdapter.ViewHolder> {

    private ArrayList<String> mContentArray;
    private RecyclerView mRecyclerView;
    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            int position = mRecyclerView.getChildLayoutPosition(v);

            switch(position) {

                //Quadratic Equation
                case 0:

                    break;

                // Pythagoras' Theorem
                case 1:

                    break;

                // Cosine Rule
                case 2:

                    break;

                // Sine Rule
                case 3:

                    break;

                // Area of a Triangle
                case 4:

                    break;
            }

        }
    };

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public RelativeLayout mRow;
        public TextView mContent;

        public ViewHolder(View itemView) {
            super(itemView);

            mRow = itemView.findViewById(R.id.default_recycler_row);
            mContent = itemView.findViewById(R.id.default_recycler_content);
        }
    }

    public MathsEquationsRAdapter(Context context) {

        mContentArray = new ArrayList<>();

        mContentArray.add("Quadratic Equation");
        mContentArray.add("Pythagoras' Theorem");
        mContentArray.add("Cosine Rule");
        mContentArray.add("Sine Rule");
        mContentArray.add("Area of a Triangle");
    }

    @NonNull
    @Override
    public MathsEquationsRAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.recycler_default_row, parent, false);

        view.setOnClickListener(mOnClickListener);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MathsEquationsRAdapter.ViewHolder holder, int position) {

        TextView contentTextView = holder.mContent;
        contentTextView.setText(mContentArray.get(position));
    }

    @Override
    public int getItemCount() {
        return mContentArray.size();
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

        mRecyclerView = recyclerView;
    }
}
