package br.utfpr.activity1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private EditText editTextFrase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextFrase = findViewById(R.id.editTextFrase);
    }

    public void enviar(View view) {
        Intent intent = new Intent(this, MostrarDadosActivity.class);
        intent.putExtra(MostrarDadosActivity.FRASE, editTextFrase.getText().toString());
        startActivity(intent);
    }
}