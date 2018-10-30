package br.edu.ifsp.monitoramento.adapters;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import br.edu.ifsp.monitoramento.R;
import br.edu.ifsp.monitoramento.models.Medicamento;

/**
 * @author Denis Magno
 */
public class FichaMedicaMedicamentoListAdapter extends BaseAdapter {
    private final List<Medicamento> medicamentos;
    private final Activity act;

    public FichaMedicaMedicamentoListAdapter(List<Medicamento> medicamentos, Activity act) {
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
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = act.getLayoutInflater().inflate(R.layout.item_ficha_medica_medicamento, parent, false);

        Medicamento medicamento = this.medicamentos.get(position);

        TextView nome = view.findViewById(R.id.txtNomeMedicamento);
        TextView quantidade = view.findViewById(R.id.txtQtdeMedicamento);
        TextView frequencia = view.findViewById(R.id.txtFrequenciaMedicamento);

        nome.setText(medicamento.getNome().toString());
        quantidade.setText(medicamento.getQuantidade().toString());
        frequencia.setText(medicamento.getFrequencia().toString());

        return view;
    }
}