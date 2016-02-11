package com.example.njood.project1;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText editTextID;
    private EditText editTextUsername;
    private EditText editTextPassword;
    private EditText editTextEmail;
    private EditText etaddress;

    private Button buttonRegister;

    private static final String REGISTER_URL = "http://10.0.2.2/project/register.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextID = (EditText) findViewById(R.id.editTextID);
        editTextUsername = (EditText) findViewById(R.id.editTextUserName);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        etaddress = (EditText) findViewById(R.id.etddress);

        buttonRegister = (Button) findViewById(R.id.buttonRegister);
        Button buttonLogin = (Button) findViewById(R.id.buttonLogin);

        buttonRegister.setOnClickListener(this);

        buttonLogin.setOnClickListener(this);

        Intent i=new Intent(MainActivity.this, ActivityLogin.class);
        startActivity(i);
    }



    @Override
    public void onClick(View v) {
        if(v == buttonRegister){
            registerUser();
        }

        Log.i("clicks", "You Clicked B1");
        Intent i=new Intent(
                MainActivity.this,
                ActivityLogin.class);
        startActivity(i);
    }





    private void registerUser() {
        String ID = editTextID.getText().toString().trim().toLowerCase();
        String name = editTextUsername.getText().toString().trim().toLowerCase();
        String password = editTextPassword.getText().toString().trim().toLowerCase();
        String email = editTextEmail.getText().toString().trim().toLowerCase();
        String address = editTextUsername.getText().toString().trim().toLowerCase();


        register(ID, name, password, email, address);
    }

    private void register(String ID, String name, String password, String email, String address) {
        String urlSuffix = "?ID="+ID+"&name="+name+"&password="+password+"&email="+email+"&address="+address;
        class RegisterUser extends AsyncTask<String, Void, String>{

            ProgressDialog loading;
            RegisterUserClass ruc = new RegisterUserClass();


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(MainActivity.this, "Please Wait",null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(String... params) {

                HashMap<String, String> data = new HashMap<String,String>();
                data.put("ID",params[0]);
                data.put("name",params[1]);
                data.put("password",params[2]);
                data.put("email",params[3]);
                data.put("address",params[3]);

                String result = ruc.sendPostRequest(REGISTER_URL, data);

                return  result;
            }
        }

        RegisterUser ru = new RegisterUser();
        ru.execute(ID,name,password,email,address);
    }
}