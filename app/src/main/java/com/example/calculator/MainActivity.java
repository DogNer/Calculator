package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    AppCompatButton btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn0;
    String number = "";
    TextView editText;
    AppCompatButton[] arr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = findViewById(R.id.input_number);
        btn1 = findViewById(R.id.btn_one);
        btn2 = findViewById(R.id.btn_two);
        btn3 = findViewById(R.id.btn_three);
        btn4 = findViewById(R.id.btn_four);
        btn5 = findViewById(R.id.btn_five);
        btn6 = findViewById(R.id.btn_six);
        btn7 = findViewById(R.id.btn_seven);
        btn8 = findViewById(R.id.btn_eight);
        btn9 = findViewById(R.id.btn_nine);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                number += "1";
                editText.setText(number);
            }
        });
    }
}