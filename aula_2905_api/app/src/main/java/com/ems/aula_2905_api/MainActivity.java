package com.ems.aula_2905_api;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    StringBuilder response = new StringBuilder();
    String jsonInputString = null;

    public class UserService extends AsyncTask<Void, Void, List<User>> {
        // construtor da classe UserService
        public UserService(User user) {
            // cria uma instância do objeto Gson
            Gson gson = new Gson();
            jsonInputString = gson.toJson(user);
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected List<User> doInBackground(Void... voids) {
            try {
                // instanciar os objetos para se conectar na internet
                // na variável url informamos o nosso endPoint
                URL url = new URL("http://emsapi.esy.es/api_android/user.php");
                // abrir a conexão com o servidor
                HttpURLConnection serv = (HttpURLConnection) url.openConnection();
                // configurar a requisição para o servidor
                serv.setRequestMethod("POST");
                serv.setRequestProperty("Content-Type", "application/json; utf-8");
                serv.setRequestProperty("Accept", "application/json");
                serv.setConnectTimeout(5000); // 5 segundos para se conectar
                serv.setReadTimeout(5000);
                serv.setDoInput(true); // permite que os dados sejam retornados
                serv.setDoOutput(true); // permite que os dados sejam lidos do servidor

                // enviando dados ao servidor
                try (OutputStream os = serv.getOutputStream()) {
                    // informando para requisição o tipo de dados a ser enviado
                    byte[] input = jsonInputString.getBytes("utf-8");
                    // enviar os dados
                    os.write(input, 0, input.length);
                }

                // ler a resposta do servidor
                try (BufferedReader br = new BufferedReader(
                        new InputStreamReader(serv.getInputStream(), "utf-8"))) {

                    // variável para receber as linhas enviadas pelo servidor
                    String responseLine = null;

                    // percorre o retorno do servidor e coloca na variável
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }
                    // mostra no Logcat o que foi enviado pelo servidor
                    System.out.println(response.toString());
                }

            } catch (IOException ex) {

            }

            // Criação de um tipo para receber os dados
            Type userType = new TypeToken<ArrayList<User>>() {
            }.getType();

            // cria uma lista para repassar os dados ao objeto (User)
            List<User> leituras = new Gson().fromJson(response.toString(), userType);
            return leituras;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        User user = new User();
        user.setType("login");
        user.setUser("edson.melo");
        user.setPassword("edson123");

        TextView resultado = findViewById(R.id.resultado);

        // chamar o serviço HTTP para API
        try {
            ArrayList<User> alUser = (ArrayList<User>) new UserService(user).execute().get();
            // percorrendo o objeto resultante para ler o conteúdo dele (Atributos)
            for (User u : alUser) {
                resultado.setText(
                        u.getId() + "\n" +
                                u.getName() + "\n" +
                                u.getPassword()
                );
            }
        } catch (ExecutionException e) {
        } catch (InterruptedException e) {
        }
    }
    //new UserService(user).execute(); // chama o serviço para a API
}

