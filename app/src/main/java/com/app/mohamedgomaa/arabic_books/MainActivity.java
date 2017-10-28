package com.app.mohamedgomaa.arabic_books;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
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
        Intent i = Intent.createChooser(intent, "Choose CD");
        startActivityForResult(i, CHOOSE_FILE_CD);
    }

    public void Upload_review(View view) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("application/pdf");
        Intent i = Intent.createChooser(intent, "Choose Review");
        startActivityForResult(i, CHOOSE_FILE_Review);
    }

    public void Upload_book(View view) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("application/pdf");
        Intent i = Intent.createChooser(intent, "choose Book");
        startActivityForResult(i, CHOOSE_FILE_Book);
    }

    public void Upload_photo(View view) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/jpg");
        Intent i = Intent.createChooser(intent, "Choose Photo");
        startActivityForResult(i, CHOOSE_FILE_Photo);
    }

    public void Upload(View view) {

    }
boolean UpLoad(Uri data, final TextView txt, final ProgressBar prgss)
{
    SimpleDateFormat simpleFormatter = new SimpleDateFormat("HH-mm-ss");
    prgss.setVisibility(View.VISIBLE);
    riversRef = mStorageRef.child("ِArabic_book/" + new Date().getTime());
            StorageTask<UploadTask.TaskSnapshot> taskSnapshotStorageTask = riversRef.putFile(data).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                boolean check;
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Uri downloadUri = taskSnapshot.getDownloadUrl();
                    UriCD = String.valueOf(downloadUri);
                    txt.setText("تم بنجاح");
                    prgss.setVisibility(View.GONE);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    prgss.setVisibility(View.GONE);
                    txt.setText("فشل حاول مرة أخرة");
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                    txt.setTextColor(Color.RED);
                    txt.setText((int) progress + "% ");
                }

            });
    return false;
}
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        final Intent Data=data;
        if (resultCode == RESULT_OK&&data!=null&&data.getData()!=null)
            switch (requestCode) {
                case CHOOSE_FILE_CD:
                {
                    try {
                        UpLoad(data.getData(),txt_cd,PrgBr_cd);
                    }catch (Exception e)
                    {
                        Toast.makeText(this, "هناك خط", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
                case CHOOSE_FILE_Book:
                {
                 /*   new Thread()
                    {
                        @Override
                        public void run() {
                            super.run();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    UpLoad(Data.getData(),txt_book,PrgBr_book);
                                }
                            });
                        }
                    }.start();
                    new Runnable() {
                        @Override
                        public void run() {


                        }
                    };*/
                    UpLoad(Data.getData(),txt_book,PrgBr_book);
                    break;
                }
                case CHOOSE_FILE_Review:
                {
                    UpLoad(Data.getData(),txt_review,PrgBr_review);
                    break;
                }
                case CHOOSE_FILE_Photo:
                {
                    UpLoad(Data.getData(),txt_photo,PrgBr_photo);
                    break;
                }
            }
    }
}
