package com.exemple.enjoyfood.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.exemple.enjoyfood.Config;
import com.thekhaeng.pushdownanim.PushDownAnim;

import com.exemple.enjoyfood.R;

import java.util.Locale;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

public class fragmentAccueil  extends Fragment {


    private Button btn, btn_produit, btn_consommation, btn_historique, btn_apropos;
    private int images[] = new int[6];
    // TODO hna dakhli les images taw3ek , bon tableau lewel ta3 anglais w zawej ta3 francais w talet bel 3arbiya
    private ViewFlipper v_flipper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_accueil,container,false);

        btn = v.findViewById(R.id.btn_scanner);
        btn_produit = v.findViewById(R.id.btn_produit);
        btn_consommation = v.findViewById(R.id.btn_consommation);
        btn_historique = v.findViewById(R.id.btn_historique);
        btn_apropos = v.findViewById(R.id.btn_apropos);


        v_flipper = v.findViewById(R.id.v_fliper);
        if(Locale.getDefault().getLanguage().equals("en")){
            images[0] = R.drawable.img1_slide1_en;
            images[1] = R.drawable.img2_slide2_en;
            images[2] = R.drawable.img3_slide3_en;
            images[3] = R.drawable.img4_slide4_en;
            images[4] = R.drawable.img5_slide5_en;
            images[5] = R.drawable.img6_slide6_en;
        }
        else if (Locale.getDefault().getLanguage().equals("fr")){
            images[0] = R.drawable.img1_slide1_fr;
            images[1] = R.drawable.img2_slide2_fr;
            images[2] = R.drawable.img3_slide3_fr;
            images[3] = R.drawable.img4_slide4_fr;
            images[4] = R.drawable.img5_slide5_fr;
            images[5] = R.drawable.img6_slide6_fr;
        }
        else if (Locale.getDefault().getLanguage().equals("ar")){
            images[0] = R.drawable.img1_slide1_ar;
            images[1] = R.drawable.img2_slide2_ar;
            images[2] = R.drawable.img3_slide3_ar;
            images[3] = R.drawable.img4_slide4_ar;
            images[4] = R.drawable.img5_slide5_ar;
            images[5] = R.drawable.img6_slide6_ar;
        }

        for (int image : images) {
            flipperImages(image);
        }

        // Activer l'animation du boutton
        if(Config.ANIMATION_BUTTON_ACTIVE) {
            PushDownAnim.setPushDownAnimTo(btn, btn_apropos, btn_consommation, btn_historique, btn_produit)
                    .setScale(MODE_SCALE, 0.89f)
                    .setDurationPush(PushDownAnim.DEFAULT_PUSH_DURATION)
                    .setDurationRelease(PushDownAnim.DEFAULT_RELEASE_DURATION)
                    .setInterpolatorPush(PushDownAnim.DEFAULT_INTERPOLATOR)
                    .setInterpolatorRelease(PushDownAnim.DEFAULT_INTERPOLATOR);
        }

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA)== PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(getActivity(), ScanCodeActivity.class);
                    //Intent intent = new Intent(getActivity(), ListeProduitsActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                }
                else {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, 0);
                }
            }
        });

        btn_apropos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.nav_apropos);
            }
        });

        btn_produit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.nav_produit);
            }
        });

        btn_consommation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.nav_consommation);
            }
        });

        btn_historique.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.nav_historique);
            }
        });

        return v;
    }


    public void flipperImages(int image){
        ImageView imageView = new ImageView(getContext());
        imageView.setBackgroundResource(image);
        //imageView.setImageResource(image);

        v_flipper.addView(imageView);
        v_flipper.setFlipInterval(4000);
        v_flipper.setAutoStart(true);

        v_flipper.setInAnimation(getContext(), android.R.anim.slide_in_left);
        v_flipper.setOutAnimation(getContext(), android.R.anim.slide_out_right);

    }

}





//v_background = v.findViewById(R.id.vv_background);
//Uri u = Uri.parse(path);
//v_background.setVideoURI(u);
//v_background.start();
        /*v_background.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
            }
        });*/
