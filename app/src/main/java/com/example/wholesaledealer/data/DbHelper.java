package com.example.wholesaledealer.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.*;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import com.example.wholesaledealer.data.WholesaleItem;

public class DbHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "wholesale.db";
    private static final int DB_VERSION = 1;
    private static final String TABLE = "items";

    public DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create = "CREATE TABLE " + TABLE + " (id INTEGER PRIMARY KEY, name TEXT, model TEXT, buy REAL, sell REAL, profit REAL, cust TEXT, phone TEXT, img TEXT)";
        db.execSQL(create);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int old, int ne) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE);
        onCreate(db);
    }

    public long insertRecord(String name, String model, double buy, double sell, double profit, String cust, String phone, Bitmap img) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name", name);
        cv.put("model", model);
        cv.put("buy", buy);
        cv.put("sell", sell);
        cv.put("profit", profit);
        cv.put("cust", cust);
        cv.put("phone", phone);

        // encode bitmap to base64
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        img.compress(Bitmap.CompressFormat.PNG, 100, bos);
        String encoded = Base64.encodeToString(bos.toByteArray(), Base64.DEFAULT);
        cv.put("img", encoded);

        return db.insert(TABLE, null, cv);
    }

    public List<WholesaleItem> getAll() {
        List<WholesaleItem> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE, null);
        while (c.moveToNext()) {
            WholesaleItem item = new WholesaleItem();
            item.id = c.getLong(c.getColumnIndex("id"));
            item.name = c.getString(c.getColumnIndex("name"));
            item.model = c.getString(c.getColumnIndex("model"));
            item.buy = c.getDouble(c.getColumnIndex("buy"));
            item.sell = c.getDouble(c.getColumnIndex("sell"));
            item.profit = c.getDouble(c.getColumnIndex("profit"));
            item.customer = c.getString(c.getColumnIndex("cust"));
            item.phone = c.getString(c.getColumnIndex("phone"));

            // decode image
            byte[] bytes = Base64.decode(c.getString(c.getColumnIndex("img")), Base64.DEFAULT);
            item.image = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

            list.add(item);
        }
        c.close();
        return list;
    }
}