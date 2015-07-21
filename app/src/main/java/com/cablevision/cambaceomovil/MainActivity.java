package com.cablevision.cambaceomovil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cablevision.cambaceomovil.dto.Domicilio;
import com.cablevision.cambaceomovil.utils.DatabaseCambaceo;
import com.cablevision.cambaceomovil.utils.DomicilioItemAdapter;
import com.cablevision.cambaceomovil.utils.UsuarioAlmacenLocal;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity implements AdapterView.OnItemSelectedListener{

    public final static String SELECTED_DOMICILIO = "com.cablevision.cambaceomovil.SELECTED_DOMICILIO";
    String URL_DOMICILIOS ="http://josmigrm.site11.com/consultaCTES_CAT.php";
    String URL_CAT_ENT_FED ="http://josmigrm.site11.com/cambaceo.php";
    String URL_CAT_PLAZAS ="http://josmigrm.site11.com/consultaPLAZA_CAT.php";

    DatabaseCambaceo dbCambaceo;
    UsuarioAlmacenLocal almacenLocalUsuario;

    ArrayList<Domicilio> arrayDomicilios;
    List<String> listPlazas;
    List<String> listMpos;
    List<String> listCols;

    EditText filterText;
    Spinner plazasSpinner;
    Spinner mposSpinner;
    Spinner colsSpinner;

    @Override
    protected void onStart() {
        super.onStart();
        if(almacenLocalUsuario == null){
            almacenLocalUsuario = new UsuarioAlmacenLocal(this);
        }

        if(almacenLocalUsuario.getLoggedInUser() != null) {
            initView();
        } else {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        almacenLocalUsuario = new UsuarioAlmacenLocal(this);
        filterText = (EditText) findViewById(R.id.editTextFilter);
        syncCatalogos();
    }

    private void syncCatalogos() {
        dbCambaceo = new DatabaseCambaceo(this);
        RequestQueue queue = Volley.newRequestQueue(this);

        if(dbCambaceo.getCatEntFedCount() <= 0) {
            StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_CAT_ENT_FED,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                dbCambaceo.insertaRegistrosCatEntFed(response);
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(MainActivity.this, "Error al cargar los datos: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(MainActivity.this, "Error al cargar los datos: " + error.toString(), Toast.LENGTH_SHORT).show();
                }
            });
            queue.add(stringRequest);
        }


        if(dbCambaceo.getCatPlazasCount() <= 0) {
            StringRequest stringRequestPlazas = new StringRequest(Request.Method.GET, URL_CAT_PLAZAS,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                dbCambaceo.insertaRegistrosCatPlazas(response);
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(MainActivity.this, "Error al cargar los datos: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(MainActivity.this, "Error al cargar los datos: " + error.toString(), Toast.LENGTH_SHORT).show();
                }
            });
            queue.add(stringRequestPlazas);
        }
    }

    private void initView(){
        setContentView(R.layout.activity_main);
        addItemsOnSpinners();
        if(arrayDomicilios!=null)
            renderList();

    }

    public void enviarRequest(View view) {
        String token = almacenLocalUsuario.getLoggedInUser().getSessionToken();
        String nomMpo = mposSpinner.getSelectedItem().toString();
        String nomCol = colsSpinner.getSelectedItem().toString();
        String idDireccion =dbCambaceo.getIdColEntFed(nomMpo, nomCol);
        String idPlaza = dbCambaceo.getIdPlaza(plazasSpinner.getSelectedItem().toString());

        //String url = URL_DOMICILIOS + "?token="+token+"&plaza="+idPlaza+"&idDireccion="+idDireccion;
        String url = "http://josmigrm.site11.com/consultaCTES_CAT.php?token=1436466877486&plaza=1-59JX&idDireccion=1@29720565";

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    arrayDomicilios = parseDomicilioData(response);
                    renderList();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(MainActivity.this, "Error al cargar los datos: "+error.toString(), Toast.LENGTH_SHORT).show();
                }
        });

        queue.add(stringRequest);
    }

    private void renderList() {
        ListView adressListView = (ListView) findViewById(R.id.adressListView);
        final DomicilioItemAdapter domicilioAdapter = new DomicilioItemAdapter(this,R.layout.domicilio_list_item,arrayDomicilios);
        adressListView.setAdapter(domicilioAdapter);

        // Clic Listener para los elementos de la lista
        adressListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parentAdapter, View view, int position,
                                    long id) {
                //Toast.makeText(MainActivity.this, doms.get(position).toString(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, DetalleDomicilioActivity.class);
                intent.putExtra(SELECTED_DOMICILIO, arrayDomicilios.get(position));
                startActivity(intent);
            }
        });

        //Agregamos el filtro
        adressListView.setTextFilterEnabled(true);
        EditText filterText = (EditText) findViewById(R.id.editTextFilter);
        filterText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                System.out.println("Text [" + s + "] - Start [" + start + "] - Before [" + before + "] - Count [" + count + "]");
                domicilioAdapter.getFilter().filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void addItemsOnSpinners() {
        dbCambaceo = new DatabaseCambaceo(this);

        listPlazas = dbCambaceo.getPlazas();
        listMpos = dbCambaceo.getMunicipios();
        listCols = dbCambaceo.getColonias("MONTERREY");
        //listCols = new ArrayList<String>();

        plazasSpinner = (Spinner)findViewById(R.id.spinPlaza);
        mposSpinner = (Spinner)findViewById(R.id.spinMpo);
        colsSpinner = (Spinner)findViewById(R.id.spinColonia);

        ArrayAdapter<String> plazaAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, listPlazas);
        plazaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        plazasSpinner.setAdapter(plazaAdapter);

        ArrayAdapter<String> mpoAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, listMpos);
        mpoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mposSpinner.setAdapter(mpoAdapter);

        ArrayAdapter<String> colsAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, listCols);
        colsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        colsSpinner.setAdapter(colsAdapter);

        mposSpinner.setOnItemSelectedListener(this);

    }


    private ArrayList<Domicilio> parseDomicilioData(String jsonStr){
        ArrayList<Domicilio> arrayDirecciones = new ArrayList<Domicilio>();
        
        try {
            JSONArray ja = null;

            if(!jsonStr.startsWith("null"))
                ja = new JSONArray(jsonStr);

            if(ja == null){
                Toast.makeText(MainActivity.this, "0 Resultados Encontrados", Toast.LENGTH_LONG).show();
            } else {
                for (int i = 0; i < ja.length(); i++) {
                    JSONObject child = ja.getJSONObject(i);
                    Domicilio tempDir = new Domicilio();
                    tempDir.setNum_Ext(child.getString("NUM_EXT"));
                    tempDir.setNum_Int(child.getString("NUM_INT"));
                    tempDir.setEdificio(child.getString("EDIFICIO"));
                    tempDir.setDepartamento(child.getString("DEPARTAMENTO"));
                    tempDir.setOrientacion(child.getString("ORIENTACION"));
                    tempDir.setAccont_No(child.getString("ACCONT_NO"));
                    tempDir.setEstatus(child.getString("ESTATUS"));
                    tempDir.setF_Activacion(child.getString("F_ACTIVACION"));
                    tempDir.setF_Suspension(child.getString("F_SUSPENSION"));
                    tempDir.setF_Cancelacion(child.getString("F_CANCELACION"));
                    tempDir.setOferta_Comercial(child.getString("OFERTA_COMERCIAL"));
                    tempDir.setConvetidor(child.getString("CONVERTIDOR"));
                    tempDir.setMTA(child.getString("MTA"));
                    tempDir.setCM(child.getString("CM"));
                    tempDir.setSaldo_Total(child.isNull("SALDO_TOTAL")?0:child.getLong("SALDO_TOTAL"));
                    tempDir.setSaldo_Incobrable(child.isNull("SALDO_INCOBRABLE")?0:child.getLong("SALDO_INCOBRABLE"));

                    arrayDirecciones.add(tempDir);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return arrayDirecciones;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menumensajeresource, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            almacenLocalUsuario.clearUserData();
            almacenLocalUsuario.setUserLoggedIn(false);
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        listCols = dbCambaceo.getColonias(mposSpinner.getSelectedItem().toString());
        ArrayAdapter<String> colsAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, listCols);
        colsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        colsSpinner.setAdapter(colsAdapter);

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
