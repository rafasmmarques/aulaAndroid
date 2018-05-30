package com.example.rafael.myapplication.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rafael.myapplication.MainActivity;
import com.example.rafael.myapplication.R;
import com.example.rafael.myapplication.modelo.Aluno;

import org.w3c.dom.Text;

import java.util.List;

public class AlunosAdapter extends BaseAdapter{

    //constantes
    private final List<Aluno> alunos;
    private final Context context;

    public AlunosAdapter(Context context, List<Aluno> alunos) {
        this.context = context;
        this.alunos = alunos;
    }

    @Override
    public int getCount() {
        //tamanho da lista
        return alunos.size();
    }

    @Override
    public Object getItem(int position) {
        //item da posição 'x' da lista
        return alunos.get(position);
    }

    @Override
    public long getItemId(int position) {
        //id do item da posição 'x' da lista
        return alunos.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Aluno aluno = alunos.get(position);

        //"Infla"/Desenha a lista
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_item, parent, false);

        //campo de nome dentro do item da lista
        TextView campoNome = (TextView)view.findViewById(R.id.item_nome);
        campoNome.setText(aluno.getNome());

        //campo de telefone dentro do item da lista
        TextView campoTelefone = (TextView) view.findViewById(R.id.item_tel);
        campoTelefone.setText(aluno.getTelefone());

        //campo da foto detro do item da lista
        ImageView campoFoto = (ImageView) view.findViewById(R.id.item_foto);

        //caminho de onde a foto está na memória
        String caminhoFoto = aluno.getCaminhoFoto();

        if (caminhoFoto != null){
            Bitmap bitmap = BitmapFactory.decodeFile(caminhoFoto);
            Bitmap bitmapReduzido = bitmap.createScaledBitmap(bitmap, 150, 150, true);

            //Salva a foto no perfil do aluno
            campoFoto.setImageBitmap(bitmapReduzido);

            //Redimensiona a foto na tela
            campoFoto.setScaleType(ImageView.ScaleType.FIT_XY);
        }
        return view;
    }
}
