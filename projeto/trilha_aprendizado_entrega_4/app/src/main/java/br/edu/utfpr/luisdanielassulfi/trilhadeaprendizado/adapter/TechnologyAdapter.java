package br.edu.utfpr.luisdanielassulfi.trilhadeaprendizado.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import br.edu.utfpr.luisdanielassulfi.trilhadeaprendizado.model.Technology;

import br.edu.utfpr.luisdanielassulfi.trilhadeaprendizado.R;

public class TechnologyAdapter extends RecyclerView.Adapter<TechnologyAdapter.MyViewHolder> {

    private List<Technology> technologies;

    public TechnologyAdapter(List<Technology> technologies) {
        this.technologies = technologies;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View listItem = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.technology_adapter, viewGroup, false);

        return new MyViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        Technology technology = technologies.get(i);

        myViewHolder.textViewTechnologyName.setText(technology.getName());
        myViewHolder.textViewTechnologyTrail.setText(technology.getTrail());
    }

    @Override
    public int getItemCount() {
        return technologies.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTechnologyName, textViewTechnologyTrail;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewTechnologyName = itemView.findViewById(R.id.textViewTechnologyName);
            textViewTechnologyTrail = itemView.findViewById(R.id.textViewTrailName);
        }
    }
}
