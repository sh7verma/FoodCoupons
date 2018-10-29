package app.com.foodcoupons.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import app.com.foodcoupons.R;
import app.com.foodcoupons.helper.InterfacesCall;
import app.com.foodcoupons.models.GooglePlaceModal;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dev on 4/10/18.
 */

public class NearByPlaceAdapter extends RecyclerView.Adapter<NearByPlaceAdapter.Holder> {

    static InterfacesCall.AdapterItemSelected mAddressSelected;
    Context context;
    List<GooglePlaceModal.PredictionsBean> mData;

    public NearByPlaceAdapter(Context context, List<GooglePlaceModal.PredictionsBean> mPlaceArrayList) {
        this.context = context;
        mData = mPlaceArrayList;
    }

    public static void onItemSelected(InterfacesCall.AdapterItemSelected addressSelected) {
        mAddressSelected = addressSelected;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_near_by_places, parent, false);
        return new NearByPlaceAdapter.Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final Holder holder, int position) {
        holder.txtPlace.setText(mData.get(position).getDescription());
        holder.txtPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAddressSelected.onAdapterItemSelected(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        @BindView(R.id.txt_place)
        TextView txtPlace;

        public Holder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
