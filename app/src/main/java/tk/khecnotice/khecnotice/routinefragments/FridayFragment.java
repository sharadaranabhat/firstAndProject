package tk.khecnotice.khecnotice.routinefragments;


import tk.khecnotice.khecnotice.ClassRoutineAdapter;
import tk.khecnotice.khecnotice.KhECNoticeApplication;
import tk.khecnotice.khecnotice.R;
import tk.khecnotice.khecnotice.database.RoutineDatabase;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.SimpleCursorAdapter.ViewBinder;

public class FridayFragment extends android.support.v4.app.ListFragment {

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
                subjectTeacher = subjectTeacher.replaceAll("\\\\n", "\\\n");
                ((TextView) view).setText(subjectTeacher);

                return true;
            }
            return false;
        }
    };
    Cursor cursor = null;
    ClassRoutineAdapter adapter = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_display_routine, container,
                false);

        return rootView;
    }

    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        if (cursor == null) {
            cursor = KhECNoticeApplication.db_routine.getRoutineData("friday");
        }
        if (adapter == null) {
            adapter = new ClassRoutineAdapter(getListView().getContext(),
                    cursor);
        }
        adapter.setViewBinder(VIEW_BINDER);
        adapter.changeCursor(cursor);
        getListView().setAdapter(adapter);

    }

}

