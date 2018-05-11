package com.example.rafael.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class FormActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        //botão de salvar o formulário
        Button botaoSalvar = (Button) findViewById(R.id.form_salvar);
        botaoSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(FormActivity.this,"Formulário enviado com sucesso",Toast.LENGTH_SHORT).show();
                Intent intentFormToMain = new Intent(FormActivity.this, MainActivity.class);
                startActivity(intentFormToMain);
                finish();
            }
        });
    }
}
