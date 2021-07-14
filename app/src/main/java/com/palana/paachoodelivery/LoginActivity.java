package com.palana.paachoodelivery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    EditText tiEtEmail;

    EditText tiEtPassword;

    Button mLogin;
    private Toolbar mToolabr;
    private ProgressDialogFragment progressDialogFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        tiEtEmail = findViewById(R.id.tiEtEmail);
        tiEtPassword = findViewById(R.id.tiEtPassword);
        mLogin = findViewById(R.id.btnLogin);
        mToolabr = findViewById(R.id.toolbar);
        setSupportActionBar(mToolabr);
        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateFields();
            }
        });

    }
    private void validateFields() {
        if (TextUtils.isEmpty(tiEtEmail.getText())) {
            tiEtEmail.requestFocus();
            Toast.makeText(this, "Username is not entered", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(tiEtPassword.getText())) {
            tiEtPassword.requestFocus();
            Toast.makeText(this, "Password is not entered", Toast.LENGTH_SHORT).show();
        } else {
            progressDialogFragment = ProgressDialogFragment.newInstance();
            progressDialogFragment.show(getSupportFragmentManager(), "progress_dialog");
            ApiClient.getApiInterface().login(tiEtEmail.getText().toString(),tiEtPassword.getText().toString()).enqueue(new Callback<Result>() {
                @Override
                public void onResponse(Call<Result> call, Response<Result> response) {
                    try {
                        if (progressDialogFragment != null && !isFinishing())
                            progressDialogFragment.dismiss();
                        Result jsonObject = response.body();
                        if (response.isSuccessful()) {
                            if (jsonObject!=null && jsonObject.getData()!=null) {
                                SharedPrefs.setString(SharedPrefs.Keys.TOKEN, jsonObject.getData().getToken());
                                SharedPrefs.setBoolean(SharedPrefs.Keys.IS_LOGIN, true);
                                SharedPrefs.setString(SharedPrefs.Keys.TYPE, jsonObject.getData().getType());
                                startActivity(new Intent(LoginActivity.this,MainActivity.class));
                                finish();
                            } else {
                                Toast toast = Toast.makeText(LoginActivity.this, jsonObject.getMessage(), Toast.LENGTH_SHORT);
                                toast.show();
                            }

                        } else {
                            if (jsonObject!=null){
                                Toast toast = Toast.makeText(LoginActivity.this, jsonObject.getMessage(), Toast.LENGTH_SHORT);
                                toast.show();
                            }

                        }
                    } catch (IllegalStateException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<Result> call, Throwable t) {
                    try {
                        if (progressDialogFragment != null && !isFinishing())
                            progressDialogFragment.dismiss();
                        Toast toast = Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT);
                        toast.show();
                    } catch (IllegalStateException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}
