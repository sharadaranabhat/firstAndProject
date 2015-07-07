package tk.khecnotice.khecnotice;

import android.app.Application;

import com.parse.Parse;

import java.util.Calendar;
import java.util.GregorianCalendar;

import tk.khecnotice.khecnotice.database.RoutineDatabase;

/**
 * Created by root on 6/21/15.
 */
public class KhECNoticeApplication extends Application {

    public static Calendar calendar;
    public static RoutineDatabase db_routine;

    @Override
    public void onCreate() {
        super.onCreate();
// Enable Local Datastore.
        Parse.enableLocalDatastore(this);

        Parse.initialize(this, "nzfezEpyg3gNuhPpLJR3gERaGpx0vHfntECkMt50", "a5rqMDYvJ7KO0IX8bXIKvDSlYXksYDQBVsAT6fcV");

        db_routine = new RoutineDatabase(this);
        calendar = new GregorianCalendar();
    }
}
