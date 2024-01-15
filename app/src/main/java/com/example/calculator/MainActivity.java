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

    AppCompatButton btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn0;
    String number = "", numberSmall = "";
    TextView editText, editTextSmall;
    double currentNumber = 0, prevNumber = 0;
    int prevOperation = 0, curOperation = 0;

    AppCompatButton[] btnNumber = new AppCompatButton[10];
    int[] idListBtn = {R.id.btn_one, R.id.btn_two, R.id.btn_three,
            R.id.btn_four, R.id.btn_five, R.id.btn_six,
            R.id.btn_seven, R.id.btn_eight, R.id.btn_nine, R.id.btn_zer};
    RelativeLayout[] btnOperation = new RelativeLayout[8];
    int[] idListOperation = {R.id.btn_div, R.id.btn_mul, R.id.btn_minus,
                             R.id.btn_plus, R.id.btn_eq, R.id.btn_cancel, R.id.btn_delete, R.id.btn_minplus};

    char[] operation = {'/', '*', '-', '+', '='};
    Vector<Double> numbers = new Vector<Double>();

    /* current operation
        1 - div
        2 - mul
        3 - minus
        4 - plus
        5 - eq
    */

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
                                numberSmall += "" + Math.round(Double.parseDouble(String.valueOf(number)));
                                numberSmall +=  operation[i];
                            }
                            editTextSmall.setText(numberSmall);

                            if(curOperation != 0){
                                if (!checkIsDouble(primitiveOperstion(curOperation)))
                                    number = "" + Math.round(Double.parseDouble(String.valueOf(primitiveOperstion(curOperation))));
                                else number = "" + primitiveOperstion(curOperation);
                                editText.setText("" + number);
                                currentNumber = primitiveOperstion(curOperation);
                                if (currentNumber - Math.round(currentNumber) == 0)
                                    currentNumber = Math.round(currentNumber);
                                curOperation = i + 1;
                            }

                            if(i == 4){
                                numberSmall = "";
                                curOperation = 0;
                            }
                            else{
                                curOperation = i + 1;
                                currentNumber = Double.parseDouble(String.valueOf(number));
                                if (!checkIsDouble(currentNumber))
                                    currentNumber = Math.round(currentNumber);
                                number = "";
                            }
                        }
                        else if (view.getId() == idListOperation[i]){
                            if(i == 5){
                                number = number.substring(0, number.length() - 1);
                                editText.setText(number);
                            }
                            else if(i == 6){

                            }

                        }
                    }
                }
                // исправить откугление до 4 знака
            });

    }

    private double primitiveOperstion(int i){
        if(curOperation == 1)
            return (currentNumber / Double.parseDouble(String.valueOf(number)));
        else if(curOperation == 2)
            return (currentNumber * Double.parseDouble(String.valueOf(number)));
        else if(curOperation == 3)
            return (currentNumber - Double.parseDouble(String.valueOf(number)));
        else if(curOperation == 4)
            return (currentNumber + Double.parseDouble(String.valueOf(number)));
        return -1;
    }

    boolean checkIsDouble(double num){
        return (num - Math.round(num)) != 0;
    }
}