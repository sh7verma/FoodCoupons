package app.com.foodcoupons.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import app.com.foodcoupons.R;
import app.com.foodcoupons.models.FilterModel;
import app.com.foodcoupons.models.FilterSingletonModel;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dev on 8/10/18.
 */

public class FilterDataAdapter extends RecyclerView.Adapter<FilterDataAdapter.Holder> {

    private Context mContext;
    private List<FilterModel.BrandModel> mList = new ArrayList<>();

    public FilterDataAdapter(Context context, List<FilterModel.BrandModel> list) {
        mContext = context;
        mList = list;
    }

    @NonNull
    @Override
    public FilterDataAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.item_store, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final FilterDataAdapter.Holder holder, int position) {

        holder.txtName.setText(mList.get(position).getName());

        if (mList.get(position).isSelected()) {
            holder.imgSelected.setImageResource(R.mipmap.ic_brand_btn_s);
        } else {
            holder.imgSelected.setImageResource(R.mipmap.ic_brand_btn);
        }

        holder.llMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mList.get(holder.getAdapterPosition()).isSelected()) {
                    holder.imgSelected.setImageResource(R.mipmap.ic_brand_btn);
                    mList.get(holder.getAdapterPosition()).setSelected(false);
                } else {
                    holder.imgSelected.setImageResource(R.mipmap.ic_brand_btn_s);
                    mList.get(holder.getAdapterPosition()).setSelected(true);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class Holder extends RecyclerView.ViewHolder {

        @BindView(R.id.ll_main)
        LinearLayout llMain;
        @BindView(R.id.txt_item_store)
        TextView txtName;

        @BindView(R.id.img_selected)
        ImageView imgSelected;

        public Holder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}