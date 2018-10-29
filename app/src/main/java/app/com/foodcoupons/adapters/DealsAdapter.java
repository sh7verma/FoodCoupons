package app.com.foodcoupons.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import app.com.foodcoupons.R;
import app.com.foodcoupons.activities.DealDetailActivity;
import app.com.foodcoupons.models.DealModel;
import app.com.foodcoupons.utils.Const;
import app.com.foodcoupons.utils.Utils;

/**
 * Created by dev on 28/9/18.
 */

public class DealsAdapter extends RecyclerView.Adapter<DealsAdapter.Holder> {

    Context mContext;
    List<DealModel.DealsBean> mList;
    Utils utils;

    public DealsAdapter(Context context, List<DealModel.DealsBean> mDealList) {
        mContext = context;
        this.mList = mDealList;
        utils = new Utils(mContext);
    }

    @NonNull
    @Override
    public DealsAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(mContext);
        View v = inflater.inflate(R.layout.iteam_deal, parent, false);
        return new Holder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull final DealsAdapter.Holder holder, int position) {

        holder.txtDealOffer.setText(mList.get(position).getDescription());

        holder.txtValidTill.setText(mContext.getString(R.string.valid_till) + " " + utils.formatChange(mList.get(position).getValid_upto()));

        holder.txtDealName.setText(mList.get(position).getDeal_name());

        Picasso.get()
                .load(mList.get(position).getImage())
                .into(holder.imgDeal);

        holder.llMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, DealDetailActivity.class);
                intent.putExtra(Const.EXTRA,mList.get(holder.getAdapterPosition()));
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        ImageView imgDeal;
        TextView txtDealOffer;
        TextView txtValidTill;
        TextView txtDealName;
        LinearLayout llMain;

        public Holder(View itemView) {
            super(itemView);
            imgDeal = itemView.findViewById(R.id.img_deal);
            txtDealOffer = itemView.findViewById(R.id.txt_deal_offer);
            txtValidTill = itemView.findViewById(R.id.txt_valid_till);
            txtDealName = itemView.findViewById(R.id.txt_deal_name);
            llMain = itemView.findViewById(R.id.ll_main);
        }
    }

}
