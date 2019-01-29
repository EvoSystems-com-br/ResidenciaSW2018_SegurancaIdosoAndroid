package com.scopus.ifsp.projetofinalteste.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.scopus.ifsp.projetofinalteste.R;

import java.util.List;

/**
 * @author Denis Magno
 */
public class StringSpinnerAdapter extends ArrayAdapter {
    private final LayoutInflater mInflater;
    private final int mResource;
    private final List<String> itemList;

    public StringSpinnerAdapter(@NonNull Context context, int resource, List<String> itemList) {
        super(context, resource, itemList);
        this.mInflater = LayoutInflater.from(context);
        this.mResource = resource;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return createItemView(position, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return createItemView(position, parent);
    }

    @Override
    public boolean isEnabled(int position) {
        return position != 0;
    }

    private View createItemView(int position, ViewGroup parent) {
        final View view = mInflater.inflate(mResource, parent, false);

        TextView txtGrupoSanguineo = view.findViewById(R.id.txtGrupoSanguineo);

        if (position == 0) {
            txtGrupoSanguineo.setTextColor(this.getContext().getColor(R.color.primaryHintTextColor));
        } else {
            txtGrupoSanguineo.setTextColor(this.getContext().getColor(R.color.primaryTextColor));
        }

        txtGrupoSanguineo.setText(itemList.get(position));

        return view;
    }
}