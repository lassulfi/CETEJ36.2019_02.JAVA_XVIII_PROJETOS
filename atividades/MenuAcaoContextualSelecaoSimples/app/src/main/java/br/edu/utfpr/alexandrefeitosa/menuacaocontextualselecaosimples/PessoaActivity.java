package br.edu.utfpr.alexandrefeitosa.menuacaocontextualselecaosimples;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class PessoaActivity extends AppCompatActivity {

    public static final String MODO    = "MODO";
    public static final String NOME    = "NOME";
    public static final int    NOVO    = 1;
    public static final int    ALTERAR = 2;

    private EditText editTextNome;
    private int    modo;
    private String nomeOriginal;

    public static void novaPessoa(AppCompatActivity activity){

        Intent intent = new Intent(activity, PessoaActivity.class);

        intent.putExtra(MODO, NOVO);

        activity.startActivityForResult(intent, NOVO);
    }

    public static void alterarPessoa(AppCompatActivity activity, Pessoa pessoa){

        Intent intent = new Intent(activity, PessoaActivity.class);

        intent.putExtra(MODO, ALTERAR);
        intent.putExtra(NOME, pessoa.getNome());

        activity.startActivityForResult(intent, ALTERAR);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pessoa);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        editTextNome = findViewById(R.id.editTextNome);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null){

            modo = bundle.getInt(MODO, NOVO);

            if (modo == NOVO){
                setTitle(getString(R.string.nova_pessoa));
            }else{
                nomeOriginal = bundle.getString(NOME);

                editTextNome.setText(nomeOriginal);

                setTitle(getString(R.string.alterar_pessoa));
            }
        }
    }

    private void salvar(){

        String nome = editTextNome.getText().toString();

        if (nome == null || nome.trim().isEmpty()){
            Toast.makeText(this,
                    R.string.erro_nome_vazio,
                    Toast.LENGTH_SHORT).show();

            editTextNome.requestFocus();
            return;
        }

        if (modo == ALTERAR && nome.equals(nomeOriginal)){
            cancelar();
            return;
        }

        Intent intent = new Intent();
        intent.putExtra(NOME, nome);

        setResult(Activity.RESULT_OK, intent);

        finish();
    }

    private void cancelar(){
        setResult(Activity.RESULT_CANCELED);
        finish();
    }

    @Override
    public void onBackPressed() {
        cancelar();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.pessoa_opcoes, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){

            case R.id.menuItemSalvar:
                salvar();
                return true;

            case android.R.id.home:
            case R.id.menuItemCancelar:
                cancelar();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
