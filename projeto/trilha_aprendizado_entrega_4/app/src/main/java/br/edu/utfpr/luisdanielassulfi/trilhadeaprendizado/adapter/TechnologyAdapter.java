package br.edu.utfpr.luisdanielassulfi.trilhadeaprendizado.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.ContextMenu;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.edu.utfpr.luisdanielassulfi.trilhadeaprendizado.model.Technology;

import br.edu.utfpr.luisdanielassulfi.trilhadeaprendizado.R;

public class TechnologyAdapter extends RecyclerView.Adapter<TechnologyAdapter.MyViewHolder> {

    private Context context;
    private List<Technology> technologies;

    private ClickAdapterListerner listerner;

    private int checkedPosition = 0;

    public TechnologyAdapter(Context context, List<Technology> technologies, ClickAdapterListerner listener) {
        this.context = context;
        this.technologies = technologies;
        this.listerner = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View listItem = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.technology_adapter, viewGroup, false);

        return new MyViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {
        Technology technology = technologies.get(position);

        myViewHolder.textViewTechnologyName.setText(technology.getName());
        myViewHolder.textViewTechnologyTrail.setText(technology.getTrail());

        myViewHolder.itemView.setActivated(true);

        applyClickEvents(myViewHolder, position);
    }

    private void applyClickEvents(MyViewHolder holder, final int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                listerner.onRowClicked(position);
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                listerner.onRowLongClicked(position);
                v.setBackgroundColor(Color.LTGRAY);
                v.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return technologies.size();
    }

    public Technology getSelectedItem() {
        return checkedPosition != -1 ? technologies.get(checkedPosition) : null;
    }

    public void removeData(Integer position) {
        technologies.remove(position);
        resetCheckedPostion();
    }

    private void resetCheckedPostion() {
        checkedPosition = -1;
    }

    public void toggleSelection(int position) {
        checkedPosition = position;
        notifyItemChanged(position);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder
            implements View.OnLongClickListener {
        TextView textViewTechnologyName, textViewTechnologyTrail;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewTechnologyName = itemView.findViewById(R.id.textViewTechnologyName);
            textViewTechnologyTrail = itemView.findViewById(R.id.textViewTrailName);

            itemView.setOnLongClickListener(this);
        }

        @Override
        public boolean onLongClick(View v) {
            return false;
        }
    }

    public void clearSelections() {
        resetCheckedPostion();
        notifyDataSetChanged();
    }

    public int getCheckedPosition() {
        return checkedPosition;
    }

    public void setCheckedPosition(int checkedPosition) {
        this.checkedPosition = checkedPosition;
    }

    public interface ClickAdapterListerner {
        void onRowClicked(int position);

        void onRowLongClicked(int position);
    }
}
