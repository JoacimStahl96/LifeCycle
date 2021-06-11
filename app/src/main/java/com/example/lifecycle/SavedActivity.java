package com.example.lifecycle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class SavedActivity extends AppCompatActivity {

    TextView tvSavedFirstName, tvSavedLastName, tvSavedEmail, tvSavedAge, tvSavedPhone;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved);

        tvSavedFirstName = findViewById(R.id.savedFirstNameSavedAct);
        tvSavedLastName = findViewById(R.id.savedSurnameSavedAct);
        tvSavedEmail = findViewById(R.id.savedEmailSavedAct);
        tvSavedAge = findViewById(R.id.savedAgeSavedAct);
        tvSavedPhone = findViewById(R.id.savedPhoneSavedAct);

        bundledDataThatWorks();
     //   savedSharedPreferences();
        Intent intent = getIntent();
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent mainActivityIntent = new Intent(this, MainActivity.class);
        Intent formActivityIntent = new Intent(this, FormActivity.class);

        switch (item.getItemId()) {
            case R.id.menuLoginActivity:
                startActivity(mainActivityIntent);
                break;

            case R.id.menuFormActivity:
                startActivity(formActivityIntent);
                break;
            default:
                Toast.makeText(this, "Please login before going further, actually it shouldn't even be possible for you to get here so congrats", Toast.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }
    // detta fungerar verkligen inte, lös det.
    public void savedSharedPreferences() {
        Intent intent = getIntent();
        Bundle savedBundle = intent.getBundleExtra("submitBundle");

        String savedFirstName = savedBundle.getString("firstNameValue");
        String savedLastName = savedBundle.getString("surnameValue");
        String savedEmail = savedBundle.getString("emailValue");
        String savedAge = savedBundle.getString("ageValue");
        String savedPhone = savedBundle.getString("phoneValue");

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sharedPreferences.edit();

        // save name
        String firstName = tvSavedFirstName.getText().toString();
        editor.putString(savedFirstName, firstName);
        editor.apply();
        // save password
        String lastName = tvSavedLastName.getText().toString();
        editor.putString(savedLastName, lastName);
        editor.commit();
        String email = tvSavedEmail.getText().toString();
        editor.putString(getString(R.string.second_activity_email), email);

        editor.apply();


        String name = sharedPreferences.getString(savedFirstName, "hejsan");
        String lastname = sharedPreferences.getString(getString(R.string.last_name_saved), "");
        String email1 = sharedPreferences.getString(getString(R.string.email_saved), "");
        String age = sharedPreferences.getString(getString(R.string.age_saved), "");
        String phone = sharedPreferences.getString(getString(R.string.phone_saved), "");

        tvSavedFirstName.setText(savedFirstName);
        tvSavedLastName.setText(lastname);
        tvSavedEmail.setText(email1);
        tvSavedAge.setText(age);
        tvSavedPhone.setText(phone);
    }

    public void bundledDataThatWorks() {
        Intent intent = getIntent();
        Bundle savedBundle = intent.getBundleExtra("submitBundle");

        String savedFirstName = savedBundle.getString("firstNameValue");
        String savedLastName = savedBundle.getString("surnameValue");
        String savedEmail = savedBundle.getString("emailValue");
        String savedAge = savedBundle.getString("ageValue");
        String savedPhone = savedBundle.getString("phoneValue");

        tvSavedFirstName.setText(savedFirstName);
        tvSavedLastName.setText(savedLastName);
        tvSavedEmail.setText(savedEmail);
        tvSavedAge.setText(savedAge);
        tvSavedPhone.setText(savedPhone);
    }
}