package com.ep4.survivethealiens.Activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ep4.survivethealiens.Feign.Task.AutenticarJogadorTask;
import com.ep4.survivethealiens.Feign.Task.GetJogadorTask;
import com.ep4.survivethealiens.Feign.Task.GetMissaoByJogadorTask;
import com.ep4.survivethealiens.Model.Credenciais;
import com.ep4.survivethealiens.Model.Jogador;
import com.ep4.survivethealiens.Model.MissaoJogador;
import com.ep4.survivethealiens.R;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

/**
 * Created by aluno on 07/10/2016.
 */
public class LoginActivity extends AppCompatActivity {
    EditText editTextEmail;
    EditText editTextSenha;
    TextView textViewAppName;
    public Jogador jogador;
    AutenticarJogadorTask autenticarJogadorTask;
    public static ArrayList<MissaoJogador> missaoJogadorList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextEmail = (EditText) findViewById(R.id.input_email);
        editTextSenha = (EditText) findViewById(R.id.input_password);
        textViewAppName = (TextView) findViewById(R.id.textViewAppName);

        missaoJogadorList = new ArrayList<>();

        //para garantir que a internet será acessada
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        //Colocando fonte no nome do app
        Typeface customFont = Typeface.createFromAsset(getAssets(), "fonts/alien_and_cows_trial.ttf");
        textViewAppName.setTypeface(customFont);
    }

    public void esqueceuSuaSenha(View v) {
        Intent intent = new Intent(this, RecuperarSenhaActivity.class);
        startActivity(intent);
    }

    public void autenticar(View v){
        String email = editTextEmail.getText().toString();
        String senha = editTextSenha.getText().toString();

        if(email.equals("")) {
            Toast.makeText(this, "E-mail não pode estar vazio.", Toast.LENGTH_SHORT).show();
            return;
        }else if(senha.equals("")){
            Toast.makeText(this, "Senha não pode estar vazia.", Toast.LENGTH_SHORT).show();
            return;
        }

        Credenciais credenciais = new Credenciais(email, senha);

        try{
            autenticarJogadorTask = new AutenticarJogadorTask(this, this);
            autenticarJogadorTask.execute(credenciais);

        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void iniciarTelaLogin(View v) {
        Intent intent = new Intent(this, PrincipalActivity.class);
        startActivity(intent);
    }

    public void iniciarTelaCadastro(View v){
        Intent intent = new Intent(this, CadastroActivity.class);
        startActivity(intent);
        //Toast.makeText(this, "Ainda não tem nada aqui", Toast.LENGTH_SHORT).show();
    }

    public void iniciarTelaPrincipal(){
        Intent intent = new Intent(this, PrincipalActivity.class);
        EventBus.getDefault().postSticky(jogador);
        startActivityForResult(intent, 0);
    }

}
