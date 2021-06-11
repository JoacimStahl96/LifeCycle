package com.example.lifecycle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // for edittext i use et for every variable, btn for buttons, cb for checkbox and tv for textview to make it a little bit easier to recognise them all

    EditText etInputLoginUserName, etInputLoginPassword;
    Button btnLoginButton;
    TextView tvNumberOfAttemptsLeft;
    private CheckBox cbRememberLoginValues;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    boolean validatedLoginValue;
    private int counter = 5;

    final String userName = "Admin";
    final String adminPassword = "qwerty";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etInputLoginUserName = findViewById(R.id.etLoginUsername);
        etInputLoginPassword = findViewById(R.id.etLoginPassword);
        btnLoginButton = findViewById(R.id.btnLogin);
        tvNumberOfAttemptsLeft = findViewById(R.id.tvAttemptsLeft);
        cbRememberLoginValues = findViewById(R.id.checkBox);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sharedPreferences.edit();

        checkSharedPreferences();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent formActivityIntent = new Intent(this, FormActivity.class);
        Intent savedActivityIntent = new Intent(this, SavedActivity.class);

        switch (item.getItemId()) {
            case R.id.menuFormActivity:
                if (validatedLoginValue) {
                    startActivity(formActivityIntent);
                } else {
                    Toast.makeText(this, "Please login before going further", Toast.LENGTH_LONG).show();
                }
                break;

            case R.id.menuSavedActivity:
                if (validatedLoginValue) {
                    startActivity(savedActivityIntent);
                } else {
                    Toast.makeText(this, "Please login before going further", Toast.LENGTH_LONG).show();
                }
                break;
            default:
                Toast.makeText(this, "Please login before going further", Toast.LENGTH_LONG).show();
        }
        if (validatedLoginValue){
            startActivity(formActivityIntent);
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean validateLogin(String username, String loginPassword) {

        if (username.equals(userName) && loginPassword.equals(adminPassword)) {
            return true;
        }
        return false;
    }

    public void onClick(View view) {
        Intent intent = new Intent(this, FormActivity.class);
        String inputUserName = etInputLoginUserName.getText().toString();
        String inputPassword = etInputLoginPassword.getText().toString();

        if (inputUserName.isEmpty() || inputPassword.isEmpty()) {
            Toast.makeText(MainActivity.this, "Please enter username and password", Toast.LENGTH_SHORT).show();
        } else {
           // validatedLoginValue = validateLogin(inputUserName, inputPassword);
            setValidatedLoginValue(validateLogin(inputUserName, inputPassword));
            // save the checkbox preference
            if (cbRememberLoginValues.isChecked()) {

                editor.putString(getString(R.string.checkBox), "True");
                editor.apply();
                // save name
                String inputName = etInputLoginUserName.getText().toString();
                editor.putString(getString(R.string.login_name_string), inputName);
                editor.apply();
                // save password
                String password = etInputLoginPassword.getText().toString();
                editor.putString(getString(R.string.login_password_string), password);
                editor.apply();

            } else { // checkbox not checked == don't save log in values and set checkbox to false + username / password to empty strings
                editor.putString(getString(R.string.checkBox), "False");
                editor.apply();

                editor.putString(getString(R.string.login_name_string), "");
                editor.apply();

                editor.putString(getString(R.string.login_password_string), "");
                editor.apply();
            }

            if (!validatedLoginValue) {
                counter--;
                Toast.makeText(MainActivity.this, "Invalid!\nPlease enter a correct username and password", Toast.LENGTH_SHORT).show();
                tvNumberOfAttemptsLeft.setText("Login attempts remaining: " + counter);

                if (counter == 0) {
                    btnLoginButton.setEnabled(false);
                    tvNumberOfAttemptsLeft.setText("You need to restart the app if you want to try again");
                }
            } else {
             //   intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        }

    }

    public boolean isValidatedLoginValue() {
        return validatedLoginValue;
    }

    public void setValidatedLoginValue(boolean validatedLoginValue) {
        this.validatedLoginValue = validatedLoginValue;
    }
    private void checkSharedPreferences() {
        String checkBox = sharedPreferences.getString(getString(R.string.checkBox), "False");
        String name = sharedPreferences.getString(getString(R.string.login_name_string), "");
        String password = sharedPreferences.getString(getString(R.string.login_password_string), "");

        etInputLoginUserName.setText(name);
        etInputLoginPassword.setText(password);

        if (checkBox.equals("True")) {
            cbRememberLoginValues.setChecked(true);
        } else {
            cbRememberLoginValues.setChecked(false);
        }
    }
}