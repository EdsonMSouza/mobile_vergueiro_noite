package com.ems.aula_1704;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    EditText ra, curso, nome;
    Button inserir, visualizar;
    ListView lvAlunos;

    // Lista de arrays para armazenar os objetos Aluno
    ArrayList<Aluno> alunos = new ArrayList<>();

    // Estrutura de dados para armazenar os itens da lista
    ArrayAdapter<Aluno> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // associar variáveis aos objetos da View
        ra = findViewById(R.id.editarTextRa);
        nome = findViewById(R.id.editarTextNome);
        curso = findViewById(R.id.editarTextCurso);
        inserir = findViewById(R.id.buttonSeguinte);
        lvAlunos = findViewById(R.id.listViewAlunos);

        // criar o adaptador para a ListView
        adapter = new ArrayAdapter<>(
                MainActivity.this,
                android.R.layout.simple_list_item_1,
                alunos);

        // associa o adaptador à ListView
        lvAlunos.setAdapter(adapter);

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

                // após adicionar na lista, limpa os campos e posiciona
                // o cursor no campo ra
                ra.setText("");
                nome.setText("");
                curso.setText("");

                // coloca o cursor no campo ra
                ra.requestFocus();

                // avisar ao adapter (Adaptador) que os dados da
                // lista de objetos foi alterado
                adapter.notifyDataSetChanged();
            }
        });

        // configurando ações da lista
        lvAlunos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // pego o ID da posição do OBJETO Aluno e coloca em uma
                // variável do tipo Aluno
                Aluno aluno = (Aluno) lvAlunos.getItemAtPosition(position);

                // Exiba os dados nas TextView's da activity_resultado
                Intent intent = new Intent(
                        getApplicationContext(),
                        Editar.class);

                // Incluir o objeto na Intent (intent)
                intent.putExtra("objAluno", aluno);

                // Iniciar a outra Activity
                startActivity(intent);
            }
        });
    }
}
