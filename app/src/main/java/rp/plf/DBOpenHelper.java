package rp.plf;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.jetbrains.annotations.Nullable;

public class DBOpenHelper extends SQLiteOpenHelper {

    private static final String CREATE_EVENTS_TABLE= "create table "+ DBStructure.EVENT_TABLE_NAME+"(ID INTEGER PRIMARY KEY AUTOINCREMENT, "
            + DBStructure.EVENT+" TEXT, "+ DBStructure.TIME+" TEXT, "+ DBStructure.DATE+" TEXT, "+ DBStructure.MONTH+" TEXT, "
            + DBStructure.YEAR+" TEXT)";
    private static final String DROP_EVENTS_TABLE= "DROP TABLE IF EXISTS "+ DBStructure.EVENT_TABLE_NAME;
    public DBOpenHelper(@Nullable Context context) {
        super(context, DBStructure.DB_NAME, null, DBStructure.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_EVENTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(DROP_EVENTS_TABLE);
        onCreate(sqLiteDatabase);
    }

    public void SaveEvent(String event, String time, String date, String month, String year, SQLiteDatabase database){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBStructure.EVENT, event);
        contentValues.put(DBStructure.TIME, time);
        contentValues.put(DBStructure.DATE, date);
        contentValues.put(DBStructure.MONTH, month);
        contentValues.put(DBStructure.YEAR, year);
        database.insert(DBStructure.EVENT_TABLE_NAME,null,contentValues);
    }

    public Cursor ReadEvents(String date, SQLiteDatabase database){
        String [] Projections = {DBStructure.EVENT , DBStructure.TIME, DBStructure.DATE, DBStructure.MONTH, DBStructure.YEAR};
        String selection = DBStructure.DATE +"=?";
        String [] selectionArgs = {date};
        return database.query(DBStructure.EVENT_TABLE_NAME,Projections,selection,selectionArgs,null,null,null);

    }
    public Cursor ReadEventsperMonth(String month,String year, SQLiteDatabase database){
        String [] Projections = {DBStructure.EVENT , DBStructure.TIME, DBStructure.DATE, DBStructure.MONTH, DBStructure.YEAR};
        String selection = DBStructure.MONTH +"=? and "+ DBStructure.YEAR+"=?";
        String [] selectionArgs = {month,year};
        return database.query(DBStructure.EVENT_TABLE_NAME,Projections,selection,selectionArgs,null,null,null);

    }

    public void deleteEvent(String event, String date, String time, SQLiteDatabase database){
        String selection = DBStructure.EVENT+"=? and "+ DBStructure.DATE+"=? and "+ DBStructure.TIME+"=?";
        String[] selectionArg = {event,date,time};
        database.delete(DBStructure.EVENT_TABLE_NAME,selection,selectionArg);
    }

}
