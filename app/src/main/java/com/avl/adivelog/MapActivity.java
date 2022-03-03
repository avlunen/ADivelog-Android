package com.avl.adivelog;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.ItemizedOverlayWithFocus;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.OverlayItem;

import java.util.ArrayList;

public class MapActivity extends AppCompatActivity {
   private final int REQUEST_PERMISSIONS_REQUEST_CODE = 1;
   private MapView map = null;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);

      // load/initialize the osmdroid configuration, this can be done
      Context ctx = getApplicationContext();
      Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));

      // inflate and create the map
      setContentView(R.layout.activity_map);
      map = (MapView) findViewById(R.id.mapview);
      map.setTileSource(TileSourceFactory.MAPNIK);

      requestPermissionsIfNecessary(new String[] {
            // if you need to show the current location, uncomment the line below
            // Manifest.permission.ACCESS_FINE_LOCATION,
            // WRITE_EXTERNAL_STORAGE is required in order to show the map
            Manifest.permission.WRITE_EXTERNAL_STORAGE
      });

      // get sites
      Intent intent = getIntent();
      ArrayList<OverlayItem> items = new ArrayList<OverlayItem>();
      ArrayList<SiteData> sdl = intent.getParcelableArrayListExtra("SPOTS");

      for(SiteData sd : sdl) {
         //items.add(new OverlayItem(sd.getM_site_name(), "...", new GeoPoint(sd.getM_latitude(),sd.getM_longitude()))); // Lat/Lon decimal degrees
         Marker m = new Marker(map);
         m.setPosition(new GeoPoint(sd.getM_latitude(),sd.getM_longitude()));
         m.setTitle(sd.getM_site_name());
         m.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_CENTER);
         //m.setIcon(getResources().getDrawable(R.mipmap.ic_marker));
         map.getOverlays().add(m);
      }

      map.setMultiTouchControls(true);
      IMapController mapController = map.getController();
      mapController.setZoom(4);
      GeoPoint startPoint = new GeoPoint(48.8583, 2.2944);
      mapController.setCenter(startPoint);

      map.setMinZoomLevel(4.0);
      map.setMaxZoomLevel(17.0);
      map.setHorizontalMapRepetitionEnabled(false);
      map.setVerticalMapRepetitionEnabled(false);
      map.setScrollableAreaLimitLatitude(MapView.getTileSystem().getMaxLatitude(),
            MapView.getTileSystem().getMinLatitude(), 0);

      map.invalidate();
   }
   @Override
   public void onResume() {
      super.onResume();
      //this will refresh the osmdroid configuration on resuming.
      //if you make changes to the configuration, use
      //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
      //Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this));
      map.onResume(); //needed for compass, my location overlays, v6.0.0 and up
   }

   @Override
   public void onPause() {
      super.onPause();
      //this will refresh the osmdroid configuration on resuming.
      //if you make changes to the configuration, use
      //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
      //Configuration.getInstance().save(this, prefs);
      map.onPause();  //needed for compass, my location overlays, v6.0.0 and up
   }

   @Override
   public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
      ArrayList<String> permissionsToRequest = new ArrayList<>();
      for (int i = 0; i < grantResults.length; i++) {
         permissionsToRequest.add(permissions[i]);
      }
      if (permissionsToRequest.size() > 0) {
         ActivityCompat.requestPermissions(
               this,
               permissionsToRequest.toArray(new String[0]),
               REQUEST_PERMISSIONS_REQUEST_CODE);
      }
   }

   private void requestPermissionsIfNecessary(String[] permissions) {
      ArrayList<String> permissionsToRequest = new ArrayList<>();
      for (String permission : permissions) {
         if (ContextCompat.checkSelfPermission(this, permission)
               != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            permissionsToRequest.add(permission);
         }
      }
      if (permissionsToRequest.size() > 0) {
         ActivityCompat.requestPermissions(
               this,
               permissionsToRequest.toArray(new String[0]),
               REQUEST_PERMISSIONS_REQUEST_CODE);
      }
   }
}