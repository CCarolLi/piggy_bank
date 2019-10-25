package com.example.PiggyBankApp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    double curr_input = 0.0;
    DecimalFormat decimal_format = new DecimalFormat("0.00");
    DecimalFormat money_format = new DecimalFormat("$0.00");
    double balance_value = 0.0;
    final int DEPOSIT = 1;
    final int WITHDRAW = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final TextView balance = (TextView) findViewById(R.id.balance);
        final EditText curr_input_view = (EditText) findViewById(R.id.input_amount);

        SharedPreferences sharedPref = this.getSharedPreferences("balance_value", Context.MODE_PRIVATE);
        /*In sharedPref, there's a balance key-value pair (key: "balance", value: String) whose
        * value string is a String representation of the float balance value
        */
        String stored_balance = sharedPref.getString("balance", "0.00");
        balance_value = Double.parseDouble(stored_balance);
        balance.setText(money_format.format(balance_value));

        final ImageButton deposit = findViewById(R.id.deposit);
        deposit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int transaction_type = DEPOSIT;
                curr_input = Double.parseDouble(curr_input_view.getText().toString());
                //necessary to catch manually entered amounts
                Intent intent = new Intent(MainActivity.this, ConfirmActivity.class);

                intent.putExtra("change_amount", curr_input); //amount to change balance by
                intent.putExtra("transaction_type", transaction_type); //what type of transaction

                startActivity(intent);
            }
        });

        final ImageButton withdraw = findViewById(R.id.withdraw);
        withdraw.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int transaction_type = WITHDRAW;
                curr_input = Double.parseDouble(curr_input_view.getText().toString());
                //necessary to catch manually entered amounts
                Intent intent = new Intent(MainActivity.this, ConfirmActivity.class);

                intent.putExtra("change_amount", curr_input);
                intent.putExtra("transaction_type", transaction_type);

                startActivity(intent);
            }
        });

    }

    @Override
    protected void onStart()
    {
        super.onStart();
        final TextView balance = (TextView) findViewById(R.id.balance);
        SharedPreferences sharedPref = this.getSharedPreferences("balance_value", Context.MODE_PRIVATE);
        String stored = sharedPref.getString("balance", "0.00");
        balance_value = Double.parseDouble(stored);
        balance.setText(money_format.format(balance_value));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        // Save UI state changes to the savedInstanceState.
        // This bundle will be passed to onCreate if the process is
        // killed and restarted.
        savedInstanceState.putDouble("balance", balance_value);
        savedInstanceState.putDouble("input_amount", curr_input);
}

    /*
    * Helper functions for button functionality
    *
    * The connection between the buttons and these functions can be seen
    * in the content_main.xml with the "onClick" field of each
    * button
    *
    * */

    public void addOneCent(View view) {
        final EditText input = (EditText) findViewById(R.id.input_amount);
        curr_input += 0.01;
        input.setText(decimal_format.format(curr_input));
    }

    public void addFiveCent(View view) {
        final EditText input = (EditText) findViewById(R.id.input_amount);
        curr_input += 0.05;
        input.setText(decimal_format.format(curr_input));
    }

    public void addTenCent(View view) {
        final EditText input = (EditText) findViewById(R.id.input_amount);
        curr_input += 0.1;
        input.setText(decimal_format.format(curr_input));
    }

    public void addTwentyfiveCent(View view) {
        final EditText input = (EditText) findViewById(R.id.input_amount);
        curr_input += 0.25;
        input.setText(decimal_format.format(curr_input));
    }

    public void addOneDollar(View view) {
        final EditText input = (EditText) findViewById(R.id.input_amount);
        curr_input += 1.00;
        input.setText(decimal_format.format(curr_input));
    }

    public void addFiveDollar(View view) {
        final EditText input = (EditText) findViewById(R.id.input_amount);
        curr_input += 5.00;
        input.setText(decimal_format.format(curr_input));
    }

    public void addTenDollar(View view) {
        final EditText input = (EditText) findViewById(R.id.input_amount);
        curr_input += 10.00;
        input.setText(decimal_format.format(curr_input));
    }
}
