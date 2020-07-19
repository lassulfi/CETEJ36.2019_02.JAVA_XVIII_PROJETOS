package br.edu.utfpr.alexandrefeitosa.menuacaocontextualselecaosimples;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class PrincipalActivity extends AppCompatActivity {

    private ListView             listViewPessoas;
    private ArrayAdapter<Pessoa> listaAdapter;
    private ArrayList<Pessoa>    listaPessoas;

    private ActionMode actionMode;
    private int        posicaoSelecionada = -1;
    private View       viewSelecionada;

    private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {

            MenuInflater inflate = mode.getMenuInflater();
            inflate.inflate(R.menu.principal_item_selecionado, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {

            switch(item.getItemId()){
                case R.id.menuItemAlterar:
                    alterarPessoa();
                    mode.finish();
                    return true;

                case R.id.menuItemExcluir:
                    excluirPessoa();
                    mode.finish();
                    return true;

                default:
                    return false;
            }
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {

            if (viewSelecionada != null){
                viewSelecionada.setBackgroundColor(Color.TRANSPARENT);
            }

            actionMode         = null;
            viewSelecionada    = null;

            listViewPessoas.setEnabled(true);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        listViewPessoas = findViewById(R.id.listViewPessoas);

        listViewPessoas.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent,
                                            View view,
                                            int position,
                                            long id) {

                        posicaoSelecionada = position;
                        alterarPessoa();
                    }
                });

        listViewPessoas.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        listViewPessoas.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {

                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent,
                                                   View view,
                                                   int position,
                                                   long id) {

                        if (actionMode != null){
                            return false;
                        }

                        posicaoSelecionada = position;

                        view.setBackgroundColor(Color.LTGRAY);

                        viewSelecionada = view;

                        listViewPessoas.setEnabled(false);

                        actionMode = startSupportActionMode(mActionModeCallback);

                        return true;
                    }
                });

        popularLista();
    }

    private void popularLista(){

        listaPessoas = new ArrayList<>();

        listaAdapter = new ArrayAdapter<>(this,
                                          android.R.layout.simple_list_item_1,
                                          listaPessoas);

        listViewPessoas.setAdapter(listaAdapter);
    }

    private void excluirPessoa(){

        listaPessoas.remove(posicaoSelecionada);
        listaAdapter.notifyDataSetChanged();
    }

    private void alterarPessoa(){

        Pessoa pessoa = listaPessoas.get(posicaoSelecionada);

        PessoaActivity.alterarPessoa(this, pessoa);
    }

    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode,
                                    Intent data) {

        if (resultCode == Activity.RESULT_OK){

            Bundle bundle = data.getExtras();

            String nome = bundle.getString(PessoaActivity.NOME);

            if (requestCode == PessoaActivity.ALTERAR){

                Pessoa pessoa = listaPessoas.get(posicaoSelecionada);
                pessoa.setNome(nome);

                posicaoSelecionada = -1;

            }else{
                listaPessoas.add(new Pessoa(nome));
            }

            listaAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.principal_opcoes, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){

            case R.id.menuItemNovo:
                PessoaActivity.novaPessoa(this);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
