package com.example.s;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

public class Bill_Activity extends AppCompatActivity {

    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_);
        DBHandler dbHandler=new DBHandler(this);
        ArrayList<CourseModal>a=dbHandler.read();
        tv=findViewById(R.id.tv);
        tv.setText(a.get(0).getTitle()+a.get(0).getAmout()+a.get(0).getTransactionType()+a.get(0).getCategory()+a.get(0).getDay()+a.get(0).getMonth());
    }
}