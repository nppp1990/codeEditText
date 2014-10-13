package com.yj.example;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.yj.views.CodeEditText;


public class Main extends ActionBarActivity implements View.OnClickListener {

    private CodeEditText codeEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        codeEditText = (CodeEditText) findViewById(R.id.edit);
        findViewById(R.id.btn1).setOnClickListener(this);
        findViewById(R.id.btn2).setOnClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private int randomNumber;

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn1) {
            codeEditText.addNumber(randomNumber++ % 10);
        } else if (view.getId() == R.id.btn2) {
            codeEditText.deleteNumber();
        }


    }
}
