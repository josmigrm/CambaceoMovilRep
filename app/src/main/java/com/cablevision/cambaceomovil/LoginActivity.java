package com.cablevision.cambaceomovil;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class LoginActivity extends Activity implements View.OnClickListener {

    Button bLogin;
    EditText etUsername, etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        bLogin = (Button)findViewById(R.id.btnLogin);
        etUsername = (EditText)findViewById(R.id.etUsername);
        etPassword = (EditText)findViewById(R.id.etPassword);

        bLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(validaCampos()){
            authenticateUser();
        }
    }

    private void authenticateUser() {
        if("admin".equals(etUsername.getText().toString()) && "1234".equals(etPassword.getText().toString())){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else {
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
            dialogBuilder.setMessage("Credenciales incorrectas");
            dialogBuilder.setPositiveButton("Ok", null);
            dialogBuilder.show();
        }
    }

    private boolean validaCampos(){
        boolean result = true;
        if("".equals(etUsername.getText().toString())){
            etUsername.setError("El nombre de usuario es requerido");
            result = false;
        }
        if("".equals(etPassword.getText().toString())){
            etPassword.setError("El password es requerido");
            result = false;
        }

        return result;
    }
}
