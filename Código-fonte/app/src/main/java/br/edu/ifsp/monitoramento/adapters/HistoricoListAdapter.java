package br.edu.ifsp.monitoramento.adapters;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

import br.edu.ifsp.monitoramento.R;
import br.edu.ifsp.monitoramento.models.Dado;

/**
 * @author Denis Magno
 */
public class HistoricoListAdapter extends BaseAdapter {
    private final List<Dado> historico;
    private final Activity act;

    public HistoricoListAdapter(List<Dado> historico, Activity act) {
        this.historico = historico;
        this.act = act;
    }

    @Override
    public int getCount() {
        return this.historico.size();
    }

    @Override
    public Object getItem(int position) {
        return this.historico.get(position);
    }

    @Override
    public long getItemId(int position) {
        return this.historico.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = act.getLayoutInflater().inflate(R.layout.item_historico, parent, false);

        Dado dados = this.historico.get(position);

        TextView txtData = view.findViewById(R.id.txtData);
        TextView txtHora = view.findViewById(R.id.txtHora);
        TextView txtStatus = view.findViewById(R.id.txtStatus);
        ImageView imgStatus = view.findViewById(R.id.imgStatus);

        SimpleDateFormat dateFormatD = new SimpleDateFormat("dd MMMM yyyy");
        txtData.setText(dateFormatD.format(dados.getDatetime()));

        SimpleDateFormat dateFormatH = new SimpleDateFormat("HH:mm");
        txtHora.setText(dateFormatH.format(dados.getDatetime()));

        txtStatus.setText(Dado.EnumStatus.getByValue(dados.getStatus()).toString());

        if (Dado.EnumStatus.getByValue(dados.getStatus()) == Dado.EnumStatus.ENTROU) {
            imgStatus.setImageResource(R.drawable.ic_status_in);
        } else if (Dado.EnumStatus.getByValue(dados.getStatus()) == Dado.EnumStatus.SAIU) {
            imgStatus.setImageResource(R.drawable.ic_status_out);
        }

        return view;
    }
}