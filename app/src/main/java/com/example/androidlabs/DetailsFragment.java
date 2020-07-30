package com.example.androidlabs;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;


public class DetailsFragment extends Fragment {

    private Bundle dataFromActivity;
    private long id;
    private AppCompatActivity parentActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        dataFromActivity = getArguments();
        id = dataFromActivity.getLong("Id");
        String message = dataFromActivity.getString("Message");
        View result = inflater.inflate(R.layout.fragment_details, container, false);
        TextView idView = (TextView) result.findViewById(R.id.idText);
        idView.setText("ID="+id);
        TextView messageView = (TextView) result.findViewById(R.id.message);
        messageView.setText(message);
        CheckBox messageStatus = (CheckBox) result.findViewById(R.id.messageStatus);
        if( dataFromActivity.getString("SendReceive").equals("SEND")){
            messageStatus.toggle();
        }
        messageView.setText(message);
        Button finishButton = (Button) result.findViewById(R.id.finishButton);
        finishButton.setOnClickListener(clk -> {
            //Tell the parent activity to remove
            parentActivity.getSupportFragmentManager().beginTransaction().remove(this).commit();
        });

        return result;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        parentActivity = (AppCompatActivity) context;
    }
}