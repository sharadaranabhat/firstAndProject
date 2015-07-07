package tk.khecnotice.khecnotice;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;


public class NetworkInfoClass {
    public boolean isOnline;

    public NetworkInfoClass(Context context) {
        // TODO Auto-generated method stub
        ConnectivityManager con = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo netInfo = con.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            isOnline = true;
        } else {
            Toast.makeText(context, "Network isn't available", Toast.LENGTH_SHORT).show();
            Log.d("aa", "network not available");
            isOnline = false;
        }
    }

}
