package com.example.androidlabs;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
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
    private MessageDB messageDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);
        messageDB = new MessageDB(this);
        messageDB.getWritableDatabase();
        messageList = messageDB.getAll();
        ListView list = findViewById(R.id.chatList);
        list.setAdapter(myAdapter = new MyListAdapter());
        Button send = findViewById(R.id.sendButton);
        Button receive = findViewById(R.id.receiveButton);
        EditText text = findViewById(R.id.chatText);

        send.setOnClickListener(v -> {
            messageDB.addMessage(new Message(text.getText().toString(), MessageType.SEND));
            updateView();
        });

        receive.setOnClickListener(v -> {
            messageDB.addMessage(new Message(text.getText().toString(), MessageType.RECIEVE));
            updateView();
        });


        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                new AlertDialog.Builder(ChatRoomActivity.this)
                        .setTitle(getResources().getString(R.string.deleteEntry))
                        .setMessage(getResources().getString(R.string.row_select_msg) + position + "\n" + getResources().getString(R.string.databse_id_msg) + id)
                        .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                            messageDB.deleteMessage(messageList.get(position));
                            updateView();
                        })

                        .setNegativeButton(android.R.string.no, null)
                        .show();
            }
        });


    }

    private void updateView() {
        messageList = messageDB.getAll();
        myAdapter.notifyDataSetChanged();
    }

    private class MyListAdapter extends BaseAdapter {

        public int getCount() {
            return messageList.size();
        }

        public Object getItem(int position) {
            return messageList.get(position).getMessage();
        }

        public long getItemId(int position) {
            return messageList.get(position).getId();
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