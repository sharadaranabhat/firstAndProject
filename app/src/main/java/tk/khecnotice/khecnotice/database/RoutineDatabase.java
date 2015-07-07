package tk.khecnotice.khecnotice.database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import tk.khecnotice.khecnotice.model.ClassRoutine;

public class RoutineDatabase {
    public static final String TAG = RoutineDatabase.class.toString();
    public static final String[] TABLE_NAMES = {"Sunday", "Monday", "Tuesday",
            "Wednesday", "Thursday", "Friday"};
    public static final String DATABASE = "db_routine.db";

    public static final int DB_VERSION = 1;
    public static final String C_ID = "_id";
    public static final String C_DAY_ID = "dayId";
    public static final String C_SUBJECT_TEACHER = "subjectTeacher";
    public static final String C_NAME_OF_SUBJECT = "nameOfSubject";
    public static final String C_BEG1 = "beg1";
    public static final String C_BEG2 = "beg2";
    public static final String C_END1 = "end1";
    public static final String C_END2 = "end2";
    static SQLiteDatabase db;
    Context context;
    DbHelper dbHelper;

    public RoutineDatabase(Context context) {
        this.context = context;
        dbHelper = new DbHelper();
    }

    public void deleteTableDatabase() {
        for (int i = 0; i < TABLE_NAMES.length; i++) {
            Log.d(RoutineDatabase.class.toString(), TABLE_NAMES[i]);
            String sql = String.format("DELETE FROM  %s ", TABLE_NAMES[i].toLowerCase());
            db = dbHelper.getWritableDatabase();
            db.execSQL(sql);
        }
    }

    public void insert(ClassRoutine classRoutine, String table) {
        db = dbHelper.getWritableDatabase();

		/*String sql = String.format("INSERT INTO  %s (%s, %s, %s, %s, %s, %s, %s, %s) VALUES(%s, %s, "+"'"+"%s" +"'"+", "+"'"+"%s" +"'"+", "+"'"+"%s" +"'"+", "+"'"+"%s" +"'"+", "+"'"+"%s" +"'"+", "+"'"+"%s" +"'"+") ",
                table,
				//column names
				C_ID,C_DAY_ID,C_SUBJECT_TEACHER,C_NAME_OF_SUBJECT,C_BEG1,C_BEG2,C_END1,C_END2,
				
				String.valueOf(classRoutine.getId()),
				classRoutine.getDayId(),
				classRoutine.getSubjectTeacher(),classRoutine.getNameOfSubject(),
				classRoutine.getBeg1(), classRoutine.getBeg2(), classRoutine.getEnd1(),classRoutine.getEnd2()
				
				);*/

        ContentValues values = new ContentValues();
        values.put(C_ID, classRoutine.getId());
        values.put(C_DAY_ID, classRoutine.getDayId());
        values.put(C_SUBJECT_TEACHER, classRoutine.getSubjectTeacher());
        values.put(C_NAME_OF_SUBJECT, classRoutine.getNameOfSubject());
        values.put(C_BEG1, classRoutine.getBeg1());
        values.put(C_BEG2, classRoutine.getBeg2());
        values.put(C_END1, classRoutine.getEnd1());
        values.put(C_END2, classRoutine.getEnd2());

        db.insertWithOnConflict(table, null, values, SQLiteDatabase.CONFLICT_IGNORE);

        db.close();
    }

    public Cursor getRoutineData(String table) {

        db = dbHelper.getReadableDatabase();
        return db.query(table, null, null, null, null, null, null);
    }

    class DbHelper extends SQLiteOpenHelper {

        public DbHelper() {
            super(context, DATABASE, null, DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            for (int i = 0; i < TABLE_NAMES.length; i++) {

                String sql = String
                        .format("create table %s "
                                        + "(%s INTEGER PRIMARY KEY,%s text,%s text, %s text, %s text, %s text, %s text, %s text)",
                                TABLE_NAMES[i], C_ID, C_DAY_ID, C_SUBJECT_TEACHER,
                                C_NAME_OF_SUBJECT, C_BEG1, C_BEG2, C_END1,
                                C_END2);
                db.execSQL(sql);
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }

}
