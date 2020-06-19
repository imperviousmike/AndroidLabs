package com.example.androidlabs;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ChatRoomActivity extends AppCompatActivity {

    private List<Message> messageList = new ArrayList<>();
    private MyListAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);
        ListView list = findViewById(R.id.chatList);
        list.setAdapter(myAdapter = new MyListAdapter());
        Button send = findViewById(R.id.sendButton);
        Button receive = findViewById(R.id.receiveButton);
        EditText text = findViewById(R.id.chatText);

        send.setOnClickListener(v -> {
            messageList.add(new Message(text.getText().toString(), MessageType.SEND));
            myAdapter.notifyDataSetChanged();
        });

        receive.setOnClickListener(v -> {
            messageList.add(new Message(text.getText().toString(), MessageType.RECIEVE));
            myAdapter.notifyDataSetChanged();
        });


        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                new AlertDialog.Builder(ChatRoomActivity.this)
                        .setTitle(getResources().getString(R.string.deleteEntry))
                        .setMessage(getResources().getString(R.string.row_select_msg) + position + "\n" + getResources().getString(R.string.databse_id_msg) + id)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                messageList.remove(position);
                                myAdapter.notifyDataSetChanged();
                            }
                        })

                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });


    }

    private class MyListAdapter extends BaseAdapter {

        public int getCount() {
            return messageList.size();
        }

        public Object getItem(int position) {
            return messageList.get(position).getMessage();
        }

        public long getItemId(int position) {
            return (long) position;
        }

        public View getView(int position, View old, ViewGroup parent) {

            LayoutInflater inflater = getLayoutInflater();
            View newView;
            TextView tView;
            if (messageList.get(position).getType().equals(MessageType.SEND)) {
                newView = inflater.inflate(R.layout.row_send, parent, false);
                tView = newView.findViewById(R.id.sendText);
            } else {
                newView = inflater.inflate(R.layout.row_receive, parent, false);
                tView = newView.findViewById(R.id.receiveText);
            }
            tView.setText(getItem(position).toString());
            EditText text = findViewById(R.id.chatText);
            text.setText("");

            //return it to be put in the table
            return newView;
        }
    }
}