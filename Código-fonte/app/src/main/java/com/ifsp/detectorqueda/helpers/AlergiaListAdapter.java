package com.ifsp.detectorqueda.helpers;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ifsp.detectorqueda.R;
import com.ifsp.detectorqueda.beans.Alergia;

import java.util.List;

public class AlergiaListAdapter extends BaseAdapter {
    private final List<Alergia> alergias;
    private final Activity act;

    public AlergiaListAdapter(List<Alergia> alergias, Activity act) {
        this.alergias = alergias;
        this.act = act;
    }

    @Override
    public int getCount() {
        return this.alergias.size();
    }

    @Override
    public Object getItem(int position) {
        return this.alergias.get(position);
    }

    @Override
    public long getItemId(int position) {
        return this.alergias.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = act.getLayoutInflater().inflate(R.layout.listview_alergias_adapter, parent, false);

        Alergia alergia = this.alergias.get(position);

        TextView descricao = (TextView) view.findViewById(R.id.txtDescricaoAlergia);

        descricao.setText(alergia.getDescricao().toString());

        return view;
    }
}
