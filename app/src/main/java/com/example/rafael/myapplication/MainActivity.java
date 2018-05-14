package com.example.rafael.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.rafael.myapplication.dao.AlunoDAO;
import com.example.rafael.myapplication.modelo.Aluno;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //método que carrega a lista do BD
        carregaLista();

        //botão que abre a view de Formulário
        Button novoAluno = (Button) findViewById(R.id.novo_aluno);
        novoAluno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentMainToForm = new Intent(MainActivity.this, FormActivity.class);
                startActivity(intentMainToForm);
            }
        });
    }

    private void carregaLista() {
        //instância do objeto DAO
        AlunoDAO dao = new AlunoDAO(this);

        //método que busca os alunos do DB
        List<Aluno> alunos = dao.buscaAlunos();
        dao.close();//fecha conexão com o DB

        //método que instancia a ListView
        ListView listaAlunos = (ListView) findViewById(R.id.lista_alunos);
        ArrayAdapter<Aluno> adapter = new ArrayAdapter<Aluno>(this, android.R.layout.simple_list_item_1, alunos);
        listaAlunos.setAdapter(adapter);
    }
}
