package com.ems.aula_1704;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Resultado extends AppCompatActivity {
    TextView resultado;
    Button btVoltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado);
        resultado = findViewById(R.id.textViewResultado);
        btVoltar = findViewById(R.id.buttonVoltar);

        // pegando os valores (objetos -> lista de objetos) enviados
        // pela MainActivity
        ArrayList<Aluno> objAlunos = (ArrayList<Aluno>)
                getIntent().getSerializableExtra("listaAlunos");

        String tmp = new String(); // variável String vazia
        // percorrer a lista para pegar os objetos internos
        // loop foreach
        for (Aluno aluno : objAlunos) {
            tmp = tmp +
                    aluno.getRa() + ", " +
                    aluno.getNome() + " , " +
                    aluno.getCurso() + "\n";
        }
        resultado.setText(tmp);

        btVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}