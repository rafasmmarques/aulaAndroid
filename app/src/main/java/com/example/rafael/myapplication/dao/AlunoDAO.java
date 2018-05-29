package com.example.rafael.myapplication.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import com.example.rafael.myapplication.modelo.Aluno;

import java.util.ArrayList;
import java.util.List;

public class AlunoDAO extends SQLiteOpenHelper{

    //contrutor da DAO
    public AlunoDAO(Context context){
        super(context, "Agenda", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //SQL para criar a tabela se ela nao existir
        String sql = "CREATE TABLE Alunos(id INTEGER PRIMARY KEY, nome TEXT NOT NULL, endereco TEXT, telefone TEXT, site TEXT, nota DOUBLE, foto TEXT);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        //SQL para deletar a tabela
        switch (oldVersion){
            case 1:
                String sql = "ALTER TABLE Alunos ADD COLUMN foto TEXT;";
                db.execSQL(sql);
        }
    }

    @NonNull
    private ContentValues getDadosAluno(Aluno aluno) {
        //instância do objeto ContentValues
        ContentValues dados = new ContentValues();

        //Guarda os valores dos inputs para serem salvos
        dados.put("nome", aluno.getNome());
        dados.put("endereco", aluno.getEndereco());
        dados.put("telefone", aluno.getTelefone());
        dados.put("site", aluno.getSite());
        dados.put("nota", aluno.getNota());
        dados.put("foto", aluno.getCaminhoFoto());
        return dados;
    }

    public List<Aluno> buscaAlunos() {
        //instância do array de Alunos
        List<Aluno> alunos = new ArrayList<Aluno>();

        //SQL para buscar todos os alunos da tabela
        String sql = "SELECT * FROM Alunos;";

        //método do SQLite que lê os dados na tabela
        SQLiteDatabase db = getReadableDatabase();

        //Cursor que aponta para os resultados da busca
        Cursor c = db.rawQuery(sql,null);

        //Move o cursor para a próxima linha da busca
        while(c.moveToNext()){
            //instância de um novo objeto Aluno
            Aluno aluno = new Aluno();

            //Seta os campos da tabela que o cursor leu
            aluno.setId(c.getLong(c.getColumnIndex("id")));
            aluno.setNome(c.getString(c.getColumnIndex("nome")));
            aluno.setEndereco(c.getString(c.getColumnIndex("endereco")));
            aluno.setTelefone(c.getString(c.getColumnIndex("telefone")));
            aluno.setSite(c.getString(c.getColumnIndex("site")));
            aluno.setNota(c.getDouble(c.getColumnIndex("nota")));
            aluno.setCaminhoFoto(c.getString(c.getColumnIndex("foto")));

            //Adiciona no objeto aluno os campos lidos
            alunos.add(aluno);
        }//end while
        c.close();//fecha o cursor

        return alunos;
    }

    //método que insere os dados dos inputs na tabela
    public void insere(Aluno aluno) {
        //método do SQLite que escreve os dados na tabela (Previne SQL Injection)
        SQLiteDatabase db = getWritableDatabase();

        //Pega os dados do aluno
        ContentValues dados = getDadosAluno(aluno);

        //insere de fato os valores, salvos anteriormente, na tabela
        db.insert("Alunos", null, dados);
    }

    public void deleta(Aluno aluno) {
        //método do SQLite que escreve os dados na tabela (Previne SQL Injection)
        SQLiteDatabase db = getWritableDatabase();

        //Array de strings que serão os parâmetros para deleção
        String [] params = {aluno.getId().toString()};

        //deleta de fato o aluno selecionado da tabela
        db.delete("Alunos", "id = ?", params);
    }

    public void edita(Aluno aluno) {
        //método do SQLite que escreve os dados na tabela (Previne SQL Injection)
        SQLiteDatabase db = getWritableDatabase();

        //instância do objeto ContentValues
        ContentValues dados = getDadosAluno(aluno);

        //Array de strngs que serão os parâmetros para update
        String [] params = {aluno.getId().toString()};

        db.update("Alunos", dados, "id = ?", params);
    }
}
