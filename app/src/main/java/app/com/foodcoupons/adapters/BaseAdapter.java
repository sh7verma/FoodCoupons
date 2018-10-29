package app.com.foodcoupons.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import app.com.foodcoupons.helper.InterfacesCall;
import butterknife.ButterKnife;

/**
 * Created by dev on 5/10/18.
 */

public abstract class BaseAdapter extends RecyclerView.Adapter<BaseAdapter.BaseHolder> {

    InterfacesCall.AdapterItemSelected mAddressSelected;

    public void onItemSelected(InterfacesCall.AdapterItemSelected addressSelected) {
        mAddressSelected = addressSelected;
    }

    abstract int onCreateView();

    abstract Context getContext();

    abstract int getCount();

    abstract void onViewHolder(@NonNull final BaseHolder holder, int position);

    @NonNull
    @Override
    public BaseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(getContext()).inflate(onCreateView(), parent, false);
        return new BaseHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final BaseHolder holder, int position) {
        onViewHolder(holder, position);
    }

    @Override
    public int getItemCount() {
        return getCount();
    }

    protected abstract View getClassView();

    public class BaseHolder extends RecyclerView.ViewHolder {

        public BaseHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, getClassView());
        }
    }
}