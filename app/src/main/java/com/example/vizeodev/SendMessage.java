package com.example.vizeodev;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.Telephony;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SendMessage extends AppCompatActivity {
    //Initialize variable
    EditText etPhone, etMessage;
    Button btSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_message);

        //Assign variable
        etPhone = findViewById(R.id.et_phone);
        etMessage = findViewById(R.id.et_message);
        btSend = findViewById(R.id.bt_send);

        btSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Check condition
                if (ContextCompat.checkSelfPermission(SendMessage.this, Manifest.permission.SEND_SMS)
                        == PackageManager.PERMISSION_GRANTED) {
                    sendMessage();
                } else {
                    //When permission is not granted
                    //Request permission
                    ActivityCompat.requestPermissions(SendMessage.this, new String[]{
                            Manifest.permission.SEND_SMS
                    }, 100);
                }
            }

        });
    }
        private void sendMessage(){
            //Get values from edit text
            String sPhone = etPhone.getText().toString().trim();
            String sMessage = etMessage.getText().toString().trim();

            //Check condition
            if (!sPhone.equals("") && !sMessage.equals("")) {
                //When both edit text value not equal to blank
                //Initialize sms manager
                SmsManager smsManager = SmsManager.getDefault();
                //Send text message
                smsManager.sendTextMessage(sPhone, null, sMessage, null, null);
                //Display toast
                Toast.makeText(getApplicationContext(), "SMS sent successfully!", Toast.LENGTH_LONG).show();
            } else {
                //When edit text value is blank
                //Display Toast
                Toast.makeText(getApplicationContext(), "Enter value first.", Toast.LENGTH_SHORT).show();
            }
        }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //Check condition
        if (requestCode == 100 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            //When permission is granted
            //Call Method
            sendMessage();
        }
        else {
            //When permission is denied
            //Display toast
            Toast.makeText(getApplicationContext(), "Permission Denied", Toast.LENGTH_SHORT).show();
        }
    }
}

