package com.example.dbsqlite;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    private static final String CHANNEL_ID = "APP_INNOVATIONS";
    private static final String CHANNEL_NAME = "APP INNOVATIONS";
    private static final String CHANNEL_DESC = "DATA ADDED" + "name"  ;
    EditText editTextName;
    Button submit;
    ListView listView;
    private  DataBaseHelper dataBaseHelper;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextName = findViewById(R.id.editTextName);
        submit = findViewById(R.id.submitButton);
        listView = findViewById(R.id.listView);
        dataBaseHelper = new DataBaseHelper(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,CHANNEL_NAME,NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription(CHANNEL_DESC);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);

        }




        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            addUser();
            showUsers();
            displayNotification();
            }
        });



    }

    private void displayNotification() {
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this,CHANNEL_ID)
                .setSmallIcon(R.drawable.notification_icon)
                .setContentTitle("Database sqlite")
                .setContentText("Data is added!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManagerCompat notificationManagerCompat =NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(1,notificationBuilder.build());

    }


    @SuppressLint("Range")
    private void showUsers(){
        Cursor cursor = dataBaseHelper.getUsers();
        if(cursor.moveToFirst()){
            String []users = new String[cursor.getCount()];
            int i = 0;
            do {
                users[i] = cursor.getString(cursor.getColumnIndex(DataBaseHelper.COLUMN_USER_NAME));
                i++;
            }while (cursor.moveToNext());
            ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1
                    ,users);
            listView.setAdapter(adapter);
        }

    }





    private void addUser(){
        String name = editTextName.getText().toString().trim();

//
        if (TextUtils.isEmpty(name)){
            Toast.makeText(this, "please enter the name !", Toast.LENGTH_SHORT).show();
        }
       else if (dataBaseHelper.addUser(name)){
            Toast.makeText(this, "Data added as" + name, Toast.LENGTH_SHORT).show();

        }
       else {
            Toast.makeText(this, "Data was not added to the database ", Toast.LENGTH_SHORT).show();
        }
    }
}