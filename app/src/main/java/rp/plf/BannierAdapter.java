package rp.plf;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;


import java.util.List;

import rp.plf.Modele.Banniere;

public class BannierAdapter extends RecyclerView.Adapter<BannierAdapter.ViewHolder>{

    private Context mContext;
    private List<Banniere> mUsers;

    public BannierAdapter(Context mContext, List<Banniere> mUsers){
        this.mContext = mContext;
        this.mUsers = mUsers;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.banniere_item,parent,false);
        return new BannierAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Banniere banniere = mUsers.get(position);
        holder.username.setText(banniere.getPrix());
        if(banniere.getLien().equals("")){
            holder.profile_image.setImageResource(R.mipmap.ic_launcher);
        }else{
            Glide.with(mContext).load(banniere.getLien()).into(holder.profile_image);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "CA MARCHE", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(mContext, MessageActivity.class);
//                intent.putExtra("userID",banniere.getLien());
//                mContext.startActivity(intent);
            }
        });
    }



    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView username;
        public ImageView profile_image;
        public ImageView img_on;
        public ImageView img_off;
        public TextView last_msg;

        public ViewHolder(View itemView){
            super(itemView);
            username = itemView.findViewById(R.id.username);
            profile_image = itemView.findViewById(R.id.profile_image);
            img_on = itemView.findViewById(R.id.img_on);
            img_off = itemView.findViewById(R.id.img_off);
            last_msg = itemView.findViewById(R.id.last_msg);
        }


    }

}
