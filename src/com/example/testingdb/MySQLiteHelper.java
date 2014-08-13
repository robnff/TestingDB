package com.example.testingdb;

import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.content.ContentValues;
import android.content.Context;

public class MySQLiteHelper extends SQLiteOpenHelper{
	private static int DATABASE_VERSION=1;
	private static String DATABASE_NAME="BookDB";
	private static String TABLE_BOOKS="books";
	private static String KEY_ID="id";
	private static String KEY_AUTHOR="author";
	private static String KEY_TITLE="title";
	private static String[] COLUMNS = {KEY_ID,KEY_TITLE,KEY_AUTHOR};

	public MySQLiteHelper(Context context){
		super(context, DATABASE_NAME,null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db){
		String CREATE_BOOK_TABLE = "CREATE TABLE books ( "+ 
				"id INTEGER PRIMARY KEY AUTOINCREMENT, "+
				"title TEXT, "+
				"author TEXT)";
		db.execSQL(CREATE_BOOK_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldV, int newV){
		db.execSQL("DROP TABLE IF EXISTS books");
		this.onCreate(db);
	}

	public void addBook(Book book){
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_TITLE, book.getTitle());
		values.put(KEY_AUTHOR, book.getAuthor());

		db.insert(TABLE_BOOKS, null, values);
		db.close();
	}

	public Book getBook(int id){
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABLE_BOOKS,
				COLUMNS,
				"id = ?",
				new String[]{String.valueOf(id)}, 
				null,
				null,
				null,
				null);
		if (cursor != null){
			cursor.moveToFirst();
		}
		Book book = new Book();
		book.setId(Integer.parseInt(cursor.getString(0)));
		book.setTitle(cursor.getString(1));
		book.setAuthor(cursor.getString(2));
		return book;
	}

}
