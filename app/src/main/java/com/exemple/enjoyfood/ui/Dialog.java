package com.exemple.enjoyfood.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.exemple.enjoyfood.Config;
import com.thekhaeng.pushdownanim.PushDownAnim;

import de.hdodenhof.circleimageview.CircleImageView;
import com.exemple.enjoyfood.R;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

public class Dialog extends AppCompatDialogFragment {

    private int nbr = 1, somme, selected;
    private TextView tv_nbr, tv_img_icone, tv_result, tv_unity;
    private CircleImageView btn_plus, btn_moin;
    private DialogListener listener;
    private LinearLayout layoutQt1, layoutQt2, btns;
    private ImageView img_icone;
    private EditText et_qt;
    private String categorie;
    private int volume;
    @NonNull
    @Override
    public android.app.Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        //Bundle args = new Bundle();
        //categorie = args.getString("categorie");
        categorie = getArguments().getString("categorie");
        volume = getArguments().getInt("volume");

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_layout, null);

        layoutQt1 = view.findViewById(R.id.layout_qt1);
        layoutQt2 = view.findViewById(R.id.layout_qt2);
        btns = view.findViewById(R.id.btns_plus_moin);
        img_icone = view.findViewById(R.id.img_icone);
        tv_img_icone = view.findViewById(R.id.tv_img_icone);
        tv_result = view.findViewById(R.id.tv_result);
        tv_unity = view.findViewById(R.id.tv_unity);
        et_qt = view.findViewById(R.id.et_qt);
        tv_nbr = view.findViewById(R.id.tv_nbr_cons);
        btn_moin = view.findViewById(R.id.btn_moin);
        btn_plus = view.findViewById(R.id.btn_plus);

        layoutQt1.setVisibility(View.VISIBLE);
        layoutQt2.setVisibility(View.GONE);
        final Spinner spinner = (Spinner) view.findViewById(R.id.echel_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.echel, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                nbr = 1;
                somme = 0;
                if(spinner.getAdapter().getItem(position).toString().equals(getResources().getStringArray(R.array.echel)[0])) {
                    layoutQt1.setVisibility(View.VISIBLE);
                    layoutQt2.setVisibility(View.GONE);
                    btns.setVisibility(View.VISIBLE);
                    img_icone.setImageResource(R.drawable.pack);
                    selected = 0;
                    if(categorie.equals("Boissons") || categorie.equals("Eau")){
                        if(volume >= 1000){
                            tv_img_icone.setText(convertion(volume) + " " + getResources().getString(R.string.unity_litre));
                            tv_nbr.setText(String.valueOf(nbr));
                            tv_result.setText(String.valueOf(convertion(volume)));
                            tv_unity.setText(getResources().getString(R.string.unity_litre));
                            somme = volume;
                        }
                        else{
                            tv_img_icone.setText(volume + " "  + getResources().getString(R.string.unity_ml));
                            tv_nbr.setText(String.valueOf(nbr));
                            tv_result.setText(String.valueOf(volume));
                            tv_unity.setText(getResources().getString(R.string.unity_ml));
                            somme = volume;
                        }

                    }
                    else{
                        if(volume >= 1000){
                            tv_img_icone.setText(convertion(volume) + " "  + getResources().getString(R.string.unity_kg));
                            tv_nbr.setText(String.valueOf(nbr));
                            tv_result.setText(String.valueOf(convertion(volume)));
                            tv_unity.setText(getResources().getString(R.string.unity_kg));
                            somme = volume;
                        }
                        else{
                            tv_img_icone.setText(volume + " " + getResources().getString(R.string.unity_g));
                            tv_nbr.setText(String.valueOf(nbr));
                            tv_result.setText(String.valueOf(volume));
                            tv_unity.setText(getResources().getString(R.string.unity_g));
                            somme = volume;
                        }
                    }
                }
                else if(spinner.getAdapter().getItem(position).toString().equals(getResources().getStringArray(R.array.echel)[1])) {
                    layoutQt1.setVisibility(View.GONE);
                    layoutQt2.setVisibility(View.VISIBLE);
                    btns.setVisibility(View.GONE);
                    selected = 1;
                    somme = Integer.parseInt(et_qt.getText().toString());
                }
                else if(spinner.getAdapter().getItem(position).toString().equals(getResources().getStringArray(R.array.echel)[2])) {
                    layoutQt1.setVisibility(View.VISIBLE);
                    layoutQt2.setVisibility(View.GONE);
                    btns.setVisibility(View.VISIBLE);
                    img_icone.setImageResource(R.drawable.icons8spoon00);
                    tv_img_icone.setText("3 g");
                    selected = 2;
                    somme = nbr * 3;
                    tv_nbr.setText(String.valueOf(nbr));
                    tv_result.setText(String.valueOf(somme));
                    tv_unity.setText(getResources().getString(R.string.unity_g));
                }
                else if(spinner.getAdapter().getItem(position).toString().equals(getResources().getStringArray(R.array.echel)[3])) {
                    layoutQt1.setVisibility(View.VISIBLE);
                    layoutQt2.setVisibility(View.GONE);
                    btns.setVisibility(View.VISIBLE);
                    img_icone.setImageResource(R.drawable.icons8spoon00);
                    tv_img_icone.setText("25 "  + getResources().getString(R.string.unity_g));
                    selected = 3;
                    somme = nbr * 25;
                    tv_nbr.setText(String.valueOf(nbr));
                    tv_result.setText(String.valueOf(somme));
                    tv_unity.setText(getResources().getString(R.string.unity_g));
                }
                else if(spinner.getAdapter().getItem(position).toString().equals(getResources().getStringArray(R.array.echel)[4])) {
                    layoutQt1.setVisibility(View.VISIBLE);
                    layoutQt2.setVisibility(View.GONE);
                    btns.setVisibility(View.VISIBLE);
                    img_icone.setImageResource(R.drawable.icons8verre64);
                    tv_img_icone.setText("250 " + getResources().getString(R.string.unity_ml));
                    selected = 4;
                    somme = nbr * 250;
                    tv_nbr.setText(String.valueOf(nbr));
                    tv_result.setText(String.valueOf(somme));
                    tv_unity.setText(getResources().getString(R.string.unity_ml));
                }
                else if(spinner.getAdapter().getItem(position).toString().equals(getResources().getStringArray(R.array.echel)[5])) {
                    layoutQt1.setVisibility(View.VISIBLE);
                    layoutQt2.setVisibility(View.GONE);
                    btns.setVisibility(View.VISIBLE);
                    img_icone.setImageResource(R.drawable.icons8_bouillie100);
                    tv_img_icone.setText("250 "  + getResources().getString(R.string.unity_g));
                    selected = 5;
                    somme = nbr * 250;
                    tv_nbr.setText(String.valueOf(nbr));
                    tv_result.setText(String.valueOf(somme));
                    tv_unity.setText(getResources().getString(R.string.unity_g));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        // Activer l'animation du boutton
        if(Config.ANIMATION_BUTTON_ACTIVE) {
            PushDownAnim.setPushDownAnimTo(btn_plus, btn_moin)
                    .setScale(MODE_SCALE, 0.89f)
                    .setDurationPush(PushDownAnim.DEFAULT_PUSH_DURATION)
                    .setDurationRelease(PushDownAnim.DEFAULT_RELEASE_DURATION)
                    .setInterpolatorPush(PushDownAnim.DEFAULT_INTERPOLATOR)
                    .setInterpolatorRelease(PushDownAnim.DEFAULT_INTERPOLATOR);
        }

        btn_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                nbr++;
                switch(selected){
                    case 0:
                        somme = nbr * volume;
                        if(categorie.equals("Boissons") || categorie.equals("Eau")){
                            if(volume >= 1000){
                                tv_nbr.setText(String.valueOf(nbr));
                                tv_result.setText(String.valueOf(nbr * convertion(volume)));
                                tv_unity.setText(getResources().getString(R.string.unity_litre));
                            }
                            else{
                                tv_nbr.setText(String.valueOf(nbr));
                                tv_result.setText(String.valueOf(somme));
                                tv_unity.setText(getResources().getString(R.string.unity_ml));
                            }
                        }
                        else{
                            if(volume >= 1000){
                                tv_nbr.setText(String.valueOf(nbr));
                                tv_result.setText(String.valueOf(nbr * convertion(volume)));
                                tv_unity.setText(getResources().getString(R.string.unity_kg));
                            }
                            else{
                                tv_nbr.setText(String.valueOf(nbr));
                                tv_result.setText(String.valueOf(somme));
                                tv_unity.setText(getResources().getString(R.string.unity_g));
                            }
                        }

                        break;
                    case 1:
                        break;
                    case 2:
                        somme = nbr * 3;
                        tv_nbr.setText(String.valueOf(nbr));
                        tv_result.setText(String.valueOf(somme));
                        tv_unity.setText(getResources().getString(R.string.unity_g));
                        break;
                    case 3:
                        somme = nbr * 25;
                        tv_nbr.setText(String.valueOf(nbr));
                        tv_result.setText(String.valueOf(somme));
                        tv_unity.setText(getResources().getString(R.string.unity_g));
                        break;
                    case 4:
                        somme = nbr * 250;
                        tv_nbr.setText(String.valueOf(nbr));
                        tv_result.setText(String.valueOf(somme));
                        tv_unity.setText(getResources().getString(R.string.unity_ml));
                        break;
                    case 5:
                        somme = nbr * 250;
                        tv_nbr.setText(String.valueOf(nbr));
                        tv_result.setText(String.valueOf(somme));
                        tv_unity.setText(getResources().getString(R.string.unity_g));
                        break;
                }

            }
        });

        btn_moin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nbr > 0){
                    nbr--;
                    switch(selected){
                        case 0:
                            somme = nbr * volume;
                            if(categorie.equals("Boissons") || categorie.equals("Eau")){
                                if(volume >= 1000){
                                    tv_nbr.setText(String.valueOf(nbr));
                                    tv_result.setText(String.valueOf(nbr * convertion(volume)));
                                    tv_unity.setText(getResources().getString(R.string.unity_g));
                                }
                                else{
                                    tv_nbr.setText(String.valueOf(nbr));
                                    tv_result.setText(String.valueOf(somme));
                                    tv_unity.setText(getResources().getString(R.string.unity_g));
                                }
                            }
                            else{
                                if(volume >= 1000){
                                    tv_nbr.setText(String.valueOf(nbr));
                                    tv_result.setText(String.valueOf(nbr * convertion(volume)));
                                    tv_unity.setText(getResources().getString(R.string.unity_g));
                                }
                                else{
                                    tv_nbr.setText(somme);
                                    tv_result.setText(String.valueOf(somme));
                                    tv_unity.setText(getResources().getString(R.string.unity_g));
                                }
                            }

                            break;
                        case 1:
                            break;
                        case 2:
                            somme = nbr * 3;
                            tv_nbr.setText(String.valueOf(nbr));
                            tv_result.setText(String.valueOf(somme));
                            tv_unity.setText(getResources().getString(R.string.unity_g));
                            break;
                        case 3:
                            somme = nbr * 25;
                            tv_nbr.setText(String.valueOf(nbr));
                            tv_result.setText(String.valueOf(somme));
                            tv_unity.setText(getResources().getString(R.string.unity_g));
                            break;
                        case 4:
                            somme = nbr * 250;
                            tv_nbr.setText(String.valueOf(nbr));
                            tv_result.setText(String.valueOf(somme));
                            tv_unity.setText(getResources().getString(R.string.unity_ml));
                            break;
                        case 5:
                            somme = nbr * 250;
                            tv_nbr.setText(String.valueOf(nbr));
                            tv_result.setText(String.valueOf(somme));
                            tv_unity.setText(getResources().getString(R.string.unity_g));
                            break;
                    }
                }
            }
        });

        builder.setView(view).setTitle(getResources().getStringArray(R.array.dialog)[0])
                .setNegativeButton(getResources().getStringArray(R.array.dialog)[2], new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton(getResources().getStringArray(R.array.dialog)[1], new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.applyNbr(somme);
                    }
                });

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (DialogListener) context;
        } catch(ClassCastException e){
            throw new ClassCastException(context.toString() + "must implement DialogListener");
        }
    }

    public interface DialogListener{
        void applyNbr(int somme);
    }

    public double convertion(double val){
        return val / 1000;
    }
}
