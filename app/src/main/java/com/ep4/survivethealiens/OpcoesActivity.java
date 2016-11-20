package com.ep4.survivethealiens;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.ep4.survivethealiens.Activity.LoginActivity;
import com.ep4.survivethealiens.Helper.SaveSharedPreference;

public class OpcoesActivity extends AppCompatActivity {

    Button buttonSair;
    Context myContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opcoes);
        if(SaveSharedPreference.getId(this).length() == 0)
        {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
        myContext = this;
        buttonSair = (Button) findViewById(R.id.buttonSair);

        buttonSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveSharedPreference.clearAllData(v, myContext);
                Intent intent = new Intent(myContext, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
