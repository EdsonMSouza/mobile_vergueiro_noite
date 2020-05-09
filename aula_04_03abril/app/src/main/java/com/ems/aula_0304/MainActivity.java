package com.ems.aula_0304;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    // declarar variáveis para receber os objetos da View
    EditText nome, sobrenome;
    Button btOk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // atribuir os objetos da View para as variáveis locais
        nome = findViewById(R.id.editTextNome);
        sobrenome = findViewById(R.id.editTextSobrenome);
        btOk = findViewById(R.id.buttonOk);

        // capturando os dados quando o botão for clicado
        btOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // captura o valor digitado nas variáveis
                String txtNome = nome.getText().toString();
                String txtSobrenome = sobrenome.getText().toString();

                // montar um pacote (Bundle)
                Bundle pacote = new Bundle();

                // criar um objeto para enviar os dados coletados (txt*)
                // para a outra View (Tela)
                // Intent(Atividade atual, Atividade alvo (segunda_tela)
                Intent intent = new Intent(getApplicationContext(), Resultado.class);

                // colocar os dados dentro do pacote
                pacote.putString("nome", txtNome);
                pacote.putString("sobrenome", txtSobrenome);

                // colocar o pacote dentro da Intent
                intent.putExtras(pacote);

                // iniciar a nova atividade (Activity - Resultado)
                startActivity(intent);

                // mostrando os dados de uma forma legal 1
                //Toast.makeText(MainActivity.this,
                //        txtNome + " " + txtSobrenome,
                //        Toast.LENGTH_LONG).show();

                // mostrando os dados de outra forma "legal tb"
                // usando os Logs do Android
                //Log("nome da tag para o log", "valor a ser exibido");
                Log.i("nome", txtNome);
                Log.i("sobrenome", txtSobrenome);
            }
        });
    }
}
