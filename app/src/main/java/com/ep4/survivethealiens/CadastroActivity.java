package com.ep4.survivethealiens;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
/**
 * Created by Carla on 08/10/2016.
 */

public class CadastroActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    Spinner generoSpinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        generoSpinner =(Spinner)findViewById(R.id.spinnerGenero);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.generos, R.layout.spinner_item);
        generoSpinner.setAdapter(adapter);
    }

    public void iniciarTelaLogin(View v){
        Intent intent = new Intent (this, LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}

