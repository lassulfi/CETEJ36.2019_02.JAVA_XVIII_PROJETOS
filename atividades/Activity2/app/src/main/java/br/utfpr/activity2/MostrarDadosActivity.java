package br.utfpr.activity2;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import br.utfpr.activity2.enums.IntentConstants;
import br.utfpr.activity2.enums.ResultConstants;

public class MostrarDadosActivity extends AppCompatActivity {

    private TextView textViewDados;
    private int modo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_dados);

        textViewDados = findViewById(R.id.textViewDados);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle != null) {
            String nome = bundle.getString(IntentConstants.NOME.getLabel(),
                    getString(R.string.nao_cadastrado));
            boolean possuiCarro = bundle.getBoolean(IntentConstants.POSSUI_CARRO.getLabel());
            int id = bundle.getInt(IntentConstants.TIPO.getLabel(), -1);

            String saida = getString(R.string.nome) + ": " + nome + "\n";
            saida += getString(R.string.possui_carro) + "? ";
            saida += (possuiCarro ? getString(R.string.sim) : getString(R.string.nao))+ "\n";
            switch (id) {
                case R.id.radioButtonAluno:
                    saida += getString(R.string.aluno);
                    break;
                case R.id.radioButtonProfessor:
                    saida += getString(R.string.professor);
                    break;
                default:
                    saida += getString(R.string.nenhum_tipo_escolhido);
            }
            textViewDados.setText(saida);

            modo = bundle.getInt(IntentConstants.MODO.getLabel(), 0);
        }

        setTitle(getString(R.string.dados_cadastrados));
    }

    private void finalizar() {
        if(modo == ResultConstants.PEDIR_NOTA.getValue()) {
            Intent intent = new Intent();
            intent.putExtra(IntentConstants.NOTA.getLabel(), 1000);
            setResult(Activity.RESULT_OK, intent);
        }

        finish();
    }

    @Override
    public void onBackPressed() {
        finalizar();
    }
}