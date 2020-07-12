package br.utfpr.activity1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MostrarDadosActivity extends AppCompatActivity {

    public static final String FRASE = "FRASE";

    private TextView textViewDados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_dados);

        setTitle(getString(R.string.dados_recebidos));

        textViewDados = findViewById(R.id.textViewDados);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if(bundle != null) {
            String frase = bundle.getString(FRASE);
            textViewDados.setText(frase);
        }
    }

    public void voltar(View view) {
        finish();
    }
}