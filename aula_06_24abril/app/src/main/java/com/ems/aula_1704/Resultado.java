package com.ems.aula_1704;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Resultado extends AppCompatActivity {
    TextView ra, nome, curso, raNovo, nomeNovo, cursoNovo;
    Button btVoltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado);
        ra = findViewById(R.id.textViewRa);
        nome = findViewById(R.id.textViewNome);
        curso = findViewById(R.id.textViewCurso);
        btVoltar = findViewById(R.id.buttonVoltar);

        raNovo = findViewById(R.id.textViewNovoRa);
        nomeNovo = findViewById(R.id.textViewNovoNome);
        cursoNovo = findViewById(R.id.textViewNovoCurso);

        // Recuperando o objeto
        ArrayList<Aluno> aluno = (ArrayList<Aluno>)
                getIntent().getSerializableExtra("objAluno");

        //  Atribuindo o valor (anterior a alteração == 0) do objeto aos campos
        ra.setText(aluno.get(0).getRa());
        nome.setText(aluno.get(0).getNome());
        curso.setText(aluno.get(0).getCurso());

        // atribuindo os valores alterados para o objeto que estão na
        // posição 1, ou seja, segundo elemento do ArrayList<Aluno>
        raNovo.setText(aluno.get(1).getRa());
        nomeNovo.setText(aluno.get(1).getNome());
        cursoNovo.setText(aluno.get(1).getCurso());

        btVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}