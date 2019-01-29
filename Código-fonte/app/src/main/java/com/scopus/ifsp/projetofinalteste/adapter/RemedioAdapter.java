package com.scopus.ifsp.projetofinalteste.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.scopus.ifsp.projetofinalteste.App;
import com.scopus.ifsp.projetofinalteste.R;
import com.scopus.ifsp.projetofinalteste.activity.InfoRemedioActivity;
import com.scopus.ifsp.projetofinalteste.model.DaoSession;
import com.scopus.ifsp.projetofinalteste.model.Remedio;
import com.scopus.ifsp.projetofinalteste.model.RemedioDao;

import java.util.List;

public class RemedioAdapter extends RecyclerView.Adapter<RemedioHolder> {

    private final List<Remedio> mRemedios;
    private Activity act;

    public RemedioAdapter(Activity act, List<Remedio> remedios){
        mRemedios = remedios;
        this.act = act;
    }

    @NonNull
    @Override
    public RemedioHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        return new RemedioHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_remedio_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RemedioHolder holder, int posicao){
        final int position = posicao;
        final Remedio remedio = mRemedios.get(position);

        holder.mNomeRemedio.setText(mRemedios.get(position).getNome_remedio());
        holder.quantidade.setText(mRemedios.get(position).getQuantidade());
        holder.frequencia.setText(mRemedios.get(position).getFrequencia());
        holder.fundo.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                Activity activity = getActivity(v);
                Intent intent = new Intent(v.getContext(),InfoRemedioActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                intent.putExtra("remedio", remedio);
                assert activity != null;
                activity.startActivity(intent);
            }
        });

        holder.setReminderTitle(mRemedios.get(position).getNome_remedio());

        holder.mDeletImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final View view = v;
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("Confirmação")
                        .setMessage("Tem certeza que deseja excluir este Remedio?" + mRemedios.get(position).toString())
                        .setPositiveButton("Excluir", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Remedio remedio = mRemedios.get(position);

                                //Salvando Dados
                                DaoSession daoSession = ((App) act.getApplication()).getDaoSession();

                                RemedioDao remedioDao = daoSession.getRemedioDao();

                                remedioDao.delete(remedio);

                                boolean sucesso = true;
                                if (sucesso) {
                                    removerItem(remedio);
                                    Activity activity = getActivity(v);
                                    Intent removeIntent = activity.getIntent();
                                    removeIntent.putExtra("id", remedio.getId());

                                    Snackbar.make(view, "Excluído " + remedio.getId(), Snackbar.LENGTH_LONG)
                                            .setAction("Action", null).show();
                                } else {
                                    Snackbar.make(view, "Erro ao exluir o item", Snackbar.LENGTH_LONG)
                                            .setAction("Action", null).show();
                                }
                            }
                        })
                        .setNegativeButton("Cancelar", null)
                        .create()
                        .show();
            }
        });
    }


    @Override
    public int getItemCount(){
        return mRemedios != null ? mRemedios.size() : 0;
    }

    private Activity getActivity(View view){
        Context context = view.getContext();
        while (context instanceof ContextWrapper){
            if(context instanceof Activity){
                return (Activity)context;
            }

            context = ((ContextWrapper)context).getBaseContext();
        }
        return null;
    }

    public void insertItem(@NonNull Remedio remedios){

        mRemedios.add(remedios);
        notifyItemInserted(getItemCount());
    }

    public void atualizarRemedios(Remedio remedio){
        mRemedios.set(mRemedios.indexOf(remedio), remedio);
        notifyItemChanged(mRemedios.indexOf(remedio));
    }

    public void removerItem(Remedio remedios){
        int postion = mRemedios.indexOf(remedios);
        mRemedios.remove(postion);
        notifyItemRemoved(postion);
    }
}
