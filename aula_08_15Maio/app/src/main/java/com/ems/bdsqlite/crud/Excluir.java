package com.ems.bdsqlite.crud;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.ems.bdsqlite.R;

public class Excluir extends AppCompatActivity {

    TextView id, ra, nome, curso;
    ImageButton btConfirmaExcusao, btVoltar;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excluir);

        // Mostra um botão na Barra Superior para voltar
        getSupportActionBar().setTitle("Exclusão de Aluno");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        id = findViewById(R.id.textViewId_excluir);
        ra = findViewById(R.id.textViewRa_excluir);
        nome = findViewById(R.id.textViewNome_excluir);
        curso = findViewById(R.id.textViewCurso_excluir);
        btConfirmaExcusao = findViewById(R.id.btExcluir);
        btVoltar = findViewById(R.id.btVoltar);

        btConfirmaExcusao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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