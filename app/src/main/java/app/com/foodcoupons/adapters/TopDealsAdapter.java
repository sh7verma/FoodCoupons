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

import java.util.ArrayList;

import app.com.foodcoupons.R;
import app.com.foodcoupons.activities.DealDetailActivity;
import app.com.foodcoupons.customviews.CircleTransform;
import app.com.foodcoupons.models.DealModel;
import app.com.foodcoupons.utils.Const;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dev on 8/10/18.
 */

public class TopDealsAdapter extends RecyclerView.Adapter<TopDealsAdapter.Holder> {

    Context mContext;
    ArrayList<DealModel.DealsBean> mData;
    int mHeight;

    public TopDealsAdapter(Context context, ArrayList<DealModel.DealsBean> mData, int mHeight) {
        mContext = context;
        this.mData = mData;
        this.mHeight = mHeight;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_top_deals, parent, false);
        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final TopDealsAdapter.Holder holder, int position) {
        Picasso.get()
                .load(mData.get(position).getImage())
                .transform(new CircleTransform())
                .resize((int) (mHeight * 0.2), (int) (mHeight * 0.2))
                .centerCrop()
                .into(holder.imgTopDeal);

        holder.txtDealOffer.setText(mData.get(position).getDescription());
        holder.llMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, DealDetailActivity.class);
                intent.putExtra(Const.EXTRA, mData.get(holder.getAdapterPosition()));
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        @BindView(R.id.ll_main)
        LinearLayout llMain;
        @BindView(R.id.img_top_deal)
        ImageView imgTopDeal;
        @BindView(R.id.txt_get_deal)
        TextView txtGetDeal;
        @BindView(R.id.txt_deal_offer)
        TextView txtDealOffer;


        public Holder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
