package pl.pollub.nawigacjapollub;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
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
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

import pl.pollub.nawigacjapollub.DijkstraAlgorithm.Graph;

import static pl.pollub.nawigacjapollub.MenuActivity.REQUEST_ACCESS_FINE_LOCATION;
import static pl.pollub.nawigacjapollub.MenuActivity.REQUEST_SMS;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback
{
    private static final String TAG = MapsActivity.class.getSimpleName();
    private SupportMapFragment mapFragment;
    private GoogleMap mMap;
    private Polyline polyline = null;
    private Context context;
    private TextView textViewFloor;
    private final LatLngBounds weiiBounds = new LatLngBounds(                                        // Wierzchołki prostokąta do podmiany
            new LatLng(51.236381, 22.548308),                                                // SW
            new LatLng(51.237231, 22.549652));                                               // NE
    private final LatLng weiiMarker = new LatLng(51.236994, 22.54918);                       // Współrzędne markera WEII
    private final LatLng weiiCamera = new LatLng(51.237132, 22.54927);                       // Współrzędne do ustawienia kamery

    public int getFloor()
    {
        return floor;
    }

    public void setFloor(int floor)
    {
        this.floor = floor;
    }

    private int floor = 0;
    private List<LatLng> ground0 = new ArrayList<>();
    private List<LatLng> ground1 = new ArrayList<>();
    private List<LatLng> ground2 = new ArrayList<>();

    public final static int REQUEST_CODE_CHOOSE_ROUTE = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        this.context = this.getApplicationContext();

        textViewFloor = findViewById(R.id.textViewFloor);
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
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
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
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
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

            context = this.getApplicationContext();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        mMap = googleMap;

        try
        {
            boolean success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            this, R.raw.style_json));                                        // Dodanie stylu, który usuwa etykiety z mapy
            if (!success) Log.e(TAG, "Style parsing failed.");

        }
        catch (Resources.NotFoundException e)
        {
            Log.e(TAG, "Can't find style. Error: ", e);
        }

        showFloor();

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

        mMap.setMinZoomPreference(20.0f);                                                           //Ustawienie domyślnego zoomu na starcie
        mMap.moveCamera(CameraUpdateFactory.newLatLng(weiiCamera));                                 // Ustawienie kamery na wejście do WEII
        //mMap.getUiSettings().isZoomGesturesEnabled(true);                       //ustawienie zoom
        // Add a marker in Sydney and move the camera
        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(weii));
    }

    private void startChooseRouteActivity()
    {
        Intent intent = new Intent(this, ChooseRouteActivity.class);
        startActivityForResult(intent, REQUEST_CODE_CHOOSE_ROUTE);
    }

    private void showMessage(final int titleId, int messageId)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(MapsActivity.this);

        builder.setTitle(titleId);
        builder.setMessage(messageId);

        builder.setNeutralButton(R.string.messageButtonOk, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                if (titleId == R.string.failureMessageTitle) startChooseRouteActivity();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void callHelp()
    {
        SmsManager smsManager = SmsManager.getDefault();
        Navigation navigation = new Navigation(this.context);
        PointsDbHelper db = new PointsDbHelper(this.context);
        StringBuilder message = new StringBuilder();
        List<String> rooms;
        boolean success;

        LatLng position = navigation.localizeByWifi();

        if (position != null)
        {
            int idPoint = db.getPoint(position);
            rooms = db.getRooms(idPoint);

            message.append(getString(R.string.caretakerSuccessfulMessage));
            for (String room: rooms)
            {
                message.append(room);
                message.append(" ");
            }
            message.append(".");

            success = true;
        }
        else
        {
            message.append(getString(R.string.caretakerFailureMessage));
            success = false;
        }

        smsManager.sendTextMessage(
                getString(R.string.caretakerPhonenumber),
                null,
                message.toString(),
                null,
                null);

        if (success) this.showMessage(
                R.string.callHelpMessageTitle,
                R.string.callHelpSuccessfulMessage);
        else this.showMessage(
                R.string.callHelpMessageTitle,
                R.string.callHelpFailureMessage);
    }

    private void clearMap()
    {
        mMap.clear();
        if (polyline != null) polyline.remove();
    }

    private void clearPaths()
    {
        if (!this.ground0.isEmpty()) this.ground0.clear();
        if (!this.ground1.isEmpty()) this.ground1.clear();
        if (!this.ground2.isEmpty()) this.ground2.clear();
    }

    public void makePath(String startPoint, String finishPoint, boolean mode)
    {
        this.clearPaths();

        Graph graph = new Graph(this.context);
        Cursor cursorStartPoint, cursorFinishPoint;
        int startPointId = 0, finishPointId = 0;

        PointsDbHelper db = new PointsDbHelper(this.context);

        if (startPoint.equals("Twoja lokalizacja"))
        {
            WifiHelper wifiHelper = new WifiHelper(this.context);

            String[] macs = wifiHelper.getBestMacs(2);
            cursorStartPoint = db.getPoint(macs);

            if (cursorStartPoint != null)
            {
                cursorStartPoint.moveToNext();
                startPointId = cursorStartPoint.getInt(cursorStartPoint.getColumnIndexOrThrow(PointsContract.PointsEntry._ID));
            }
        }
        else
        {
            cursorStartPoint = db.getPoint(startPoint);
            cursorStartPoint.moveToNext();
            startPointId = cursorStartPoint.getInt(cursorStartPoint.getColumnIndexOrThrow(PointsContract.PointsEntry._ID));
        }

        cursorFinishPoint = db.getPoint(finishPoint);
        cursorFinishPoint.moveToNext();
        finishPointId = cursorFinishPoint.getInt(cursorFinishPoint.getColumnIndexOrThrow(PointsContract.PointsEntry._ID));

        if (startPointId != 0 && finishPointId != 0)
        {
            List<Integer> path = graph.calculateShortestPath(startPointId, finishPointId, mode);

            for (int i: path)
            {
                int whichFloor = db.getFloor(i);

                switch (whichFloor)
                {
                    case 0:
                        ground0.add(db.getCoordinates(i));
                        break;
                    case 1:
                        ground1.add(db.getCoordinates(i));
                        break;
                    case 2:
                        ground2.add(db.getCoordinates(i));
                        break;
                }
            }

            this.setFloor(db.getFloor(startPointId));
            showFloor();

            if (startPointId == finishPointId) this.showMessage(
                    R.string.reachedTargetMessageTitle, R.string.reachedTargetMessageBody);
        }
        else
        {
            showFloor();
            this.showMessage(R.string.failureMessageTitle, R.string.failureMessageBody);
        }
    }

    public void showFloor()
    {
        if (floor >= 0 && floor <= 2)
        {
            this.clearMap();
//            mMap.addMarker(new MarkerOptions().position(weiiMarker).draggable(true));                   // Dodanie markera WEII

            switch (floor)
            {
                // mMap.addMarker(new MarkerOptions().position(ground0.get(0)).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                case 0:
                    PolylineOptions polylineOptions0 = new PolylineOptions();
                    mMap.addGroundOverlay(new GroundOverlayOptions()
                            .image(BitmapDescriptorFactory.fromResource(R.drawable.weii_parter))
                            .positionFromBounds(weiiBounds));
                    textViewFloor.setText(R.string.floorGround);

                    if (!ground0.isEmpty())
                    {
                        mMap.addMarker(new MarkerOptions().position(ground0.get(0)).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(ground0.get(0)));
                        for (LatLng i: ground0)
                            polylineOptions0.add(i).width(10).color(Color.RED);

                        polyline = mMap.addPolyline(polylineOptions0);
                        mMap.addMarker(new MarkerOptions().position(ground0.get(ground0.size()-1)).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                    }
                    break;

                case 1:
                    PolylineOptions polylineOptions1 = new PolylineOptions();
                    mMap.addGroundOverlay(new GroundOverlayOptions()
                            .image(BitmapDescriptorFactory.fromResource(R.drawable.weii_i_pietro))
                            .positionFromBounds(weiiBounds));
                    textViewFloor.setText(R.string.floor1);

                    if (!ground1.isEmpty())
                    {
                        mMap.addMarker(new MarkerOptions().position(ground1.get(0)).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(ground1.get(0)));
                        for (LatLng i: ground1)
                            polylineOptions1.add(i).width(10).color(Color.RED);

                        polyline = mMap.addPolyline(polylineOptions1);
                        mMap.addMarker(new MarkerOptions().position(ground1.get(ground1.size()-1)).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                    }
                    break;

                case 2:
                    PolylineOptions polylineOptions2 = new PolylineOptions();
                    mMap.addGroundOverlay(new GroundOverlayOptions()
                            .image(BitmapDescriptorFactory.fromResource(R.drawable.weii_ii_pietro))
                            .positionFromBounds(weiiBounds));
                    textViewFloor.setText(R.string.floor2);

                    if (!ground2.isEmpty())
                    {
                        mMap.addMarker(new MarkerOptions().position(ground2.get(0)).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(ground2.get(0)));
                        for (LatLng i: ground2)
                            polylineOptions2.add(i).width(10).color(Color.RED);

                        polyline = mMap.addPolyline(polylineOptions2);
                        mMap.addMarker(new MarkerOptions().position(ground2.get(ground2.size()-1)).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                    }
                    break;
            }

            //Toast.makeText(this, "Piętro: " + this.getFloor(), Toast.LENGTH_SHORT).show();
        }
    }

    public void floorUp(View v)
    {
        if (floor != 2)
        {
            floor++;
            showFloor();
        }
    }

    public void floorDown(View v)
    {
        if (floor != 0)
        {
            floor--;
            showFloor();
        }
    }

    public void buttonCallHelpOnClick(View v)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(MapsActivity.this);

        builder.setMessage(R.string.callHelpInfoMessage);

        builder.setPositiveButton(R.string.messageButtonOk, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                callHelp();
            }
        });

        builder.setNegativeButton(R.string.messageButtonCancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {}
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void buttonLocalizeMeOnClick(View v)
    {
        Navigation navigation = new Navigation(this.context);
        LatLng position;

        position = navigation.localizeByWifi();

        if (position != null)
        {
            mMap.addMarker(new MarkerOptions().position(position).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(position));
        }
        else Toast.makeText(this.context, "Nie ustalono pozycji...", Toast.LENGTH_LONG).show();
    }

    public void buttonChooseRouteOnClick(View v)
    {
        this.startChooseRouteActivity();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_CODE_CHOOSE_ROUTE && resultCode == Activity.RESULT_OK)
        {
            Bundle bundle = data.getExtras();
            String startPoint = bundle.getString("startPoint");
            String finishPoint = bundle.getString("finishPoint");
            boolean mode = bundle.getBoolean("mode");

            this.makePath(startPoint, finishPoint, mode);
        }
    }
}
