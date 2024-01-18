package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Set;
import java.util.Vector;

public class MainActivity extends AppCompatActivity {

    String number = "", numberSmall = "";
    TextView editText, editTextSmall, textDeleteBtn;
    double prevNumber = 0;
    int curOperation = 0;

    AppCompatButton[] btnNumber = new AppCompatButton[10];
    int[] idListBtn = {R.id.btn_one, R.id.btn_two, R.id.btn_three,
            R.id.btn_four, R.id.btn_five, R.id.btn_six,
            R.id.btn_seven, R.id.btn_eight, R.id.btn_nine, R.id.btn_zer};
    RelativeLayout[] btnOperation = new RelativeLayout[9];
    int[] idListOperation = {R.id.btn_div, R.id.btn_mul, R.id.btn_minus,
                             R.id.btn_plus, R.id.btn_eq, R.id.btn_cancel,
                             R.id.btn_delete, R.id.btn_minplus, R.id.btn_dot};

    char[] operation = {'/', '*', '-', '+', '='};

    /* current operation
        1 - div
        2 - mul
        3 - minus
        4 - plus
        5 - equal
        6 - cancel
        7 - delete
        8 - minus or plus
        9 - coma
    */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = findViewById(R.id.input_number);
        editTextSmall = findViewById(R.id.input_expression);
        textDeleteBtn = findViewById(R.id.text_del);

        for(int i = 0 ; i < btnNumber.length ; i++)
            btnNumber[i] = findViewById (idListBtn[i]);

        for(AppCompatButton btn:btnNumber)
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    for(int i = 0 ; i < btnNumber.length; i++){

                        if(view.getId() == idListBtn[i]) {
                            if(number.equals("0")) continue;
                            if (number.length() <= 6) {
                                number += "" + (i + 1) % 10;
                                editText.setText(number);
                            }
                            if(textDeleteBtn.getText().equals("C"))
                                textDeleteBtn.setText("CE");
                        }
                    }
                }
            });

        for(int i = 0; i < btnOperation.length; ++i)
            btnOperation[i] = findViewById (idListOperation[i]);

        for(RelativeLayout btn:btnOperation)
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    for(int i = 0 ; i < btnOperation.length; i++){

                        if(view.getId() == idListOperation[i] && i < 5 && !number.isEmpty()){

                            if (checkIsDouble(Double.parseDouble(number)))
                                numberSmall += number + operation[i];
                            else{
                                numberSmall += String.valueOf(Math.round(Double.parseDouble(String.valueOf(number))));
                                numberSmall +=  operation[i];
                            }
                            editTextSmall.setText(numberSmall);

                            if(curOperation != 0){
                                if (!checkIsDouble(primitiveOperstion(curOperation)))
                                    number = String.valueOf(Math.round(Double.parseDouble(String.valueOf(primitiveOperstion(curOperation)))));
                                else number = String.valueOf(primitiveOperstion(curOperation));
                                editText.setText("" + number);
                                prevNumber = primitiveOperstion(curOperation);
                                if (prevNumber - Math.round(prevNumber) == 0)
                                    prevNumber = Math.round(prevNumber);
                                curOperation = i + 1;
                            }

                            if(i == 4){
                                numberSmall = "";
                                curOperation = 0;
                            }
                            else{
                                curOperation = i + 1;
                                prevNumber = Double.parseDouble(String.valueOf(number));
                                if (!checkIsDouble(prevNumber))
                                    prevNumber = Math.round(prevNumber);
                                number = "";
                            }
                        }
                        else if (view.getId() == idListOperation[i]){
                            if(i == 5){
                                if (number.length() > 1) {
                                    number = number.substring(0, number.length() - 1);
                                    editText.setText(number);
                                }

                                else if(number.length() == 1){
                                    number = "";
                                    editText.setText("0");
                                }
                            }
                            else if(i == 6){
                                if(textDeleteBtn.getText().equals("CE")) {
                                    number = "";
                                    editText.setText("");
                                    textDeleteBtn.setText("C");
                                }
                                else if (textDeleteBtn.getText().equals("C")){
                                    number = "";
                                    numberSmall = "";
                                    editText.setText("");
                                    prevNumber = 0;
                                    curOperation = 0;
                                    editTextSmall.setText("");
                                }
                            }
                            else if (i == 7){
                                if(!number.isEmpty()) {
                                    number = String.valueOf((-Double.parseDouble(number)));
                                    if (!checkIsDouble(Double.parseDouble(number)))
                                        number = "" + Math.round(Double.parseDouble(String.valueOf(number)));
                                    editText.setText(number);
                                }
                            }
                            else if (i == 8){
                                if (!number.isEmpty() && (number.charAt(number.length() - 1) != '.') && !checkIsDouble(Double.parseDouble(number))) {
                                    number = "" + Math.round(Double.parseDouble(String.valueOf(number)));
                                    number += '.';
                                }
                                editText.setText(number);
                            }
                        }

                    }
                }
                // исправить откугление до 4 знака
            });

    }

    private double primitiveOperstion(int i){
        if(curOperation == 1)
            return (prevNumber / Double.parseDouble(String.valueOf(number)));
        else if(curOperation == 2)
            return (prevNumber * Double.parseDouble(String.valueOf(number)));
        else if(curOperation == 3)
            return (prevNumber - Double.parseDouble(String.valueOf(number)));
        else if(curOperation == 4)
            return (prevNumber + Double.parseDouble(String.valueOf(number)));
        return -1;
    }

    boolean checkIsDouble(double num){
        return (num - Math.round(num)) != 0;
    }
}