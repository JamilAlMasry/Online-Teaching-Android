package com.example.teachingonline;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.vishnusivadas.advanced_httpurlconnection.PutData;

import java.io.ByteArrayOutputStream;

public class EditInstProfile extends AppCompatActivity {
    String instemail;
    EditText opass, iname, npass;
    ImageView instProfilePic;
    String instructormail;
    Button instProfilePicBtn, changePass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.instprofile);
        Bundle instructoremail = this.getIntent().getExtras();
        instemail = instructoremail.getString("passmail");
        instructormail = instemail;
        instProfilePic = findViewById(R.id.instProfilePic);
        instProfilePicBtn = findViewById(R.id.instProfilePicBtn);
        instProfilePicBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryintent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryintent, 1);
            }
        });
        iname = findViewById(R.id.iname);
        opass = findViewById(R.id.opass);
        npass = findViewById(R.id.npass);
        changePass = findViewById(R.id.changePass);
        changePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HttpsTrustManager.allowAllSSL();
                String newpassword= npass.getText().toString();
                String oldpassword = opass.getText().toString();
                String email = instructormail;
                HttpsTrustManager.allowAllSSL();
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        //Starting Write and Read data with URL
                        //Creating array for parameters
                        String[] field = new String[3];
                        field[0] = "email";
                        field[1] = "newpassword";
                        field[2] = "oldpassword";

                        //Creating array for data
                        String[] data = new String[3];
                        data[0] = email;
                        data[1] = newpassword;
                        data[2] = oldpassword;


                        PutData putData = new PutData("http://192.168.1.11/Onlineteaching/resetPassword.php", "POST", field, data);
                        if (putData.startPut()) {
                            HttpsTrustManager.allowAllSSL();
                            if (putData.onComplete()) {
                                String result = putData.getResult();
                                if (result.equals("Password Changed")) {
                                    Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();

                                    finish();
                                } else {
                                    Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
                                }
                                //   Log.i("PutData", result);
                            }
                        }
                    }
                });
            }

        });
      //  inemail.setText(instemail);

    }
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null){
            Uri selectedimage = data.getData();
            instProfilePic.setImageURI(selectedimage);
        }
    }

    public void editinstructorprofile(View v) {
        Bitmap image = ((BitmapDrawable) instProfilePic.getDrawable()).getBitmap();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        String profileencoded = Base64.encodeToString(byteArrayOutputStream.toByteArray(), android.util.Base64.DEFAULT);
        HttpsTrustManager.allowAllSSL();
        String name = iname.getText().toString();
        String email = instructormail;
        HttpsTrustManager.allowAllSSL();
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                //Starting Write and Read data with URL
                //Creating array for parameters
                String[] field = new String[3];
                field[0] = "name";
                field[1] = "profileencoded";
                field[2] = "email";

                //Creating array for data
                String[] data = new String[3];
                data[0] = name;
                data[1] = profileencoded;
                data[2] = email;


                PutData putData = new PutData("http://192.168.1.11/Onlineteaching/editinstuctorprofile.php", "POST", field, data);
                if (putData.startPut()) {
                    HttpsTrustManager.allowAllSSL();
                    if (putData.onComplete()) {
                        String result = putData.getResult();
                        if (result.equals("Profile Edited")) {
                            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();

                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
                        }
                     //   Log.i("PutData", result);
                    }
                }
            }
        });
    }
}