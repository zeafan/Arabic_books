package com.app.mohamedgomaa.arabic_books;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.SimpleFormatter;

public class MainActivity extends AppCompatActivity {
    private StorageReference mStorageRef;
    StorageReference riversRef;
    TextView txt_photo;
    TextView txt_cd;
    TextView txt_review;
    TextView txt_book;
    ProgressBar PrgBr_photo, PrgBr_cd, PrgBr_review, PrgBr_book;
    EditText ed_price, ed_id, ed_title_ar, ed_title_en, ed_details_ar, ed_details_en, ed_author_ar, ed_author_en;
    String UriCD, Uri_book, Uri_Review, Uri_photo;

    void initailize() {
        mStorageRef = FirebaseStorage.getInstance().getReference();
        txt_photo = (TextView) findViewById(R.id.tx_photo);
        txt_cd = (TextView) findViewById(R.id.tx_CD);
        txt_book = (TextView) findViewById(R.id.tx_Book);
        txt_review = (TextView) findViewById(R.id.tx_Review);
        PrgBr_photo = (ProgressBar) findViewById(R.id.prgss_photo);
        PrgBr_book = (ProgressBar) findViewById(R.id.prgss_Book);
        PrgBr_cd = (ProgressBar) findViewById(R.id.prgss_CD);
        PrgBr_review = (ProgressBar) findViewById(R.id.prgss_Review);
        ed_price = (EditText) findViewById(R.id.edPrice);
        ed_id = (EditText) findViewById(R.id.edID);
        ed_title_ar = (EditText) findViewById(R.id.editTitle_ar);
        ed_title_en = (EditText) findViewById(R.id.editTitle_en);
        ed_details_ar = (EditText) findViewById(R.id.editDetails_ar);
        ed_details_en = (EditText) findViewById(R.id.editDetails_en);
        ed_author_ar = (EditText) findViewById(R.id.edit_autor_ar);
        ed_author_en = (EditText) findViewById(R.id.edit_autor_en);
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

    final static public int CHOOSE_FILE_CD = 1;
    final static public int CHOOSE_FILE_Book=2;

    final static public int CHOOSE_FILE_Review=3;

    final static public int CHOOSE_FILE_Photo=4;

    public void Upload_CD(View view) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("*/*");
        Intent i = Intent.createChooser(intent, "File");
        startActivityForResult(i, CHOOSE_FILE_CD);
    }

    public void Upload_review(View view) {
    }

    public void Upload_book(View view) {
    }

    public void Upload_photo(View view) {
    }

    public void Upload(View view) {

    }
boolean UpLoad(Uri data)
{
    final ProgressDialog progressDailog=new ProgressDialog(this);
    progressDailog.setTitle("Uploading...");
    progressDailog.show();
    SimpleDateFormat simpleFormatter = new SimpleDateFormat("HH-mm-SS");
    Date date = new Date();
    simpleFormatter.format(date);
    riversRef = mStorageRef.child("ِArabic_book/" + simpleFormatter.toString());
    StorageTask<UploadTask.TaskSnapshot> taskSnapshotStorageTask = riversRef.putFile(data).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
        boolean check;
        @Override
        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
            Uri downloadUri = taskSnapshot.getDownloadUrl();
            UriCD = String.valueOf(downloadUri);
            Toast.makeText(MainActivity.this, "successfull", Toast.LENGTH_SHORT).show();
        }
    }).addOnFailureListener(new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception e) {
            Toast.makeText(MainActivity.this, "Failure", Toast.LENGTH_SHORT).show();
        }
    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
        @Override
        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
            double progress=(100.0*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
            progressDailog.setMessage((int)progress+ "%  uploading..");
        }

    });
    return false;
}
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data12345) {
        super.onActivityResult(requestCode, resultCode, data12345);
        if (resultCode == RESULT_OK&&data12345!=null&&data12345.getData()!=null)
            switch (requestCode) {
                case CHOOSE_FILE_CD:
                {
                    try {
                        Uri uri_cd = data12345.getData();
                        UpLoad(uri_cd);
                    }catch (Exception e)
                    {
                        Toast.makeText(this, "هناك خط", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
                case CHOOSE_FILE_Book:
                {

                    break;
                }
                case CHOOSE_FILE_Review:
                {

                    break;
                }
                case CHOOSE_FILE_Photo:
                {

                    break;
                }
            }
    }
}
