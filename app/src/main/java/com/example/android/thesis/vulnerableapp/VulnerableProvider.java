package com.example.android.thesis.vulnerableapp;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.content.ContentUris;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.text.TextUtils;

import java.util.HashMap;

public class VulnerableProvider extends ContentProvider {
    static final String PROVIDER_NAME = "com.example.VulnerableApp.VulnerableProvider";
    static final String URL = "content://" + PROVIDER_NAME + "/secrets";
    public static final Uri CONTENT_URI = Uri.parse(URL);

    // Those are the two columns of the table "secrets"
    public static final String _ID = "_id";
    public static final String SECRET = "secret";

    static final int SECRETS = 1;
    static final int SECRET_ID = 2;

    private static HashMap<String, String> SECRETS_PROJECTION_MAP;

    /**
     * Database specific constant declarations
     */
    private SQLiteDatabase db;
    static final String DATABASE_NAME = "VulnerableDB";
    static final String SECRETS_TABLE_NAME = "secrets";
    static final int DATABASE_VERSION = 1;

    // We will create a DB with one table: "secrets"
    // This table will contain two columns: "_id" and "secret"
    static final String CREATE_DB_TABLE = " CREATE TABLE " + SECRETS_TABLE_NAME + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, " + " secret TEXT NOT NULL); ";

    static final UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, "secrets", SECRETS);
        uriMatcher.addURI(PROVIDER_NAME, "secrets/#", SECRET_ID);
    }


    /**
     * Helper class that actually creates and manages
     * the provider's underlying data repository.
     */
    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_DB_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + SECRETS_TABLE_NAME);
            onCreate(db);
        }
    }


    /**
     * Create a writable database which will trigger its
     * creation if it doesn't already exist.
     */
    @Override
    public boolean onCreate() {
        Context context = getContext();
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();
        return (db == null) ? false : true;
    }


    @Override
    public Uri insert(Uri uri, ContentValues values) {
        /**
         * Add a new secret record
         */
        long rowID = db.insert(SECRETS_TABLE_NAME, "", values);

        /**
         * If record is added successfully
         */
        if (rowID > 0) {
            Uri _uri = ContentUris.withAppendedId(CONTENT_URI, rowID);
            getContext().getContentResolver().notifyChange(_uri, null);
            return _uri;
        }
        throw new SQLException("Failed to add a record into " + uri);
    }


    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
////         SECURE -> But it should be vulnerable!!
//        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
//        qb.setTables(SECRETS_TABLE_NAME);
//
//        switch (uriMatcher.match(uri)) {
//            case SECRETS:
//                qb.setProjectionMap(SECRETS_PROJECTION_MAP);
//                break;
//
//            case SECRET_ID:
//                qb.appendWhere( _ID + "=" + uri.getPathSegments().get(1));
//                break;
//
//            default:
//        }
//
//        if (sortOrder == null || sortOrder == ""){
//            /**
//             * By default sort on secrets
//             */
//            sortOrder = SECRET;
//        }
//
//        Cursor c = qb.query(db,	projection,	selection,
//                selectionArgs,null, null, sortOrder);
//        /**
//         * register to watch a content URI for changes
//         */
//        c.setNotificationUri(getContext().getContentResolver(), uri);
//        return c;

        // VULNERABLE
        Cursor cursor = null;
        switch (uriMatcher.match(uri)) {
            case SECRETS:   // insert also 'selection' and 'selectionArgs' to perform a sql injection
                cursor = db.query(false, SECRETS_TABLE_NAME, projection, null, null, null, null, sortOrder, null);
                break;

            case SECRET_ID:
                cursor = db.query(false, SECRETS_TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder, null);
                break;

            default:
        }

        /**
         * register to watch a content URI for changes
         */
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }


    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int count = 0;
        switch (uriMatcher.match(uri)) {
            case SECRETS:
                count = db.delete(SECRETS_TABLE_NAME, selection, selectionArgs);
                break;

            case SECRET_ID:
                String id = uri.getPathSegments().get(1);
                count = db.delete(SECRETS_TABLE_NAME, _ID + " = " + id +
                        (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }


    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int count = 0;
        switch (uriMatcher.match(uri)) {
            case SECRETS:
                count = db.update(SECRETS_TABLE_NAME, values, selection, selectionArgs);
                break;

            case SECRET_ID:
                count = db.update(SECRETS_TABLE_NAME, values,
                        _ID + " = " + uri.getPathSegments().get(1) +
                                (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }


    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)) {
            /**
             * Get all secrets
             */
            case SECRETS:
                return "vnd.android.cursor.dir/vnd.example.secrets";

            /**
             * Get a particular secret
             */
            case SECRET_ID:
                return "vnd.android.cursor.item/vnd.example.secrets";

            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
    }
}

