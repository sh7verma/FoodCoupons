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
import java.util.List;

import app.com.foodcoupons.R;
import app.com.foodcoupons.activities.CategoryActivity;
import app.com.foodcoupons.customviews.CircleTransform;
import app.com.foodcoupons.models.CategoryModel;
import app.com.foodcoupons.utils.Const;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dev on 28/9/18.
 */

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.Holder> {

    Context mContext;
    List<CategoryModel.DataBean> mData = new ArrayList<>();
    int mHeight;

    public CategoryAdapter(Context context, List<CategoryModel.DataBean> list, int mHeight) {
        mContext = context;
        this.mData = list;
        this.mHeight = mHeight;
    }

    @NonNull
    @Override
    public CategoryAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.item_food_category, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CategoryAdapter.Holder holder, final int position) {

        Picasso.get()
                .load(mData.get(position).getImage())
                .transform(new CircleTransform())
                .resize((int) (mHeight * 0.2), (int) (mHeight * 0.2))
                .centerCrop()
                .into(holder.imgCategory);

        holder.txtCategoryName.setText(mData.get(position).getCategory());
        holder.llMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, CategoryActivity.class);
                intent.putExtra(Const.EXTRA,mData.get(holder.getAdapterPosition()));
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_category)
        ImageView imgCategory;
        @BindView(R.id.txt_category_name)
        TextView txtCategoryName;
        @BindView(R.id.ll_main)
        LinearLayout llMain;

        Holder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}
