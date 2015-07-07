package tk.khecnotice.khecnotice;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.util.Locale;


public class SignUpActivity extends Activity implements AdapterView.OnItemSelectedListener {

    public static final String TAG = SignUpActivity.class.toString();
    private Spinner faculty_spinner;
    private Spinner semester_spinner;
    private Spinner sex_spinner;
    private EditText usernameField, passwordField, emailField;
    private Button button_signup;

    private String faculty, semester, sex, username, password, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        initialize_vars();
    }

    private void initialize_vars() {
        // TODO Auto-generated method stub
        faculty_spinner = (Spinner) findViewById(R.id.spinner_faculty);
        semester_spinner = (Spinner) findViewById(R.id.spinner_semester);
        sex_spinner = (Spinner) findViewById(R.id.spinner_sex);

        ArrayAdapter<CharSequence> adapter_faculty = ArrayAdapter
                .createFromResource(SignUpActivity.this, R.array.faculties,
                        android.R.layout.simple_spinner_dropdown_item);
        adapter_faculty
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        faculty_spinner.setAdapter(adapter_faculty);


        ArrayAdapter<CharSequence> adapter_sex = ArrayAdapter
                .createFromResource(SignUpActivity.this, R.array.array_sex,
                        android.R.layout.simple_spinner_dropdown_item);
        adapter_sex
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sex_spinner.setAdapter(adapter_sex);

        faculty_spinner.setOnItemSelectedListener(this);
        semester_spinner.setOnItemSelectedListener(this);
        sex_spinner.setOnItemSelectedListener(this);

        usernameField = (EditText) findViewById(R.id.usernameField);
        passwordField = (EditText) findViewById(R.id.passwordField);
        emailField = (EditText) findViewById(R.id.emailField);
        button_signup = (Button) findViewById(R.id.button_signup);

        button_signup.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                username = usernameField.getText().toString().trim();
                password = passwordField.getText().toString().trim();
                email = emailField.getText().toString().trim();
                if (username.isEmpty()) {
                    Toast.makeText(SignUpActivity.this,
                            R.string.error_usernameField_signup,
                            Toast.LENGTH_SHORT).show();
                } else if (password.isEmpty()) {
                    Toast.makeText(SignUpActivity.this,
                            R.string.error_passwordField_signup,
                            Toast.LENGTH_SHORT).show();
                } else if (email.isEmpty()) {
                    Toast.makeText(SignUpActivity.this,
                            R.string.error_emailField_signup,
                            Toast.LENGTH_SHORT).show();
                } else if (!email.contains("@")) {
                    Toast.makeText(SignUpActivity.this, "Please Enter A Valid Email Adderss.", Toast.LENGTH_LONG).show();
                } else {
                    if (new NetworkInfoClass(SignUpActivity.this).isOnline) {
                        button_signup.setEnabled(false);
                        ParseUser newUser = new ParseUser();
                        newUser.setUsername(username);
                        newUser.setPassword(password);
                        newUser.setEmail(email);
                        newUser.put("faculty", faculty);
                        newUser.put("semester", semester);
                        newUser.put("sex", sex);

                        newUser.signUpInBackground(new SignUpCallback() {

                            @Override
                            public void done(ParseException e) {
                                if (e == null) {
                                    usernameField.setText("");
                                    passwordField.setText("");
                                    Toast.makeText(SignUpActivity.this,
                                            R.string.successful_log_in,
                                            Toast.LENGTH_SHORT).show();
                                    ParseUser.getCurrentUser().put(
                                            "db_version", 1);
                                    ParseUser.getCurrentUser()
                                            .saveInBackground();
                                    Intent intent = new Intent(
                                            SignUpActivity.this,
                                            DisplayRoutineActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                } else {
                                    button_signup.setEnabled(true);
                                    Toast.makeText(SignUpActivity.this,
                                            e.getMessage(), Toast.LENGTH_SHORT)
                                            .show();
                                }
                            }
                        });
                    }

                }
            }

        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos,
                               long id) {

        switch (parent.getId()) {

            case R.id.spinner_faculty:
                int a = parent.getPositionForView(view);
                faculty = parent.getItemAtPosition(a).toString()
                        .toLowerCase(Locale.US);
                Log.d(TAG, faculty);
                if (faculty.trim().equals("architecture")) {
                    ArrayAdapter<CharSequence> adapter_semester = ArrayAdapter
                            .createFromResource(this,
                                    R.array.array_semester_architecture,
                                    android.R.layout.simple_spinner_dropdown_item);
                    adapter_semester
                            .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    semester_spinner.setAdapter(adapter_semester);
                } else {
                    ArrayAdapter<CharSequence> adapter_semester = ArrayAdapter
                            .createFromResource(SignUpActivity.this,
                                    R.array.array_semester,
                                    android.R.layout.simple_spinner_dropdown_item);
                    adapter_semester
                            .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    semester_spinner.setAdapter(adapter_semester);
                }

                break;
            case R.id.spinner_semester:
                a = parent.getPositionForView(view);
                semester = parent.getItemAtPosition(a).toString()
                        .toLowerCase(Locale.US);

                break;
            case R.id.spinner_sex:
                a = parent.getPositionForView(view);
                sex = parent.getItemAtPosition(a).toString().toLowerCase(Locale.US);
                break;

        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

    }


}
