package com.scopus.ifsp.projetofinalteste.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.scopus.ifsp.projetofinalteste.R;
import com.scopus.ifsp.projetofinalteste.model.Remedio;

import java.util.List;

/**
 * @author Denis Magno
 */
public class FichaMedicaRemedioListAdapter extends BaseAdapter {
    private final List<Remedio> remedios;
    private final Activity act;

    public FichaMedicaRemedioListAdapter(List<Remedio> remedios, Activity act) {
        this.remedios = remedios;
        this.act = act;
    }

    @Override
    public int getCount() {
        return this.remedios.size();
    }

    @Override
    public Object getItem(int position) {
        return this.remedios.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = act.getLayoutInflater().inflate(R.layout.item_ficha_medica_remedio, parent, false);

        Remedio remedio = this.remedios.get(position);

        TextView nome = view.findViewById(R.id.txtNomeMedicamento);
        TextView quantidade = view.findViewById(R.id.txtQtdeMedicamento);
        TextView frequencia = view.findViewById(R.id.txtFrequenciaMedicamento);

        nome.setText(remedio.getNome_remedio().toString());
        quantidade.setText(remedio.getQuantidade().toString());
        frequencia.setText(remedio.getFrequencia().toString());

        return view;
    }
}