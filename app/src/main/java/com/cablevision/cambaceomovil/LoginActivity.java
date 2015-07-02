package com.cablevision.cambaceomovil;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import android.widget.Toast;
import android.app.AlertDialog;

public class LoginActivity extends Activity implements View.OnClickListener {
    String IP="192.168.100.6";
    String PUERTO="9090";
    final String url ="http://"+IP+":"+PUERTO+"/RESTfulExample/rest/CambaceoMovil/login?user=";
    String resultRequest = "false";
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

    public String getResultRequest() {
        return resultRequest;
    }

    public void setResultRequest(String resultRequest) {
        this.resultRequest = resultRequest;
    }

    private void authenticateUser() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String user = etUsername.getText().toString();
        String pass = etPassword.getText().toString();
        String urlFinal= url+user+"&password="+pass;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, urlFinal,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    setResultRequest(response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(LoginActivity.this, "Error al cargar los datos: "+error.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        );
        queue.add(stringRequest);

        if(resultRequest.equals("true")){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else if(resultRequest.equals("false")){
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
            dialogBuilder.setMessage("Error del sistema intente en un momento");
            dialogBuilder.setPositiveButton("Ok", null);
            dialogBuilder.show();
        } else {
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
            dialogBuilder.setMessage(getResultRequest());
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
