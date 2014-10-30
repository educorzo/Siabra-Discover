/*==============================================================================
 Copyright (c) 2012-2014 Qualcomm Connected Experiences, Inc.
 All Rights Reserved.
 ==============================================================================*/

package com.CloudRecognition;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import com.qualcomm.vuforia.Renderer;
import com.qualcomm.vuforia.State;
import com.qualcomm.vuforia.TrackableResult;
import com.qualcomm.vuforia.VIDEO_BACKGROUND_REFLECTION;
//import com.qualcomm.vuforia.Vuforia;
import com.qualcomm.vuforia.SampleApplication.SampleApplicationSession;


// The renderer class for the CloudReco sample. 
public class CloudRecoRenderer implements GLSurfaceView.Renderer
{
    SampleApplicationSession vuforiaAppSession;

    private CloudReco mActivity;
    
    public CloudRecoRenderer(SampleApplicationSession session, CloudReco activity)
    {
        vuforiaAppSession = session;
        mActivity = activity;
    }
    
    
    // Called when the surface is created or recreated.
    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config)
    {   
        // Call Vuforia function to (re)initialize rendering after first use
        // or after OpenGL ES context was lost (e.g. after onPause/onResume):
        vuforiaAppSession.onSurfaceCreated();
    }
    
    
    // Called when the surface changed size.
    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height)
    {
        // Call Vuforia function to handle render surface size changes:
        vuforiaAppSession.onSurfaceChanged(width, height);
    }
    
    
    // Called to draw the current frame.
    @Override
    public void onDrawFrame(GL10 gl)
    {
        // Call our function to render content
        renderFrame();
    }
    
    // The render function.
    private void renderFrame()
    {
        // Clear color and depth buffer
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
        
        // Get the state from Vuforia and mark the beginning of a rendering
        // section
        State state = Renderer.getInstance().begin();
        
        // Explicitly render the Video Background
        Renderer.getInstance().drawVideoBackground();
        
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);
        GLES20.glEnable(GLES20.GL_CULL_FACE);
        if (Renderer.getInstance().getVideoBackgroundConfig().getReflection() == VIDEO_BACKGROUND_REFLECTION.VIDEO_BACKGROUND_REFLECTION_ON)
            GLES20.glFrontFace(GLES20.GL_CW);  // Front camera
        else
            GLES20.glFrontFace(GLES20.GL_CCW);   // Back camera
            
        // Did we find any trackables this frame?
        if (state.getNumTrackableResults() > 0)
        {
            // Gets current trackable result
            TrackableResult trackableResult = state.getTrackableResult(0);
            
            if (trackableResult == null)
            {
                return;
            }

            mActivity.stopFinderIfStarted();
            
            // Renders the Augmentation View with the 3D Book data Panel
            //renderAugmentation(trackableResult);
            
        }
        else
        {
            mActivity.startFinderIfStopped();
        }
        
        GLES20.glDisable(GLES20.GL_DEPTH_TEST); 
        Renderer.getInstance().end();
    }
    
}
