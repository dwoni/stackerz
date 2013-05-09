package com.woni.utils;

import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLDisplay;
import javax.microedition.khronos.opengles.GL10;

import com.woni.Stackerz.Screens.GameLoop;
import com.woni.Stackerz.Screens.GameScreen;


import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.opengl.GLSurfaceView;
import android.opengl.GLSurfaceView.EGLConfigChooser;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class GameActivity extends Activity implements GLSurfaceView.Renderer, OnTouchListener, SensorEventListener
{       
        private GLSurfaceView glSurface;
        private int width, height;
        private GameListener listener;  
        long lastFrameStart;
        float deltaTime;
        int touchX, touchY;
        boolean isTouched;
        float[] acceleration = new float[3];
        public GameScreen listenerLoop;

        /**
         * Called on creation of the Activity
         */
        public void onCreate(Bundle savedInstanceState) 
        {
                super.onCreate(savedInstanceState);

                glSurface = new GLSurfaceView( this );

                glSurface.setRenderer( this );
                this.setContentView( glSurface );

                glSurface.setOnTouchListener( this );
}

/**
 * Called each Frame
 */
public void onDrawFrame(GL10 gl) 
{                                               
        long currentFrameStart = System.nanoTime();
        deltaTime = (currentFrameStart-lastFrameStart) / 1000000000.0f;
        lastFrameStart = currentFrameStart;                     

        if( listener != null )
                listener.mainLoopIteration( this, gl );         
}

/**
 * Called when the surface size changed, e.g. due to tilting
 */
public void onSurfaceChanged(GL10 gl, int width, int height) 
{       
        this.width = width;
        this.height = height;           
}

/**
 * Called when the GLSurfaceView has finished initialization
 */
public void onSurfaceCreated(GL10 gl, EGLConfig config) 
{                       
        lastFrameStart = System.nanoTime();
        String renderer = gl.glGetString( GL10.GL_RENDERER );
        if( renderer.toLowerCase().contains("pixelflinger" ) )
               Mesh.globalVBO = false;

        if( listener != null )
                listener.setup( this, gl );
}

/**
 * Called when the application is paused. We need to
 * also pause the GLSurfaceView.
 */
@Override
protected void onPause() {
        super.onPause();
        glSurface.onPause();            
}

/**
 * Called when the application is resumed. We need to
 * also resume the GLSurfaceView.
 */
@Override
protected void onResume() {
        super.onResume();
        glSurface.onResume();           
}               

/**
 * Called when a touch event occurs.
 */
public boolean onTouch(View v, MotionEvent event) 
{
        if( event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE )
        {
                if(listenerLoop != null)
                	listenerLoop.OnTouch(event.getX(), event.getY());
        }

        
        if( event.getAction() == MotionEvent.ACTION_UP ){
                if(listenerLoop != null)
                	listenerLoop.OnUp();
                }
        
        return true;
}

/** 
 * Called when the accuracy of the Sensor has changed.
 * @param sensor
 * @param accuracy
 */
public void onAccuracyChanged(Sensor sensor, int accuracy) 
{
        // we ignore this
}

/**
 * Called when the sensor has new input
 * @param event The SensorEvent
 */
public void onSensorChanged(SensorEvent event) {
        System.arraycopy( event.values, 0, acceleration, 0, 3 );
}

/**
 * Sets the {@link GameListener}
 * @param listener the GameListener
 */
public void setGameListener(GameListener listener) 
{       
        this.listener = listener;               
}

/**
 * @return the viewport width in pixels
 */
public int getViewportWidth( )
{
        return width;
}

/**
 * @return the viewport height in pixels
 */
public int getViewportHeight( )
{
        return height;
}

/**
 * @return the delta time in seconds
 */
public float getDeltaTime( )
{
        return deltaTime;
}

/**
 * @return the last known touch coordinate on the x axis
 */
public int getTouchX( )
{
        return touchX;
}

/**
 * @return the last known touch coordinate on the x axis
 */
public int getTouchY( )
{
        return touchY;
}

/**
 * @return wheter the touch screen is touched or not
 */
public boolean isTouched( )
{
        return isTouched;
}

/**
 * @return the acceleration on the x-Axis of the device
 */
public float getAccelerationOnXAxis( )
{
        return acceleration[0];
}

/**
 * @return the acceleration on the x-Axis of the device
 */
public float getAccelerationOnYAxis( )
{
        return acceleration[1];
}

/**
 * @return the acceleration on the x-Axis of the device
 */
public float getAccelerationOnZAxis( )
{
        return acceleration[2];
}
}
