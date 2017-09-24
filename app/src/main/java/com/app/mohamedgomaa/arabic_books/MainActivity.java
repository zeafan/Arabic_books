package com.app.mohamedgomaa.arabic_books;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView txt_photo;
    TextView txt_cd;
    TextView txt_review;
    TextView txt_book;
    ProgressBar PrgBr_photo, PrgBr_cd, PrgBr_review, PrgBr_book;
    EditText ed_price, ed_id, ed_title_ar, ed_title_en, ed_details_ar, ed_details_en, ed_author_ar, ed_author_en;

    void initailize() {
        txt_photo = (TextView) findViewById(R.id.tx_photo);
        txt_cd = (TextView) findViewById(R.id.tx_CD);
        txt_book = (TextView) findViewById(R.id.tx_Book);
        txt_review = (TextView) findViewById(R.id.tx_Review);
        PrgBr_photo=(ProgressBar)findViewById(R.id.prgss_photo);
        PrgBr_book=(ProgressBar)findViewById(R.id.prgss_Book);
        PrgBr_cd=(ProgressBar)findViewById(R.id.prgss_CD);
        PrgBr_review=(ProgressBar)findViewById(R.id.prgss_Review);
        ed_price=(EditText)findViewById(R.id.edPrice);
        ed_id=(EditText)findViewById(R.id.edID);
        ed_title_ar=(EditText)findViewById(R.id.editTitle_ar);
        ed_title_en=(EditText)findViewById(R.id.editTitle_en);
        ed_details_ar=(EditText)findViewById(R.id.editDetails_ar);
        ed_details_en=(EditText)findViewById(R.id.editDetails_en);
        ed_author_ar=(EditText)findViewById(R.id.edit_autor_ar);
        ed_author_en=(EditText)findViewById(R.id.edit_autor_en);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewCompat.setLayoutDirection(findViewById(R.id.relative), ViewCompat.LAYOUT_DIRECTION_LTR);
        initailize();

    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alerm = new AlertDialog.Builder(this);
        alerm.setTitle("Exit");
        alerm.setIcon(R.drawable.ic_exit_to_app_black_24dp);
        alerm.setMessage("sure,you want Exit app ?");
        alerm.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alerm.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        alerm.show();
    }

    public void Upload_CD(View view) {

    }

    public void Upload_review(View view) {
    }

    public void Upload_book(View view) {
    }

    public void Upload_photo(View view) {
    }

    public void Upload(View view) {

    }
}
