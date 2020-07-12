package br.utfpr.activity3;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView textViewSite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public void ciclar(View view) {
        abreSite("http://www.utfpr.edu.br");
    }

    private void abreSite(String endereco) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(endereco));
        if(intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Toast.makeText(this, R.string.nenhum_aplicativo_atende, Toast.LENGTH_SHORT)
                .show();
        };
    }
}