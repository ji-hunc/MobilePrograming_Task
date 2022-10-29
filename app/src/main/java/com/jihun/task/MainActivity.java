package com.jihun.task;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    Button button_info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);
        button_info = findViewById(R.id.button_info);

        ArrayList<Product> arrayList = new ArrayList<>();

        arrayList.add(new Product(R.drawable.macbook, "맥북 에어 13", "1,400,000"));
        arrayList.add(new Product(R.drawable.ipad, "아이패드 프로 11", "900,000"));
        arrayList.add(new Product(R.drawable.iphone, "아이폰 13 프로", "1,400,000"));
        arrayList.add(new Product(R.drawable.airpod, "에어팟 프로 2", "300,000"));
        arrayList.add(new Product(R.drawable.applewatch, "애플 워치", "500,000"));

        ProductAdapter productAdapter = new ProductAdapter(this, R.layout.list_row, arrayList);
        listView.setAdapter(productAdapter);


        Intent intent = getIntent();
        String user_id = intent.getStringExtra("USER_ID");

        button_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog(user_id);
            }
        });
    }
    public void openDialog(String user_id) {
        MyDialog myDialog = new MyDialog(user_id);
        myDialog.show(getSupportFragmentManager(), "qwe");
    }
}