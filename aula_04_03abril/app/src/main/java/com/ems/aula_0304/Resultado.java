package com.ems.aula_0304;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Resultado extends Activity {
    TextView nome, sobrenome;
    Button btVoltar;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_segunda_tela);

        nome = findViewById(R.id.textViewNome);
        sobrenome = findViewById(R.id.textViewSobrenome);
        btVoltar = findViewById(R.id.buttonVoltar);

        // pegar os valores enviados pela MainActivity
        Intent intent = getIntent();

        // pegar os valores enviados dentro da intent
        Bundle parametros = intent.getExtras();

        // atribuindo os valores recuperados aos objetos da View
        nome.setText(parametros.getString("nome"));
        sobrenome.setText(parametros.getString("sobrenome"));

        btVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // "mata" a atividade atual
                finish();
            }
        });
    }
}
