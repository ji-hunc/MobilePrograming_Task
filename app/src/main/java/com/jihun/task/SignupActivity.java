package com.jihun.task;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.content.SharedPreferences;

import com.google.android.material.textfield.TextInputEditText;

public class SignupActivity extends AppCompatActivity {
    TextInputEditText editText_Id, editText_Pw, editText_PwCo, editText_Name, editText_Call, editText_Add;
    Button btn_Signup, btn_checkDuplicate;
    RadioButton rbtn_refuse, rbtn_agree;
    RadioGroup radioGroup;

    private Context mContext;

    String[] ids = new String[100];

    boolean canUseId = false;
    boolean isCheckIdDuplicated = false;
    boolean isAgreeTerms = false;

    int userCount = 0;

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

        rbtn_refuse = findViewById(R.id.rbtn_refuse);
        rbtn_agree = findViewById(R.id.rbtn_agree);
        radioGroup = findViewById(R.id.radioGroup);

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
                    Toast.makeText(getApplicationContext(), "모든 항목을 채워주십시오", Toast.LENGTH_SHORT).show();
                }
                else {
                    if (isCheckIdDuplicated && canUseId) { // 중복 확인완료 및 ID 사용 가능
                        if (isAgreeTerms) { // 약관 동의 완료
                            userCount = preferences.getInt("USER_COUNT", 0);
                            userCount++;
                            String id = editText_Id.getText().toString();
                            String pw = editText_Pw.getText().toString();
                            String name = editText_Name.getText().toString();
                            String call = editText_Call.getText().toString();
                            String address = editText_Add.getText().toString();
                            editor.putString("ID_" + userCount, id);
                            editor.putString("PW_" + userCount, pw);
                            editor.putString("NAME_" + userCount, name);
                            editor.putString("CALL_" + userCount, call);
                            editor.putString("ADDRESS_" + userCount, address);
                            editor.putInt("USER_COUNT", userCount);
                            editor.apply();
                            Toast.makeText(getApplicationContext(), "회원가입이 완료되었습니다", Toast.LENGTH_SHORT).show();

                            // 로그인 페이지로 이동
                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivity(intent);
                        }
                        else { // 약관 미동의
                            Toast.makeText(getApplicationContext(), "약관에 동의하십시오", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else { // 중복 미확인 또는 아이디 사용 불가
                        Toast.makeText(getApplicationContext(), "ID 중복확인을 하십시오", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        btn_checkDuplicate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                CLEAR USER DATA
//                editor.clear();
//                editor.apply();
                if (editText_Id.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "ID를 입력하시오", Toast.LENGTH_SHORT).show();
                }
                else {
                    isCheckIdDuplicated = true;
                    String tempId = editText_Id.getText().toString();
                    boolean isDuplicated = false;
                    userCount = preferences.getInt("USER_COUNT", 0);
                    for (int i = 0; i < userCount; i++) {
                        ids[i] = preferences.getString("ID_" + (i+1), "");
                        if (ids[i].equals(tempId)) {
                            Toast.makeText(getApplicationContext(), "이미 사용되고 있는 ID입니다", Toast.LENGTH_SHORT).show();
                            canUseId = false;
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

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int id) {
                switch (id) {
                    case R.id.rbtn_agree:
                        isAgreeTerms = true;
                        break;
                    default:
                        isAgreeTerms = false;
                }
            }
        });
    }
}