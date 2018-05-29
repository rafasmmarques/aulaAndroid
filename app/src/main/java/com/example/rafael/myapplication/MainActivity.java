package com.example.rafael.myapplication;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import com.example.rafael.myapplication.dao.AlunoDAO;
import com.example.rafael.myapplication.modelo.Aluno;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    //atributo listaAlunos
    private ListView listaAlunos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //instancia a lista
        listaAlunos = (ListView) findViewById(R.id.lista_alunos);

        //Clique de cada aluno
        listaAlunos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> lista, View item, int position, long id) {
                //posição do aluno clicado
                Aluno aluno = (Aluno)listaAlunos.getItemAtPosition(position);

                Intent intentClickToFormEdit = new Intent(MainActivity.this, FormActivity.class);
                intentClickToFormEdit.putExtra("aluno", aluno);
                startActivity(intentClickToFormEdit);
            }
        });

        //botão que abre a view de Formulário
        Button novoAluno = (Button) findViewById(R.id.novo_aluno);
        novoAluno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentMainToForm = new Intent(MainActivity.this, FormActivity.class);
                startActivity(intentMainToForm);
            }
        });

        //registra essa View como um Menu de Contexto
        registerForContextMenu(listaAlunos);
    }//end onCreate
//-----------------------------------------------------------------------------------------------------------------
    private void carregaLista() {
        //instância do objeto DAO
        AlunoDAO dao = new AlunoDAO(this);

        //método que busca os alunos do DB
        List<Aluno> alunos = dao.buscaAlunos();
        dao.close();//fecha conexão com o DB

        ArrayAdapter<Aluno> adapter = new ArrayAdapter<Aluno>(this, android.R.layout.simple_list_item_1, alunos);
        listaAlunos.setAdapter(adapter);
    }//end carregaLista
//-----------------------------------------------------------------------------------------------------------------
    @Override
    protected void onResume() {
        super.onResume();
        //método que carrega a lista do BD após o envio do formulário
        carregaLista();
    }//end onResume
//-----------------------------------------------------------------------------------------------------------------
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, final ContextMenu.ContextMenuInfo menuInfo) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;

        //Pega a posição na lista do aluno clicado
        final Aluno aluno = (Aluno) listaAlunos.getItemAtPosition(info.position);

        //item "Deletar" do menu do contexto
        MenuItem itemDeletar = menu.add("Deletar");

        //item "Site" do menu do contexto
        MenuItem itemSite = menu.add("Visitar Site");

        //item "SMS" do menu do contexto
        MenuItem itemSMS = menu.add("Enviar SMS");

        //item "Mapa" do menu do contexto
        MenuItem itemMapa = menu.add("Visualizar Endereço");

        //item "Telefone" do menu do contexto
        MenuItem itemTel = menu.add("Ligar para " + ((Aluno) listaAlunos.getItemAtPosition(info.position)).getNome());

        //Listener do item "Telefone"
        itemTel.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                //Checa se não tem permissão
                if(ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 123);
                }
                //se já tem, inicia a Intent
                else{
                    //Intent para ligar para o Aluno quando clicado
                    Intent intentTel = new Intent(Intent.ACTION_CALL);
                    intentTel.setData(Uri.parse("tel:" + aluno.getTelefone()));
                    startActivity(intentTel);

                }
                return false;
            }
        });

        //Checa se o site tem o protocolo http junto, se não, adiciona
        String site = aluno.getSite();
        if (!site.startsWith("http://")){
            site = "http://" + site;
        }

        //Intent para abrir site do aluno quando clicado
        Intent intentSite = new Intent(Intent.ACTION_VIEW);
        intentSite.setData(Uri.parse(site));
        itemSite.setIntent(intentSite);

        //Intent para mandar SMS quando clicado
        Intent intentSMS = new Intent(Intent.ACTION_VIEW);
        intentSMS.setData(Uri.parse("sms:" + aluno.getTelefone()));
        itemSMS.setIntent(intentSMS);

        //Intent para abrir o Mapa com o endereço do aluno
        Intent intentMapa = new Intent(Intent.ACTION_VIEW);
        intentMapa.setData(Uri.parse("geo:0,0?q=" + aluno.getEndereco()));
        itemMapa.setIntent(intentMapa);

        //Listener do item "Deletar" do menu
        itemDeletar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                //instância do objeto DAO
                AlunoDAO dao = new AlunoDAO(MainActivity.this);

                //método que deleta o aluno do DB
                dao.deleta(aluno);

                //fecha a conexão com o DB
                dao.close();

                //método que carrega a lista de alunos do DB
                carregaLista();
                return false;
            }
        });
    }//end onCreateContextMenu
    //-----------------------------------------------------------------------------------------------------------------
}
