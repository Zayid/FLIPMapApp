package com.whackyard.flipmapapp;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ContactViewActivity extends AppCompatActivity {

    private DatabaseHelper db;
    private String [] listName;
    private String [] listNameManually;
    private ListView listView;
    private ListView listViewManual;
    private List<Contact> contactList = new ArrayList<Contact>();
    private List<Contact> contactListManually = new ArrayList<Contact>();
    private CustomListAdapter adapterImported;
    private CustomListAdapter adapterManually;
    private List<Contact> contacts;
    private List<Contact> contactsManually;
    private LinearLayout mContactsList, mNoContacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_view);

        ActionBar myActionBar = getSupportActionBar();
        myActionBar.hide();

        listView = (ListView) findViewById(R.id.lvContactsSql);
        listViewManual = (ListView) findViewById(R.id.lvContactsManual);
        mContactsList = (LinearLayout) findViewById(R.id.llContactView);
        mNoContacts = (LinearLayout) findViewById(R.id.llNoContacts);

        LayoutInflater inflater = getLayoutInflater();
        ViewGroup header = (ViewGroup) inflater.inflate(R.layout.header, listView,false);
        listView.addHeaderView(header, null, false);

        ViewGroup headerMan = (ViewGroup) inflater.inflate(R.layout.header_man, listViewManual,false);
        listViewManual.addHeaderView(headerMan, null, false);

        db = new DatabaseHelper(this);
        contacts = db.getAllContacts();
        contactsManually = db.getAllContactsManually();
        listName = new String[contacts.size()];
        listNameManually = new String[contactsManually.size()];

        if(contacts.size() == 0 && contactsManually.size() == 0){
            mContactsList.setVisibility(View.GONE);
            mNoContacts.setVisibility(View.VISIBLE);
            myActionBar.show();
        }

        for (int i = 0; i < listName.length; i++) {
            Contact cont = new Contact();
            cont.setName(contacts.get(i).getName());
            cont.setIcon(contacts.get(i).getIcon());

            contactList.add(cont);
        }

        for (int i = 0; i < listNameManually.length; i++) {
            Contact cont = new Contact();
            cont.setName(contactsManually.get(i).getName());
            cont.setIcon(contactsManually.get(i).getIcon());

            contactListManually.add(cont);
        }
        if (listName != null && listName.length > 0 ) {
            adapterImported = new CustomListAdapter(this, contactList);
            listView.setAdapter(adapterImported);
            ListUtils.setDynamicHeight(listView);

        }
        if (listNameManually != null && listNameManually.length > 0 ) {
            adapterManually = new CustomListAdapter(this, contactListManually);
            listViewManual.setAdapter(adapterManually);
            ListUtils.setDynamicHeight(listViewManual);

        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String name = contacts.get(position-1).getName();
                String mobNum = contacts.get(position-1).getMobile_number();
                String landNum = contacts.get(position-1).getLandline_number();
                String email = contacts.get(position-1).getEmail();
                int idContact = contacts.get(position-1).getId();

                Log.d("Zayid", name);
                Log.d("Zayid", mobNum);
                Log.d("Zayid", landNum);
                Log.d("Zayid", email);
                Log.d("Zayid", String.valueOf(idContact));

                Intent contactDeatails = new Intent(getBaseContext(), ContactDetailsActivity.class);
                contactDeatails.putExtra("name", name);
                contactDeatails.putExtra("mob", mobNum);
                contactDeatails.putExtra("land", landNum);
                contactDeatails.putExtra("email", email);
                contactDeatails.putExtra("id", idContact);
                startActivityForResult(contactDeatails, 100);
            }
        });

        listViewManual.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String name = contactsManually.get(position-1).getName();
                String mobNum = contactsManually.get(position-1).getMobile_number();
                String landNum = contactsManually.get(position-1).getLandline_number();
                String email = contactsManually.get(position-1).getEmail();
                int idContact = contactsManually.get(position-1).getId();

                Intent contactDeatails = new Intent(getBaseContext(), ContactDetailsActivity.class);
                contactDeatails.putExtra("name", name);
                contactDeatails.putExtra("mob", mobNum);
                contactDeatails.putExtra("land", landNum);
                contactDeatails.putExtra("email", email);
                contactDeatails.putExtra("id", idContact);
                startActivityForResult(contactDeatails, 200);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 100 && resultCode == RESULT_OK){
            contactList.clear();
            contacts = db.getAllContacts();
            listName = new String[contacts.size()];
            for (int i = 0; i < listName.length; i++) {
                Contact cont = new Contact();
                cont.setName(contacts.get(i).getName());
                cont.setIcon(contacts.get(i).getIcon());
                Log.d("Zayid", contacts.get(i).getName());
                contactList.add(cont);
            }

            adapterImported = new CustomListAdapter(this, contactList);
            listView.setAdapter(adapterImported);
            ListUtils.setDynamicHeight(listView);
            adapterImported.notifyDataSetChanged();
        }

        if(requestCode == 200 && resultCode == RESULT_OK){
            contactListManually.clear();
            contactsManually = db.getAllContactsManually();
            listNameManually = new String[contactsManually.size()];
            for (int i = 0; i < listNameManually.length; i++) {
                Contact cont = new Contact();
                cont.setName(contactsManually.get(i).getName());
                cont.setIcon(contactsManually.get(i).getIcon());
                Log.d("Zayid", contactsManually.get(i).getName());
                contactListManually.add(cont);
            }

            adapterManually = new CustomListAdapter(this, contactListManually);
            listViewManual.setAdapter(adapterManually);
            ListUtils.setDynamicHeight(listViewManual);
            adapterManually.notifyDataSetChanged();
        }
    }

    public static class ListUtils {
        public static void setDynamicHeight(ListView mListView) {
            ListAdapter mListAdapter = mListView.getAdapter();
            if (mListAdapter == null) {
                // when adapterImported is null
                return;
            }
            int height = 0;
            int desiredWidth = View.MeasureSpec.makeMeasureSpec(mListView.getWidth(), View.MeasureSpec.UNSPECIFIED);
            for (int i = 0; i < mListAdapter.getCount(); i++) {
                View listItem = mListAdapter.getView(i, null, mListView);
                listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
                height += listItem.getMeasuredHeight();
            }
            ViewGroup.LayoutParams params = mListView.getLayoutParams();
            params.height = height + (mListView.getDividerHeight() * (mListAdapter.getCount() - 1));
            mListView.setLayoutParams(params);
            mListView.requestLayout();
        }
    }
}
