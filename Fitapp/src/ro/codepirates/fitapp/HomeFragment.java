package ro.codepirates.fitapp;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

public class HomeFragment extends Fragment {

	GPSReciverService mGPSService;

	TextView mSpeedView;
	TextView mDistanceView;
	TextView mEnergyView;
	TextView mAltitudeView;

	Communicator com;
	Context context;
	private Chronometer crono;
	private long timeWhenStopped;

	private Button stopButton;
	private Button startButton;

	/*
	 * private BroadcastReceiver mReceiver = new BroadcastReceiver() {
	 * 
	 * @Override public void onReceive(Context context, Intent intent) { final
	 * TextView responseFromService = (TextView)
	 * getView().findViewById(R.id.speed);
	 * responseFromService.setText(intent.getCharSequenceExtra("longitude"));
	 * responseFromService.invalidate(); } };
	 */

	public HomeFragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_home, container,
				false);

		context = getActivity().getApplicationContext();
		mSpeedView = (TextView) rootView.findViewById(R.id.speed);
		mDistanceView = (TextView) rootView.findViewById(R.id.distance);
		mAltitudeView = (TextView) rootView.findViewById(R.id.altitude);
		mEnergyView = (TextView) rootView.findViewById(R.id.energy);

		startButton = (Button) rootView.findViewById(R.id.start_button);
		stopButton = (Button) rootView.findViewById(R.id.stop_button);

		crono = (Chronometer) rootView.findViewById(R.id.chronometer1);
		crono.setBase(SystemClock.elapsedRealtime());

		return rootView;
	}

	@Override
	public void onAttach(Activity activity) {
		// We verify that our activity implements the listener
		if (!(activity instanceof Communicator))
			throw new ClassCastException();
		else
			com = (Communicator) activity;
		super.onAttach(activity);
	}

	public void onActivityCreated(Bundle savedInstanceState) {
		// com.doAction();

		startButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				crono.setBase(SystemClock.elapsedRealtime() + timeWhenStopped);
				crono.start();

			}
		});

		stopButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				timeWhenStopped = crono.getBase()
						- SystemClock.elapsedRealtime();
				crono.stop();
			}

		});

		super.onActivityCreated(savedInstanceState);
	}

	public void onStart() {
		super.onStart();

	}

	public void updateTextView() {
		mSpeedView.setText(((MainActivity) getActivity()).getSpeed());
		mDistanceView.setText(((MainActivity) getActivity()).getDistance());
		mAltitudeView.setText(((MainActivity) getActivity()).getAltitude());
		mEnergyView.setText(((MainActivity) getActivity()).getEnergy());
		mSpeedView.invalidate();
		mDistanceView.invalidate();
		mAltitudeView.invalidate();
		mEnergyView.invalidate();

	}

}
