package com.example.rafael.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.rafael.myapplication.dao.AlunoDAO;
import com.example.rafael.myapplication.modelo.Aluno;

public class FormActivity extends AppCompatActivity {

    private FormHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        //instância do objeto FormHelper
        helper = new FormHelper(this);

    }

    //Menu superior
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        //"Infla"/Desenha o menu superior
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_form, menu);
        return super.onCreateOptionsMenu(menu);

    }

    //Evento do item do menu selecionado
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //switch case para cada botão do menu
        switch (item.getItemId()){

            case R.id.menu_formulario_ok:

                //metodo que pega as informações dos inputs e salva
                Aluno aluno = helper.pegaAluno();

                //instância do objeto AlunoDAO
                AlunoDAO dao = new AlunoDAO(this);

                //método do objeto AlunoDAO que insere no banco de dados os inputs
                dao.insere(aluno);

                //fecha a conexão com o banco de dados
                dao.close();

                Toast.makeText(FormActivity.this,"Aluno " + aluno.getNome() + " salvo com sucesso.",Toast.LENGTH_SHORT).show();
                finish();//fecha activity
            break;//end case
        }
        return super.onOptionsItemSelected(item);
    }
}
