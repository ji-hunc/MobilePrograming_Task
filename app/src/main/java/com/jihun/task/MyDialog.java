package com.jihun.task;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class MyDialog extends AppCompatDialogFragment {
    String user_id;

    public MyDialog(String user_id) {
        this.user_id = user_id;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        if (!(user_id.equals("guest"))) {
            Intent intent = getActivity().getIntent();
            Member user_info = (Member) intent.getSerializableExtra("USER_INFO");

            String id = user_info.getId();
            String name = user_info.getName();
            String call = user_info.getCall();
            String address = user_info.getAddress();
            String info = "아이디: " + id +
                          "\n이 름: " + name +
                          "\n전화번호: " + call +
                          "\n주 소: " + address;

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
