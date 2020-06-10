package com.ems.aula_2905_api;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
    String jsonInputString = null;
    EditText loginUsuario, loginSenha, txtNome, txtUsuario, txtSenha;
    Button btCadastrar, btLogin;
    TextView saida;

    // Cria uma variável para armazenar os dados internamente no Android
    SharedPreferences sharedPreferences;

    public class UserService extends AsyncTask<Void, Void, List<User>> {
        // construtor da classe UserService
        public UserService(User user) {
            // cria uma instância do objeto Gson
            Gson gson = new Gson();
            // preparando a string para ser enviada como JSON (Parseada)
            jsonInputString = gson.toJson(user);
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected List<User> doInBackground(Void... voids) {
            final StringBuilder response = new StringBuilder();
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
                    // enviar os dados (de fato)
                    os.write(input, 0, input.length);
                }

                // ler a resposta do servidor (Response)
                try (BufferedReader br = new BufferedReader(
                        new InputStreamReader(serv.getInputStream(), "utf-8"))) {

                    // variável para receber as linhas enviadas pelo servidor
                    String responseLine = null;
                    //response.delete(0, response.indexOf(""));

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
        sharedPreferences = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);

        // Eliminar os dados das Preferences
        // SharedPreferences.Editor myVars = sharedPreferences.edit();
        // myVars.clear();
        // myVars.commit();

        // Associando os campos de login
        loginUsuario = findViewById(R.id.txtUsuario);
        loginSenha = findViewById(R.id.txtSenha);
        saida = findViewById(R.id.txtSaida);
        btLogin = findViewById(R.id.btLogin);

        // Associando campos do cadastro
        txtNome = findViewById(R.id.editTextNome);
        txtUsuario = findViewById(R.id.editTextUsuario);
        txtSenha = findViewById(R.id.editTextSenha);
        btCadastrar = findViewById(R.id.btCadastrar);

        // vou usar os mesmos campos para mostrar os dados
        if(sharedPreferences.contains("user")) {
            loginUsuario.setText(sharedPreferences.getString("user", "") );
            loginSenha.setText(sharedPreferences.getString("password", "") );
        }

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // cria o objeto Usuario (user)
                User user = new User();
                // {"type":"login", "user":"tio.patinhas", "password":"123"}

                user.setType("login"); // tipo de operação na API
                user.setUser(loginUsuario.getText().toString());
                user.setPassword(loginSenha.getText().toString());

                // chamar o serviço HTTP para API
                try {
                    ArrayList<User> alUser = (ArrayList<User>)
                            new UserService(user).execute().get();

                    // percorrendo o objeto resultante para ler o conteúdo dele (Atributos)
                    for (User u : alUser) {
                        if(u.getType() == null){
                            saida.setText("Olá, " + u.getName());

                            // Cria as entradas para as preferências
                            SharedPreferences.Editor myVars = sharedPreferences.edit();
                            myVars.putString("user", u.getUser());
                            myVars.putString("password", u.getPassword());
                            myVars.commit();

                        } else{
                            if (u.getType().equals("request_incorreto")) {
                                saida.setText("Preencha os campos");
                            } else if (u.getType().equals("login_incorreto")) {
                                saida.setText("Usuário/senha incorretos");
                            }
                        }
                    }
                } catch (ExecutionException e) {
                } catch (InterruptedException e) {
                }
            }
        });

        btCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // cria o objeto Usuario (user)
                User user = new User();
                // {"type":"new", "name":"um nome qualquer", user":"tio.patinhas", "password":"123"}

                user.setType("new"); // tipo de operação na API
                user.setName(txtNome.getText().toString());
                user.setUser(txtUsuario.getText().toString());
                user.setPassword(txtSenha.getText().toString());

                // chamar o serviço HTTP para API
                try {
                    ArrayList<User> alUser = (ArrayList<User>)
                            new UserService(user).execute().get();
                    // percorrendo o objeto resultante para ler o conteúdo dele (Atributos)
                    for (User u : alUser) {
                        if(u.getType().equals("true")){
                            saida.setText("Olá, " + u.getName());
                        } else{
                            saida.setText("Erro ao criar o usuário");
                        }
                    }
                } catch (ExecutionException e) {
                } catch (InterruptedException e) {
                }
            }
        });
    }
}

