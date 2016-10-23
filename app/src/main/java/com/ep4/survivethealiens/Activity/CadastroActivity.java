package com.ep4.survivethealiens.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ep4.survivethealiens.Model.Jogador;
import com.ep4.survivethealiens.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Carla on 08/10/2016.
 */

public class CadastroActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    TextView textNome;
    TextView textApelido;
    TextView textEmail;
    TextView textSenha;
    String genero;
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    Spinner generoSpinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        textNome = (TextView) findViewById(R.id.textNome);
        textApelido = (TextView) findViewById(R.id.textApelido);
        textEmail = (TextView) findViewById(R.id.textEmail);
        textSenha = (TextView) findViewById(R.id.textSenha);
        generoSpinner =(Spinner)findViewById(R.id.spinnerGenero);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.generos, R.layout.spinner_item);
        generoSpinner.setAdapter(adapter);
    }

    public void iniciarTelaLogin(View v){
        Intent intent = new Intent (this, LoginActivity.class);
        startActivity(intent);
    }

    public void cadastrar(View v){
        if(TextUtils.isEmpty(textNome.getText())){
            Toast.makeText(this, "Seu nome não pode estar vazio.", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(textEmail.getText())){
            Toast.makeText(this, "Seu e-mail não pode estar vazio.", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(textSenha.getText())){
            Toast.makeText(this, "Sua senha não pode estar vazia.", Toast.LENGTH_SHORT).show();
            return;
        }
        if(validate(textEmail.getText().toString())){
            Toast.makeText(this, "E-mail em formato errado.", Toast.LENGTH_SHORT).show();
            return;
        }
        if(genero == null || TextUtils.isEmpty(genero)){
            Toast.makeText(this, "Escolha um gênero", Toast.LENGTH_SHORT).show();
            return;
        }

        Jogador jogador = new Jogador(textNome.getText().toString(), textApelido.getText().toString(),
                textEmail.getText().toString(), textSenha.getText().toString(), genero);
    }

    public boolean validate(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(emailStr);
        return matcher.find();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        genero = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}

