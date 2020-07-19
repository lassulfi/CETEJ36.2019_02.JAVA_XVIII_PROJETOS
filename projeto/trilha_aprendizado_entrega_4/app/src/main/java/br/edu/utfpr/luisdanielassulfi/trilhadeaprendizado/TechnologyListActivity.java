package br.edu.utfpr.luisdanielassulfi.trilhadeaprendizado;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

import br.edu.utfpr.luisdanielassulfi.trilhadeaprendizado.adapter.TechnologyAdapter;
import br.edu.utfpr.luisdanielassulfi.trilhadeaprendizado.enums.IntentConstants;
import br.edu.utfpr.luisdanielassulfi.trilhadeaprendizado.enums.ResultConstants;
import br.edu.utfpr.luisdanielassulfi.trilhadeaprendizado.listerners.RecyclerItemClickListener;
import br.edu.utfpr.luisdanielassulfi.trilhadeaprendizado.model.Technology;

public class TechnologyListActivity extends AppCompatActivity {

    private RecyclerView recyclerViewTechnologies;
    private RecyclerView.LayoutManager layoutManager;
    private TechnologyAdapter technologyAdapter;

    private ArrayList<Technology> technologies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_technology_list);

        recyclerViewTechnologies = findViewById(R.id.recyclerViewTechnologies);

        technologies = new ArrayList<>();
        technologyAdapter = new TechnologyAdapter(technologies);

        layoutManager = new LinearLayoutManager(this);

        recyclerViewTechnologies.setAdapter(technologyAdapter);
        recyclerViewTechnologies.setLayoutManager(layoutManager);
        recyclerViewTechnologies.setHasFixedSize(true);
        recyclerViewTechnologies.addItemDecoration(new DividerItemDecoration(this,
                LinearLayout.VERTICAL));

        recyclerViewTechnologies.addOnItemTouchListener(new RecyclerItemClickListener(
                getApplicationContext(), recyclerViewTechnologies,
                new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Technology technology = technologies.get(position);
                Toast.makeText(getApplicationContext(),
                        technology.getName() + getString(R.string.recieved_a_click),
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongItemClick(View view, int position) {
                Technology technology = technologies.get(position);
                Toast.makeText(getApplicationContext(),
                        technology.getName() + getString(R.string.recieved_a_long_click),
                        Toast.LENGTH_SHORT).show();
            }
        }));

    }

    public void showAboutActivity(MenuItem menuItem) {
        Intent intent = new Intent(this, AboutActivity.class);
        startActivity(intent);
    }

    public void showAddActivity(MenuItem menuItem) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(IntentConstants.MODE.getValue(), ResultConstants.ADD_TECHNOLOGY.getValue());
        startActivityForResult(intent, ResultConstants.ADD_TECHNOLOGY.getValue());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == ResultConstants.ADD_TECHNOLOGY.getValue()
                && resultCode == Activity.RESULT_OK) {
            Bundle bundle = data.getExtras();
            if(bundle != null) {
                String message = bundle.getString(IntentConstants.ADD_TECHNOLOGY_MESSAGE.getValue());

                Technology technology = (Technology) bundle
                        .getParcelable(IntentConstants.NEW_TECHNOLOGY.getValue());
                technologies.add(technology);
                technologyAdapter.notifyDataSetChanged();

                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.technology_list_menu, menu);
        return true;
    }
}