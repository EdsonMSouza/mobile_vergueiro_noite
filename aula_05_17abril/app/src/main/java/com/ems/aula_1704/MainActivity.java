package com.ems.aula_1704;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    EditText ra, curso, nome;
    Button inserir, visualizar;

    // Lista de arrays para armazenar os objetos Aluno
    ArrayList<Aluno> alunos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // associar variáveis aos objetos da View
        ra = findViewById(R.id.editTextRa);
        nome = findViewById(R.id.editTextNome);
        curso = findViewById(R.id.editTextCurso);
        inserir = findViewById(R.id.buttonInserir);
        visualizar = findViewById(R.id.buttonVisualizar);

        // configurar o botão inserir
        inserir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // criando um novo objeto para cada clique no botão
                // e colocando o objeto dentro do ArrayList<Aluno>
                alunos.add(
                        new Aluno(
                                ra.getText().toString(),
                                nome.getText().toString(),
                                curso.getText().toString()
                        )
                );

                // após adicionar na lista, lismpa os campos e posiciona
                // o cursor no campo ra
                ra.setText("");
                nome.setText("");
                curso.setText("");

                // coloca o cursor no campo ra
                ra.requestFocus();
            }
        });

        visualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // passar os dados para outra View (activity_resultado.xml)
                // Intent(classe origem, classe destino.class)
                Intent itAlunos = new Intent(
                        MainActivity.this,
                        Resultado.class
                );

                // colocando a lista de objetos (Aluno) na Intent
                itAlunos.putExtra("listaAlunos", alunos);

                // chamar a outra Activity
                startActivity(itAlunos);
            }
        });
    }
}
