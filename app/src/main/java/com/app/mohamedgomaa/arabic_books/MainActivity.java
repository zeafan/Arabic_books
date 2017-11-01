package com.app.mohamedgomaa.arabic_books;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity {
Button add,update,delete,Ok_d,Ok_up;
LinearLayout linearLayout_u,linearLayout_d;
EditText editText_u,editText_d;
boolean check_update=false;
    boolean check_delete=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        View view =getLayoutInflater().inflate(R.layout.dialog_1,null);
        add=(Button)view.findViewById(R.id.btnAdd);
        update=(Button)view.findViewById(R.id.btnUpdate);
        delete=(Button)view.findViewById(R.id.btnDelete);
        Ok_up=(Button) view.findViewById(R.id.Ok_updta);
        Ok_d=(Button) view.findViewById(R.id.Ok_delete);
        linearLayout_u=(LinearLayout)view.findViewById(R.id.LinearLayout_updta);
        linearLayout_d=(LinearLayout)view.findViewById(R.id.LinearLayout_delete);
        editText_d=(EditText)view.findViewById(R.id.editText_delete);
        editText_u=(EditText)view.findViewById(R.id.editText_updta);
        AlertDialog.Builder build1=new AlertDialog.Builder(this);
        linearLayout_d.setVisibility(View.GONE);
        linearLayout_u.setVisibility(View.GONE);
        build1.setView(view);
        build1.setCancelable(false);
        build1.show();
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,UplaodClss.class));
                finish();
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!check_update) {
                    check_update=true;
                    add.setVisibility(View.GONE);
                    delete.setVisibility(View.GONE);
                    linearLayout_u.setVisibility(View.VISIBLE);
                    update.setText("الرجوع");
                }else {
                    check_update=false;
                    add.setVisibility(View.VISIBLE);
                    delete.setVisibility(View.VISIBLE);
                    linearLayout_u.setVisibility(View.GONE);
                    update.setText("تعديل كتاب");
                }
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!check_delete) {
                    check_delete=true;
                    add.setVisibility(View.GONE);
                    update.setVisibility(View.GONE);
                    linearLayout_d.setVisibility(View.VISIBLE);
                    delete.setText("الرجوع");
                }else {
                    check_delete=false;
                    add.setVisibility(View.VISIBLE);
                    update.setVisibility(View.VISIBLE);
                    linearLayout_d.setVisibility(View.GONE);
                    delete.setText("حذف كتاب");
                }
            }
        });
        Ok_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id=editText_u.getText().toString();
                if(!id.equals("")&&CheckIdProduct(id))
                {
                    Intent intent=new Intent(MainActivity.this,UplaodClss.class);
                    intent.putExtra("id_prod",id);
                    startActivity(intent);
                    finish();
                }
            }
        });
        Ok_d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
    boolean CheckIdProduct(String id)
    {
       String id_prod=getSharedPreferences("ProductID_file",MODE_PRIVATE).getString(id,"");
      if(!id_prod.equals("")) {
          return true;
      }else {
          return false;
      }
    }
}
