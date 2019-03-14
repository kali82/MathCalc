package com.gunners.kalisz.mathcalc;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.mariuszgromada.math.mxparser.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class MainActivity extends Activity {

    private TextView screen;
    private String operation = " ";
    private  Button buttonClear;
    private List<String> history = new ArrayList<>();



    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        screen = findViewById(R.id.textView);
        buttonClear = findViewById(R.id.btnClearId);
        screen.setText(operation);
        screen.setMovementMethod(new ScrollingMovementMethod());
        buttonClear.setOnLongClickListener(v ->  {
                onLongClickClear(v);
                return true;

        });

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.menu_history_id){
            screen.setText("");
           String historyList =  history.stream()
                    .collect(Collectors.joining("\n","",""));
            screen.setGravity(View.TEXT_ALIGNMENT_TEXT_START);
            screen.setText(historyList);
            Toast.makeText(getApplicationContext(),"history menu", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }



    public void updateScreen(){
        screen.setText(operation);
    }

    public void onClickNumber(View view) {
        screen.setGravity(View.TEXT_ALIGNMENT_TEXT_END);
        Button button = (Button) view;
        operation += button.getText();
        updateScreen();
    }

    public void onClickOperator(View view) {
        Button button = (Button) view;
        operation += button.getText();
        updateScreen();
    }





    public void onClickEqual(View view){
        Expression expression = new Expression(screen.getText().toString());
        Double result = expression.calculate();
        history.add(screen.getText()+" = "+result.toString()+"\n");
        screen.setText(result.toString());

        operation = "";
    }


    public void onLongClickClear(View view){
        operation = "";
        updateScreen();
    }

    public void onClickClear(View view) {
        if (operation.length() > 0){
           operation =  operation.substring(0, operation.length() -1);
        } else {
            operation = "";
        }
        updateScreen();

    }

}
