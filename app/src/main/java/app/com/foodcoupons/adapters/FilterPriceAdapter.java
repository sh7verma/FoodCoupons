package app.com.foodcoupons.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import app.com.foodcoupons.R;
import app.com.foodcoupons.models.FilterModel;
import app.com.foodcoupons.models.FilterSingletonModel;

/**
 * Created by dev on 8/10/18.
 */

public class FilterPriceAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context mContext;
    List<FilterModel.PriceModel> mList = new ArrayList<>();
    Integer mSelection = null;
    boolean mInbind = false;


    public FilterPriceAdapter(Context context, List<FilterModel.PriceModel> list) {
        mContext = context;
        mList = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.item_price, parent, false);
        return new MyView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {

        MyView view = (MyView) holder;
        mInbind = true;
        view.txtPrice.setText(mList.get(position).getMin() + "-" + mList.get(position).getMax());
        if (mList.get(position).isSelected()) {
            view.rbPrice.setChecked(true);
            mSelection = position;
        } else {
            view.rbPrice.setChecked(false);
        }
        mInbind = false;
    }


    @Override
    public int getItemCount() {
        return mList.size();
    }

    private class MyView extends RecyclerView.ViewHolder {

        TextView txtPrice;
        RadioButton rbPrice;

        public MyView(View itemView) {
            super(itemView);

            txtPrice = itemView.findViewById(R.id.txt_price);
            rbPrice = itemView.findViewById(R.id.rb_price);
            rbPrice.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    if (!mInbind) {
                        if (mSelection != null) {
                            mList.get(mSelection).setSelected(false);
                            notifyItemChanged(mSelection);
                            mList.get(getAdapterPosition()).setSelected(true);
                            mSelection = getAdapterPosition();

                        } else {
                            mList.get(getAdapterPosition()).setSelected(true);
                            mSelection = getAdapterPosition();
                            notifyItemChanged(mSelection);
                        }
                    }
                }
            });

        }
    }
}
