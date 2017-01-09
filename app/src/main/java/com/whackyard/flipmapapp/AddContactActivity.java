package com.whackyard.flipmapapp;

import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddContactActivity extends AppCompatActivity {

    private DatabaseHelper db;
    private Button mAddContact;
    private TextInputLayout inputLayoutName, inputLayoutMob, inputLayoutLand, inputLayoutEmail;
    private EditText mName, mMobile, mLand, mEmail;
    private String strName, strMob, strLand, strEmail, strMode, strIcon;
    private LinearLayout mMainLayout, mSuccess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        db = new DatabaseHelper(this);

        mName = (EditText) findViewById(R.id.input_name);
        mMobile = (EditText) findViewById(R.id.input_mob);
        mLand = (EditText) findViewById(R.id.input_land);
        mEmail = (EditText) findViewById(R.id.input_email);
        mMainLayout = (LinearLayout) findViewById(R.id.activity_add_contact);
        mSuccess = (LinearLayout) findViewById(R.id.on_success);

        inputLayoutName = (TextInputLayout) findViewById(R.id.input_name_layout);
        inputLayoutMob = (TextInputLayout) findViewById(R.id.input_mob_layout);
        inputLayoutLand = (TextInputLayout) findViewById(R.id.input_land_layout);
        inputLayoutEmail = (TextInputLayout) findViewById(R.id.input_email_layout);

        mAddContact =(Button) findViewById(R.id.btnSave);

        mAddContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToContact();
            }
        });
    }

    public void addToContact(){
        strName = mName.getText().toString();
        if(!isValidName(strName)){
            inputLayoutName.setError("Invalid Name");
            requestFocus(mName);
        }else {
            inputLayoutName.setErrorEnabled(false);
        }

        strEmail = mEmail.getText().toString();
        if(!isValidEmail(strEmail)){
            inputLayoutEmail.setError("Invalid Email");
            requestFocus(mEmail);
        }else {
            inputLayoutEmail.setErrorEnabled(false);
        }

        strMob = mMobile.getText().toString();
        if(!isValidMobNumber(strMob)){
            inputLayoutMob.setError("Invalid Number");
            requestFocus(mMobile);
        }else {
            inputLayoutMob.setErrorEnabled(false);
        }

        strLand = mLand.getText().toString();
        if(!isValidMobNumber(strLand)){
            inputLayoutLand.setError("Invalid Number");
            requestFocus(mLand);
        }else {
            inputLayoutLand.setErrorEnabled(false);
        }

        strMode = "Single";
        if(!strName.isEmpty()){
            strIcon = String.valueOf(strName.charAt(0)).toUpperCase();
        }
        if(isValidName(strName) && isValidEmail(strEmail) && isValidMobNumber(strMob)){
            db.addContact(new Contact(strName, strEmail, strMob, strLand, strIcon, strMode));
            mMainLayout.setVisibility(View.GONE);
            mSuccess.setVisibility(View.VISIBLE);

            ActionBar myActionBar = getSupportActionBar();
            myActionBar.hide();
        }
    }

    private boolean isValidName(String name) {
        String NAME_PATTERN = "^[\\p{L} .'-]+$";

        Pattern pattern = Pattern.compile(NAME_PATTERN);
        Matcher matcher = pattern.matcher(name);
        return matcher.matches();
    }

    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private boolean isValidMobNumber(String mob) {
        String NUM_PATTERN = "\\d{10}";
        Pattern pattern = Pattern.compile(NUM_PATTERN);
        Matcher matcher = pattern.matcher(mob);
        return  matcher.matches();
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }
}
