package org.vkedco.mobapp.circular_motion;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

/*
 * ***********************************************************************
 * CirclarMotionTuneupAct.java is an activity that allows the user
 * to control which rotating circles are displayed and their amplitudes.
 * 
 * Bugs to vladimir dot kulyukin at gmail dot com
 *************************************************************************
 */

public class CirclarMotionTuneupAct extends Activity 
implements OnSeekBarChangeListener
{
	
	CheckBox mRedCircleCheckBox 		= null;
	CheckBox mBlueCircleCheckBox 		= null;
	CheckBox mMagentaCircleCheckBox 	= null;
	CheckBox mYellowCircleCheckBox 		= null;
	Button mBtnConfirm 					= null;
	CircularMotionApp mApp				= null;
	Resources mRes 						= null;
	CirclarMotionTuneupAct mThisAct			= null;
	TextView mRedSeekBarProgressTV      = null; SeekBar mRedSeekBar 	= null;
	TextView mBlueSeekBarProgressTV		= null; SeekBar mBlueSeekBar 	= null;
	TextView mMagentaSeekBarProgressTV	= null; SeekBar mMagentaSeekBar = null;
	TextView mYellowSeekBarProgressTV	= null; SeekBar mYellowSeekBar 	= null;
	
	static final String LOGTAG = CirclarMotionTuneupAct.class.getSimpleName() + "_TAG";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.motion_circle_tuneup);
		
		mBtnConfirm = (Button) this.findViewById(R.id.btn_confirm);
		mRedCircleCheckBox = (CheckBox) this.findViewById(R.id.redMotionCircle);
		mBlueCircleCheckBox = (CheckBox) this.findViewById(R.id.blueMotionCircle);
		mMagentaCircleCheckBox = (CheckBox) this.findViewById(R.id.magentaMotionCircle);
		mYellowCircleCheckBox = (CheckBox) this.findViewById(R.id.yellowMotionCircle);
		mApp = (CircularMotionApp) this.getApplication();
		mThisAct = this;
		mRes = this.getResources();
		
		mRedSeekBarProgressTV = (TextView) this.findViewById(R.id.tvRedAmpProgressStatus);
		mBlueSeekBarProgressTV = (TextView) this.findViewById(R.id.tvBlueAmpProgressStatus);
		mMagentaSeekBarProgressTV = (TextView) this.findViewById(R.id.tvMagentaAmpProgressStatus);
		mYellowSeekBarProgressTV = (TextView) this.findViewById(R.id.tvYellowAmpProgressStatus);
		
		mRedSeekBar = (SeekBar) this.findViewById(R.id.sbRedCircleAmp);
		mRedSeekBar.setOnSeekBarChangeListener(this);
		mBlueSeekBar = (SeekBar) this.findViewById(R.id.sbBlueCircleAmp);
		mBlueSeekBar.setOnSeekBarChangeListener(this);
		mMagentaSeekBar = (SeekBar) this.findViewById(R.id.sbMagentaCircleAmp);
		mMagentaSeekBar.setOnSeekBarChangeListener(this);
		mYellowSeekBar = (SeekBar) this.findViewById(R.id.sbYellowCircleAmp);
		mYellowSeekBar.setOnSeekBarChangeListener(this);
		
		Intent i = this.getIntent();
		
		mRedCircleCheckBox.setChecked(i.getBooleanExtra(mRes.getString(R.string.is_red_circle_displayed), false));
		mBlueCircleCheckBox.setChecked(i.getBooleanExtra(mRes.getString(R.string.is_blue_circle_displayed), false));
		mMagentaCircleCheckBox.setChecked(i.getBooleanExtra(mRes.getString(R.string.is_magenta_circle_displayed), false));
		mYellowCircleCheckBox.setChecked(i.getBooleanExtra(mRes.getString(R.string.is_yellow_circle_displayed), false));
		
		//final int max_amp 		= i.getIntExtra(mRes.getString(R.string.max_amp), 0);
		final int red_amp 		= i.getIntExtra(mRes.getString(R.string.red_amp), 0);
		final int blue_amp 		= i.getIntExtra(mRes.getString(R.string.blue_amp), 0);
		final int magenta_amp 	= i.getIntExtra(mRes.getString(R.string.magenta_amp), 0);
		final int yellow_amp 	= i.getIntExtra(mRes.getString(R.string.yellow_amp), 0);
		
		mApp.setRedAmp(red_amp);
		mRedSeekBar.setProgress(red_amp);

		mApp.setBlueAmp(blue_amp);
		mBlueSeekBar.setProgress(blue_amp);

		mApp.setMagentaAmp(magenta_amp);
		mMagentaSeekBar.setProgress(magenta_amp);

		mApp.setYellowAmp(yellow_amp);
		mYellowSeekBar.setProgress(yellow_amp);
		
		//checkSavedInstanceState(savedInstanceState);
		
		mRedCircleCheckBox.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if ( ((CheckBox) v).isChecked() ) {
					mApp.setRedCircleDisplayed(true);
					Log.v("CirclarMotionTuneupAct", "RED CHECKED");
				}
				else {
					mApp.setRedCircleDisplayed(false);
					Log.v("CirclarMotionTuneupAct", "RED UNCHECKED");
				}
			}});
		
		mBlueCircleCheckBox.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if ( ((CheckBox) v).isChecked() ) {
					mApp.setBlueCircleDisplayed(true);
				}
				else {
					mApp.setBlueCircleDisplayed(false);
				}
			}});
		
		mMagentaCircleCheckBox.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if ( ((CheckBox) v).isChecked() ) {
					mApp.setMagentaCircleDisplayed(true);
				}
				else {
					mApp.setMagentaCircleDisplayed(false);
				}
			}});
		
		mYellowCircleCheckBox.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if ( ((CheckBox) v).isChecked() ) {
					mApp.setYellowCircleDisplayed(true);
				}
				else {
					mApp.setYellowCircleDisplayed(false);
				}
			}});
		
		mBtnConfirm.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mThisAct.finish();
			}	
		}
		);
	}
	
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		Log.d(LOGTAG, "onRestoreInstanceState()");
		//checkSavedInstanceState(savedInstanceState);
	}
	
	
	@Override
	protected void onStart() {
		super.onStart();
		mRedCircleCheckBox.setChecked(mApp.isRedCircleDisplayed());
		mBlueCircleCheckBox.setChecked(mApp.isBlueCircleDisplayed());
		mMagentaCircleCheckBox.setChecked(mApp.isMagentaCircleDisplayed());
		mYellowCircleCheckBox.setChecked(mApp.isYellowCircleDisplayed());
		
		mRedSeekBar.setProgress(mApp.getRedAmp());
		mBlueSeekBar.setProgress(mApp.getBlueAmp());
		mMagentaSeekBar.setProgress(mApp.getMagentaAmp());
		mYellowSeekBar.setProgress(mApp.getYellowAmp());
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		Log.d(LOGTAG, "onSaveInstanceState()");
		
		
	}

	// The curve motion circle disappears as well.
	@Override
	public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
		switch ( seekBar.getId() ) {
		case R.id.sbRedCircleAmp: this.mRedSeekBarProgressTV.setText(Integer.toString(progress)); 
			break;
		case R.id.sbBlueCircleAmp: this.mBlueSeekBarProgressTV.setText(Integer.toString(progress)); break;
		case R.id.sbMagentaCircleAmp: this.mMagentaSeekBarProgressTV.setText(Integer.toString(progress)); break;
		case R.id.sbYellowCircleAmp: this.mYellowSeekBarProgressTV.setText(Integer.toString(progress)); break;
		}
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub	
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
		switch ( seekBar.getId() ) {
		case R.id.sbRedCircleAmp: 
			Log.v("MOTIONCIRCLETUNEUP_TAG", "progress = " + seekBar.getProgress());
			mApp.setRedAmp(seekBar.getProgress());
			break;
		case R.id.sbBlueCircleAmp: 
			mApp.setBlueAmp(seekBar.getProgress());
			break;
		case R.id.sbMagentaCircleAmp: 
			mApp.setMagentaAmp(seekBar.getProgress());
			break;
		case R.id.sbYellowCircleAmp: 
			mApp.setYellowAmp(seekBar.getProgress()); 
			break;
		}
	}
}
