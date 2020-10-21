package DataHandler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import Model.Task;

public class DatabaseHandler extends SQLiteOpenHelper {

    //Database version
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "todosDB";
    public static final String TABLE_NAME = "todos";

    //Contacts table column name
    public static final String KEY_ID = "id";
    public static final String KEY_TASK = "task";
    public static final String KEY_DESC = "description";
    public static final String KEY_DATE = "date";
    public static final String KEY_TIME = "time";
    public static final String KEY_PRIORITY = "priority";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TODO_TABLE = "CREATE TABLE " + TABLE_NAME + "(" + KEY_ID +
                " INTEGER PRIMARY KEY," + KEY_TASK + " TEXT," + KEY_DESC + " TEXT," +
                KEY_DATE + " TEXT," + KEY_TIME + " TEXT," + KEY_PRIORITY + " TEXT" + ")";
        db.execSQL(CREATE_TODO_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    //CRUD OPERATIONS

    public void addTask(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues value = new ContentValues();
        value.put(KEY_TASK, task.getTask());
        value.put(KEY_DESC, task.getDescription());
        value.put(KEY_DATE, task.getDate());
        value.put(KEY_TIME, task.getTime());
        value.put(KEY_PRIORITY, task.getPriority());

        db.insert(TABLE_NAME, null, value);
        db.close();
    }

    public List<Task> getAllTasks() {
        SQLiteDatabase db = this.getReadableDatabase();

        List<Task> tasklist = new ArrayList<>();

        String GET_ALL_TASKS = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(GET_ALL_TASKS, null);

        if (cursor.moveToFirst()) {
            do {
                Task task = new Task();

                task.setId(Integer.parseInt(cursor.getString(0)));
                task.setTask(cursor.getString(1));
                task.setDescription(cursor.getString(2));
                task.setDate(cursor.getString(3));
                task.setTime(cursor.getString(4));
                task.setPriority(cursor.getString(5));

                tasklist.add(task);

            } while (cursor.moveToNext());
        }
        return tasklist;
    }

    public Task getTask(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME, new String[] { KEY_ID,
                        KEY_TASK, KEY_DESC, KEY_DATE, KEY_TIME, KEY_PRIORITY }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        Task task = new Task(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), cursor.getString(3),
                cursor.getString(4), cursor.getString(5));

        return task;
    }

    public int updateTask(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues value = new ContentValues();
        value.put(KEY_TASK, task.getTask());
        value.put(KEY_DESC, task.getDescription());
        value.put(KEY_DATE, task.getDate());
        value.put(KEY_TIME, task.getTime());
        value.put(KEY_PRIORITY, task.getPriority());

        return db.update(TABLE_NAME, value, KEY_ID + "=?",
                new String[] { String.valueOf(task.getId())} );
    }

    public void delTask(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, KEY_ID + " = ?", new String[] {String.valueOf(id)});
        db.close();
    }

    public void delAllTasks() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
    }
}
