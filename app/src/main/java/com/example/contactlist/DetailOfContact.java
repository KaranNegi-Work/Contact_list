package com.example.contactlist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class DetailOfContact extends AppCompatActivity {
TextView name,phoneNo;
EditText email,atlphoneNo;
Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_of_contact);
        name=findViewById(R.id.Ename);
        phoneNo=findViewById(R.id.Ephoneno);
        email=findViewById(R.id.email);
        atlphoneNo=findViewById(R.id.alternatephoneno);
        save=findViewById(R.id.save);
        int id=getIntent().getIntExtra("id",0);
        SharedPreferences.Editor editor=getApplicationContext().getSharedPreferences(String.valueOf(id),MODE_PRIVATE).edit();

        SharedPreferences prf=getSharedPreferences(String.valueOf(id),MODE_PRIVATE);
        //Toast.makeText(this, ""+prf.getString("email", "No name defined"), Toast.LENGTH_SHORT).show();

if(prf.getString("email", "No name defined")!="No name defined"&&prf.getString("email", "No name defined")!=null){
    email.setText(prf.getString("email", "No name defined"));
}
        if(prf.getString("altphoneno", "No name defined")!="No name defined"&&prf.getString("altphoneno", "No name defined")!=null){
            atlphoneNo.setText(prf.getString("altphoneno", "No name defined"));
        }

        List<ContactModel> contactModelList= (List<ContactModel>) getIntent().getSerializableExtra("contactList");

        name.setText(contactModelList.get(id).getName());
        phoneNo.setText(contactModelList.get(id).getPhoneNo());
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(email.getText().toString())) {
                    email.setError("fill field");
                    return;
                }
                if(TextUtils.isEmpty(atlphoneNo.getText().toString())) {
                    email.setError("fill field");
                    return;
                }

                editor.putString("name",contactModelList.get(id).getName());
                editor.putString("phoneNo",contactModelList.get(id).getPhoneNo());
                editor.putString("email",email.getText().toString());
                editor.putString("altphoneno",atlphoneNo.getText().toString());
                Intent intent=new Intent(DetailOfContact.this,MainActivity.class);
                editor.apply();
                startActivity(intent);
            }
        });






    }
}