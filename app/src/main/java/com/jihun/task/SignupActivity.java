package com.jihun.task;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.content.SharedPreferences;

import com.google.android.material.textfield.TextInputEditText;

public class SignupActivity extends AppCompatActivity {
    TextInputEditText editText_Id, editText_Pw, editText_PwCo, editText_Name, editText_Call, editText_Add;
    Button btn_Signup, btn_checkDuplicate;

    private Context mContext;

    String[] ids = new String[100];
    int userCount;

    boolean canUseId = false;
    boolean isCheckIdDuplicated = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        mContext = this;

        SharedPreferences preferences = getSharedPreferences("sharedPreference", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editText_Id = findViewById(R.id.textInputEditTextSignupID);
        editText_Pw = findViewById(R.id.textInputEditTextSignupPassword);
        editText_PwCo = findViewById(R.id.textInputEditTextSignupPasswordConfirm);
        editText_Name = findViewById(R.id.textInputEditTextSignupName);
        editText_Call = findViewById(R.id.textInputEditTextSignupCall);
        editText_Add = findViewById(R.id.textInputEditTextSignupAddress);

        btn_Signup = findViewById(R.id.btnSignup);
        btn_checkDuplicate = findViewById(R.id.btn_checkDuplicate);

        btn_Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                String data = editTextId.getText().toString();
//                Log.v("qweqweqwe", data);
                System.out.println(editText_Id.getText().toString().equals(""));
                boolean isAllFilled = editText_Id.getText().toString().equals("")
                        || editText_Pw.getText().toString().equals("")
                        || editText_PwCo.getText().toString().equals("")
                        || editText_Name.getText().toString().equals("")
                        || editText_Call.getText().toString().equals("")
                        || editText_Add.getText().toString().equals("");
                if (isAllFilled) {
                    Toast.makeText(getApplicationContext(), "Fill the text", Toast.LENGTH_SHORT).show();
                }
                else {
                    if (isCheckIdDuplicated && canUseId) { // 중복 확인완료 및 ID 사용 가능
                        Toast.makeText(getApplicationContext(), "Signup Complete", Toast.LENGTH_SHORT).show();
                        String id = editText_Id.getText().toString();
                        String pw = editText_Pw.getText().toString();
                        String name = editText_Name.getText().toString();
                        String call = editText_Call.getText().toString();
                        String address = editText_Add.getText().toString();
                        editor.putString("ID", id);
                        editor.putString("PW", pw);
                        editor.putString("NAME", name);
                        editor.putString("CALL", call);
                        editor.putString("ADDRESS", address);
                        editor.apply();
                        userCount++;
                        ids[userCount] = id;
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "ID 중복확인을 하십시오", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        btn_checkDuplicate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editText_Id.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "ID를 입력하시오", Toast.LENGTH_SHORT).show();
                }
                else {
                    isCheckIdDuplicated = true;
                    String tempId = editText_Id.getText().toString();
                    boolean isDuplicated = false;
                    userCount = preferences.getInt("USER_COUNT", 0);
                    for (int i = 0; i < userCount; i++) {
                        if (ids[i].equals(tempId)) {
                            Toast.makeText(getApplicationContext(), "Duplicated ID", Toast.LENGTH_SHORT).show();
                            isDuplicated = true;
                            break;
                        }
                    }
                    if (!isDuplicated) {
                        Toast.makeText(getApplicationContext(), "사용 가능한 아이디입니다.", Toast.LENGTH_SHORT).show();
                        canUseId = true;
                    }
                }
            }
        });

        editText_Id.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                isCheckIdDuplicated = false;
                canUseId = false;
            }
        });
    }
}