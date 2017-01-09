package com.whackyard.flipmapapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class ContactDetailsActivity extends FragmentActivity implements OnMapReadyCallback{

    private GoogleMap mMap;
    private LatLng location;
    private TextView tvName, tvNumber, tvEmail;
    private String mName, mMobNum, mLanNum, mEmail;
    private LinearLayout mMapFrag, mDeletePrompt;
    private Button mDelYes, mDelNo;
    private int mId;
    private ImageView mDelete;
    private double lat;
    private double lng;
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_details);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        tvName = (TextView) findViewById(R.id.tvName);
        tvNumber = (TextView) findViewById(R.id.tvMobNum);
        tvEmail = (TextView) findViewById(R.id.tvEmailDet);
        mDelete = (ImageView) findViewById(R.id.ivDelete);
        mMapFrag = (LinearLayout) findViewById(R.id.llMapFrag);
        mDeletePrompt = (LinearLayout) findViewById(R.id.llDeleltePrompt);
        mDelYes = (Button) findViewById(R.id.btnDeleteYes);
        mDelNo = (Button) findViewById(R.id.btnDeleteNo);

        db = new DatabaseHelper(this);

        mName = getIntent().getExtras().getString("name");
        mMobNum = getIntent().getExtras().getString("mob");
        mLanNum = getIntent().getExtras().getString("land");
        mEmail = getIntent().getExtras().getString("email");
        mId = getIntent().getExtras().getInt("id");

        tvName.setText(mName);
        tvNumber.setText(mMobNum);
        tvEmail.setText(mEmail);

        lat = generateCoordinate(mMobNum);
        lng = generateCoordinate(mLanNum);
        Log.d("Zayid", String.valueOf(lat));
        Log.d("Zayid", String.valueOf(lng));

        mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMapFrag.setVisibility(View.GONE);
                mDeletePrompt.setVisibility(View.VISIBLE);

                mDelYes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        db.deleteContact(new Contact(mId));
                        setResult(RESULT_OK);
                        finish();
                    }
                });

                mDelNo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDeletePrompt.setVisibility(View.GONE);
                        mMapFrag.setVisibility(View.VISIBLE);
                    }
                });
            }
        });
    }

    public double generateCoordinate(String phoneNumber){
        int strLen = phoneNumber.length();
        double coord = 0;

        if(!phoneNumber.isEmpty()){
            phoneNumber = phoneNumber.substring(0, strLen-2);
            coord = Double.parseDouble(phoneNumber)/1000000;
            Log.d("Zayid", phoneNumber);
        }
        return coord;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        location = new LatLng(lat,lng);
        mMap.addMarker(new MarkerOptions()
                .position(location)
                .title(mName)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)));

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 12.0f));

    }
}

