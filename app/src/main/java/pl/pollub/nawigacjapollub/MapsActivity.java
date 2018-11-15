package pl.pollub.nawigacjapollub;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback
{
    private static final String TAG = MapsActivity.class.getSimpleName();
    private GoogleMap mMap;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        context = this.getApplicationContext();
    }

    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        mMap = googleMap;

        LatLng weiiMarker = new LatLng(51.236994, 22.54918);                                // Współrzędne markera WEII
        LatLng weiiCamera = new LatLng(51.237132, 22.54927);                                   // Współrzędne do ustawienia kamery
        LatLngBounds weiiBounds = new LatLngBounds(                                                  // Wierzchołki prostokąta do podmiany
                new LatLng(51.236381, 22.548308),                                            // SW
                new LatLng(51.237231, 22.549652));                                           // NE

        try
        {
            boolean success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            this, R.raw.style_json));
                                                                                                    // Dodanie stylu, który usuwa etykiety z mapy
            if (!success) Log.e(TAG, "Style parsing failed.");

        }
        catch (Resources.NotFoundException e)
        {
            Log.e(TAG, "Can't find style. Error: ", e);
        }


        mMap.addGroundOverlay(new GroundOverlayOptions()
        .image(BitmapDescriptorFactory.fromResource(R.drawable.weii_parter))                        // Podmiana wycinka mapy
        .positionFromBounds(weiiBounds));

        mMap.addMarker(new MarkerOptions().position(weiiMarker).draggable(true));                   // Dodanie markera WEII
        mMap.moveCamera(CameraUpdateFactory.newLatLng(weiiCamera));                                 // Ustawienie kamery na wejście do WEII
        mMap.setMinZoomPreference(20.0f);                                                           //Ustawienie domyślnego zoomu na starcie

        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener()                           // do pomiarów
        {
            @Override
            public void onMarkerDragStart(Marker marker)
            {

            }

            @Override
            public void onMarkerDrag(Marker marker)
            {

            }

            @Override
            public void onMarkerDragEnd(Marker marker)
            {
                Toast.makeText(getApplicationContext(), "N: "+ String.valueOf(marker.getPosition().latitude) + "\nE: " + marker.getPosition().longitude, Toast.LENGTH_LONG).show();
            }
        });

        //mMap.getUiSettings().isZoomGesturesEnabled(true);                       //ustawienie zoom
        // Add a marker in Sydney and move the camera
        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(weii));
    }

    public void buttonLocalizeMeOnClick(View v)
    {
        WifiHelper wifiHelper = new WifiHelper(this.context);
        Navigation navigation = new Navigation(this.context);
        LatLng position;

        String[] macs = wifiHelper.getBestMacs(2);

        position = navigation.localize(macs);

        if (position != null)
        {
            mMap.addMarker(new MarkerOptions().position(position).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(position));
        }
        else Toast.makeText(this.context, "Nie ustalono pozycji...", Toast.LENGTH_LONG).show();
    }
}