package com.jihun.task;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {
    Button buttonSignup, buttonLogin, buttonGuest;
    TextInputEditText editTextInput_ID, editTextInput_PW;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        buttonSignup = findViewById(R.id.button_signup);
        buttonLogin = findViewById(R.id.button_login);
        buttonGuest = findViewById(R.id.button_guest);

        editTextInput_ID = findViewById(R.id.editTextInput_ID);
        editTextInput_PW = findViewById(R.id.editTextInput_PW);

        SharedPreferences preferences = getSharedPreferences("sharedPreference", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        String autoId = preferences.getString("ID_1", "");
        String autoPw = preferences.getString("PW_1", "");
        if (!autoId.equals("") && !autoPw.equals("")) {
            editTextInput_ID.setText(autoId);
            editTextInput_PW.setText(autoPw);
        }

        buttonSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivity(intent);
            }
        });

        buttonGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // present user data ex) 3 -> ID_3, PW_3 ...
                boolean isFindMatchingID = false;
                String inputId = editTextInput_ID.getText().toString();
                String inputPw = editTextInput_PW.getText().toString();
                int presentUser = 0;
                int userCount = preferences.getInt("USER_COUNT", 0);
                for (int i=0; i<userCount; i++) {
                    String userId = preferences.getString("ID_" + (i+1), "");
                    if (userId.equals(inputId)) {
                        presentUser = i+1;
                        isFindMatchingID = true;
                    }
                }
                if (isFindMatchingID) {
                    String userPw = preferences.getString("PW_" + presentUser, "");
                    if (userPw.equals(inputPw)) {
                        // present user data 보내기 정수 1자리.
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        Toast.makeText(getApplicationContext(), "로그인 성공", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "비밀번호가 틀렸습니다", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(), "등록된 ID가 아닙니다", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}