package com.whackyard.flipmapapp;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar myActionBar = getSupportActionBar();
        myActionBar.hide();

    }
    public void doNavigate(View v){
       switch (v.getId()){
            case R.id.flAdd:
                Intent add = new Intent(MainActivity.this, AddContactActivity.class);
                startActivity(add);
                break;
            case R.id.flView:
                Intent view = new Intent(MainActivity.this, ContactViewActivity.class);
                startActivity(view);
                break;
            case R.id.flImport:
                Intent imprt = new Intent(MainActivity.this, ImportActivity.class);
                startActivity(imprt);
                break;
            case R.id.flExpert:
                break;
        }
    }
}
