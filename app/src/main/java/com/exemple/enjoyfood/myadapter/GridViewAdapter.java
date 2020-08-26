package com.exemple.enjoyfood.myadapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.volley.RequestQueue;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Locale;

import com.exemple.enjoyfood.Config;
import com.exemple.enjoyfood.VolleySingleton;
import com.exemple.enjoyfood.R;
import com.exemple.enjoyfood.model.Produit;
import com.exemple.enjoyfood.myrequest.MyRequest;
import com.exemple.enjoyfood.ui.ResultatActivity;
import com.thekhaeng.pushdownanim.PushDownAnim;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

public class GridViewAdapter extends ArrayAdapter<Produit> {

    Context context;

    public GridViewAdapter(@NonNull Context context, ArrayList<Produit> items) {

        super(context,0, items);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.categorie_item, parent, false);
        }

        final Produit c = getItem(position);
        final String code = c.getCode_bar();
        final TextView label = convertView.findViewById(R.id.tv_adapter);
        final ImageView logo = convertView.findViewById(R.id.iv_adapter);
        final ProgressBar pg = convertView.findViewById(R.id.prog_adapter);

        String imgURL = Config.URL_PHOTO + c.getImage();
        Picasso.with(context).load(imgURL).fit().centerInside().into(logo);
        pg.setVisibility(View.GONE);
        logo.setVisibility(View.VISIBLE);

        if(Locale.getDefault().getLanguage().equals("fr")){
            label.setText(c.getTitre());
        }
        else if(Locale.getDefault().getLanguage().equals("en")){
            label.setText(c.getTitre_en());
        }
        else if(Locale.getDefault().getLanguage().equals("ar")){
            label.setText(c.getTitre_ar());
        }

        if(Config.ANIMATION_BUTTON_ACTIVE) {
            PushDownAnim.setPushDownAnimTo(convertView)
                    .setScale(MODE_SCALE, 0.89f)
                    .setDurationPush(PushDownAnim.DEFAULT_PUSH_DURATION)
                    .setDurationRelease(PushDownAnim.DEFAULT_RELEASE_DURATION)
                    .setInterpolatorPush(PushDownAnim.DEFAULT_INTERPOLATOR)
                    .setInterpolatorRelease(PushDownAnim.DEFAULT_INTERPOLATOR);
        }
        //logo.setImageResource(c.getLogo());
        //logo.setBackgroundResource(c.getImage());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                pg.setVisibility(View.VISIBLE);
                label.setVisibility(View.GONE);
                logo.setVisibility(View.GONE);
                final Intent i = new Intent(getContext(), ResultatActivity.class);
                Bundle b = new Bundle();
                b.putString("code_bar",code);
                i.putExtras(b);
                context.startActivity(i);
                pg.setVisibility(View.GONE);
                logo.setVisibility(View.VISIBLE);
                label.setVisibility(View.VISIBLE);
            }
        });

        return convertView;
    }

}
