package com.example.rafael.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.rafael.myapplication.dao.AlunoDAO;
import com.example.rafael.myapplication.modelo.Aluno;

import java.io.File;

public class FormActivity extends AppCompatActivity {

    //atributos da classe
    private FormHelper helper;
    private String caminhoFoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        //instância do objeto FormHelper
        helper = new FormHelper(this);

        //Pega a intent do click na lista
        Intent intent = getIntent();
        Aluno aluno = (Aluno)intent.getSerializableExtra("aluno");

        if (aluno != null){
            helper.preencheForm(aluno);
        }

        //Botão para tirar foto do perfil
        Button btnFoto = (Button) findViewById(R.id.form_btn_foto);
        btnFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent para abrir a câmera
                Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                //caminho de diretório onde irá salvar a foto (usando Current Millis como nome da foto)
                caminhoFoto = getExternalFilesDir(null) + "/" + System.currentTimeMillis() + ".jpg";

                //instancia o objeto File (passando o caminho como parâmetro)
                File arquivoFoto = new File(caminhoFoto);

                //A PARTIR DO ANDROID 7 É NECESSÁRIO USAR O FILE PROVIDER PARA LIDAR COM ARQUIVOS
                intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(FormActivity.this,BuildConfig.APPLICATION_ID + ".provider", arquivoFoto));
                startActivityForResult(intentCamera, 456);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if ((requestCode == 456) && (resultCode == Activity.RESULT_OK)) {
            helper.carregaImagem(caminhoFoto);
        }
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

                if(aluno.getId() != null){
                    //método do objeto AlunoDAo que edita no banco de dados os inputs
                    dao.edita(aluno);
                } else{
                    //método do objeto AlunoDAO que insere no banco de dados os inputs
                    dao.insere(aluno);
                }

                //fecha a conexão com o banco de dados
                dao.close();

                Toast.makeText(FormActivity.this,"Aluno " + aluno.getNome() + " salvo com sucesso.",Toast.LENGTH_SHORT).show();
                finish();//fecha activity
            break;//end case
        }
        return super.onOptionsItemSelected(item);
    }
}
