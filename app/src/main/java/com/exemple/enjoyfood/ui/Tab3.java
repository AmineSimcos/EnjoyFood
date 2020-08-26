package com.exemple.enjoyfood.ui;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.exemple.enjoyfood.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

import com.exemple.enjoyfood.R;
import com.exemple.enjoyfood.model.Produit;

public class Tab3 extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private PieChart pieChart;

    private String mParam1;
    private String mParam2;

    public Tab3() {
        // Required empty public constructor
    }

    public static Tab3 newInstance(String param1, String param2) {
        Tab3 fragment = new Tab3();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_tab3, container, false);
        pieChart = v.findViewById(R.id.pieChart);

        ArrayList<PieEntry> nutri = new ArrayList<>();
        nutri.add(new PieEntry((float) ResultatActivity.proteine,getResources().getString(R.string.prot_ines)));
        nutri.add(new PieEntry((float) ResultatActivity.matiere_grasse,getResources().getString(R.string.mati_res_grasses)));
        nutri.add(new PieEntry((float) ResultatActivity.glucide,getResources().getString(R.string.glucides)));
        nutri.add(new PieEntry((float) ResultatActivity.fibre,getResources().getString(R.string.fibres)));
        //nutri.add(new PieEntry((float)((fibre + proteine + matiere_grasse + sucre)) ,"Autre"));

        PieDataSet dataSet = new PieDataSet(nutri, getResources().getString(R.string.nutrition));
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        //dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        dataSet.setValueTextColor(Color.WHITE);
        dataSet.setValueTextSize(16f);
        dataSet.setSliceSpace(2);


        PieData pieData = new PieData(dataSet);
        pieChart.setData(pieData);
        pieChart.getDescription().setEnabled(false);
        pieChart.setCenterText(getResources().getString(R.string.nutrition));
        pieChart.animate();
        pieChart.setRotationEnabled(true);
        pieChart.setUsePercentValues(true);
        pieChart.setHoleRadius(25f);
        pieChart.setTransparentCircleAlpha(20);
        pieChart.setDrawEntryLabels(true);
        pieChart.setEntryLabelTextSize(16);
        //pieChart.setCenterTextSize(13);
        return v;
    }
}
