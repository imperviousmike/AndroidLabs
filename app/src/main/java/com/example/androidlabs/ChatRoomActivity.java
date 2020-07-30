package com.example.androidlabs;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ChatRoomActivity extends AppCompatActivity {

    private List<Message> messageList = new ArrayList<>();
    private MyListAdapter myAdapter;
    private MessageDB messageDB;
    private DetailsFragment dFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);
        boolean isTablet = findViewById(R.id.frame) != null;
        ;
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
                Bundle dataToPass = new Bundle();
                dataToPass.putString("Message", messageList.get(position).getMessage());
                dataToPass.putLong("Id", id);
                dataToPass.putString("SendReceive", messageList.get(position).getType().toString());
                if (isTablet) {
                    dFragment = new DetailsFragment(); //add a DetailFragment
                    dFragment.setArguments(dataToPass); //pass it a bundle for information
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.frame, dFragment) //Add the fragment in FrameLayout
                            .commit(); //actually load the fragment. Calls onCreate() in DetailFragment
                } else //isPhone
                {
                    Intent nextActivity = new Intent(ChatRoomActivity.this, EmptyActivity.class);
                    nextActivity.putExtras(dataToPass); //send data to next activity
                    startActivity(nextActivity); //make the transition
                }
            }
        });

        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                new AlertDialog.Builder(ChatRoomActivity.this)
                        .setTitle(getResources().getString(R.string.deleteEntry))
                        .setMessage(getResources().getString(R.string.row_select_msg) + position + "\n" + getResources().getString(R.string.databse_id_msg) + id)
                        .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                            messageDB.deleteMessage(messageList.get(position));
                            updateView();
                            getSupportFragmentManager().beginTransaction().remove(dFragment).commit();
                        })
                        .setNegativeButton(android.R.string.no, null)
                        .show();
                return true;
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