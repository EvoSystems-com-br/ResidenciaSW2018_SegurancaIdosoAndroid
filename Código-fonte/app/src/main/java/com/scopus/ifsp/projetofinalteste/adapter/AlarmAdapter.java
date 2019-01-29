package com.scopus.ifsp.projetofinalteste.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.scopus.ifsp.projetofinalteste.R;
import com.scopus.ifsp.projetofinalteste.activity.AddReminderActivity;
import com.scopus.ifsp.projetofinalteste.model.Alarme;

import java.util.List;

public class AlarmAdapter extends RecyclerView.Adapter<AlarmeHolder> {
    private Context context;
    private final List<Alarme> mAlarme;

    public AlarmAdapter(Context context, List<Alarme> alarmes){mAlarme = alarmes; this.context = context;}

    @NonNull
    @Override
    public AlarmeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AlarmeHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.alarm_items, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AlarmeHolder holder, int position) {
        final Alarme alarme = mAlarme.get(position);
        String titulo = mAlarme.get(position).getTitulo();
        String date = alarme.getData();
        String time = alarme.getTime();
        String repeat = alarme.getRepeat();
        String repeatNo = alarme.getRepeat_no();
        String repeatType = alarme.getRepeat_type();
        String active = alarme.getActive();
        String last_date = alarme.getUltima_data();

        holder.setReminderTitle(titulo);

        if (date != null){
            String dateTime = date + " " + time;
            String afterDatetime = holder.calculoProximaData(date,time,repeatNo,repeatType);
            holder.setReminderDateTime(dateTime,afterDatetime);
        } else  {
            holder.mDateAndTimeText.setText("Data não definida");
        }

        if (repeat != null){
            holder.setReminderRepeatInfo(repeat, repeatNo, repeatType);
        } else {
            holder.mRepeatInfoText.setText("Repetição não definida");
        }

        if (active != null){
            holder.setActiveImage(active);
        } else {
            holder.mActiveImage.setImageResource(R.drawable.ic_notifications_off_black_24dp);
        }

        holder.fundo.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity activity = getActivity(v);
                Intent intent = new Intent(context,AddReminderActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                intent.putExtra("alarme", alarme);
                activity.finish();
                activity.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mAlarme != null ? mAlarme.size() : 0;
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

    public void insertItem(@NonNull Alarme alarmes){

        mAlarme.add(alarmes);
        notifyItemInserted(getItemCount());
    }

    public void atualizarRemedios(Alarme alarmes){
        mAlarme.set(mAlarme.indexOf(alarmes), alarmes);
        notifyItemChanged(mAlarme.indexOf(alarmes));
    }

    public void removerItem(Alarme alarme){
        int postion = mAlarme.indexOf(alarme);
        mAlarme.remove(postion);
        notifyItemRemoved(postion);
    }
}
