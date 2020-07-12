package br.edu.utfpr.luisdanielassulfi.trilhadeaprendizado;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

        layoutManager = new LinearLayoutManager(this);

        recyclerViewTechnologies.setLayoutManager(layoutManager);
        recyclerViewTechnologies.setHasFixedSize(true);
        recyclerViewTechnologies.addItemDecoration(new DividerItemDecoration(this,
                LinearLayout.VERTICAL));

        populateList();

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

    public void showAboutActivity(View view) {
        Intent intent = new Intent(this, AboutActivity.class);
        startActivity(intent);
    }

    public void showAddActivity(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(IntentConstants.MODE.getValue(), ResultConstants.ADD_TECHNOLOGY.getValue());
        startActivityForResult(intent, ResultConstants.ADD_TECHNOLOGY.getValue());
    }

    private void populateList() {
        String[] technologyNames = getResources().getStringArray(R.array.technology_names);
        String[] technologyDescriptions = getResources().getStringArray(R.array.technology_descriptions);
        String[] technologyPreRequirements = getResources().getStringArray(R.array.technology_pre_requirements);
        int[] technologyTimes = getResources().getIntArray(R.array.technology_times);
        int[] mandatoryResources = getResources().getIntArray(R.array.technology_mandatory);
        boolean[] mandatoryTechnologies = new boolean[mandatoryResources.length];
        for(int i = 0; i < mandatoryResources.length; i++) {
            if(mandatoryResources[i] == 0) {
                mandatoryTechnologies[i] = false;
            } else {
                mandatoryTechnologies[i] = true;
            }
        }
        String[] technologyTrails = getResources().getStringArray(R.array.technology_trail);
        int[] technologyKnowledges = getResources().getIntArray(R.array.technology_knowledge);

        technologies = new ArrayList<>();

        for(int i = 0; i < technologyNames.length; i++) {
            Technology technology = new Technology(technologyNames[i], technologyDescriptions[i],
                    technologyPreRequirements[i], technologyTimes[i], mandatoryTechnologies[i],
                    technologyTrails[i], technologyKnowledges[i]);
            technologies.add(technology);
        }

        technologyAdapter = new TechnologyAdapter(technologies);
        recyclerViewTechnologies.setAdapter(technologyAdapter);
    }
}