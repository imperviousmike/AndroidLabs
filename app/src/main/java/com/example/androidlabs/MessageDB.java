package com.example.androidlabs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MessageDB extends SQLiteOpenHelper {

    protected final static String DATABASE_NAME = "MessageDB";
    protected final static int VERSION_NUM = 1;
    public final static String TABLE_NAME = "MESSAGES";
    public final static String COL_MESSAGE = "MESSAGE";
    public final static String COL_ISSEND = "ISSEND";
    public final static String COL_ISRECEIVE = "ISRECEIVE";
    public final static String COL_ID = "_id";
    public final String[] columns = {COL_ID, COL_MESSAGE, COL_ISSEND, COL_ISRECEIVE};

    public MessageDB(Context ctx) {
        super(ctx, DATABASE_NAME, null, VERSION_NUM);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " ("
                + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_MESSAGE + " TEXT,"
                + COL_ISSEND + " INTEGER,"
                + COL_ISRECEIVE + " INTEGER);");
    }

    public boolean addMessage(Message msg) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_MESSAGE, msg.getMessage());
        if (msg.getType() == MessageType.SEND) {
            values.put(COL_ISSEND, 1);
        } else {
            values.put(COL_ISRECEIVE, 1);
        }
        if (db.insert(TABLE_NAME, null, values) > 0) {
            return true;
        }
        return false;
    }


    public boolean deleteMessage(Message msg) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_MESSAGE, msg.getMessage());
        if (msg.getType() == MessageType.SEND) {
            values.put(COL_ISSEND, 1);
        } else {
            values.put(COL_ISRECEIVE, 1);
        }
        if (db.delete(TABLE_NAME, COL_ID + "=" + msg.getId(), null) > 0) {
            return true;
        }
        return false;
    }

    public List<Message> getAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        List<Message> msgList = new ArrayList<>();
        MessageType msgType = null;
        Cursor c = db.query(TABLE_NAME, columns, null, null, null, null, null);
        c.moveToFirst();
        while (!c.isAfterLast()) {
            if (c.getString(2) != null) {
                msgType = MessageType.SEND;
            } else if (c.getString(3) != null) {
                msgType = MessageType.RECIEVE;
            }
            msgList.add(new Message(c.getInt(0), c.getString(1), msgType));
            c.moveToNext();
        }
        printCursor(c, VERSION_NUM);
        return msgList;
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    private void printCursor(Cursor c, int version) {
        Log.i("printCursor", "DB version number: " + version
                + "\nNumber of columns: "
                + c.getColumnCount()
                + "\nColumn Names: " + Arrays.toString(c.getColumnNames())
                + "\nNumber of rows: " + c.getCount());
    }

}
