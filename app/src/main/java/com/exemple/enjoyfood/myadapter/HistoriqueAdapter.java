package com.exemple.enjoyfood.myadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.exemple.enjoyfood.Config;
import com.exemple.enjoyfood.Nutriscore;
import com.exemple.enjoyfood.R;
import com.exemple.enjoyfood.model.Produit;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class HistoriqueAdapter extends RecyclerView.Adapter<HistoriqueAdapter.ProduitViewHolder> implements Filterable {

    private Context mContext;
    private ArrayList<Produit> mProduit;
    private onItemClickListenr mLstener;

    private ArrayList<Produit> mProduitListAll;


    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            List<Produit> filteredList = new ArrayList<>();
            if(constraint.toString().isEmpty()){
                filteredList.addAll(mProduitListAll);
            }
            else{
                for (Produit p: mProduitListAll){
                    if(p.getTitre().toLowerCase().contains(constraint.toString().toLowerCase())){
                        filteredList.add(p);
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;

            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mProduit.clear();
            mProduit.addAll((Collection<? extends Produit>) results.values);
            notifyDataSetChanged();
        }
    };

    public interface onItemClickListenr{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(onItemClickListenr listener){
        mLstener = listener;
    }

    public HistoriqueAdapter(Context context, ArrayList<Produit> produits){
        mContext = context;
        mProduit = produits;
        this.mProduitListAll = new ArrayList<>(mProduit);
    }

    @NonNull
    @Override
    public ProduitViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.row_historique, parent, false);
        return new ProduitViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ProduitViewHolder holder, int position) {
        Produit p = mProduit.get(position);
        String imgURL = Config.URL_PHOTO + p.getImage();
        String name = p.getTitre();
        String description = p.getDescription();
        holder.mTextNameProduct.setText(name);
        holder.mTextDescriptionProduit.setText(description);
        Picasso.with(mContext).load(imgURL).fit().centerInside().into(holder.mImageView);

//        holder.mProgressBar.setVisibility(View.GONE);
        holder.mImageView.setVisibility(View.VISIBLE);

        String result = Nutriscore.calcul(p);
        holder.evaluation.setText(result);
        if(result.equals("A")){
            holder.evaluation.setBackground(mContext.getResources().getDrawable(R.drawable.bg_rounded_a));
        }
        else if(result.equals("B")){
            holder.evaluation.setBackground(mContext.getResources().getDrawable(R.drawable.bg_rounded_b));
        }
        else if(result.equals("C")){
            holder.evaluation.setBackground(mContext.getResources().getDrawable(R.drawable.bg_rounded_c));
        }
        else if(result.equals("D")){
            holder.evaluation.setBackground(mContext.getResources().getDrawable(R.drawable.bg_rounded_d));
        }
        else if(result.equals("E")){
            holder.evaluation.setBackground(mContext.getResources().getDrawable(R.drawable.bg_rounded_e));
        }

    }

    @Override
    public int getItemCount() {
        return mProduit.size();
    }

    public class ProduitViewHolder extends RecyclerView.ViewHolder{

        public ImageView mImageView;
        public TextView mTextNameProduct;
        public TextView mTextDescriptionProduit;
        public ProgressBar mProgressBar;
        public TextView evaluation;

        public ProduitViewHolder(@NonNull View itemView) {
            super(itemView);

            mImageView  = itemView.findViewById(R.id.imagev);
            //mProgressBar  = itemView.findViewById(R.id.pg_produit_liste);
            mTextNameProduct = itemView.findViewById(R.id.titre);
            mTextDescriptionProduit = itemView.findViewById(R.id.descript);
            evaluation = itemView.findViewById(R.id.evaluation);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mLstener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            mLstener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

//    private class MonFiltre extends Filter {
//        @Override
//        protected FilterResults performFiltering(CharSequence constraint) {
//// Actions à faire pendant le filtrage (constraint contient le filtre)
//        }
//        @Override
//        protected void publishResults(CharSequence contraint, FilterResults results) {
//// Affichage des résultats du filtrage
//        }
//    }
//
}
