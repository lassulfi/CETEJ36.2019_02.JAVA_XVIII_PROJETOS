package br.edu.utfpr.luisdanielassulfi.menuopcoes;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private ConstraintLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        layout = findViewById(R.id.mainLayout);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_opcoes, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuItemSalvar:
                showMessage(getString(R.string.salvar) + getString(R.string.foi_clicado));
                return true;
            case R.id.menuItemExcluir:
                showMessage(getString(R.string.excluir) + getString(R.string.foi_clicado));
                return true;
            case R.id.menuItemBlue:
                item.setChecked(true);
                layout.setBackgroundColor(Color.BLUE);
                return true;
            case R.id.menuItemRed:
                item.setChecked(true);
                layout.setBackgroundColor(Color.RED);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onMenuItemUseDataClick(MenuItem menuItem) {
        menuItem.setChecked(!menuItem.isChecked());
    }

    private void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}