package app.com.foodcoupons.helper;

import com.google.android.gms.location.LocationResult;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

import app.com.foodcoupons.models.FilterModel;

/**
 * Created by dev on 3/10/18.
 */

public class InterfacesCall {

    public interface LocationInterface {
        void onresume();

        void onpause();
    }

    public interface MapInterface {
        void onLocationUpdate(LocationResult location);

        void onMapReady(GoogleMap map);
    }

    public interface AddressSelected {
        void onAddressSelected();
    }

    public interface AdapterItemSelected {
        void onAdapterItemSelected(int position);
    }

    public interface GetPlaceById {
        void onGetPlace(LatLng latLng);
    }

    public interface FilterApplied {
        void onFilterApplied();
    }

    public interface CategoryFilterApplied {
        void onFilterApplied(FilterModel filterModel);
        void onClear(FilterModel filterModel);
    }
}
