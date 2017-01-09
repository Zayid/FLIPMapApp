package com.whackyard.flipmapapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lypeer.fcpermission.FcPermissions;
import com.lypeer.fcpermission.impl.FcPermissionsCallbacks;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import static com.whackyard.flipmapapp.FileChooser.getPath;

public class ImportActivity extends AppCompatActivity implements FcPermissionsCallbacks {

    private Button mSelect, mImport, mCancel;
    private String icon;
    private static final int TEXT_REQUEST = 1;
    private Uri textUri = null;
    private DatabaseHelper db;
    private LinearLayout mFrstImp, mSecImp, mThirdImp;
    private TextView mFileName, mFileSize, mContactCount, mImportRes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_import);

        mSelect = (Button) findViewById(R.id.btnTestImp);
        mImport = (Button) findViewById(R.id.btnImport);
        mCancel = (Button) findViewById(R.id.btnCancel);
        mFrstImp = (LinearLayout) findViewById(R.id.llFirstImport);
        mSecImp = (LinearLayout) findViewById(R.id.llSecondImport);
        mThirdImp = (LinearLayout) findViewById(R.id.llThiredImport);
        mFileName = (TextView) findViewById(R.id.tvFileName);
        mFileSize = (TextView) findViewById(R.id.tvFileSize);
        mContactCount = (TextView) findViewById(R.id.tvContactsCount);
        mImportRes = (TextView) findViewById(R.id.tvImportRes);

        db = new DatabaseHelper(this);

        mSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent galleryIntnt = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntnt.setType("text/*");
                startActivityForResult(galleryIntnt, TEXT_REQUEST);*/
                requestStoragePermission();
                getFile();
            }
        });
    }

    public void getFile(){
        Intent galleryIntnt = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntnt.setType("text/*");
        startActivityForResult(galleryIntnt, TEXT_REQUEST);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == TEXT_REQUEST && resultCode == RESULT_OK){
            textUri = data.getData();
            String filePath = "";
            final String MODE = "Import";
            icon="";
            filePath = getPath(getBaseContext(),textUri);

            if (textUri == null ) {
                // The user has not selected any text file
            }
            else{
                final ArrayList<Contact> listContact = getListContactFromTextFile(filePath);

                File tempFile = new File(filePath);
                String fileName = tempFile.getName();
                String fileSize = String.valueOf(tempFile.length()) + " Bytes";
                final int contactCount = listContact.size();

                mFrstImp.setVisibility(View.GONE);
                mSecImp.setVisibility(View.VISIBLE);

                mFileName.setText(fileName);
                mFileSize.setText(fileSize);
                mContactCount.setText(String.valueOf(contactCount));

                Log.d("Zayid", fileName);
                Log.d("Zayid", fileSize);
                Log.d("Zayid", String.valueOf(contactCount));

                mImport.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        for(int i = 0; i < listContact.size(); i ++) {
                            icon = String.valueOf(listContact.get(i).getName().charAt(0));
                            //Log.d("Zayid",icon);
                            db.addContact(new Contact(listContact.get(i).getName(), listContact.get(i).getEmail(), listContact.get(i).getMobile_number(), listContact.get(i).getLandline_number(), icon, MODE));
                            mSecImp.setVisibility(View.GONE);
                            mThirdImp.setVisibility(View.VISIBLE);
                            mImportRes.setText("Successfully added "+ contactCount + " contacts!");

                        }
                    }
                });

                mCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });

            }
        }
    }

    public static ArrayList<Contact> getListContactFromTextFile(String filePath) {
        FileInputStream fis = null;
        InputStreamReader isr = null;
        BufferedReader bReader = null;
        ArrayList<Contact> listResult = new ArrayList<Contact>();
        try {
            fis = new FileInputStream(filePath);
            isr = new InputStreamReader(fis);
            bReader = new BufferedReader(isr);
            //String save line get from text file
            String line = null;
            //Array save product
            String[]strProduct = null;

            //Loop and get all data in text file
            while(true) {
                //Get 1 line
                line = bReader.readLine();
                //Check line get empty, exit loop
                if(line == null) {
                    break;
                } else {
                    strProduct = line.split(",");
                    listResult.add(new Contact(strProduct[0], strProduct[1], strProduct[2], strProduct[3]));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //close file
            try {
                if(bReader != null)
                bReader.close();
                if(isr != null)
                isr.close();
                if(fis != null)
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return listResult;
    }

    public void requestStoragePermission() {
        FcPermissions.requestPermissions(this , getString(R.string.storage_rationale) ,
                FcPermissions.REQ_PER_CODE , android.Manifest.permission.READ_EXTERNAL_STORAGE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        FcPermissions.onRequestPermissionsResult(requestCode , permissions , grantResults , this);
    }

    @Override
    public void onPermissionsGranted(int i, List<String> list) {
    }

    @Override
    public void onPermissionsDenied(int i, List<String> list) {

    }
}
