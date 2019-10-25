package com.example.PiggyBankApp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.text.DecimalFormat;

public class ConfirmActivity extends AppCompatActivity {

    DecimalFormat money_format = new DecimalFormat("$0.00");
    final int DEPOSIT = 1;
    final int WITHDRAW = 2;
    String deposit_statement = " will be deposited into your account";
    String withdraw_statement = " will be withdrawn from your account";
    double new_balance = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);

        TextView change_amount_view = (TextView) findViewById(R.id.withdraw_amount);
        TextView balance_amount_view = (TextView) findViewById(R.id.balance);

        Intent intent = getIntent();
        double change_amount = intent.getDoubleExtra("change_amount", 0.0);
        int transaction_type = intent.getIntExtra("transaction_type", 0);

        SharedPreferences sharedPref = this.getSharedPreferences("balance_value", Context.MODE_PRIVATE);
        double balance = Double.parseDouble(sharedPref.getString("balance", "0.00"));



        // Spinner
        Spinner spinner = (Spinner) findViewById(R.id.spinner2);
        // Create an ArrayAdapter using the string array and a default spinner layout
                ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                        R.array.planets_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
                spinner.setAdapter(adapter);



        if (transaction_type == DEPOSIT) {

            new_balance = balance + change_amount;
            change_amount_view.setText(money_format.format(change_amount) + deposit_statement);
            balance_amount_view.setText(money_format.format(new_balance));

        } else if (transaction_type == WITHDRAW) {

            //we don't want to automatically change new_balance in the case that we get a negative
            //balance, so first set it as our original balance

            new_balance = balance;
            if (balance - change_amount > 0) {

                new_balance = balance - change_amount; //since it's a valid amount, actually perform transaction
                change_amount_view.setText(money_format.format(change_amount) + withdraw_statement);
                balance_amount_view.setText(money_format.format(new_balance));

            } else {

                change_amount_view.setText("Insufficient balance, will not withdraw");
                balance_amount_view.setText(money_format.format(new_balance));

            }

        } else {
            //This should never happen unless you change the constants declared or make your own transaction,
            //neither of which you should be doing.
            try {
                invalidTransaction();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        /*
         * Code for button onClickListeners
         */

        final ImageButton confirm = findViewById(R.id.confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SharedPreferences sharedPref = getSharedPreferences("balance_value", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("balance", Double.toString(new_balance));
                editor.commit();
                startActivity(new Intent(ConfirmActivity.this, MainActivity.class));
            }
        });

        final ImageButton cancel = findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(ConfirmActivity.this, MainActivity.class));
            }
        });

    }

    public void invalidTransaction() throws IOException {
        throw new IOException("An invalid transaction was attempted. Something went terribly wrong, " +
                "tell the TAs :(");
    }
}
