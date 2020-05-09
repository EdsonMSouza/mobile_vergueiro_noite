package com.ems.aula_0805;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Editar extends AppCompatActivity {
    EditText ra, nome, curso;
    Button btSeguinte;

    // Variável para receber os dados anteriores e alterados do aluno
    ArrayList<Aluno> alunoDados = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar);

        ra = findViewById(R.id.editarTextRa);
        nome = findViewById(R.id.editarTextNome);
        curso = findViewById(R.id.editarTextCurso);
        btSeguinte = findViewById(R.id.buttonSeguinte);

        // Recuperando o objeto
        Aluno aluno = (Aluno) getIntent().getSerializableExtra("objAluno");

        // coloquei aqui os dados atuais do aluno antes de alterar
        alunoDados.add(aluno);

        //  Atribuindo o valor do objeto aos campos
        ra.setText(aluno.getRa());
        nome.setText(aluno.getNome());
        curso.setText(aluno.getCurso());

        btSeguinte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(
                        getApplicationContext(),
                        Resultado.class);

                Aluno aluno = new Aluno(
                        ra.getText().toString(),
                        nome.getText().toString(),
                        curso.getText().toString()
                );

                // colocando os novos dados no ArrayList<Aluno>
                alunoDados.add(aluno);

                // passa para a tela (View) seguinte o array com os dados anteriores e atuais do aluno
                intent.putExtra("objAluno", alunoDados);
                startActivity(intent);
            }
        });
    }
}