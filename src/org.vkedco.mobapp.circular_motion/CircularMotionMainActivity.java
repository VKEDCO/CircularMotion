package org.vkedco.mobapp.circular_motion;

/*
 * *************************************************
 * Main activity of the CircularMotion application.
 * 
 * Bugs to vladimir dot kulyukin at gmail dot com
 *************************************************
 */

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
//import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
//import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class CircularMotionMainActivity extends Activity {

	CircularMotionApp mApp 				= null;
	final static int SLEEP_INTERVAL 	= 200; // SLEEP_INTERVAL = 200 is 1Hz, i.e., 1 circle/second
	CircularMotionPainterView mPainterView 			= null;
	ArrayList<Thread> mCircleThreads 	= null;
	Resources mRes 						= null;
	
	static final String LOGTAG = CircularMotionMainActivity.class.getSimpleName() + "_TAG";
	
	class UnsynchronizedPainterRunnable implements Runnable {
		Circle mCircle = null;
		boolean mThreadRunning = false;

		public UnsynchronizedPainterRunnable(Circle c) {
			mCircle = c;
		}

		@Override
		public void run() {
			mThreadRunning = true;
			while ( mThreadRunning ) {
				((RotatingCircle) mCircle).move();
				try {
					// Have the thread sleep
					Thread.sleep(CircularMotionMainActivity.SLEEP_INTERVAL);
				} catch (InterruptedException e) {
					e.printStackTrace();
					mThreadRunning = false;
				}
			}
		}
	}
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d(LOGTAG, "onCreate()");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_circular_motion_main);
		mPainterView = (CircularMotionPainterView) this.findViewById(R.id.pntr);
		mApp = (CircularMotionApp) getApplication();
		mApp.setPainterView(mPainterView);
		mPainterView.setApp(mApp);
		mCircleThreads = new ArrayList<Thread>();
		startUnsynchronizedThreads();
		mRes = this.getResources();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.circular_motion_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_tuneup) {
			Intent tuneupIntent = new Intent(this, CirclarMotionTuneupAct.class);
			tuneupIntent.putExtra(mRes.getString(R.string.is_red_circle_displayed), mApp.isRedCircleDisplayed());
			tuneupIntent.putExtra(mRes.getString(R.string.is_blue_circle_displayed), mApp.isBlueCircleDisplayed());
			tuneupIntent.putExtra(mRes.getString(R.string.is_magenta_circle_displayed), mApp.isMagentaCircleDisplayed());
			tuneupIntent.putExtra(mRes.getString(R.string.is_yellow_circle_displayed), mApp.isYellowCircleDisplayed());
			tuneupIntent.putExtra(mRes.getString(R.string.max_amp), (int)mPainterView.getRhoMax());
			tuneupIntent.putExtra(mRes.getString(R.string.red_amp), (int)mPainterView.getRedRho());
			tuneupIntent.putExtra(mRes.getString(R.string.blue_amp), (int)mPainterView.getBlueRho());
			tuneupIntent.putExtra(mRes.getString(R.string.magenta_amp), (int)mPainterView.getMagentaRho());
			tuneupIntent.putExtra(mRes.getString(R.string.yellow_amp), (int)mPainterView.getYellowRho());
			Log.v("CircularMotionMainActivity_TAG", mRes.getString(R.string.red_amp) + "="+ mPainterView.getRedRho());
			Log.v("CircularMotionMainActivity_TAG", mRes.getString(R.string.blue_amp) + "="+ mPainterView.getBlueRho());
			Log.v("CircularMotionMainActivity_TAG", mRes.getString(R.string.magenta_amp) + "="+ mPainterView.getMagentaRho());
			Log.v("CircularMotionMainActivity_TAG", mRes.getString(R.string.yellow_amp) + "="+ mPainterView.getYellowRho());
			this.startActivity(tuneupIntent);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	protected void onPause() {
		Log.d(LOGTAG, "onPause()");
		super.onPause();
		interruptThreads();
	}

	@Override
	protected void onStart() {
		Log.d(LOGTAG, "onStart()");
		super.onStart();
	}

	@Override
	protected void onStop() {
		Log.d(LOGTAG, "onStop()");
		super.onStop();
		interruptThreads();
	}
	
	@Override
	protected void onRestart() {
		Log.d(LOGTAG, "onRestart()");
		super.onRestart();
		this.startUnsynchronizedThreads();
	}

	@Override
	protected void onResume() {
		Log.d(LOGTAG, "onResume()");
		super.onResume();
		Log.d(LOGTAG, "redCircle's rho == " + mApp.getRedAmp());
		Log.d(LOGTAG, "redCircle's rho in pv == " + mPainterView.mRedMotionCircle.getRho());
		this.startUnsynchronizedThreads();
	}

	private void interruptThreads() {
		for(Thread th: mCircleThreads) {
			if ( th.isAlive() ) {
				try {
					th.interrupt();
				}
				catch ( Exception e ) {
					System.err.println(e.toString());
				}
			}
		}
		mCircleThreads.clear();
	}

	private void startUnsynchronizedThreads() {
		Thread th = null;
		for(Circle c: mPainterView.getCircles()) {
			th = new Thread(new UnsynchronizedPainterRunnable(c));
			mCircleThreads.add(th);
			th.start();
		}
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		Log.d(LOGTAG, "onConfigurationChanged()");
		super.onConfigurationChanged(newConfig);
	}
}
