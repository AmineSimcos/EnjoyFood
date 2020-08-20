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
    private MyRequest request;
    private RequestQueue queue;

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
        queue = VolleySingleton.getInstance(getContext()).getRequestQueue();
        request = new MyRequest(getContext(),queue);

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
                request.informationProduct(code, new MyRequest.InformationCallback() {

                    @Override
                    public void onSucces(String code_bar, String titre, String titre_en, String titre_ar, String description, String desc_en, String desc_ar, String image, String categorie, double energie, double matiere_grasse, double graisse, double glucide, double sucre, double proteine,double fibre, double sodium, double sel, double calicium, int fruits_lesgumes, String ingrediant, String ingrediant_en, String ingrediant_ar, int volume) {
                        Bundle b = new Bundle();
                        b.putString("code_bar",code_bar);
                        b.putString("titre",titre);
                        b.putString("titre_en",titre_en);
                        b.putString("titre_ar",titre_ar);
                        b.putString("description",description);
                        b.putString("desc_en",desc_en);
                        b.putString("desc_ar",desc_ar);
                        b.putString("image",image);
                        b.putString("categorie",categorie);
                        b.putDouble("energie",energie);
                        b.putDouble("matiere_grasse",matiere_grasse);
                        b.putDouble("graisse",graisse);
                        b.putDouble("glucide",glucide);
                        b.putDouble("sucre",sucre);
                        b.putDouble("proteine",proteine);
                        b.putDouble("fibres",fibre);
                        b.putDouble("sodium",sodium);
                        b.putDouble("sel",sel);
                        b.putDouble("calicium",calicium);
                        b.putInt("fruits_lesgumes",fruits_lesgumes);
                        b.putInt("volume",volume);
                        b.putString("ingrediant",ingrediant);
                        b.putString("ingrediant_en",ingrediant_en);
                        b.putString("ingrediant_ar",ingrediant_ar);
                        i.putExtras(b);
                        context.startActivity(i);
                        pg.setVisibility(View.GONE);
                        logo.setVisibility(View.VISIBLE);
                        label.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onError(String message) {
                        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        return convertView;
    }

}
