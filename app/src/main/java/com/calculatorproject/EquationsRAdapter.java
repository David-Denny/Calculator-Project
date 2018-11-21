package com.calculatorproject;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class EquationsRAdapter extends RecyclerView.Adapter<EquationsRAdapter.ViewHolder> {
    private ArrayList<String> mContentArray;
    private RecyclerView mRecyclerView;
    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Toast.makeText(v.getContext(), "CLICKED", Toast.LENGTH_SHORT).show();
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

    public EquationsRAdapter(Context context) {

        mContentArray = new ArrayList<>();

        mContentArray.add("Maths");
        mContentArray.add("Physics");
        mContentArray.add("Biology");
        mContentArray.add("Chemistry");

    }

    @NonNull
    @Override
    public EquationsRAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.recycler_default_row, parent, false);

        view.setOnClickListener(mOnClickListener);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EquationsRAdapter.ViewHolder holder, int position) {

        TextView contentTextView = holder.mContent;
        contentTextView.setText(mContentArray.get(position));
    }

    @Override
    public int getItemCount() {
        return mContentArray.size();
    }
}
