package com.example.mymemo;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class Fragment1 extends Fragment {
    EditText editDiary;
    Button btnWrite;

    String fileName;
    String str;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_1, container, false);

        DatePicker dp= view.findViewById(R.id.datePicker);
        editDiary = view.findViewById(R.id.editDiary);
        btnWrite=view.findViewById(R.id.btnWrite);

        int year = dp.getYear();
        int month=dp.getMonth();
        int day=dp.getDayOfMonth();

        fileName=year+"_"+(month+1)+"_"+day+".txt";//2022_8_22
        dp.init(year, month, day, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                fileName=year+"_"+(monthOfYear+1)+"_"+dayOfMonth+".txt";//2022_8_22
                Log.d("TODAY", fileName);


                str= readDiary(fileName);
                editDiary.setText(str);
            }
        });


        btnWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    //파일오픈
                    FileOutputStream outFs=getContext().openFileOutput(fileName, Context.MODE_PRIVATE);
                    //쓴다
                    str=editDiary.getText().toString();
                    outFs.write(str.getBytes());
                    outFs.close();//닫는다
                    Toast.makeText(getContext(), fileName+"에 저장 하였습니다.", Toast.LENGTH_SHORT).show();


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        return view;
    }

    private String readDiary(String fileName) {
        //파일을 열고 읽고 닫아
        try {
            FileInputStream inFs = getContext().openFileInput(fileName);
            //바이트형의 버퍼(배열)을 선언
            byte[] txt = new byte[500];
            inFs.read(txt);
            inFs.close();
            //바이트형으로 읽어온 일기를 스트링으로 변하여 리턴하자
            str = new String(txt);
        } catch (IOException e) {
            str="";
            editDiary.setHint("선택한 날의 일기 없음");

        }
        return str;
    }
}