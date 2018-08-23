package com.ifsp.detectorqueda.helpers;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ifsp.detectorqueda.R;
import com.ifsp.detectorqueda.beans.Medicamento;

import java.util.List;

public class MedicamentoListAdapter extends BaseAdapter {
    private final List<Medicamento> medicamentos;
    private final Activity act;

    public MedicamentoListAdapter(List<Medicamento> medicamentos, Activity act) {
        this.medicamentos = medicamentos;
        this.act = act;
    }

    @Override
    public int getCount() {
        return this.medicamentos.size();
    }

    @Override
    public Object getItem(int position) {
        return this.medicamentos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return this.medicamentos.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = act.getLayoutInflater().inflate(R.layout.listview_medicamentos_adapter, parent, false);

        Medicamento medicamento = this.medicamentos.get(position);

        TextView nome = (TextView) view.findViewById(R.id.txtNomeMedicamento);

        nome.setText(medicamento.getNome().toString());

        return view;
    }
}
