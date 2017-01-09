package com.whackyard.flipmapapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nazila on 07/01/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "contactManager";

    private static final String TABLE_CONTACT = "importedContact";

    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_MOB = "mobile";
    private static final String KEY_LAND = "landline";
    private static final String KEY_MOD = "mod";
    private static final String KEY_ICON = "icon";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACT + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_EMAIL + " TEXT," + KEY_MOB + " TEXT,"+ KEY_LAND + " TEXT," + KEY_MOD + " TEXT,"
                + KEY_ICON + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACT);
        onCreate(db);
    }

    void addContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, contact.getName());
        values.put(KEY_EMAIL, contact.getEmail());
        values.put(KEY_MOB, contact.getMobile_number());
        values.put(KEY_LAND, contact.getLandline_number());
        values.put(KEY_MOD, contact.getMode());
        values.put(KEY_ICON, contact.getIcon());

        db.insert(TABLE_CONTACT, null, values);
        db.close();
    }

    public List<Contact> getAllContacts() {
        List<Contact> contactList = new ArrayList<Contact>();

        String selectQuery = "SELECT  * FROM " + TABLE_CONTACT + " WHERE MOD LIKE"+" '"+"IMPORT"+"'" +" ORDER BY NAME";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Contact contact = new Contact();
                contact.setId(cursor.getInt(0));
                contact.setName(cursor.getString(1));
                contact.setEmail(cursor.getString(2));
                contact.setMobile_number(cursor.getString(3));
                contact.setLandline_number(cursor.getString(4));
                contact.setMode(cursor.getString(5));
                contact.setIcon(cursor.getString(6));
                // Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contactList;
    }

    public List<Contact> getAllContactsManually() {
        List<Contact> contactList = new ArrayList<Contact>();

        String selectQuery = "SELECT  * FROM " + TABLE_CONTACT + " WHERE MOD LIKE"+" '"+"SINGLE"+"'" +" ORDER BY NAME";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Contact contact = new Contact();
                contact.setId(cursor.getInt(0));
                contact.setName(cursor.getString(1));
                contact.setEmail(cursor.getString(2));
                contact.setMobile_number(cursor.getString(3));
                contact.setLandline_number(cursor.getString(4));
                contact.setMode(cursor.getString(5));
                contact.setIcon(cursor.getString(6));
                // Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contactList;
    }

    public void deleteContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACT, KEY_ID + " = ?",
                new String[] { String.valueOf(contact.getId()) });
        db.close();
    }
}
