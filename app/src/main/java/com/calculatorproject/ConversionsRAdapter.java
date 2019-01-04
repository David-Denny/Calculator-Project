package com.calculatorproject;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;

public class ConversionsRAdapter extends RecyclerView.Adapter<ConversionsRAdapter.ViewHolder> {

    private ArrayList<String> mContentArray;
    private RecyclerView mRecyclerView;
    private Context mContext;
    private View.OnClickListener mOnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {

            // get position for switch-case statement
            int position = mRecyclerView.getChildLayoutPosition(v);

            switch (position) {

                case 0:

                    // create dialog variable
                    final Dialog dialog = new Dialog(v.getContext());

                    // inflate dialog layout
                    dialog.setContentView(R.layout.conversion_dialog);

                    // initialise  buttons
                    Button dialogBack = dialog.findViewById(R.id.dialog_cancel);
                    Button dialogCalculate = dialog.findViewById(R.id.dialog_calculate);

                    // dismiss dialog when user clicks cancel button
                    dialogBack.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    
                    dialogCalculate.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {

                            EditText denaryEditText = dialog.findViewById(R.id.conversion_input_1);
                            EditText binaryEditText = dialog.findViewById(R.id.conversion_input_2);

                            String denary = denaryEditText.getText().toString();
                            String binary = binaryEditText.getText().toString();

                            if (denary.equals("")) {

                                // convert binary to denary and display to user
                                denary = String.valueOf(Integer.parseInt(binary, 2));
                                denaryEditText.setText(denary);
                            } else if (binary.equals("")) {

                                // convert denary to binary and display to user
                                binary = String.valueOf(Integer.toBinaryString(Integer.valueOf(denary)));
                                binaryEditText.setText(binary);
                            } else {


                            }
                        }
                    });
                    
                    dialog.show();

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
