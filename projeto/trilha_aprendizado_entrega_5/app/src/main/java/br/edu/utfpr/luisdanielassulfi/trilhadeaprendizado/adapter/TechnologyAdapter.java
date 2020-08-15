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

    private ClickAdapterListener listener;

    private int checkedPosition = 0;

    public TechnologyAdapter(Context context, List<Technology> technologies,
                             ClickAdapterListener listener) {
        this.context = context;
        this.technologies = technologies;
        this.listener = listener;
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
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onRowClicked(position);
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                listener.onRowLongClicked(position);
                v.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);

                if(checkedPosition == position) {
                    v.setBackgroundColor(Color.LTGRAY);
                } else {
                    v.setBackgroundColor(Color.TRANSPARENT);
                }

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

    public void toggleSelection(int position) {
        checkedPosition = position;
        notifyItemChanged(position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
            implements View.OnLongClickListener{
        TextView textViewTechnologyName, textViewTechnologyTrail;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewTechnologyName = itemView.findViewById(R.id.textViewTechnologyName);
            textViewTechnologyTrail = itemView.findViewById(R.id.textViewTrailName);

            itemView.setOnLongClickListener(this);
        }

        @Override
        public boolean onLongClick(View v) {
            listener.onRowLongClicked(getAdapterPosition());
            v.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);
            return true;
        }

        @Override
        public String toString() {
            return super.toString();
        }
    }

    public int getCheckedPosition() {
        return checkedPosition;
    }

    public void setCheckedPosition(int checkedPosition) {
        this.checkedPosition = checkedPosition;
    }

    public interface ClickAdapterListener {
        void onRowClicked(int position);

        void onRowLongClicked(int position);
    }

    public void setTechnologies(List<Technology> technologies) {
        this.technologies = technologies;
    }

    public Technology getItemAtPosition(int position) {
        if(position < technologies.size()) {
            return technologies.get(position);
        } else {
            return null;
        }
    }
}
