package ro.bluebit.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ro.bluebit.ActivitateQRInformatiiTrimitere;
import ro.bluebit.ActivitateTrimitereNoua;
import ro.bluebit.Descarca_Trimiteri_Activity;
import ro.bluebit.Incarca_Trimiteri_Activity;
import ro.bluebit.R;
import ro.bluebit.UTILITARE.ClasaIteme;
import ro.bluebit.UTILITARE.SelectieInitialaActivity;

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
    public void onBindViewHolder(@NonNull ItemeViewHolder holder, final int position) {
        ClasaIteme itemCurent = mListaIteme.get(position);
        holder.mImageView.setImageResource(itemCurent.getResurseImagini());
        holder.mLinieTextView.setText(itemCurent.getTextLinie());
        holder.mLinieTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent intent;

                switch (position){ // Adauga case-uri in continuare pentru activitatile noi pe care vrei sa le deschizi
                    case 0:
                        intent =  new Intent(view.getContext(), ActivitateTrimitereNoua.class);
                        break;

                    case 1:
                        intent =  new Intent(view.getContext(), ActivitateQRInformatiiTrimitere.class);
                        break;
                    case 2:
                        intent =  new Intent(view.getContext(), Incarca_Trimiteri_Activity.class);
                        intent.putExtra("ACTIUNE","incarcare");
                        break;
                    case 3:
                        intent =  new Intent(view.getContext(), Descarca_Trimiteri_Activity.class);
                        intent.putExtra("ACTIUNE","descarcare");
                        break;
                    default:
                        intent =  new Intent(view.getContext(), SelectieInitialaActivity.class);
                        break;
                }
                view.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mListaIteme.size();
    }


}
