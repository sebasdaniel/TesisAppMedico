package com.tesis.medico.gui;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;

public class GCMBroadcastReceiver extends WakefulBroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		
		ComponentName comp = new ComponentName(context.getPackageName(), GCMIntentServiceMedico.class.getName());

		startWakefulService(context, (intent.setComponent(comp)));

		setResultCode(Activity.RESULT_OK);
	}
}
