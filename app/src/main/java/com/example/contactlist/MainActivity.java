package com.example.contactlist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.ContactsContract;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;
List<ContactModel> finalContactList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       RecyclerView recyclerView=findViewById(R.id.recyclerview);
        showContacts(recyclerView);
    }

    /**
     * Show the contacts in the ListView.
     */
    private void showContacts(RecyclerView recyclerview) {
        // Check the SDK version and whether the permission is already granted or not.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, PERMISSIONS_REQUEST_READ_CONTACTS);
            //After this point you wait for callback in onRequestPermissionsResult(int, String[], int[]) overriden method
        } else {
           finalContactList= displayContacts();
            recyclerview.setAdapter(new rAdapter(finalContactList, new OnClickItem() {
                @Override
                public void onCLickEdit(int position) {
                    Intent intent=new Intent(MainActivity.this,DetailOfContact.class);
                    intent.putExtra("id",position);
                    intent.putExtra("contactList", (Serializable) finalContactList);
                    startActivity(intent);

                }
            }));

        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
             finalContactList=displayContacts();
               // recyclerview.setAdapter(new rAdapter(finalContactList));
                // Permission is granted
                // Toast.makeText(this, getContactNames().size(), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Until you grant the permission, we canot display the names", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private List<ContactModel> displayContacts() {
        List<ContactModel> contactModelList=new ArrayList<>();
        ContentResolver cr = getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);
        if (cur.getCount() > 0) {
            while (cur.moveToNext()) {
                String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                //Toast.makeText(MainActivity.this, "Name: " + name + ", Phone No: " + id, Toast.LENGTH_SHORT).show();
                if (Integer.parseInt(cur.getString(
                        cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                    Cursor pCur = cr.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{id}, null);
                    while (pCur.moveToNext()) {
                        ContactModel contactModel=new ContactModel();
                        String phoneNo = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        contactModel.setId(id);
                        contactModel.setName(name);
                        contactModel.setPhoneNo(phoneNo);
                        contactModelList.add(contactModel);
                        //Toast.makeText(MainActivity.this, "Name: " + name + ", Phone No: " + phoneNo, Toast.LENGTH_SHORT).show();
                    }
                    pCur.close();
                }
            }
        }
        return contactModelList;
    }

}