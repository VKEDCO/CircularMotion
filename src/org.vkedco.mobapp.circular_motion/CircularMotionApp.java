package org.vkedco.mobapp.circular_motion;

/*
 * *************************************************
 * CicularMotionApp.java is an extended application 
 * class used to pass information from the CircularMotionTuneupAct.java
 * to CircularMotionPainterView.java.
 * 
 * Bugs to vladimir dot kulyukin at gmail dot com
 *************************************************
 */
import android.app.Application;

public class CircularMotionApp extends Application {
	static enum CIRCLE_COLOR {
		RED, BLUE, MAGENTA, GRAY
	};
	
	CircularMotionPainterView mPainterView 	= null;
	boolean mIsRedCircleDisplayed 			= true;
	boolean mIsBlueCircleDisplayed 			= true;
	boolean mIsMagentaCircleDisplayed 		= true;
	boolean mIsYellowCircleDisplayed 		= true;
	
	int mRedAmp 	= 0;
	int mBlueAmp 	= 0;
	int mMagentaAmp = 0;
	int mYellowAmp 	= 0;
	
	public CircularMotionApp() {}
	
	void setPainterView(CircularMotionPainterView pw) {
		mPainterView = pw;	
	}
	
	CircularMotionPainterView getPainterView() {
		return mPainterView;
	}
	
	void setRedCircleDisplayed(boolean v) {
		this.mIsRedCircleDisplayed = v;
	}
	
	void setBlueCircleDisplayed(boolean v) {
		this.mIsBlueCircleDisplayed = v;
	}
	
	void setMagentaCircleDisplayed(boolean v) {
		this.mIsMagentaCircleDisplayed = v;
	}
	
	void setYellowCircleDisplayed(boolean v) {
		this.mIsYellowCircleDisplayed = v;
	}
	
	boolean isRedCircleDisplayed() {
		return this.mIsRedCircleDisplayed;
	}
	
	boolean isBlueCircleDisplayed() {
		return this.mIsBlueCircleDisplayed;
	}
	
	boolean isMagentaCircleDisplayed() {
		return this.mIsMagentaCircleDisplayed;
	}
	
	boolean isYellowCircleDisplayed() {
		return this.mIsYellowCircleDisplayed;
	}
	
	int getRedAmp() { return mRedAmp; }
	void setRedAmp(int v) { 
		mRedAmp = v; 
		mPainterView.mRedMotionCircle.setRho((double)v);
		mPainterView.mRedCurveMotionCircle.setRho((double)v);
	}
	
	int getBlueAmp() { return mBlueAmp; }
	void setBlueAmp(int v) { 
		mBlueAmp = v; 
		mPainterView.mBlueMotionCircle.setRho((double)v);
		mPainterView.mBlueCurveMotionCircle.setRho((double)v);
	}
	
	int getMagentaAmp() { return mMagentaAmp; }
	void setMagentaAmp(int v) { 
		mMagentaAmp = v; 
		mPainterView.mMagentaMotionCircle.setRho((double)v);
		mPainterView.mMagentaCurveMotionCircle.setRho((double)v);
	}
	
	int getYellowAmp() { return mYellowAmp; }
	void setYellowAmp(int v) { 
		mYellowAmp = v; 
		mPainterView.mYellowMotionCircle.setRho((double)v);
		mPainterView.mYellowCurveMotionCircle.setRho((double)v);
	}
	
}
