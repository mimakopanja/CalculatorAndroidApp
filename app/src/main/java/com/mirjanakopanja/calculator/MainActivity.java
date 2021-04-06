package com.mirjanakopanja.calculator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;



public class MainActivity extends AppCompatActivity {

    protected TextView operationTextView;
    private EditText resultEditText;
    private Button btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btnErase, btnPlus, btnMinus, btnDivide, btnMultiply, btnClear, btnEquals, btnDot, optionBtn;
    static final String stateKey = "stateKey";
    private int first, second;
    private static final String KEY = "options";
    private static final int CODE = 1111;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.relative_layout);
        initFields();
        btnClickListener();
    }

    private void initFields() {
        operationTextView = findViewById(R.id.textView);
        resultEditText = findViewById(R.id.resultEditText);
        btn0 = findViewById(R.id.buttonZero);
        btn1 = findViewById(R.id.buttonOne);
        btn2 = findViewById(R.id.buttonTwo);
        btn3 = findViewById(R.id.buttonThree);
        btn4 = findViewById(R.id.buttonFour);
        btn5 = findViewById(R.id.buttonFive);
        btn6 = findViewById(R.id.buttonSix);
        btn7 = findViewById(R.id.buttonSeven);
        btn8 = findViewById(R.id.buttonEight);
        btn9 = findViewById(R.id.buttonNine);
        btnPlus = findViewById(R.id.buttonPlus);
        btnMinus = findViewById(R.id.buttonMinus);
        btnDivide = findViewById(R.id.buttonDivide);
        btnMultiply = findViewById(R.id.buttonMultiply);
        btnClear = findViewById(R.id.buttonReset);
        btnDot = findViewById(R.id.buttonDot);
        btnErase = findViewById(R.id.buttonErase);
        btnEquals = findViewById(R.id.buttonEquals);
        optionBtn = findViewById(R.id.optionBtn);
    }

    private void btnClickListener() {
        btn0.setOnClickListener(v -> writeText("0"));
        btn1.setOnClickListener(v -> writeText("1"));
        btn2.setOnClickListener(v -> writeText("2"));
        btn3.setOnClickListener(v -> writeText("3"));
        btn4.setOnClickListener(v -> writeText("4"));
        btn5.setOnClickListener(v -> writeText("5"));
        btn6.setOnClickListener(v -> writeText("6"));
        btn7.setOnClickListener(v -> writeText("7"));
        btn8.setOnClickListener(v -> writeText("8"));
        btn9.setOnClickListener(v -> writeText("9"));
        btnDot.setOnClickListener(v -> checkOperationOrder(btnDot));

        btnErase.setOnClickListener(v -> {
            if (!operationTextView.getText().toString().isEmpty()){
                eraseLast();
            } else Toast.makeText(getApplicationContext(), "Nothing to delete!" ,Toast.LENGTH_SHORT).show();
        });

        btnClear.setOnClickListener(v -> {
            if (!operationTextView.getText().toString().isEmpty()) {
                operationTextView.setText("");
                resultEditText.setText("");
            }else Toast.makeText(getApplicationContext(), "Nothing to delete!" ,Toast.LENGTH_SHORT).show();
        });

        btnPlus.setOnClickListener(v -> checkOperationOrder(btnPlus));
        btnMinus.setOnClickListener(v -> checkOperationOrder(btnMinus));
        btnDivide.setOnClickListener(v -> checkOperationOrder(btnDivide));
        btnMultiply.setOnClickListener(v -> checkOperationOrder(btnMultiply));

        optionBtn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ThemeChooser.class);
            startActivityForResult(intent, CODE);
        });

        btnEquals.setOnClickListener(v -> {
            int result = 0;
            result = first + second;
            String resultStr = String.valueOf(result);
            resultEditText.setText(resultStr);

        });
    }

    private void checkOperationOrder(Button button) {
        String string = operationTextView.getText().toString();
        String substring = string.substring(string.length() - 1);
        first = Integer.parseInt(operationTextView.getText().toString());

        if(!string.isEmpty() &&
                !substring.contentEquals(btnPlus.getText()) &&
                !substring.contentEquals(btnMinus.getText()) &&
                !substring.equals(btnMultiply.getText()) &&
                !substring.equals(btnDivide.getText()) &&
                !substring.contentEquals(btnDot.getText())){

            writeText(String.valueOf(button.getText()));

        } else Toast.makeText(getApplicationContext(), "Cant do that!" ,Toast.LENGTH_SHORT).show();

    }

    @SuppressLint("SetTextI18n")
    private void writeText(String toWrite) {
        operationTextView.setText(operationTextView.getText() + toWrite);
    }

    private void eraseLast(){
        operationTextView.setText(operationTextView.getText().toString().substring(0, operationTextView.length() - 1));
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putCharSequence(stateKey, operationTextView.getText());
        Log.i("STATUS", "SAVE STATE");
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.i("STATUS", "RESTORE");
        operationTextView.setText(savedInstanceState.getCharSequence(stateKey));

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        boolean isChecked = data.getBooleanExtra(KEY, true);
        if (data != null &&
        resultCode == RESULT_OK &&
        requestCode == CODE &&
        isChecked){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

    }
}