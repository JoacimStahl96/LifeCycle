package com.example.lifecycle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class FormActivity extends AppCompatActivity {

    EditText etFirstName, etSurname, etEmail, etAge, etPhone;
    Button submitButton;
    MainActivity mainActivity = new MainActivity();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        etFirstName = findViewById(R.id.etNameOfPersonFormAct);
        etSurname = findViewById(R.id.etSurNameOfPersonFormAct);
        etEmail = findViewById(R.id.etEmailOfPersonFormAct);
        etAge = findViewById(R.id.etAgeOfPersonFormAct);
        etPhone = findViewById(R.id.etPhoneOfPersonFormAct);
        submitButton = findViewById(R.id.btnFormActivity);
    }

    public void submitForm(View view) {
/*
    *
    *
    *       testa att göra exakt likadant som i mainActivity med att spara värdena i strings, kan man skippa bundle så sharedpreferences fungerar på ett smidigt sätt.
    *       Bästa däremot är att fortsätta använda bundle och kunna lägga in värdena i sharedPreferences i savedActivity
    *
    *
 */
        Intent intent = new Intent(this, SavedActivity.class);

        String firstNameValue = etFirstName.getText().toString();
        String surnameValue = etSurname.getText().toString();
        String emailValue = etEmail.getText().toString();
        String ageValue = etAge.getText().toString();
        String phoneValue = etPhone.getText().toString();

        Bundle bundle = new Bundle();
        bundle.putString("firstNameValue", firstNameValue);
        bundle.putString("surnameValue", surnameValue);
        bundle.putString("emailValue", emailValue);
        bundle.putString("ageValue", ageValue);
        bundle.putString("phoneValue", phoneValue);

        intent.putExtra("submitBundle", bundle);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        startActivity(intent);
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
        Intent savedActivityIntent = new Intent(this, SavedActivity.class);

        switch (item.getItemId()) {
            case R.id.menuLoginActivity:
                    startActivity(mainActivityIntent);
                    break;
            case R.id.menuSavedActivity:
             //   savedActivityIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(savedActivityIntent);
                    break;
            default:
                Log.d("boolean", "onOptionsItemSelected: " + mainActivity.isValidatedLoginValue());
                Toast.makeText(this, "Please login before going further, actually it shouldn't even be possible for you to get here so congrats" + mainActivity.isValidatedLoginValue(), Toast.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }
}