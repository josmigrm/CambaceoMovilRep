package com.cablevision.cambaceomovil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.cablevision.cambaceomovil.dto.Domicilio;


public class DetalleDomicilioActivity extends Activity {

    Domicilio domicilio = new Domicilio();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_domicilio);
        Intent intent = getIntent();
        domicilio = (Domicilio) intent.getSerializableExtra(MainActivity.SELECTED_DOMICILIO);
        TextView textView = (TextView)findViewById(R.id.detAccNum);
        textView.setText(domicilio.getAccont_No());
        textView = (TextView)findViewById(R.id.detCm);
        textView.setText(domicilio.getCM());
        textView = (TextView)findViewById(R.id.detConvertidor);
        textView.setText(domicilio.getConvetidor());
        textView = (TextView)findViewById(R.id.detDepto);
        textView.setText(domicilio.getDepartamento());
        textView = (TextView)findViewById(R.id.detEdificio);
        textView.setText(domicilio.getEdificio());
        textView = (TextView)findViewById(R.id.detEstatus);
        textView.setText(domicilio.getEstatus());
        textView = (TextView)findViewById(R.id.detFActivacion);
        textView.setText(domicilio.getF_Activacion());
        textView = (TextView)findViewById(R.id.detFCancelacion);
        textView.setText(domicilio.getF_Cancelacion());
        textView = (TextView)findViewById(R.id.detFSuspension);
        textView.setText(domicilio.getF_Suspension());
        textView = (TextView)findViewById(R.id.detMta);
        textView.setText(domicilio.getMTA());
        textView = (TextView)findViewById(R.id.detNumInt);
        textView.setText(domicilio.getNum_Int());
        textView = (TextView)findViewById(R.id.detNumExt);
        textView.setText(domicilio.getNum_Ext());
        textView = (TextView)findViewById(R.id.detOfertaComer);
        textView.setText(domicilio.getOferta_Comercial());
        textView = (TextView)findViewById(R.id.detOrientacion);
        textView.setText(domicilio.getOrientacion());
        textView = (TextView)findViewById(R.id.detSaldoIncobrable);
        textView.setText(String.valueOf(domicilio.getSaldo_Incobrable()));
        textView = (TextView)findViewById(R.id.detSaldoTotal);
        textView.setText(String.valueOf(domicilio.getSaldo_Total()));

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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
