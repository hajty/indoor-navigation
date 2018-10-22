package pl.pollub.nawigacjapollub;

import android.content.pm.PackageManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback
{
    public final static int REQUEST_SMS = 100;
    public final static int REQUEST_ACCESS_FINE_LOCATION = 200;
    public final static int REQUEST_ACCESS_WIFI = 300;
    public final static int REQUEST_CHANGE_WIFI = 400;

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults)
    {
        switch (requestCode)
        {
            case REQUEST_SMS:
            {
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    Toast.makeText(getApplicationContext(), "Nadano uprawnienia wysyłania SMS",
                            Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Nie nadano uprawnień wysyłania SMS",
                            Toast.LENGTH_LONG).show();
                }
            }

            case REQUEST_ACCESS_FINE_LOCATION:
            {
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    Toast.makeText(getApplicationContext(), "Nadano uprawnienia lokalizacji",
                            Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Nie nadano uprawnień lokalizacji",
                            Toast.LENGTH_LONG).show();
                }
            }

            case REQUEST_ACCESS_WIFI:
            {
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    Toast.makeText(getApplicationContext(), "Nadano uprawnienia stanu sieci Wi-Fi",
                            Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Nie nadano uprawnień stanu sieci Wi-Fi",
                            Toast.LENGTH_LONG).show();
                }
            }

            case REQUEST_CHANGE_WIFI:
            {
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    Toast.makeText(getApplicationContext(), "Nadano uprawnienia zmiany sieci Wi-Fi",
                            Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Nie nadano uprawnień zmiany sieci Wi-Fi",
                            Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        mMap = googleMap;

        LatLng weii = new LatLng(51.236381, 22.548308);                    // Marker WEII
        LatLngBounds weiiBounds = new LatLngBounds(                               // Wierzchołki prostokąta do podmiany
                new LatLng(51.236381, 22.548308),                           // SW
                new LatLng(51.237231, 22.549652));                          // NE

        mMap.addGroundOverlay(new GroundOverlayOptions()
        .image(BitmapDescriptorFactory.fromResource(R.drawable.weii_parter))                       // Podmiana wycinka mapy
        .positionFromBounds(weiiBounds));

        mMap.addMarker(new MarkerOptions().position(weii).draggable(true));                      // Dodanie markera WEII
        mMap.moveCamera(CameraUpdateFactory.newLatLng(weii));                                   // Ustawienie kamery na marker WEII

        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener()                       // do pomiarów
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
}