package com.example.counter;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class counterActivity extends Activity implements View.OnClickListener {
    Button button1;
    Button button2;
    Button button3;
    Button button4;
    Button button5;
    Button button6;
    Button button7;
    Button button8;
    Button button9;
    Button button0;
    Button button_equals;
    Button button_dot;
    Button button_del;
    Button button_add;
    Button button_divide;
    Button button_AC;
    Button button_C;
    Button button_minus;
    Button button_ride;
    private EditText editText1;
    boolean clear = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counter);

        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        button4 = (Button) findViewById(R.id.button4);
        button5 = (Button) findViewById(R.id.button5);
        button6 = (Button) findViewById(R.id.button6);
        button7 = (Button) findViewById(R.id.button7);
        button8 = (Button) findViewById(R.id.button8);
        button9 = (Button) findViewById(R.id.button9);
        button0 = (Button) findViewById(R.id.button0);
        button_ride = (Button) findViewById(R.id.button_ride);
        button_del = (Button) findViewById(R.id.button_del);
        button_dot = (Button) findViewById(R.id.button_dot);
        button_add = (Button) findViewById(R.id.button_add);
        button_AC = (Button) findViewById(R.id.button_AC);
        button_C = (Button) findViewById(R.id.button_C);
        button_divide = (Button) findViewById(R.id.button_divide);
        button_equals = (Button) findViewById(R.id.button_equals);
        button_minus = (Button) findViewById(R.id.button_minus);
        editText1 = (EditText) findViewById(R.id.editText1);

        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);
        button6.setOnClickListener(this);
        button7.setOnClickListener(this);
        button8.setOnClickListener(this);
        button9.setOnClickListener(this);
        button0.setOnClickListener(this);
        button_ride.setOnClickListener(this);
        button_del.setOnClickListener(this);
        button_dot.setOnClickListener(this);
        button_equals.setOnClickListener(this);
        button_divide.setOnClickListener(this);
        button_add.setOnClickListener(this);
        button_C.setOnClickListener(this);
        button_AC.setOnClickListener(this);
        button_minus.setOnClickListener(this);
        editText1.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int sum = 0;
        String [] a;
        String text = editText1.getText().toString();
        //a = text;
        switch (view.getId()) {
            case R.id.button_dot:
            case R.id.button0:
            case R.id.button1:
            case R.id.button2:
            case R.id.button3:
            case R.id.button4:
            case R.id.button5:
            case R.id.button6:
            case R.id.button7:
            case R.id.button8:
            case R.id.button9:
            /*    for(int i =1;i<text.length();i++){
                    if(text.contains("."))

                }*/
                if (text.equals("0") || text.equals("结果未定义")) {
                    editText1.setText("0");
                    clear = false;
                    text = "";
                }
                if (clear) {
                    clear = false;
                    text = "";
                }
                editText1.setText(text + ((Button) view).getText());
                break;

            case R.id.button_add:
            case R.id.button_del:
            case R.id.button_divide:
            case R.id.button_minus:
            case R.id.button_ride:
                if(text.equals("结果未定义"))
                    text="0";
                if (clear == false || clear == true) {
                    clear = false;
                    int n = 0;
                    String str = text;
                    String str2 = " ";
                    int count = 0;
                    int str2_length = str2.length();
                    for (int i = 0; i < text.length(); i++) {
                        String temp = str.substring(n, n + str2_length);
                        if (temp.equals(str2)) {
                            n++;
                            count++;
                        } else
                            n++;
                    }
                    if (count >= 2) {
                        getResult();
                        break;
                    }
                }
                editText1.setText(text + " " + ((Button) view).getText() + " ");
                break;
            case R.id.button_AC:
                 if(text.length() == 1){
                     editText1.setText("0");
                 }else{
                     editText1.setText(text.substring(0, text.length() - 1));
                 }
                break;
            case R.id.button_C:
                editText1.setText("0");
                break;
            case R.id.button_equals:
                if (!text.contains(" ")) {
                    editText1.setText(text);
                    break;
                }
                getResult();
        }

    }

    public void getResult() {
        double result = 0;
        String result2;
        String text = editText1.getText().toString();
        clear = true;
        String number1 = text.substring(0, text.indexOf(" "));
        String op = text.substring(text.indexOf(" ") + 1, (text.indexOf(" ") + 2));
        String number2 = text.substring(text.indexOf(" ") + 3);

        if (!number1.equals("") && !number2.equals("")) {
            double n1 = Double.parseDouble(number1);
            double n2 = Double.parseDouble(number2);

            if (op.equals("+")) {
                result = n1 + n2;
            }
            if (op.equals("-")) {
                result = n1 - n2;
            }
            if (op.equals("×")) {
                result = n1 * n2;
            }
            if (op.equals("÷")) {
                if (n2 == 0) {
                    result2 = "结果未定义";
                    editText1.setText(result2);
                    return;
                } else
                    result = n1 / n2;
            }
            if (op.equals("%")) {
                if (n2 == 0)
                    result = 0;
                else
                    result = n1 % n2;
            }
            if(number1.equals(".") || number2.equals(".")) {
                editText1.setText("结果未定义");return;
            }
            if (!number1.contains(".") && !number2.contains(".")) {
                int r = (int) result;
                editText1.setText(r + "");
            } else
                editText1.setText(result + "");
            }


        if (!number1.equals("") && number2.equals("")) {
            double n1 = Double.parseDouble(number1);
            if (op.equals("+")) {
                result = n1 + n1;
            }
            if (op.equals("-")) {
                result = 0;
            }
            if (op.equals("*")) {
                result = n1 * n1;
            }
            if (op.equals("÷")) {
                result = 1;
            }
            if (op.equals("%")) {
                result = 0;
            }
          /*  if(number1.equals(".") || number2.equals(".")){
                editText1.setText("结果未定义");return;
            }*/
            if (!number1.contains(".") && !number2.contains(".")) {
                int r = (int) result;
                editText1.setText(r + "");
            } else
            editText1.setText(result + "");

        }
    }
}