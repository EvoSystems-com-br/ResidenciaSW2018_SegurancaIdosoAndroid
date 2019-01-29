package com.scopus.ifsp.projetofinalteste.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.scopus.ifsp.projetofinalteste.R;

public class AlarmeHolder extends RecyclerView.ViewHolder{

    public TextView mTitleText, mDateAndTimeText, mRepeatInfoText, mDateAndTimeAfterText;
    public ImageView mActiveImage, mThumbnailImage;
    public ColorGenerator mColorGenerator = ColorGenerator.DEFAULT;
    public TextDrawable mDrawableBuilder;
    public RelativeLayout fundo;

    public AlarmeHolder(View view) {
        super(view);

        mTitleText = view.findViewById(R.id.AlarmeTitulo);
        mDateAndTimeAfterText = view.findViewById(R.id.AlarmeDate_timeAfter);
        mDateAndTimeText = view.findViewById(R.id.Alarmedate_time);
        mRepeatInfoText = view.findViewById(R.id.AlarmeRepeat_info);
        mActiveImage = view.findViewById(R.id.notificatioId);
        mThumbnailImage = view.findViewById(R.id.AlarmThumbnail_image);
        fundo = view.findViewById(R.id.layout_item);

    }

    //Set reminder title view
    public void setReminderTitle(String title){
        mTitleText.setText(title);
        String letter = "A";

        if(title != null && !title.isEmpty()){
            letter = title.substring(0,1);
        }

        int color = mColorGenerator.getRandomColor();

        //Create a circular icon consisting of a random background colour and first letter of title.
        mDrawableBuilder = TextDrawable.builder().buildRound(letter,color);
        mThumbnailImage.setImageDrawable(mDrawableBuilder);
    }

    //Set date and time views
    public void setReminderDateTime(String dateTime, String afterDateTime){
        mDateAndTimeText.setText(dateTime);
        mDateAndTimeAfterText.setText(afterDateTime);
    }

    public void setReminderRepeatInfo(String repeat, String repeatNo, String repeatType){
        if(repeat.equals("true")){
            mRepeatInfoText.setText("A cada " + repeatNo + " " + repeatType + "(s)");
        }else if(repeat.equals("false")){
            mRepeatInfoText.setText("Repeat Off");
        }else {
            mRepeatInfoText.setText(repeat);
        }
    }



    //Set active image as on or off
    public void setActiveImage(String active){
        if(active.equals("true")){
            mActiveImage.setImageResource(R.drawable.ic_notifications_on);
        }else if(active.equals("false")){
            mActiveImage.setImageResource(R.drawable.ic_notifications_off_black_24dp);
        }
    }

    public String calculoProximaData(String data, String time, String repeatNo, String repeatType){

        int intervalo = Integer.parseInt(repeatNo);

        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String dateTime = data + " " + time;

        Date AfterDate;
        Calendar c = Calendar.getInstance();

        try {
            c.setTime(formato.parse(dateTime));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        switch(repeatType){
            case "Mês":
                c.add(Calendar.MONTH, intervalo);
                break;
            case "Semana":
                c.add(Calendar.DAY_OF_MONTH, (6 * intervalo));
                break;

            case "Dia":
                c.add(Calendar.DAY_OF_MONTH, intervalo);
                break;

            case "Hora":
                c.add(Calendar.HOUR, intervalo);
                break;

            case "Minuto":
                c.add(Calendar.MINUTE, intervalo);
                break;

        }
        AfterDate = c.getTime();
        return formato.format(AfterDate);
    }
}
