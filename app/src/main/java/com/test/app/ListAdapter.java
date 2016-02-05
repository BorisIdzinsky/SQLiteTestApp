package com.test.app;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by boris on 03.02.16.
 *
 */
public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(Specialist specialist);
    }

    private ArrayList<Specialist> data;

    private OnItemClickListener listener;

    public ListAdapter(ArrayList<Specialist> data, OnItemClickListener listener) {
        this.data = data;
        this.listener = listener;
    }

    public ArrayList<Specialist> getData() {
        return data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Specialist specialist = data.get(position);
        holder.setValues(specialist);
        holder.showDialog(specialist, listener);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView nameSurname;
        private TextView yearOfBirth;
        private TextView position;
        private TextView city;


        public ViewHolder(View itemView) {
            super(itemView);
            initializeTextViews(itemView);
        }

        private void initializeTextViews(View itemView) {
            nameSurname = (TextView) itemView.findViewById(R.id.name_surname);
            yearOfBirth = (TextView) itemView.findViewById(R.id.year_of_birth);
            position = (TextView) itemView.findViewById(R.id.position);
            city = (TextView) itemView.findViewById(R.id.city);
        }

        private void setValues(Specialist specialist) {
            if (specialist != null) {
                nameSurname.setText(String.format("%s %s", specialist.getName(), specialist.getSurname()));
                yearOfBirth.setText(String.format("%d", specialist.getYob()));
                position.setText(specialist.getPosition());
                city.setText(specialist.getCity());
            }
        }

        private void showDialog(final Specialist specialist, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(specialist);
                }
            });
        }
    }
}
