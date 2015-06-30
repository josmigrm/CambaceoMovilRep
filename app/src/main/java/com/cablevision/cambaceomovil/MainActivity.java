package com.cablevision.cambaceomovil;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    String url ="http://192.168.100.5:8080/RESTfulExample/rest/json/metallica/get";
    public final static String SELECTED_DOMICILIO = "com.cablevision.cambaceomovil.SELECTED_DOMICILIO";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addItemsOnSpinners();
    }

    public void enviarRequest(View view) {
        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    ArrayList<Domicilio> dirs = parseDomicilioData(response);
                    renderList(dirs);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(MainActivity.this, "Error al cargar los datos: "+error.toString(), Toast.LENGTH_SHORT).show();
                }
        });

        queue.add(stringRequest);
    }

    private void renderList(final ArrayList<Domicilio> doms) {
        ListView adressListView = (ListView) findViewById(R.id.adressListView);
        final DomicilioItemAdapter domicilioAdapter = new DomicilioItemAdapter(this,R.layout.domicilio_list_item,doms);
        adressListView.setAdapter(domicilioAdapter);

        // React to user clicks on item
        adressListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parentAdapter, View view, int position,
                                    long id) {
                //Toast.makeText(MainActivity.this, doms.get(position).toString(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, DetalleDomicilioActivity.class);
                intent.putExtra(SELECTED_DOMICILIO, doms.get(position));
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
                System.out.println("Text ["+s+"] - Start ["+start+"] - Before ["+before+"] - Count ["+count+"]");
                domicilioAdapter.getFilter().filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void addItemsOnSpinners() {

        List<String> listPlazas = new ArrayList<>();
        listPlazas.add("Monterrey");
        listPlazas.add("Abasolo");
        listPlazas.add("Allende");
        listPlazas.add("Anahuac");

        List<String>listMpos = new ArrayList<>();
        listMpos.add("Monterrey");
        listMpos.add("Apodaca");
        listMpos.add("Juarez");
        listMpos.add("García");

        List<String> listCols = new ArrayList<>();
        listCols.add("La Alianza");
        listCols.add("Fundidora");
        listCols.add("Chapultepec");
        listCols.add("Brisas");

        Spinner plazasSpinner = (Spinner)findViewById(R.id.spinPlaza);
        Spinner mposSpinner = (Spinner)findViewById(R.id.spinMpo);
        Spinner colsSpinner = (Spinner)findViewById(R.id.spinColonia);

        ArrayAdapter<String> plazaAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, listPlazas);
        plazaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        plazasSpinner.setAdapter(plazaAdapter);

        ArrayAdapter<String> mpoAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, listMpos);
        mpoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mposSpinner.setAdapter(mpoAdapter);

        ArrayAdapter<String> colsAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, listCols);
        colsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        colsSpinner.setAdapter(colsAdapter);


    }


    private ArrayList<Domicilio> parseDomicilioData(String jsonStr){
        ArrayList<Domicilio> arrayDirecciones = new ArrayList<>();
        
        try {
            JSONArray ja = new JSONArray(jsonStr);

            for (int i=0; i<ja.length(); i++){
                JSONObject child =  ja.getJSONObject(i);
                Domicilio tempDir = new Domicilio();
                tempDir.setNum_Ext(child.getString("num_Ext"));
                tempDir.setNum_Int(child.getString("num_Int"));
                tempDir.setEdificio(child.getString("edificio"));
                tempDir.setDepartamento(child.getString("departamento"));
                tempDir.setOrientacion(child.getString("orientacion"));
                tempDir.setAccont_No(child.getString("accont_No"));
                tempDir.setEstatus(child.getString("estatus"));
                tempDir.setF_Activacion(child.getString("f_Activacion"));
                tempDir.setF_Suspension(child.getString("f_Suspension"));
                tempDir.setF_Cancelacion(child.getString("f_Cancelacion"));
                tempDir.setOferta_Comercial(child.getString("oferta_Comercial"));
                tempDir.setConvetidor(child.getString("convetidor"));
                tempDir.setMTA(child.getString("mta"));
                tempDir.setCM(child.getString("cm"));
                tempDir.setSaldo_Total(child.getLong("saldo_Total"));
                tempDir.setSaldo_Incobrable(child.getLong("saldo_Incobrable"));

                arrayDirecciones.add(tempDir);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return arrayDirecciones;
    }
}
