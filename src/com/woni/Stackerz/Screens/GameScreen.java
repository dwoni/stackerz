package com.woni.Stackerz.Screens;

import javax.microedition.khronos.opengles.GL10;

import com.woni.utils.GameActivity;

public interface GameScreen 
{
   public void update( GameActivity activity );
   public void render( GL10 gl, GameActivity activity );
   public boolean isDone( );
   public void dispose( );
   public void OnUp();
   public void OnTouch(float x, float y);
}  

