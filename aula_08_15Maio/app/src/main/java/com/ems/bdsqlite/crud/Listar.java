package com.ems.bdsqlite.crud;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.ems.bdsqlite.R;
import com.ems.bdsqlite.pojo.Aluno;
import com.ems.bdsqlite.utils.Banco;

import java.sql.PreparedStatement;
import java.util.ArrayList;

public class Listar extends AppCompatActivity {
    ListView listViewAlunos;
    ImageButton btVoltar;
    SQLiteDatabase db;
    ArrayList<Aluno> alunos = new ArrayList<>();
    ArrayAdapter<Aluno> adaptador;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar);

        // abrir o banco de dados
        db = openOrCreateDatabase(
                Banco.banco(),
                Context.MODE_PRIVATE,
                null);

        // Mostra um botão na Barra Superior para voltar
        getSupportActionBar().setTitle("Listagem dos Alunos");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        listViewAlunos = findViewById(R.id.lvAlunos);
        btVoltar = findViewById(R.id.btVoltar);

        // carregando os dados do banco de dados
        // limpar a lista de alunos (ArrayList<Aluno>)
        alunos.clear();

        // interagindo com o banco
        Cursor dados = db.rawQuery(
                "SELECT * FROM " + Banco.tabela() + " ORDER BY nome ASC",
                null);

        // percorrer os registros e inserir no ArrayList<Aluno>
        while (dados.moveToNext()) {
            alunos.add(new Aluno(
                    dados.getInt(0),
                    dados.getString(1),
                    dados.getString(2),
                    dados.getString(3))
            );
        }

        adaptador = new ArrayAdapter<>(
                getApplicationContext(),
                android.R.layout.simple_list_item_1,
                alunos);
        listViewAlunos.setAdapter(adaptador);

        listViewAlunos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            }
        });

        btVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    // Configura o botão (seta) na ActionBar (Barra Superior)
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}