/*
 * 
 *  Copyright (c) 2014 Eduardo Corzo
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 3 of the License, or
 *  (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 *  Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,MA 02110-1301, USA.
*/  
package com.CloudRecognition;

import java.lang.ref.WeakReference;

import com.example.reconocimientoar.R;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;


public final class CargarInformacionHandler extends Handler
{
    private final WeakReference<Activity> mActivity;
    // Constants for Hiding/Showing Loading dialog
    public static final int HIDE_LOADING_DIALOG = 0;
    public static final int SHOW_LOADING_DIALOG = 1;
    public static final int HIDE_EVERYTHING=2;
    public static final int USER_DETECTED=3;
    public View mLoadingDialogContainer;
    
    
    public TextView textTarjeta;
    public TextView textComentario;
    public View facebookButton;
    public View linkedinButton;
    public View twitterButton;
    public View webPersonalButton;
    public View webProfesionalButton;
    
    public CargarInformacionHandler(Activity activity)
    {
        mActivity = new WeakReference<Activity>(activity);
    }
    
    public void handleMessage(Message msg){
        Activity imageTargets = mActivity.get();
        if (imageTargets == null)
        {
            return;
        }
        
        if (msg.what == SHOW_LOADING_DIALOG)
        {
            mLoadingDialogContainer.setVisibility(View.VISIBLE);
            
        } else if (msg.what == HIDE_LOADING_DIALOG)
        {
            mLoadingDialogContainer.setVisibility(View.GONE);
        }else if (msg.what==HIDE_EVERYTHING){
        	ocultarTodo();
        }else if (msg.what==USER_DETECTED){
        	
        	mostrarInformacion(msg);
        }
        
     }// funcion
    
    private void ocultarTodo(){
    	mLoadingDialogContainer.setVisibility(View.GONE);
    	textTarjeta.setVisibility(View.GONE);
        textComentario.setVisibility(View.GONE);
        facebookButton.setVisibility(View.GONE);
        linkedinButton.setVisibility(View.GONE);
        twitterButton.setVisibility(View.GONE);
        webPersonalButton.setVisibility(View.GONE);
        webProfesionalButton.setVisibility(View.GONE);
    }
    
    private void mostrarInformacion(Message msg){
    	Bundle data= msg.getData();
    	String tarjeta= new String();
    	
    	//El formato de la tarjeta seguira el siguiente
    	/*
    	 * nombre apellidos
    	 * profesion empresa
    	 * estatus
    	 * email
    	 * telefono pais
    	 * DNI nacimiento
    	 * direccion
    	 */
    	if(!data.getString("nombre").isEmpty()){
    		tarjeta=tarjeta.concat(' '+data.getString("nombre"));	
    	}
    	if(!data.getString("apellidos").isEmpty()){
    		tarjeta=tarjeta.concat(' '+data.getString("apellidos")+'\n');
    	}
    	else if(data.getString("apellidos").isEmpty()){
    		tarjeta=tarjeta.concat("\n");	
    	}
    	if(!data.getString("profesion").isEmpty()){
    		tarjeta=tarjeta.concat(' '+data.getString("profesion"));
    	}
    	if(!data.getString("empresa").isEmpty()){
    		tarjeta=tarjeta.concat(' '+data.getString("empresa")+'\n');
    	}else if(data.getString("profesion").isEmpty()){
    		tarjeta=tarjeta.concat("\n");
    	}
    	
    	if(!data.getString("estatus").isEmpty()){
    		tarjeta=tarjeta.concat(' '+data.getString("estatus")+'\n');
    	}
    	if(!data.getString("email").isEmpty()){
    		tarjeta=tarjeta.concat(' '+data.getString("email")+'\n');
    	}
    	if(!data.getString("telefono").isEmpty()){
    		tarjeta=tarjeta.concat(' '+data.getString("telefono"));
    	}
    	if(!data.getString("pais").isEmpty()){
    		tarjeta=tarjeta.concat(' '+data.getString("pais")+'\n');
    	}else if(data.getString("telefono").isEmpty()){
    		tarjeta=tarjeta.concat("\n");	
    	}
    	if(!data.getString("dni").isEmpty()){
    		tarjeta=tarjeta.concat(' '+data.getString("dni"));
    	}
    	if(!data.getString("nacimiento").isEmpty()){
    		tarjeta=tarjeta.concat(' '+data.getString("nacimiento")+'\n');
    	}else if(data.getString("dni").isEmpty()){
    		tarjeta=tarjeta.concat("\n");
    	}
    	
    	if(!data.getString("direccion").isEmpty()){
    		tarjeta=tarjeta.concat(' '+data.getString("direccion"));
    	}
    	String tarjetaAux=tarjeta.replace("\n", "");
    	if(!tarjetaAux.isEmpty()){
    		textTarjeta.setText(tarjeta);
    		textTarjeta.setVisibility(View.VISIBLE);
    	}
		if(!data.getString("comentario").isEmpty()){
    		textComentario.setText(data.getString("comentario"));
    		textComentario.setVisibility(View.VISIBLE);
    	}
    	if(!data.getString("facebook").isEmpty()){
    		facebookButton.setVisibility(View.VISIBLE);
    	}
    	if(!data.getString("twitter").isEmpty()){
    		twitterButton.setVisibility(View.VISIBLE);
    	}
    	if(!data.getString("linkedin").isEmpty()){
    		linkedinButton.setVisibility(View.VISIBLE);
    	}
    	if(!data.getString("webPersonal").isEmpty()){
    		webPersonalButton.setVisibility(View.VISIBLE);
    	}
    	if(!data.getString("webProfesional").isEmpty()){
    		webProfesionalButton.setVisibility(View.VISIBLE);
    	}
    }
    
    public void setContenido(RelativeLayout mUILayout){
    	 mLoadingDialogContainer = mUILayout.findViewById(R.id.loading_indicator);
    	 
    	 facebookButton = mUILayout.findViewById(R.id.facebookButton);
    	 twitterButton = mUILayout.findViewById(R.id.twitterButton);
    	 linkedinButton = mUILayout.findViewById(R.id.linkedinButton);
    	 webPersonalButton = mUILayout.findViewById(R.id.webPersonalButton);
    	 webProfesionalButton = mUILayout.findViewById(R.id.webProfesionalButton);
    	 
    	 textComentario=(TextView) mUILayout.findViewById(R.id.textComentario);
    	 textTarjeta=(TextView) mUILayout.findViewById(R.id.textTarjeta); 
    }
    
    
    
}
