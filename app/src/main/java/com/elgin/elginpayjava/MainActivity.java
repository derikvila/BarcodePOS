package com.elgin.elginpayjava;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

import com.elgin.e1.Scanner.*;


public class MainActivity extends AppCompatActivity {
    static Context context;
    Button buttonInitRead;
    Button buttonCleanField;
    EditText editTextCodeBar1;
    EditText editTextCodeBar2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Tela do aplicativo

        buttonInitRead = findViewById(R.id.buttonInitRead);
        buttonCleanField = findViewById(R.id.buttonCleanField);
        editTextCodeBar1 = findViewById(R.id.editTextCodeBar1);
        editTextCodeBar2 = findViewById(R.id.editTextCodeBar2);


        buttonCleanField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editTextCodeBar1.setText("");
                editTextCodeBar2.setText("");
            }
        });
        buttonInitRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lerCodigo(view);
            }
        });
    }

    // Essa funcao pode ser chamada em onCreate() ou atraves de um botao, por exemplo
    public void lerCodigo(View view) {
        editTextCodeBar1.setText("");
        editTextCodeBar2.setText("");
        /*
         * Intent in e' chamada com requestCode #1;
         * se bem-sucedida, a Intent retorna resultCode #2
         */
        Intent in = Scanner.getScanner(MainActivity.this);
        startActivityForResult(in, 1);

    }

    // Sobrescreve onActivityResult(), para manipulacao dos dados retornados...
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) { // Intent Scanner
            if (resultCode == 2) {
                String[] result = data.getStringArrayExtra("result");

                CharSequence cs;
                if (result[0].equals("1")) {
                    cs = "Codigo: " + result[1] + "\nTipo: " + result[3];
                    editTextCodeBar1.setText(result[1]);
                    editTextCodeBar2.setText(result[3]);
                } else {
                    cs = "Erro # " + result[0] + " na leitura do código.";
                    Toast.makeText(MainActivity.this, cs, Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}
