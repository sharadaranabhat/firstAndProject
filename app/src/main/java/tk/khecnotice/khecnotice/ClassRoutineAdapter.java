package tk.khecnotice.khecnotice;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import tk.khecnotice.khecnotice.database.RoutineDatabase;

public class ClassRoutineAdapter extends SimpleCursorAdapter {

    static final String[] FROM = {RoutineDatabase.C_SUBJECT_TEACHER,
            RoutineDatabase.C_NAME_OF_SUBJECT, RoutineDatabase.C_BEG1,
            RoutineDatabase.C_BEG2, RoutineDatabase.C_END1,
            RoutineDatabase.C_END2};
    static final int[] TO = {R.id.tv_subject_teacher, R.id.tv_subject,
            R.id.tv_beg1, R.id.tv_beg2, R.id.tv_end1, R.id.tv_end2};
    final ViewBinder VIEW_BINDER = new ViewBinder() {

        @Override
        public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
            if (view.getId() == R.id.tv_subject) {
                String subjectName = cursor.getString(cursor.getColumnIndex(RoutineDatabase.C_NAME_OF_SUBJECT));
                subjectName = subjectName.replaceAll("\\\\n", "\\\n");
                subjectName = subjectName.replaceAll("LAB", "\\\t \\\t  (Lab)");
                subjectName = subjectName.trim();
                ((TextView) view).setText(subjectName);
                return true;
            } else if (view.getId() == R.id.tv_subject_teacher) {
                String subjectTeacher = cursor.getString(cursor.getColumnIndex(RoutineDatabase.C_SUBJECT_TEACHER));
                subjectTeacher = subjectTeacher.replace("\\\\n", "\\\n");
            }
            return false;
        }
    };

    public ClassRoutineAdapter(Context context, Cursor cursor) {
        super(context, R.layout.routine_rows, cursor, FROM, TO);
        // TODO Auto-generated constructor stub

    }

}


