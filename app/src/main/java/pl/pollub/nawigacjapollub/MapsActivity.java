package pl.pollub.nawigacjapollub;


import android.content.Intent;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import static pl.pollub.nawigacjapollub.MenuActivity.REQUEST_ACCESS_FINE_LOCATION;
import static pl.pollub.nawigacjapollub.MenuActivity.REQUEST_SMS;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private static final String TAG = MapsActivity.class.getSimpleName();
    private GoogleMap mMap;
    private Context context;
    Spinner spinnerFloorList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        this.context = this.getApplicationContext();
    }

    public void addRouteOnClick(View v) {
        Intent intent = new Intent(this, ChooseRouteActivity.class);
        startActivity(intent);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_SMS: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(), "Nadano uprawnienia wysyłania SMS",
                            Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Nie nadano uprawnień wysyłania SMS",
                            Toast.LENGTH_LONG).show();
                }
            }

            case REQUEST_ACCESS_FINE_LOCATION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(), "Nadano uprawnienia lokalizacji",
                            Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Nie nadano uprawnień lokalizacji",
                            Toast.LENGTH_LONG).show();
                }
            }

            context = this.getApplicationContext();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng weiiMarker = new LatLng(51.236994, 22.54918);                                // Współrzędne markera WEII
        LatLng weiiCamera = new LatLng(51.237132, 22.54927);                                   // Współrzędne do ustawienia kamery
        final LatLngBounds weiiBounds = new LatLngBounds(                                                  // Wierzchołki prostokąta do podmiany
                new LatLng(51.236381, 22.548308),                                            // SW
                new LatLng(51.237231, 22.549652));                                           // NE

        try {
            boolean success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            this, R.raw.style_json));
            // Dodanie stylu, który usuwa etykiety z mapy
            if (!success) Log.e(TAG, "Style parsing failed.");

        } catch (Resources.NotFoundException e) {
            Log.e(TAG, "Can't find style. Error: ", e);
        }

        //**************Utworzenie spinnera oraz zmiana map************************//
        spinnerFloorList = (Spinner) findViewById(R.id.spinnerFloorList);
        String[] floorList = {"Parter", "I piętro", "II piętro"};

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                floorList
        );

        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFloorList.setAdapter(spinnerAdapter);

        spinnerFloorList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        mMap.addGroundOverlay(new GroundOverlayOptions()
                                .image(BitmapDescriptorFactory.fromResource(R.drawable.weii_parter))                        // Podmiana wycinka mapy
                                .positionFromBounds(weiiBounds));
                        break;
                    case 1:
                        mMap.addGroundOverlay(new GroundOverlayOptions()
                                .image(BitmapDescriptorFactory.fromResource(R.drawable.weii_i_pietro))                        // Podmiana wycinka mapy
                                .positionFromBounds(weiiBounds));

                        break;
                    case 2:
                        mMap.addGroundOverlay(new GroundOverlayOptions()
                                .image(BitmapDescriptorFactory.fromResource(R.drawable.weii_ii_pietro))                        // Podmiana wycinka mapy
                                .positionFromBounds(weiiBounds));
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //*************************************************************************//


        mMap.addGroundOverlay(new GroundOverlayOptions()
                .image(BitmapDescriptorFactory.fromResource(R.drawable.weii_parter))                        // Podmiana wycinka mapy
                .positionFromBounds(weiiBounds));

        mMap.addMarker(new MarkerOptions().position(weiiMarker).draggable(true));                   // Dodanie markera WEII
        mMap.moveCamera(CameraUpdateFactory.newLatLng(weiiCamera));                                 // Ustawienie kamery na wejście do WEII
        mMap.setMinZoomPreference(20.0f);                                                           //Ustawienie domyślnego zoomu na starcie

        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener()                           // do pomiarów
        {
            @Override
            public void onMarkerDragStart(Marker marker) {

            }

            @Override
            public void onMarkerDrag(Marker marker) {

            }

            @Override
            public void onMarkerDragEnd(Marker marker) {
                Toast.makeText(getApplicationContext(), "N: " + String.valueOf(marker.getPosition().latitude) + "\nE: " + marker.getPosition().longitude, Toast.LENGTH_LONG).show();
            }
        });

        //mMap.getUiSettings().isZoomGesturesEnabled(true);                       //ustawienie zoom
        // Add a marker in Sydney and move the camera
        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(weii));
    }

    public void buttonLocalizeMeOnClick(View v) {
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
