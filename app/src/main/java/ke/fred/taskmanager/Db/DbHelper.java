package ke.fred.taskmanager.Db;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Fredrick on 03/06/2015.
 */
public class DbHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "task_db";
    public static final int DB_VERSION = 4;
    public static final String TODO_TABLE = "todo";
    public static final String ID = "_id";
    public static final String TITLE = "title";
    public static final String DATE = "date";
    public static final String PRIORITY = "priority";

    private SQLiteDatabase sqLiteDb = null;
    private static final String TAG ="DbHelper";

    public DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    private static final String CREATE_TABLE_TODO = "create table "
            + TODO_TABLE
            + " "
            + "( "
            + ID
            + " integer primary key autoincrement, "
            + TITLE
            + " text not null, "
            + DATE
            + " DATE not null, "
            + PRIORITY
            + " text not null);";

    public void openDb(){
        if (sqLiteDb == null){
            sqLiteDb= this.getWritableDatabase();
        }
    }

    public void closeDb(){
        if (sqLiteDb !=null)
            sqLiteDb.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_TODO);
        Log.e(TAG, "TODO TABLE CREATED!");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            db.execSQL("drop table if exists " + TODO_TABLE);
            onCreate(db);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
