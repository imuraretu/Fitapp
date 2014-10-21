package ro.codepirates.fitapp;

import java.util.ArrayList;

import android.app.Fragment;
import android.os.Bundle;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;

public class Map_Fragment extends Fragment {

	@SuppressWarnings("unused")
	private static final String ARG_SECTION_NUMBER = "section_number";
	private static View view;
	/**
	 * Note that this may be null if the Google Play services APK is not
	 * available.
	 */

	private static GoogleMap mMap;
	ArrayList<LatLng> pList = new ArrayList<LatLng>();
	double lat;
	double lng;
	double speed;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		/*
		 * if (container == null) { return null; } view =
		 * inflater.inflate(R.layout.map_view, container, false);
		 * 
		 * setUpMapIfNeeded(); // For setting up the MapFragment
		 * 
		 * return view;
		 */
		if (view != null) {
			ViewGroup parent = (ViewGroup) view.getParent();
			if (parent != null)
				parent.removeView(view);
		}
		try {
			view = inflater.inflate(R.layout.fragment_map, container, false);
		} catch (InflateException e) {
			/* map is already there, just return view as it is */
		}

		return view;
		
	}

	
	/***** Sets up the map if it is possible to do so *****/
	/*
	 * public void setUpMapIfNeeded() { // Do a null check to confirm that we
	 * have not already instantiated the map. if (mMap == null) { // Try to
	 * obtain the map from the SupportMapFragment. mMap = ((SupportMapFragment)
	 * MainActivity.fragmentManager .findFragmentById(R.id.map)).getMap(); //
	 * Check if we were successful in obtaining the map. if (mMap != null)
	 * setUpMap(); } }
	 */

	/**
	 * This is where we can add markers or lines, add listeners or move the
	 * camera.
	 * <p>
	 * This should only be called once and when we are sure that {@link #mMap}
	 * is not null.
	 */
	private void setUpMap() {
		// For showing a move to my loction button
		mMap.setMyLocationEnabled(true);
		setUpOverlay();
	}

	public void setUpOverlay() {

	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		if (mMap != null)
			setUpMap();

		if (mMap == null) {
			// Try to obtain the map from the SupportMapFragment.
			mMap = ((MapFragment) MainActivity.fragmentManager
					.findFragmentById(R.id.map)).getMap();
			// Check if we were successful in obtaining the map.
			// if (mMap != null)
			setUpMap();
		}
	}

	/****
	 * The mapfragment's id must be removed from the FragmentManager or else if
	 * the same it is passed on the next time then app will crash
	 ****/
	public void onDestroyView() {
		/*
		 * if (mMap != null) { MainActivity.fragmentManager.beginTransaction()
		 * .remove
		 * (MainActivity.fragmentManager.findFragmentById(R.id.map)).commit();
		 * mMap = null; }
		 */
		Fragment f = (Fragment) getFragmentManager().findFragmentById(R.id.map);
		if (f != null) {
			getFragmentManager().beginTransaction().remove(f).commit();
		}
		super.onDestroyView();
	}

}