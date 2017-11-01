package com.app.mohamedgomaa.arabic_books;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
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
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class UplaodClss extends AppCompatActivity {
    private StorageReference mStorageRef;
    FirebaseDatabase firebaseDatabase;
    StorageReference riversRef;
    TextView txt_photo;
    TextView txt_cd;
    TextView txt_review;
    TextView txt_book;
    ProgressBar PrgBr_photo, PrgBr_cd, PrgBr_review, PrgBr_book;
    EditText ed_price, ed_id, ed_title_ar, ed_title_en, ed_details_ar, ed_details_en, ed_author_ar, ed_author_en;
    String value_id, value_price, value_title_ar, value_title_en, value_details_ar, value_details_en, value_author_ar, value_author_en;
    String Uri_CD="", Uri_book="", Uri_Review="", Uri_photo="";
    final String Key_price="price";
    final String Key_id="id";
    final String Key_title_ar="title_ar";
    final String Key_title_en="title_en";
    final String Key_details_ar="details_ar";
    final String Key_details_en="details_en";
    final String Key_author_ar="author_ar";
    final String Key_author_en="author_en";
    final String Key_Uri_CD="UriCD";
    final String Key_Uri_book="Uri_book";
    final String Key_Uri_Review="Uri_Review";
    final String Key_Uri_photo="Uri_photo";
    final static public int CHOOSE_FILE_CD = 1;
    final static public int CHOOSE_FILE_Book = 2;
    final static public int CHOOSE_FILE_Review = 3;
    final static public int CHOOSE_FILE_Photo = 4;
    final int requsetCode = 1;
    Uri intent;
    TextView txtView;
    ProgressBar prgrssBar;
    int choose;
    boolean checkValues() {
        if (!ed_price.getText().toString().equals("") && !ed_id.getText().toString().equals("") && !ed_title_ar.getText().toString().equals("") &&
                !ed_title_en.getText().toString().equals("") && !ed_details_ar.getText().toString().equals("") &&
                !ed_details_en.getText().toString().equals("") && !ed_author_ar.getText().toString().equals("") &&
                !ed_author_en.getText().toString().equals("") && !Uri_CD.equals("") && !Uri_book.equals("") &&
                !Uri_Review.equals("") && !Uri_photo.equals("")) {
            value_id = ed_id.getText().toString();
            value_price = ed_price.getText().toString();
            value_title_ar = ed_title_ar.getText().toString();
            value_title_en = ed_title_en.getText().toString();
            value_details_ar = ed_details_ar.getText().toString();
            value_details_en = ed_details_en.getText().toString();
            value_author_ar = ed_author_ar.getText().toString();
            value_author_en = ed_author_en.getText().toString();
            return true;
        }else {
            Toast.makeText(this, "أكمل أدخال البيانات بالكامل",Toast.LENGTH_LONG).show();
        }
        return false;
    }

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

    public void permissionGetFile(Uri data, final TextView txt, final ProgressBar prgss, int _choose) {
        if(new CheckConnection_Internet(this).IsConnection()) {
            if (Build.VERSION.SDK_INT >= 23) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != getPackageManager().PERMISSION_GRANTED) {
                    if (!shouldShowRequestPermissionRationale(android.Manifest.permission.READ_EXTERNAL_STORAGE)) {
                        requestPermissions(new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, requsetCode);
                        intent = data;
                        txtView = txt;
                        prgrssBar = prgss;
                        choose = _choose;
                    }
                    return;
                }

            }
            UpLoad(data, txt, prgss, _choose);
        }
        else {
            Toast.makeText(this, "مفيش أتصال بالأنترنت", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case requsetCode:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    UpLoad(intent, txtView, prgrssBar, choose);
                } else {
                    Toast.makeText(this, "there Error", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        firebaseDatabase = FirebaseDatabase.getInstance();
        ViewCompat.setLayoutDirection(findViewById(R.id.relative), ViewCompat.LAYOUT_DIRECTION_LTR);
        initailize();
        if(!new CheckConnection_Internet(this).IsConnection()) {
            Toast.makeText(this, "لا يوجد أتصال بالأنترنت", Toast.LENGTH_SHORT).show();
        }
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

    public void Upload_btn(View view) {
        if(!new CheckConnection_Internet(this).IsConnection()){
            Toast.makeText(this, "مفيش أتصال بالأنترنت", Toast.LENGTH_SHORT).show();
        }else if(checkValues()&&Check_Product_ID(value_id)) {

            DatabaseReference IDProduct = firebaseDatabase.getReference().child(value_id);
            IDProduct.updateChildren(Setmap());
            IDProduct.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    String value= (String) dataSnapshot.getValue();
                    Toast.makeText(UplaodClss.this, " بنجاح "+value+" تم إضافة ", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }
    private boolean Check_Product_ID(String id_pro)
    {
        SharedPreferences sharedPreferences=getSharedPreferences("ProductID_file",MODE_PRIVATE);
        String id_prod=sharedPreferences.getString(id_pro,"");
        if(id_prod.equals(""))
        {
            SharedPreferences.Editor editor=sharedPreferences.edit();
            editor.putString(id_pro,id_pro);
            editor.apply();
            return true;
        }else {
            Toast.makeText(this, "تم إدخال ID الخاص للمنتج مسبقاً لا يمكن أضافة منتج له نفس الID", Toast.LENGTH_LONG).show();
            return false;
        }
    }
    private Map<String,Object>Setmap()
    {
        Map<String,Object> map=new HashMap<>();
        map.put(Key_price,value_price);
        map.put(Key_title_ar,value_title_ar);
        map.put(Key_title_en,value_title_en);
        map.put(Key_author_en,value_author_en);
        map.put(Key_author_ar,value_author_ar);
        map.put(Key_details_ar,value_details_ar);
        map.put(Key_details_en,value_details_en);
        map.put(Key_Uri_photo,Uri_photo);
        map.put(Key_Uri_book,Uri_book);
        map.put(Key_Uri_Review,Uri_Review);
        map.put(Key_Uri_CD,Uri_CD);
        return map;
    }
    boolean UpLoad(Uri data, final TextView txt, final ProgressBar prgss, final int choose) {
        prgss.setVisibility(View.VISIBLE);
        riversRef = mStorageRef.child("ِArabic_book/" + new Date().getTime());
        StorageTask<UploadTask.TaskSnapshot> taskSnapshotStorageTask = riversRef.putFile(data).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Uri downloadUri = taskSnapshot.getDownloadUrl();
                switch (choose) {
                    case CHOOSE_FILE_CD:
                        Uri_CD = String.valueOf(downloadUri);
                        break;
                    case CHOOSE_FILE_Book:
                        Uri_book = String.valueOf(downloadUri);
                        break;
                    case CHOOSE_FILE_Review:
                        Uri_Review = String.valueOf(downloadUri);
                        break;
                    case CHOOSE_FILE_Photo:
                        Uri_photo = String.valueOf(downloadUri);
                }
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
        final Intent Data = data;
        if (resultCode == RESULT_OK && data != null && data.getData() != null)
            switch (requestCode) {
                case CHOOSE_FILE_CD: {
                    try {
                        permissionGetFile(data.getData(), txt_cd, PrgBr_cd, requestCode);
                    } catch (Exception e) {
                        Toast.makeText(this, "هناك خط", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
                case CHOOSE_FILE_Book: {
                    try {
                        permissionGetFile(Data.getData(), txt_book, PrgBr_book, CHOOSE_FILE_Book);
                    } catch (Exception e) {
                        Toast.makeText(this, "هناك خط", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
                case CHOOSE_FILE_Review: {
                    try {
                        permissionGetFile(Data.getData(), txt_review, PrgBr_review, CHOOSE_FILE_Review);
                    } catch (Exception e) {
                        Toast.makeText(this, "هناك خط", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
                case CHOOSE_FILE_Photo: {
                    try {
                        permissionGetFile(Data.getData(), txt_photo, PrgBr_photo, CHOOSE_FILE_Photo);
                    } catch (Exception e) {
                        Toast.makeText(this, "هناك خط", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
            }
    }
}
