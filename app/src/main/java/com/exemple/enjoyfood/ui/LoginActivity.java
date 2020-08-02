package com.exemple.enjoyfood.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.exemple.enjoyfood.Config;
import com.google.android.material.textfield.TextInputLayout;
import com.thekhaeng.pushdownanim.PushDownAnim;

import com.exemple.enjoyfood.R;
import com.exemple.enjoyfood.SessionManager;
import com.exemple.enjoyfood.VolleySingleton;
import com.exemple.enjoyfood.myrequest.MyRequest;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

public class LoginActivity extends AppCompatActivity {

    private TextInputLayout til_pseudo_login, til_password_login;
    private TextView tv_sinscrir, tv_ignorer;
    private RequestQueue queue;
    private MyRequest request;
    private Button btn_send;
    private ProgressBar pb_loader;
    private Handler handler;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);


        sessionManager = new SessionManager(this);

        if(sessionManager.isLogged()){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        //Réccupération du message de l'nscription
        Intent intent = getIntent();
        if(intent.hasExtra("REGISTER")){
            Toast.makeText(getApplicationContext(), intent.getStringExtra("REGISTER"), Toast.LENGTH_SHORT).show();
        }

        til_pseudo_login = findViewById(R.id.til_pseudo_login);
        til_password_login = findViewById(R.id.til_pass_login);
        btn_send = findViewById(R.id.seconnecter);
        pb_loader = findViewById(R.id.pb_loader);
        tv_sinscrir = findViewById(R.id.tv_sinscrir);
        tv_ignorer = findViewById(R.id.tv_ignorer);

        // Activer l'animation du boutton
        if(Config.ANIMATION_BUTTON_ACTIVE) {
            PushDownAnim.setPushDownAnimTo(btn_send, tv_sinscrir)
                    .setScale(MODE_SCALE, 0.89f)
                    .setDurationPush(PushDownAnim.DEFAULT_PUSH_DURATION)
                    .setDurationRelease(PushDownAnim.DEFAULT_RELEASE_DURATION)
                    .setInterpolatorPush(PushDownAnim.DEFAULT_INTERPOLATOR)
                    .setInterpolatorRelease(PushDownAnim.DEFAULT_INTERPOLATOR);
        }

        queue = VolleySingleton.getInstance(this).getRequestQueue();
        request = new MyRequest(this,queue);
        handler = new Handler();
        sessionManager = new SessionManager(this);

        tv_sinscrir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),RegisterActivity.class);
                startActivity(intent);
            }
        });

        tv_ignorer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        final Bundle b = new Bundle();

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String pseudo = til_pseudo_login.getEditText().getText().toString().trim();
                final String password = til_password_login.getEditText().getText().toString().trim();

                pb_loader.setVisibility(View.VISIBLE);

                if(pseudo.length() > 0 && password.length() > 0) {
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            request.connection(pseudo, password, new MyRequest.LoginCallBack() {
                                @Override
                                public void onSucces(String id, String pseudo, String email) {
                                    pb_loader.setVisibility(View.GONE);
                                    sessionManager.insertUser(id, pseudo, email);

                                    if(b.getBoolean("fragment")){
                                        Intent data = new Intent();
                                        data.putExtra("cnx", "Connecté avec succée");
                                        setResult(RESULT_OK, data);
                                    }
                                    else {
                                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                        startActivity(intent);
                                    };

                                    finish();
                                }

                                @Override
                                public void onError(String message) {
                                    pb_loader.setVisibility(View.GONE);
                                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    },1000);

                }
                else{
                    Toast.makeText(getApplicationContext(),"Veillez remplir tous les champs",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}