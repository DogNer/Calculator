package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    AppCompatButton btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn0;
    String number = "", numberSmall = "";
    TextView editText, editTextSmall;
    int currentNumber = 0, prevOperation = 0;

    AppCompatButton[] btnNumber = new AppCompatButton[10];
    int[] idListBtn = {R.id.btn_one, R.id.btn_two, R.id.btn_three,
            R.id.btn_four, R.id.btn_five, R.id.btn_six,
            R.id.btn_seven, R.id.btn_eight, R.id.btn_nine, R.id.btn_zer};
    RelativeLayout[] btnOperation = new RelativeLayout[5];
    int[] idListOperation = {R.id.btn_div, R.id.btn_mul, R.id.btn_minus,
                             R.id.btn_plus, R.id.btn_eq};

    /* current operation
        1 - div
        2 - mul
        3 - minus
        4 - plus
        5 - eq
    */
    int curOperation = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = findViewById(R.id.input_number);
        editTextSmall = findViewById(R.id.input_expression);

        for(int i = 0 ; i < btnNumber.length ; i++)
            btnNumber[i] = findViewById (idListBtn[i]);

        for(AppCompatButton btn:btnNumber)
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    for(int i = 0 ; i < btnNumber.length; i++){

                        if(view.getId() == idListBtn[i]) {
                            if (number.length() <= 6) {
                                number += "" + (i + 1) % 10;
                                editText.setText(number);
                            }
                        }
                    }
                }
            });

        for(int i = 0; i < 5; ++i)
            btnOperation[i] = findViewById (idListOperation[i]);

        for(RelativeLayout btn:btnOperation)
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    for(int i = 0 ; i < btnOperation.length; i++){

                        if(view.getId() == idListOperation[i]){
                            curOperation = i + 1;
                            currentNumber = Integer.parseInt(String.valueOf(number));


                            if(prevOperation != 0){
                                editText.setText("" + Integer.parseInt(String.valueOf(numberSmall)));
                            }
                            if(i == 0)
                                numberSmall += number + '/';
                            else if (i == 1)
                                numberSmall += number + '*';
                            else if (i == 2)
                                numberSmall += number + '-';
                            else if (i == 3)
                                numberSmall += number + '+';
                            else {
                                numberSmall += number + '=';
                                prevOperation = 0;
                            }
                            editTextSmall.setText(numberSmall);
                            number = "";
                            prevOperation = curOperation;
                            curOperation = 0;
                        }
                    }
                }
            });

    }
}