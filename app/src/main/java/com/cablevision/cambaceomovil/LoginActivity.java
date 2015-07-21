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
import com.cablevision.cambaceomovil.dto.Usuario;
import com.cablevision.cambaceomovil.utils.UsuarioAlmacenLocal;

import android.widget.Toast;
import android.app.AlertDialog;

public class LoginActivity extends Activity implements View.OnClickListener {

    final String urlLogin ="http://josmigrm.site11.com/login.php";

    Button bLogin;
    EditText etUsername, etPassword;

    UsuarioAlmacenLocal almacenLocalUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        bLogin = (Button)findViewById(R.id.btnLogin);
        etUsername = (EditText)findViewById(R.id.etUsername);
        etPassword = (EditText)findViewById(R.id.etPassword);

        bLogin.setOnClickListener(this);
        almacenLocalUsuario = new UsuarioAlmacenLocal(this);
    }

    @Override
    public void onClick(View v) {
        if(validaCampos()){
            authenticateUser();
        }
    }


    private void authenticateUser() {
        RequestQueue queue = Volley.newRequestQueue(this);
        final String user = etUsername.getText().toString();
        final String pass = etPassword.getText().toString();
        String urlFinal= urlLogin+"?user="+user+"&password="+pass;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, urlFinal,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if(response.substring(0,13).matches("\\d*")){
                        almacenLocalUsuario.storeUserData(new Usuario("Miguel Rubio", user, pass, response.substring(0,13)));
                        almacenLocalUsuario.setUserLoggedIn(true);
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                    } else if(response.equals("false")) {
                        almacenLocalUsuario.setUserLoggedIn(false);
                        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(LoginActivity.this);
                        dialogBuilder.setMessage("Error del sistema intente en un momento");
                        dialogBuilder.setPositiveButton("Ok", null);
                        dialogBuilder.show();
                    } else {
                        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(LoginActivity.this);
                        dialogBuilder.setMessage(response);
                        dialogBuilder.setPositiveButton("Ok", null);
                        dialogBuilder.show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(LoginActivity.this, "Error al cargar los datos: "+error.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        );
        queue.add(stringRequest);

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

    @Override
    public void onBackPressed() {
    }
}
