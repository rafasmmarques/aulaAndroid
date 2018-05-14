package com.example.rafael.myapplication;

import android.widget.EditText;
import android.widget.RatingBar;

import com.example.rafael.myapplication.modelo.Aluno;

public class FormHelper {

    //atributos dos inputs do formulários
    private final EditText campoNome;
    private final EditText campoEndereco;
    private final EditText campoTel;
    private final EditText campoSite;
    private final RatingBar campoNota;

    //construtor dos inputs
    public FormHelper (FormActivity activity) {
         campoNome = (EditText)activity.findViewById(R.id.form_nome);
         campoEndereco = (EditText)activity.findViewById(R.id.form_endereco);
         campoTel = (EditText)activity.findViewById(R.id.form_tel);
         campoSite = (EditText)activity.findViewById(R.id.form_site);
         campoNota = (RatingBar) activity.findViewById(R.id.form_nota);
    }

    //método que salva os dados dos inputs na classe Aluno
    public Aluno pegaAluno() {
        Aluno aluno = new Aluno();
        aluno.setNome(campoNome.getText().toString());
        aluno.setEndereco(campoEndereco.getText().toString());
        aluno.setTelefone(campoTel.getText().toString());
        aluno.setSite(campoSite.getText().toString());
        aluno.setNota(Double.valueOf(campoNota.getProgress()));
        return aluno;
    }
}
