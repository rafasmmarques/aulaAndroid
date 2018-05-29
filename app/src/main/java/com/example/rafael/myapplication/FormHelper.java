package com.example.rafael.myapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;

import com.example.rafael.myapplication.modelo.Aluno;

public class FormHelper {

    //atributos dos inputs do formulários
    private final EditText campoNome;
    private final EditText campoEndereco;
    private final EditText campoTel;
    private final EditText campoSite;
    private final RatingBar campoNota;
    private final ImageView campoFoto;
    private Aluno aluno;

    //construtor dos inputs
    public FormHelper (FormActivity activity) {
         campoNome = (EditText)activity.findViewById(R.id.form_nome);
         campoEndereco = (EditText)activity.findViewById(R.id.form_endereco);
         campoTel = (EditText)activity.findViewById(R.id.form_tel);
         campoSite = (EditText)activity.findViewById(R.id.form_site);
         campoNota = (RatingBar)activity.findViewById(R.id.form_nota);
         campoFoto = (ImageView)activity.findViewById(R.id.form_foto);
         aluno = new Aluno();
    }

    //método que salva os dados dos inputs na classe Aluno
    public Aluno pegaAluno() {
        aluno.setNome(campoNome.getText().toString());
        aluno.setEndereco(campoEndereco.getText().toString());
        aluno.setTelefone(campoTel.getText().toString());
        aluno.setSite(campoSite.getText().toString());
        aluno.setNota(Double.valueOf(campoNota.getProgress()));
        aluno.setCaminhoFoto((String) campoFoto.getTag());
        return aluno;
    }

    public void preencheForm(Aluno aluno) {
        campoNome.setText(aluno.getNome());
        campoEndereco.setText(aluno.getEndereco());
        campoTel.setText(aluno.getTelefone());
        campoSite.setText(aluno.getSite());
        campoNota.setProgress(aluno.getNota().intValue());
        carregaImagem(aluno.getCaminhoFoto());
        this.aluno = aluno;
    }

    public void carregaImagem(String caminhoFoto) {
        if (caminhoFoto != null){
            Bitmap bitmap = BitmapFactory.decodeFile(caminhoFoto);
            Bitmap bitmapReduzido = bitmap.createScaledBitmap(bitmap, 150, 150, true);
            //Salva a foto no perfil do aluno
            campoFoto.setImageBitmap(bitmapReduzido);
            //Redimensiona a foto na tela
            campoFoto.setScaleType(ImageView.ScaleType.FIT_XY);
            //Seta uma tag para o caminho da foto
            campoFoto.setTag(caminhoFoto);
        }
    }
}
