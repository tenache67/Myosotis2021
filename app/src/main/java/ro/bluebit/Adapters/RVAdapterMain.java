package ro.bluebit.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ro.bluebit.Activitate3;
import ro.bluebit.R;
import ro.bluebit.UTILITARE.ClasaIteme;

public class RVAdapterMain extends RecyclerView.Adapter<RVAdapterMain.ItemeViewHolder> {
    private ArrayList<ClasaIteme> mListaIteme;
    private Context context;

    public RVAdapterMain(ArrayList<ClasaIteme>listaIteme){
        mListaIteme = listaIteme;
        this.context=context;
    }



    public static class ItemeViewHolder extends RecyclerView.ViewHolder{
        public ImageView mImageView;
        public TextView mLinieTextView;
        Context context;


        public ItemeViewHolder(@NonNull View itemView, final Context context) {
            super(itemView);
            this.context = context;
            mImageView = itemView.findViewById(R.id.imageview_id);
            mLinieTextView = itemView.findViewById(R.id.TextViewRecycler_id);

        }
    }


    @NonNull
    @Override
    public ItemeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_recycler_item, parent, false);
        ItemeViewHolder ivh = new ItemeViewHolder(v,context);
        return ivh;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemeViewHolder holder, int position) {
        ClasaIteme itemCurent = mListaIteme.get(position);
        holder.mImageView.setImageResource(itemCurent.getResurseImagini());
        holder.mLinieTextView.setText(itemCurent.getTextLinie());
        holder.mLinieTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), Activitate3.class);
                view.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mListaIteme.size();
    }


}
