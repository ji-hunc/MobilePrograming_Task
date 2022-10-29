package com.jihun.task;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

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

        HashMap<String, String> memberHashMap = LoadUrlMap(getApplicationContext());

        String autoId = (String) memberHashMap.keySet().toArray()[0];
        Gson gson = new Gson();
        Member firstMember = gson.fromJson(memberHashMap.get(autoId), Member.class);
        String autoPw = firstMember.getPassword();

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
                intent.putExtra("USER_ID", "guest");
                startActivity(intent);
            }
        });

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isFindMatchingID = false;
                String inputId = editTextInput_ID.getText().toString();
                String inputPw = editTextInput_PW.getText().toString();
                if (inputId.equals("") || inputPw.equals("")) {
                    if (inputId.equals("")) {
                        Toast.makeText(getApplicationContext(), "ID를 입력해주세요", Toast.LENGTH_SHORT).show();
                    }
                    if (inputPw.equals("")) {
                        Toast.makeText(getApplicationContext(), "비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    int presentUser = 0;
                    for (Map.Entry<String, String> members : memberHashMap.entrySet()) {
                        String alreadyId = members.getKey();
                        if (alreadyId.equals(inputId)) {
                            isFindMatchingID = true;
                            break;
                        }
                    }
                    if (isFindMatchingID) {
                        Gson gson = new Gson();
                        Member userInfo = gson.fromJson(memberHashMap.get(inputId), Member.class);
                        String userPw = userInfo.getPassword();
                        if (userPw.equals(inputPw)) {
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            intent.putExtra("USER_ID", inputId);
                            intent.putExtra("USER_INFO", userInfo);
                            startActivity(intent);
                            Toast.makeText(getApplicationContext(), "로그인 성공", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "비밀번호가 일치하지 않습니다", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "등록된 ID가 아닙니다", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    public HashMap<String, String> LoadUrlMap(Context context) {
        HashMap<String, String> outputMap = new HashMap<String, String>();
        SharedPreferences mmPref = context.getSharedPreferences("sharedPreference", Context.MODE_PRIVATE);
        try {
            if (mmPref != null) {
                String jsonString = mmPref.getString("userData", (new JSONObject()).toString());
                JSONObject jsonObject = new JSONObject(jsonString);

                Iterator<String> keysItr = jsonObject.keys();
                while (keysItr.hasNext()) {
                    String key = keysItr.next();
                    String value = (String) jsonObject.get(key);
                    outputMap.put(key, value);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return outputMap;
    }
}