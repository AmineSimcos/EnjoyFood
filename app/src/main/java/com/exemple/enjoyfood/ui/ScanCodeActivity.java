package com.exemple.enjoyfood.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.google.zxing.Result;

import dz.baichoudjedi.lovefood.VolleySingleton;
import dz.baichoudjedi.lovefood.model.Produit;
import dz.baichoudjedi.lovefood.myrequest.MyRequest;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScanCodeActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler{
    private RequestQueue queue;
    private MyRequest request;
    private ZXingScannerView scannerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        scannerView = new ZXingScannerView(this);
        setContentView(scannerView);
        queue = VolleySingleton.getInstance(this).getRequestQueue();
        request = new MyRequest(this,queue);
    }

    @Override
    public void handleResult(final Result result) {
        final Intent i = new Intent(getApplicationContext(), ResultatActivity.class);
        request.informationProduct(result.getText(), new MyRequest.InformationCallback() {

            @Override
            public void onSucces(String code_bar, String titre, String description, String image, String categorie, double energie, double matiere_grasse, double graisse, double glucide, double sucre, double proteine,double fibre, double sodium, double sel, double calicium, int fruits_lesgumes, String ingrediant) {
                Bundle b = new Bundle();
                b.putString("code_bar",code_bar);
                b.putString("titre",titre);
                b.putString("description",description);
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
                b.putString("ingrediant",ingrediant);
                i.putExtras(b);
                startActivity(i);
            }

            @Override
            public void onError(String message) {
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            }
        });
        onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        scannerView.stopCamera();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        scannerView.setResultHandler(this);
        scannerView.startCamera();

    }
}
