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
                    final Dialog dialogDenToBin = new Dialog(v.getContext());

                    // inflate dialog layout
                    dialogDenToBin.setContentView(R.layout.conversion_dialog);

                    // initialise  buttons
                    Button dialogBack = dialogDenToBin.findViewById(R.id.dialog_cancel);
                    Button dialogCalculate = dialogDenToBin.findViewById(R.id.dialog_calculate);

                    // dismiss dialog when user clicks cancel button
                    dialogBack.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialogDenToBin.dismiss();
                        }
                    });


                    dialogCalculate.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            
                            try {

                                // initialise variables
                                EditText denaryEditText = dialogDenToBin.findViewById(R.id.conversion_input_1);
                                EditText binaryEditText = dialogDenToBin.findViewById(R.id.conversion_input_2);

                                String denary = denaryEditText.getText().toString();
                                String binary = binaryEditText.getText().toString();

                                if (denary.equals("")) {

                                    // convert binary to denary and display to user
                                    denary = String.valueOf(Integer.parseInt(binary, 2));
                                    denaryEditText.setText(denary);
                                } else if (binary.equals("")) {

                                    // convert denary to binary and display to user
                                    binary = String.valueOf(Integer.toBinaryString(Integer.
                                            valueOf(denary)));
                                    binaryEditText.setText(binary);
                                } else {

                                    // alert user to their error
                                    Toast.makeText(v.getContext(),
                                            "Leave one entry variable blank.",
                                            Toast.LENGTH_SHORT).show();
                                }

                                // prevent crashes from invalid input
                            } catch (NumberFormatException e) {
                                e.printStackTrace();

                                // inform user of reason of the error
                                Toast.makeText(v.getContext(), "ERROR: invalid input",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                    // display dialog
                    dialogDenToBin.show();
                    break;

                case 1:

                    // create dialog variable
                    final Dialog dialogDenToHex = new Dialog(v.getContext());

                    // inflate dialog layout
                    dialogDenToHex.setContentView(R.layout.conversion_dialog);

                    // initialise  buttons
                    Button dialogDenToHexBack = dialogDenToHex.findViewById(R.id.dialog_cancel);
                    Button dialogDenToHexCalculate = dialogDenToHex.findViewById(R.id.dialog_calculate);

                    // set different title
                    TextView title2 = dialogDenToHex.findViewById(R.id.input_title_2);
                    title2.setText(v.getResources().getString(R.string.hexadecimal));

                    // dismiss dialog when user clicks cancel button
                    dialogDenToHexBack.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialogDenToHex.dismiss();
                        }
                    });

                    dialogDenToHexCalculate.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            
                            try {

                                EditText denaryEditText = dialogDenToHex.findViewById(R.id.conversion_input_1);
                                EditText hexEditText = dialogDenToHex.findViewById(R.id.conversion_input_2);

                                String denary = denaryEditText.getText().toString();
                                String hex = hexEditText.getText().toString();

                                if (denary.equals("")) {

                                    // convert and display hex to denary
                                    denary = String.valueOf(Integer.parseInt(hex, 16));
                                    denaryEditText.setText(denary);

                                } else if (hex.equals("")) {

                                    // convert and display denary to hex
                                    hex = String.valueOf(Integer.toHexString(Integer.valueOf(denary)));
                                    hexEditText.setText(hex);

                                } else {

                                    // display error message to user
                                    Toast.makeText(v.getContext(),
                                            "Leave one entry variable blank.",
                                            Toast.LENGTH_SHORT).show();
                                }

                            // prevent crashes from invalid input
                            } catch (NumberFormatException e) {
                                e.printStackTrace();

                                // inform user of reason of the error
                                Toast.makeText(v.getContext(), "ERROR: invalid input",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                    // display dialog;
                    dialogDenToHex.show();
                    break;

                case 2:

                    // create dialog variable
                    final Dialog dialogBinToHex = new Dialog(v.getContext());

                    // inflate dialog layout
                    dialogBinToHex.setContentView(R.layout.conversion_dialog);

                    // initialise  buttons
                    Button dialogBinToHexBack = dialogBinToHex.findViewById(R.id.dialog_cancel);
                    Button dialogBinToHexCalculate = dialogBinToHex.findViewById(R.id.dialog_calculate);

                    // set different titles
                    TextView titleBin = dialogBinToHex.findViewById(R.id.input_title_1);
                    TextView titleHex = dialogBinToHex.findViewById(R.id.input_title_2);

                    titleBin.setText(v.getResources().getString(R.string.binary));
                    titleHex.setText(v.getResources().getString(R.string.hexadecimal));

                    // dismiss dialog when user clicks cancel button
                    dialogBinToHexBack.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialogBinToHex.dismiss();
                        }
                    });

                    dialogBinToHexCalculate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            try {


                                EditText binaryEditText = dialogBinToHex.findViewById(R.id.conversion_input_1);
                                EditText hexEditText = dialogBinToHex.findViewById(R.id.conversion_input_2);

                                String binary = binaryEditText.getText().toString();
                                String hex = hexEditText.getText().toString();

                                if (binary.equals("")) {

                                    // convert hex to binary and display
                                    binary = Integer.toBinaryString(Integer.parseInt(hex, 16));
                                    binaryEditText.setText(binary);

                                } else if (hex.equals("")) {

                                    // convert and display binary to hex
                                    hex = Integer.toString(Integer
                                            .parseInt(binary, 2), 16);

                                    hexEditText.setText(hex);

                                } else {

                                    // display error message to user
                                    Toast.makeText(v.getContext(),
                                            "Leave one entry variable blank.",
                                            Toast.LENGTH_SHORT).show();
                                }

                                // prevent crashes from invalid input
                            } catch (NumberFormatException e) {
                                e.printStackTrace();

                                // inform user of reason of the error
                                Toast.makeText(v.getContext(), "ERROR: invalid input",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                    // display dialog to user
                    dialogBinToHex.show();
                    break;
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
