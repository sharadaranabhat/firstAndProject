package tk.khecnotice.khecnotice;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;


public class LogInActivity extends ActionBarActivity {

    public static final String TAG = LogInActivity.class.toString();
    private EditText et_usernameField;
    private EditText et_passwordField;
    private Button button_login;
    private TextView tv_signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        initialize_vars();

    }

    private void initialize_vars() {
        // TODO Auto-generated method stub
        et_usernameField = (EditText) findViewById(R.id.usernameField);
        et_passwordField = (EditText) findViewById(R.id.passwordField);
        button_login = (Button) findViewById(R.id.loginButton);
        button_login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String username = et_usernameField.getText().toString().trim();
                String password = et_passwordField.getText().toString().trim();
                if (username.isEmpty()) {
                    Toast.makeText(LogInActivity.this,
                            R.string.error_usernameField, Toast.LENGTH_SHORT)
                            .show();
                } else if (password.isEmpty()) {
                    Toast.makeText(LogInActivity.this,
                            R.string.error_passwordField, Toast.LENGTH_SHORT)
                            .show();

                } else {
                    if (new NetworkInfoClass(LogInActivity.this).isOnline) {

                        button_login.setEnabled(false);
                        ParseUser loginUser = new ParseUser();
                        loginUser.setUsername(username);
                        loginUser.setPassword(password);
                        ParseUser.logInInBackground(username, password,
                                new LogInCallback() {

                                    @Override
                                    public void done(ParseUser user,
                                                     ParseException e) {
                                        if (e == null) {
                                            Toast.makeText(LogInActivity.this,
                                                    R.string.successful_log_in,
                                                    Toast.LENGTH_SHORT).show();

                                            user.put("db_version", 1);
                                            user.saveInBackground(new SaveCallback() {

                                                @Override
                                                public void done(
                                                        ParseException e) {

                                                    if (e == null) {
                                                        Intent intent = new Intent(
                                                                LogInActivity.this,
                                                                DisplayRoutineActivity.class);
                                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                        startActivity(intent);
                                                    } else {
                                                        Toast.makeText(LogInActivity.this, "Oops! Something went wrong.\nPlease Try Again", Toast.LENGTH_SHORT).show();
                                                        et_usernameField.setText("");
                                                        et_passwordField.setText("");
                                                        button_login.setEnabled(true);

                                                    }

                                                }
                                            });

                                        } else {

                                            button_login.setEnabled(true);
                                            Toast.makeText(LogInActivity.this,
                                                    "Please Check Your Internet Connection.",
                                                    Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                });

                    }
                }
            }
        });
        tv_signup = (TextView) findViewById(R.id.tv_signup);
        tv_signup.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogInActivity.this,
                        SignUpActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_log_in, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
