package com.scopus.ifsp.projetofinalteste.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.scopus.ifsp.projetofinalteste.R;


public class RemedioHolder extends RecyclerView.ViewHolder {

    public TextView mNomeRemedio, quantidade, frequencia;
    public ImageView mDeletImage, mThumbnailImage;
    public ColorGenerator mColorGenerator = ColorGenerator.DEFAULT;
    public TextDrawable mDrawableBuilder;
    RelativeLayout fundo;

    //public ImageButton editButton, deleteButton, calendarButton, infoButton;


    public RemedioHolder(View itemView){
        super(itemView);
        mNomeRemedio = itemView.findViewById(R.id.nomeRemedio);
        quantidade = itemView.findViewById(R.id.quantidade);
        frequencia = itemView.findViewById(R.id.frequencia);
        mThumbnailImage = itemView.findViewById(R.id.RemedioThumbnail_image);
        fundo = itemView.findViewById(R.id.clickLayout);
        mDeletImage = itemView.findViewById(R.id.removeIten);
    }

    //Set reminder title view
    public void setReminderTitle(String title){
        mNomeRemedio.setText(title);
        String letter = "A";

        if(title != null && !title.isEmpty()){
            letter = title.substring(0,1);
        }

        int color = mColorGenerator.getRandomColor();

        //Create a circular icon consisting of a random background colour and first letter of title.
        mDrawableBuilder = TextDrawable.builder().buildRound(letter,color);
        mThumbnailImage.setImageDrawable(mDrawableBuilder);
    }
}
