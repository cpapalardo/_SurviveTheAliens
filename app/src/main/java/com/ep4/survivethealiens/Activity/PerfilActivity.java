package com.ep4.survivethealiens.Activity;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ep4.survivethealiens.Feign.Task.AutenticarJogadorTask;
import com.ep4.survivethealiens.Feign.Task.PutJogadorTask;
import com.ep4.survivethealiens.Model.Jogador;
import com.ep4.survivethealiens.R;

import org.greenrobot.eventbus.EventBus;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PerfilActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    PutJogadorTask putJogadorTask;
    public Jogador jogador;
    TextView textNome, textApelido, textEmail, textSenha;
    String genero;
    Spinner generoSpinner;

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        jogador = EventBus.getDefault().getStickyEvent(Jogador.class);

        textNome = (TextView) findViewById(R.id.textNome);
        textApelido = (TextView) findViewById(R.id.textApelido);
        textEmail = (TextView) findViewById(R.id.textEmail);
        textSenha = (TextView) findViewById(R.id.textSenha);
        generoSpinner =(Spinner)findViewById(R.id.spinnerGenero);

        textNome.setText(jogador.getNome());
        textApelido.setText(jogador.getApelido());
        textEmail.setText(jogador.getEmail());
        textSenha.setText(jogador.getSenha());

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.generos, R.layout.spinner_item);
        generoSpinner.setAdapter(adapter);
        if (!jogador.getGenero().equals(null)) {
            if(jogador.getGenero().equals("F")) {
                genero = "Feminino";
            } else if (jogador.getGenero().equals("M")) {
                genero = "Masculino";
            } else if (jogador.getGenero().equals("O")) {
                genero = "Outros";
            }

            int spinnerPosition = adapter.getPosition(genero);
            generoSpinner.setSelection(spinnerPosition);
        }
    }

    public void atualizarJogador(View view) {
        try {
            genero = generoSpinner.getSelectedItem().toString();

            if(TextUtils.isEmpty(textNome.getText())) {
                Toast.makeText(this, "Seu nome não pode estar vazio.", Toast.LENGTH_SHORT).show();
                return;
            }

            if(TextUtils.isEmpty(textEmail.getText())) {
                Toast.makeText(this, "Seu e-mail não pode estar vazio.", Toast.LENGTH_SHORT).show();
                return;
            }
            if(TextUtils.isEmpty(textSenha.getText())) {
                Toast.makeText(this, "Sua senha não pode estar vazia.", Toast.LENGTH_SHORT).show();
                return;
            }

            if(!validate(textEmail.getText().toString())) {
                Toast.makeText(this, "E-mail em formato errado.", Toast.LENGTH_SHORT).show();
                return;
            }

            if(genero == null || TextUtils.isEmpty(genero) || genero.equals("Gênero")) {
                Toast.makeText(this, "Escolha um gênero", Toast.LENGTH_SHORT).show();
                return;
            }

            if(genero.equals("Feminino")) {
                genero = "F";
            } else if (genero.equals("Masculino")) {
                genero = "M";
            } else if (genero.equals("Outros")) {
                genero = "O";
            }

            jogador.setNome(textNome.getText().toString());
            jogador.setApelido(textApelido.getText().toString());
            jogador.setEmail(textEmail.getText().toString());
            jogador.setSenha(textSenha.getText().toString());
            jogador.setGenero(genero);

            putJogadorTask = new PutJogadorTask(this, this);
            putJogadorTask.execute(jogador);
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        genero = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public boolean validate(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(emailStr);
        return matcher.find();
    }
}
