package com.exemple.enjoyfood.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.thekhaeng.pushdownanim.PushDownAnim;

import de.hdodenhof.circleimageview.CircleImageView;
import dz.baichoudjedi.lovefood.R;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

public class Dialog extends AppCompatDialogFragment {

    private int nbr = 0;
    private TextView tv_nbr;
    private CircleImageView btn_plus, btn_moin;
    private DialogListener listener;
    @NonNull
    @Override
    public android.app.Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_layout, null);

        tv_nbr = view.findViewById(R.id.tv_nbr_cons);
        btn_moin = view.findViewById(R.id.btn_moin);
        btn_plus = view.findViewById(R.id.btn_plus);

        // Animation buttons
        PushDownAnim.setPushDownAnimTo(btn_plus,btn_moin)
                .setScale( MODE_SCALE, 0.89f)
                .setDurationPush( PushDownAnim.DEFAULT_PUSH_DURATION )
                .setDurationRelease( PushDownAnim.DEFAULT_RELEASE_DURATION )
                .setInterpolatorPush( PushDownAnim.DEFAULT_INTERPOLATOR )
                .setInterpolatorRelease( PushDownAnim.DEFAULT_INTERPOLATOR );

        btn_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nbr++;
                tv_nbr.setText(String.valueOf(nbr));
            }
        });

        btn_moin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nbr > 0){
                    nbr--;
                    tv_nbr.setText(String.valueOf(nbr));
                }
            }
        });

        builder.setView(view).setTitle("Confirmer")
                .setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.applyNbr(nbr);
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
        void applyNbr(int nbr);
    }
}
