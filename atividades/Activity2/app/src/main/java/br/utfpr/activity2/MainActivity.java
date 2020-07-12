package br.utfpr.activity2;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import br.utfpr.activity2.enums.IntentConstants;
import br.utfpr.activity2.enums.ResultConstants;

public class MainActivity extends AppCompatActivity {

    private EditText editTextNome;
    private CheckBox checkBoxPossuiCarro;
    private RadioGroup radioGroupTipo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextNome = findViewById(R.id.editTextNome);
        checkBoxPossuiCarro = findViewById(R.id.checkBoxPossuiCarro);
        radioGroupTipo = findViewById(R.id.radioGroupTipo);
    }

    public void enviarPedidoNota(View view) {
        chamadaTela2(true);
    }

    public void enviarSemPedirNota(View view) {
        chamadaTela2(false);
    }

    private void chamadaTela2(boolean pedirNota) {
        Intent intent = new Intent(this, MostrarDadosActivity.class);

        String nome = editTextNome.getText().toString();
        if(!nome.isEmpty()) {
            intent.putExtra(IntentConstants.NOME.getLabel(), nome);
        }

        intent.putExtra(IntentConstants.POSSUI_CARRO.getLabel(), checkBoxPossuiCarro.isChecked());

        int id = radioGroupTipo.getCheckedRadioButtonId();
        if(id != -1) {
            intent.putExtra(IntentConstants.TIPO.getLabel(), id);
        }

        if(pedirNota) {
            intent.putExtra(IntentConstants.MODO.getLabel(), ResultConstants.PEDIR_NOTA.getValue());
            startActivityForResult(intent, ResultConstants.PEDIR_NOTA.getValue());
        } else {
            startActivity(intent);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == ResultConstants.PEDIR_NOTA.getValue() && resultCode == Activity.RESULT_OK) {
            Bundle bundle = data.getExtras();
            if(bundle != null) {
                int nota = bundle.getInt(IntentConstants.NOTA.getLabel());
                Toast.makeText(this, getString(R.string.nota) + nota, Toast.LENGTH_SHORT).show();
            }
        }
    }
}