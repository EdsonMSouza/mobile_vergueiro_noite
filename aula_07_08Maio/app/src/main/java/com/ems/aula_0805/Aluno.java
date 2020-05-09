package com.ems.aula_0805;

import java.io.Serializable;

public class Aluno implements Serializable {
    private String ra;
    private String nome;
    private String curso;

    // Construtor vazio - permite instanciar o objeto sem a necessidade de informar os valores na sua criação
    public Aluno() {
    }

    // Método construtor inicializado com os valores - devem ser atribuídos na criação do objeto
    public Aluno(String ra, String nome, String curso) {
        this.ra = ra;
        this.nome = nome;
        this.curso = curso;
    }

    // getter e setters

    public String getRa() {
        return ra;
    }

    public void setRa(String ra) {
        this.ra = ra;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    @Override
    public String toString() {
        return nome;
    }

    /**
     * Método que retorna todos os dados do objeto, mas no formato String e não como um objeto
     *
     * @return String os dados do aluno
     */
    public String getDados() {
        return ra + ", " + nome + " ," + curso;
    }
}
