package com.ems.bdsqlite.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by Edson Melo de Souza on 18/10/2017.
 */
public class Mensagem {
    private Context _context;

    // Construtor
    public Mensagem(Context context) {
        this._context = context;
    }

    // Mostra a mensagem
    public int show(String titulo, final String texto, int icone) {
        AlertDialog.Builder msg = new AlertDialog.Builder(_context);
        msg.setIcon(icone);
        msg.setTitle(titulo);
        msg.setMessage(texto);

        msg.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //((AppCompatActivity) _context).finish();
            }
        });
        msg.show();
        return 1;
    }
}