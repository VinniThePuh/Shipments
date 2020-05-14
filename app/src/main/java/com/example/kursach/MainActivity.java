package com.example.kursach;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button btnStore, btnGetall;
    private EditText etname, etdate, etvalue, etproducer;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseHelper = new DatabaseHelper(this);

        btnStore = (Button) findViewById(R.id.btnstore);
        btnGetall = (Button) findViewById(R.id.btnget);
        etname = (EditText) findViewById(R.id.etname);
        etdate = (EditText) findViewById(R.id.etdate);
        etvalue = (EditText) findViewById(R.id.etvalue);
        etproducer = (EditText) findViewById(R.id.etproducer);

        btnStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseHelper.addUser(etname.getText().toString(),  etdate.getText().toString(), etvalue.getText().toString(), etproducer.getText().toString());
                etname.setText("");
                etdate.setText("");
                etvalue.setText("");
                etproducer.setText("");
                Toast.makeText(MainActivity.this, "Успешно сохранено!", Toast.LENGTH_SHORT).show();
            }
        });

        btnGetall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GetAllUsersActivity.class);
                startActivity(intent);
            }
        });

    }
}