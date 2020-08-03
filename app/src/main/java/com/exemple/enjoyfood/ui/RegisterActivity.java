package com.exemple.enjoyfood.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.exemple.enjoyfood.Config;
import com.google.android.material.textfield.TextInputLayout;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.util.Map;

import com.exemple.enjoyfood.R;
import com.exemple.enjoyfood.VolleySingleton;
import com.exemple.enjoyfood.myrequest.MyRequest;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

public class RegisterActivity extends AppCompatActivity {

    private Button btn_send;
    private ProgressBar pb_loader;
    private TextInputLayout til_pseudo, til_email, til_password, til_password2;
    private RequestQueue queue;
    private MyRequest request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_register);


        btn_send = findViewById(R.id.sinscrir);
        pb_loader = findViewById(R.id.pb_loader);
        til_pseudo = findViewById(R.id.til_pseudo);
        til_email = findViewById(R.id.til_mail);
        til_password = findViewById(R.id.til_pass);
        til_password2 = findViewById(R.id.til_pass2);

        // Activer l'animation du boutton
        if(Config.ANIMATION_BUTTON_ACTIVE) {
            PushDownAnim.setPushDownAnimTo(btn_send)
                    .setScale(MODE_SCALE, 0.89f)
                    .setDurationPush(PushDownAnim.DEFAULT_PUSH_DURATION)
                    .setDurationRelease(PushDownAnim.DEFAULT_RELEASE_DURATION)
                    .setInterpolatorPush(PushDownAnim.DEFAULT_INTERPOLATOR)
                    .setInterpolatorRelease(PushDownAnim.DEFAULT_INTERPOLATOR);
        }

        queue = VolleySingleton.getInstance(this).getRequestQueue();
        request = new MyRequest(this, queue);

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pb_loader.setVisibility(View.VISIBLE);
                String pseudo = til_pseudo.getEditText().getText().toString().trim();
                String email = til_email.getEditText().getText().toString().trim();
                String pass = til_password.getEditText().getText().toString().trim();
                String pass2 = til_password2.getEditText().getText().toString().trim();

                if(pseudo.length() > 0 && email.length() > 0 && pass.length() > 0 && pass2.length() >0) {
                    request.register(pseudo, email, pass, pass2, new MyRequest.RegisterCallBack() {
                        @Override
                        public void onSucces(String message) {
                            pb_loader.setVisibility(View.GONE);
                            Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                            intent.putExtra("REGISTER", message);
                            startActivity(intent);
                            finish();
                        }

                        @Override
                        public void inputErrors(Map<String, String> errors) {
                            pb_loader.setVisibility(View.GONE);

                            if(errors.get("pseudo") != null){
                                til_pseudo.setError(errors.get("pseudo"));
                            }
                            else{
                                til_pseudo.setErrorEnabled(false);
                            }


                            if(errors.get("email") != null){
                                til_email.setError(errors.get("email"));
                            }
                            else{
                                til_email.setErrorEnabled(false);
                            }


                            if(errors.get("password") != null){
                                til_password.setError(errors.get("password"));
                                til_password2.setError(errors.get("password"));
                            }
                            else{
                                til_password.setErrorEnabled(false);
                                til_password2.setErrorEnabled(false);
                            }
                        }

                        @Override
                        public void onError(String message) {
                            pb_loader.setVisibility(View.GONE);
                            Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else{
                    Toast.makeText(getApplicationContext(),getResources().getString(R.string.verification),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}