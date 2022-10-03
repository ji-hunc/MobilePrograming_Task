package com.jihun.task;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class MyDialog extends AppCompatDialogFragment {
    public MyDialog(int user_code) {
        this.user_code = user_code;
    }

    int user_code;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        if (user_code != 0) {
            SharedPreferences preferences = getActivity().getSharedPreferences("sharedPreference", Activity.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            String id = preferences.getString("ID_" + user_code, "");
            String name = preferences.getString("NAME_" + user_code, "");
            String call = preferences.getString("CALL_" + user_code, "");
            String address = preferences.getString("ADDRESS_" + user_code, "");
            String info = "ID: " + id + "\nname: " + name + "\ncall: " + call + "\naddress: " + address;

            builder.setTitle("회원정보").setMessage(info).setPositiveButton("확인", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
        } else {
            builder.setTitle("회원정보").setMessage("아직 회원이 아닙니다.\n회원가입을 하시겠습니까?")
                    .setPositiveButton("예", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent = new Intent(getActivity(), SignupActivity.class);
                    startActivity(intent);
                }
            }).setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
        }
        return builder.create();
    }
}
