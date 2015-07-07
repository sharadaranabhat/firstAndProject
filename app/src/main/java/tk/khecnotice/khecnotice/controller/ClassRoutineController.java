package tk.khecnotice.khecnotice.controller;

import java.util.List;

import android.util.Log;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import tk.khecnotice.khecnotice.KhECNoticeApplication;
import tk.khecnotice.khecnotice.database.RoutineDatabase;
import tk.khecnotice.khecnotice.model.ClassRoutine;
import tk.khecnotice.khecnotice.parser.ClassRoutineXMLParser;

public class ClassRoutineController {

    public static final String TAG = ClassRoutineController.class.toString();

    public static final String[] TABLE_NAMES = {"Sunday", "Monday", "Tuesday",
            "Wednesday", "Thursday", "Friday"};
    static List<ClassRoutine> classRoutineList;
    private static String routine;

    public static void checkAndInsertDatabase() {

        String faculty = ParseUser.getCurrentUser().getString("faculty");
        final String semester = ParseUser.getCurrentUser()
                .getString("semester");


        ParseQuery<ParseObject> aa = new ParseQuery<>(faculty);
        aa.setLimit(10);
        aa.findInBackground(new FindCallback<ParseObject>() {

            @Override
            public void done(List<ParseObject> objects, ParseException e) {

                if (e == null) {
                    for (ParseObject parseObject : objects) {
                        if (parseObject.getString("semester").equals(semester)) {
                            int db_version = Integer.parseInt(parseObject.getString("dv_version"));
                            if (db_version > ParseUser.getCurrentUser().getInt("db_version")) {
                                KhECNoticeApplication.db_routine.deleteTableDatabase();
                                setRoutine(parseObject.getString("routineData"));
                                classRoutineList = ClassRoutineXMLParser
                                        .parseFeed(routine);

                                insert_into_database();
                                ParseUser.getCurrentUser().put("db_version", db_version);
                                ParseUser.getCurrentUser().saveInBackground();
                            } else {
                                //Routine is upto date
                            }
                        }

                    }
                    Log.d(TAG, "true");
                } else {
                    Log.d(TAG, "false");
                    /*
					 * } AlertDialog.Builder dialog = new AlertDialog.Builder(
					 * ClassRoutineActivity.this); dialog.setTitle("Null");
					 * dialog.setMessage(e.getMessage());
					 * dialog.setPositiveButton(android.R.string.ok, null);
					 * dialog.show();
					 */
                }
            }
        });
    }


    public static void insert_into_database() {
        RoutineDatabase routineDatabase = KhECNoticeApplication.db_routine;
        for (ClassRoutine classRoutine : classRoutineList) {
            Log.d(TAG, "a \n" + classRoutine.getNameOfSubject());
            switch (classRoutine.getDayId()) {
                case "1":
                    routineDatabase.insert(classRoutine, TABLE_NAMES[0]);
                    break;
                case "2":
                    routineDatabase.insert(classRoutine, TABLE_NAMES[1]);

                    break;
                case "3":
                    routineDatabase.insert(classRoutine, TABLE_NAMES[2]);
                    break;
                case "4":
                    routineDatabase.insert(classRoutine, TABLE_NAMES[3]);
                    break;
                case "5":
                    routineDatabase.insert(classRoutine, TABLE_NAMES[4]);
                    break;
                case "6":
                    routineDatabase.insert(classRoutine, TABLE_NAMES[5]);
                    break;
            }

        }
        Log.d(TAG, "done inserting");

    }

    public String getRoutine() {
        return routine;
    }

    public static void setRoutine(String aa) {
        routine = aa;
    }

}
