package br.edu.ifsp.monitoramento.adapters;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import br.edu.ifsp.monitoramento.R;
import br.edu.ifsp.monitoramento.models.Alergia;

/**
 * @author Denis Magno
 */
public class FichaMedicaAlergiaListAdapter extends BaseAdapter {
    private final List<Alergia> alergias;
    private final Activity act;

    public FichaMedicaAlergiaListAdapter(List<Alergia> alergias, Activity act) {
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
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = act.getLayoutInflater().inflate(R.layout.item_ficha_medica_alergia, parent, false);

        Alergia alergia = this.alergias.get(position);

        TextView descricao = (TextView) view.findViewById(R.id.txtDescricaoAlergia);

        descricao.setText(alergia.getDescricao().toString());

        return view;
    }
}