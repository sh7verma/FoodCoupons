package app.com.foodcoupons.adapters;

/**
 * Created by dev on 28/9/18.
 */

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import app.com.foodcoupons.R;
import app.com.foodcoupons.models.BroucherModel;


public class BrounchersAdapter extends RecyclerView.Adapter<BrounchersAdapter.Holder> {
    Context context;
    ArrayList<BroucherModel> arrayList;

    public BrounchersAdapter(Context context, ArrayList<BroucherModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }


    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_brouchers, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        //holder..setBackground(arrayList.get(position).getmImage());
        holder.tvTotaldeals.setText(arrayList.get(position).getmDeals());
        holder.tvPlacename.setText(arrayList.get(position).getmPlaceName());
        holder.tvPlaceAddress.setText(arrayList.get(position).getmPlaceAddress());
        Picasso.get().load(arrayList.get(position).getmImage()).into(holder.imgBg);
        int i = arrayList.get(position).getmRating();
        if (i == 1) {
            holder.img_1.setVisibility(View.GONE);
            holder.img_2.setVisibility(View.GONE);
            holder.img_3.setVisibility(View.GONE);
            holder.img_4.setVisibility(View.GONE);
        } else if (i == 2) {
            holder.img_1.setVisibility(View.GONE);
            holder.img_2.setVisibility(View.GONE);
            holder.img_3.setVisibility(View.GONE);
        } else if (i == 3) {
            holder.img_1.setVisibility(View.GONE);
            holder.img_2.setVisibility(View.GONE);
        } else if (i == 4) {
            holder.img_1.setVisibility(View.GONE);
        } else if (i == 5) {
        }


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        //RelativeLayout relativeLayout;
        TextView tvTotaldeals, tvPlacename, tvPlaceAddress;
        ImageView img_1, img_2, img_3, img_4, img_5, imgBg;

        public Holder(View itemView) {
            super(itemView);
            imgBg = itemView.findViewById(R.id.imageview_item);
            tvTotaldeals = itemView.findViewById(R.id.txt_total_deals);
            tvPlacename = itemView.findViewById(R.id.txt_place_name);
            tvPlaceAddress = itemView.findViewById(R.id.txt_place_address);
            img_1 = itemView.findViewById(R.id.img_rating_1);
            img_2 = itemView.findViewById(R.id.img_rating_2);
            img_3 = itemView.findViewById(R.id.img_rating_3);
            img_4 = itemView.findViewById(R.id.img_rating_4);
            img_5 = itemView.findViewById(R.id.img_rating_5);

        }
    }

}