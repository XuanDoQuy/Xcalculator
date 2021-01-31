package com.learn.xcalculator;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Stack;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "AAA";
    RecyclerView recyclerView;
    ArrayList<String> listButton;
    KeyBoardAdapter adapter;
    TextView txtInput, txtResult;
    Button btnExit, btnAbout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.keybroadContainer);
        txtInput = findViewById(R.id.textInput);
        txtResult = findViewById(R.id.textViewResult);
        btnExit = findViewById(R.id.buttonExit);
        btnAbout = findViewById(R.id.buttonAbout);
        initKeyBoard();
        txtInput.setMovementMethod(new ScrollingMovementMethod());

        adapter.setOnItemClickListener(new KeyBoardAdapter.OnItemClickListener() {
            @Override
            public void onClick(View v, int position) {
                String temp = listButton.get(position);
                //Toast.makeText(MainActivity.this, "11", Toast.LENGTH_SHORT).show();
                String ex = txtInput.getText().toString();
                String res = txtResult.getText().toString();
                int n = ex.length();
                switch (temp) {
                    case "1":
                    case "2":
                    case "3":
                    case "4":
                    case "5":
                    case "6":
                    case "7":
                    case "8":
                    case "9":
                    case "0":
                        txtInput.append(temp);
                        break;
                    case "+":
                    case "/":
                    case "*":
                        String s = txtInput.getText().toString();
                        if (!s.isEmpty() && !isOperator(s.charAt(s.length() - 1))) {
                            txtInput.append(temp);
                        }
                        break;
                    case "-":
                        if (ex.isEmpty()||ex.charAt(ex.length()-1)!='-') {
                            txtInput.append(temp);
                        }
                        break;
                    case "=":
                        String input = txtInput.getText().toString();
                        if (!input.isEmpty()) {
                            if (!isOperator(input.charAt(input.length() - 1))) {
                                double result = calculate();
                                long l = (long) result;
                                if (result == l) {
                                    txtResult.setText(String.valueOf(l));
                                } else {
                                    txtResult.setText(String.valueOf(result));
                                }

                            }
                        }
                        break;
                    case ".":
                        if (!ex.isEmpty() && ex.charAt(n - 1) >= '0' && ex.charAt(n - 1) <= '9') {
                            txtInput.append(temp);
                        }
                        break;
                    case "AC":
                        txtInput.setText("");
                        txtResult.setText("");
                        break;
                    case "DEL":
                        StringBuilder curEx = new StringBuilder(txtInput.getText().toString());
                        if (curEx.length() > 0) {
                            curEx.deleteCharAt(curEx.length() - 1);
                        }
                        txtInput.setText(String.valueOf(curEx));
                        break;
                    case "(":
                        txtInput.append(temp);
                        break;
                    case ")":
                        txtInput.append(temp);
                        break;
                }
            }
        });

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogExit();
            }
        });

        btnAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "App Xcalculator!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showDialogExit() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setPositiveButton("Exit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.setMessage("Do you want to exit?");
        dialog.show();
    }

    private void initKeyBoard() {
        listButton = new ArrayList<>();
        listButton.add("7");
        listButton.add("8");
        listButton.add("9");
        listButton.add("DEL");
        listButton.add("AC");
        listButton.add("4");
        listButton.add("5");
        listButton.add("6");
        listButton.add("*");
        listButton.add("/");
        listButton.add("1");
        listButton.add("2");
        listButton.add("3");
        listButton.add("+");
        listButton.add("-");
        listButton.add("0");
        listButton.add(".");
        listButton.add("(");
        listButton.add(")");
        listButton.add("=");
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 5);
        recyclerView.setLayoutManager(gridLayoutManager);
        adapter = new KeyBoardAdapter(listButton, getApplicationContext());
        recyclerView.setAdapter(adapter);
        int spaceHorizontal = getResources().getDimensionPixelSize(R.dimen.spaceHorizontal);
        int spaceVertica = getResources().getDimensionPixelSize(R.dimen.spaceVertical);
        recyclerView.addItemDecoration(new ItemDecoration(spaceHorizontal, spaceVertica));
    }

    private boolean isOperator(char c) {
        switch (c) {
            case '+':
            case '-':
            case '*':
            case '/':
                return true;
        }
        return false;
    }

    private boolean isBracket(char c) {
        if (c == '(' || c == ')') return true;
        else return false;
    }

    private int valueOp(char c) {
        if (c == '+' || c == '-') return 1;
        if (c == '*' || c == '/') return 2;
        return -1;
    }

    private double calculate() {
        String s = txtInput.getText().toString();
        ArrayList<String> vs = splitString(s);
        vs = convertToPostfix(vs);
        Stack<Double> st = new Stack<>();
        for (int i = 0; i < vs.size(); i++) {
            if (isOperator(vs.get(i).charAt(0)) && vs.get(i).length() > 1) {
                st.push(Double.parseDouble(vs.get(i)));
                continue;
            }
            if (!isOperator(vs.get(i).charAt(0))) {
                st.push(Double.parseDouble(vs.get(i)));
            } else {
                double x1 = st.peek();
                st.pop();
                double x2 = st.peek();
                st.pop();
                double x = 0;
                switch (vs.get(i).charAt(0)) {
                    case '+':
                        x = x2 + x1;
                        break;
                    case '-':
                        x = x2 - x1;
                        break;
                    case '*':
                        x = x2 * x1;
                        break;
                    case '/':
                        x = x2 / x1;
                        break;
                }
                st.push(x);
            }
        }
        return st.peek();

    }

    ArrayList<String> splitString(String s) {
        ArrayList<String> vs = new ArrayList<>();
        StringBuilder temp = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            if ((i == 0 && s.charAt(i) == '-') || (s.charAt(i) == '-' && isOperator(s.charAt(i - 1)))) {
                temp.append(s.charAt(i));
                continue;
            }
            if (!isOperator(s.charAt(i)) && !isBracket(s.charAt(i))) {
                temp.append(s.charAt(i));
            } else {
                if (temp.length() != 0) {
                    vs.add(String.valueOf(temp));
                    temp.setLength(0);
                }
                vs.add(String.valueOf(s.charAt(i)));
            }
        }
        if (temp.length()!=0) vs.add(String.valueOf(temp));
        Log.d(TAG, "splitString:" + vs);
        return vs;
    }

    ArrayList<String> convertToPostfix(ArrayList<String> ex) {
        ArrayList<String> res = new ArrayList<>();
        Stack<Character> st = new Stack<>();
        for (int i = 0; i < ex.size(); i++) {
            if (isOperator(ex.get(i).charAt(0)) && ex.get(i).length() == 1) {
                char si = ex.get(i).charAt(0);
                while (!st.empty() && valueOp(si) <= valueOp(st.peek())) {
                    res.add(String.valueOf(st.peek()));
                    st.pop();
                }
                st.push(si);
                continue;
            }
            if (ex.get(i).charAt(0) == '(') {
                st.push(ex.get(i).charAt(0));
                continue;
            }
            if (ex.get(i).charAt(0) == ')') {
                while (!st.empty() && st.peek() != '(') {
                    res.add(String.valueOf(st.peek()));
                    st.pop();
                }
                if (!st.empty()) st.pop();
                continue;
            }
            res.add(ex.get(i));
        }
        while (!st.empty()) {
            if (st.peek() != '(' && st.peek() != ')') {
                res.add(String.valueOf(st.peek()));
            }
            st.pop();
        }
        Log.d(TAG, "convertToPostfix:" + res);
        return res;
    }

}
