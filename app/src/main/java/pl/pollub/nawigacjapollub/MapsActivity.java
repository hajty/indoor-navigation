package pl.pollub.nawigacjapollub;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.location.LocationManager;
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

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback
{
    private static final String TAG = MapsActivity.class.getSimpleName();
    private GoogleMap mMap;
    private Polyline polyline = null;
    private Context context;
    private TextView textViewFloor;
    private int startFloor;
    private int finishFloor;
    private boolean mode;
    private final LatLngBounds weiiBounds = new LatLngBounds(                                        // Wierzchołki prostokąta do podmiany
            new LatLng(51.236381, 22.548308),                                                // SW
            new LatLng(51.237231, 22.549652));                                               // NE
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

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        this.context = this.getApplicationContext();
        textViewFloor = findViewById(R.id.textViewFloor);
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

        builder.setNeutralButton(R.string.button_message_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                if (titleId == R.string.localization_failure_message_title ||
                    titleId == R.string.locationofff_title)
                    startChooseRouteActivity();
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
        ArrayList<String> parts;
        List<String> rooms;
        boolean success;

        LatLng position = navigation.localizeByWifi();

        if (position != null)
        {
            int idPoint = db.getPoint(position);
            rooms = db.getRooms(idPoint);

            message.append(getString(R.string.caretaker_message_successful));
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
            message.append(getString(R.string.caretaker_message_failure));
            success = false;
        }

        parts = smsManager.divideMessage(message.toString());

        smsManager.sendMultipartTextMessage(
                /*USTAW NUMER TELEFONU DOZORCY W strings.xml!!!*/
                getString(R.string.caretaker_phonenumber),
                null,
                parts,
                null,
                null);

        if (success) this.showMessage(
                R.string.callhelp_message_title,
                R.string.callhelp_message_successful);
        else this.showMessage(
                R.string.callhelp_message_title,
                R.string.callhelp_message_failure);
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
        int startPointId = 0, finishPointId = 0;

        PointsDbHelper db = new PointsDbHelper(this.context);

        if (startPoint.equals("Twoja lokalizacja"))
        {
            Navigation navigation = new Navigation(this.context);
            LatLng coordinates = navigation.localizeByWifi();

            if (coordinates != null) startPointId = db.getPoint(coordinates);
        }
        else
        {
            startPointId = db.getPoint(startPoint);
        }

        finishPointId = db.getPoint(finishPoint);

        if (startPointId != 0 && finishPointId != 0)
        {
            List<Integer> path = graph.calculateShortestPath(startPointId, finishPointId, mode);
            this.startFloor = db.getFloor(startPointId);
            this.finishFloor = db.getFloor(finishPointId);

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
                    R.string.reachedtarget_message_title, R.string.reachedtarget_message_body);
        }
        else
        {
            showFloor();
            LocationManager locationManager = (LocationManager) context.getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
            if (locationManager.isLocationEnabled())
            {
                this.showMessage(R.string.localization_failure_message_title, R.string.localization_failure_message_body);
            }
            else
            {
                this.showMessage(R.string.locationofff_title, R.string.locationoff_body);
            }
        }
    }

    public void showFloor()
    {
        Marker startPoint = null;
        Marker finishPoint = null;

        if (this.getFloor() >= 0 && this.getFloor() <= 2)
        {
            this.clearMap();

            switch (this.getFloor())
            {
                case 0:
                    PolylineOptions polylineOptions0 = new PolylineOptions();
                    mMap.addGroundOverlay(new GroundOverlayOptions()
                            .image(BitmapDescriptorFactory.fromResource(R.drawable.weii_parter))
                            .positionFromBounds(weiiBounds));
                    textViewFloor.setText(R.string.label_floor_ground);

                    if (!ground0.isEmpty())
                    {
                        startPoint = mMap.addMarker(new MarkerOptions().position(ground0.get(0))
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.start_point_icon))
                                .title(getString(R.string.path_startpoint_title))
                                .snippet(getString(R.string.path_startpoint_body)));

                        mMap.moveCamera(CameraUpdateFactory.newLatLng(ground0.get(0)));
                        for (LatLng i: ground0)
                            polylineOptions0.add(i).width(10).color(Color.RED);

                        polyline = mMap.addPolyline(polylineOptions0);

                        if (finishFloor == 0)
                        {
                            finishPoint = mMap.addMarker(new MarkerOptions().position(ground0.get(ground0.size()-1))
                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.finish_point_icon))
                                    .title(getString(R.string.path_finishpoint_title))
                                    .snippet(getString(R.string.path_finishpoint_body)));
                        }
                        else
                        {
                            if (!mode)
                            {
                                finishPoint = mMap.addMarker(new MarkerOptions()
                                        .position(ground0.get(ground0.size()-1))
                                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.staircase_icon))
                                        .title(getString(R.string.path_stairs_title))
                                        .snippet(getString(R.string.path_stairs_body_1) + " "
                                                + String.valueOf(finishFloor) + " "
                                                + getString(R.string.path_stairs_body_2)));
                            }
                            else
                            {
                                finishPoint = mMap.addMarker(new MarkerOptions()
                                        .position(ground0.get(ground0.size()-1))
                                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.elevator_icon))
                                        .title(getString(R.string.path_elevator_title))
                                        .snippet(getString(R.string.path_elevator_body_1) + " "
                                                + String.valueOf(finishFloor) + " "
                                                + getString(R.string.path_elevator_body_2)));
                            }
                        }
                    }
                    break;

                case 1:
                    PolylineOptions polylineOptions1 = new PolylineOptions();
                    mMap.addGroundOverlay(new GroundOverlayOptions()
                            .image(BitmapDescriptorFactory.fromResource(R.drawable.weii_i_pietro))
                            .positionFromBounds(weiiBounds));
                    textViewFloor.setText(R.string.label_floor_1);

                    if (!ground1.isEmpty())
                    {
                        startPoint = mMap.addMarker(new MarkerOptions().position(ground1.get(0))
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.start_point_icon))
                                .title(getString(R.string.path_startpoint_title))
                                .snippet(getString(R.string.path_startpoint_body)));

                        mMap.moveCamera(CameraUpdateFactory.newLatLng(ground1.get(0)));
                        for (LatLng i: ground1)
                            polylineOptions1.add(i).width(10).color(Color.RED);

                        polyline = mMap.addPolyline(polylineOptions1);

                        if (finishFloor == 1)
                        {
                            finishPoint = mMap.addMarker(new MarkerOptions().position(ground1.get(ground1.size()-1))
                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.finish_point_icon))
                                    .title(getString(R.string.path_finishpoint_title))
                                    .snippet(getString(R.string.path_finishpoint_body)));
                        }
                        else
                        {
                            if (!mode)
                            {
                                finishPoint = mMap.addMarker(new MarkerOptions()
                                        .position(ground1.get(ground1.size()-1))
                                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.staircase_icon))
                                        .title(getString(R.string.path_stairs_title))
                                        .snippet(getString(R.string.path_stairs_body_1) + " "
                                                + String.valueOf(finishFloor) + " "
                                                + getString(R.string.path_stairs_body_2)));
                            }
                            else
                            {
                                finishPoint = mMap.addMarker(new MarkerOptions()
                                        .position(ground1.get(ground1.size()-1))
                                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.elevator_icon))
                                        .title(getString(R.string.path_elevator_title))
                                        .snippet(getString(R.string.path_elevator_body_1) + " "
                                                + String.valueOf(finishFloor) + " "
                                                + getString(R.string.path_elevator_body_2)));
                            }
                        }
                    }
                    break;

                case 2:
                    PolylineOptions polylineOptions2 = new PolylineOptions();
                    mMap.addGroundOverlay(new GroundOverlayOptions()
                            .image(BitmapDescriptorFactory.fromResource(R.drawable.weii_ii_pietro))
                            .positionFromBounds(weiiBounds));
                    textViewFloor.setText(R.string.label_floor_2);

                    if (!ground2.isEmpty())
                    {
                        startPoint = mMap.addMarker(new MarkerOptions().position(ground2.get(0))
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.start_point_icon))
                                .title(getString(R.string.path_startpoint_title))
                                .snippet(getString(R.string.path_startpoint_body)));


                        mMap.moveCamera(CameraUpdateFactory.newLatLng(ground2.get(0)));
                        for (LatLng i: ground2)
                            polylineOptions2.add(i).width(10).color(Color.RED);

                        polyline = mMap.addPolyline(polylineOptions2);

                        if (finishFloor == 2)
                        {
                            finishPoint = mMap.addMarker(new MarkerOptions()
                                    .position(ground2.get(ground2.size()-1))
                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.finish_point_icon))
                                    .title(getString(R.string.path_finishpoint_title))
                                    .snippet(getString(R.string.path_finishpoint_body)));
                        }
                        else
                        {
                            if (!mode)
                            {
                                finishPoint = mMap.addMarker(new MarkerOptions()
                                        .position(ground2.get(ground2.size()-1))
                                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.staircase_icon))
                                        .title(getString(R.string.path_stairs_title))
                                        .snippet(getString(R.string.path_stairs_body_1) + " "
                                                + String.valueOf(finishFloor) + " "
                                                + getString(R.string.path_stairs_body_2)));
                            }
                            else
                            {
                                finishPoint = mMap.addMarker(new MarkerOptions()
                                        .position(ground2.get(ground2.size()-1))
                                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.elevator_icon))
                                        .title(getString(R.string.path_elevator_title))
                                        .snippet(getString(R.string.path_elevator_body_1) + " "
                                                + String.valueOf(finishFloor) + " "
                                                + getString(R.string.path_elevator_body_2)));
                            }
                        }
                    }
                    break;
            }
            if (startPoint != null) startPoint.showInfoWindow();
            if (finishPoint != null) finishPoint.showInfoWindow();
        }
    }

    public void floorUp(View v)
    {
        if (this.getFloor() != 2)
        {
            floor++;
            showFloor();
        }
    }

    public void floorDown(View v)
    {
        if (this.getFloor() != 0)
        {
            floor--;
            showFloor();
        }
    }

    public void buttonCallHelpOnClick(View v)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(MapsActivity.this);

        builder.setMessage(R.string.callhelp_message_info);

        builder.setPositiveButton(R.string.button_message_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                callHelp();
            }
        });

        builder.setNegativeButton(R.string.button_message_cancel, new DialogInterface.OnClickListener() {
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
        String startPoint = "";
        String finishPoint = "";

        if(requestCode == REQUEST_CODE_CHOOSE_ROUTE && resultCode == Activity.RESULT_OK)
        {
            Bundle bundle = data.getExtras();
            try
            {
                startPoint = bundle.getString("startPoint");
                finishPoint = bundle.getString("finishPoint");
            }catch (NullPointerException e)
            {
                e.printStackTrace();
            }
            this.mode = bundle.getBoolean("mode");

            this.makePath(startPoint, finishPoint, this.mode);
        }
    }
}