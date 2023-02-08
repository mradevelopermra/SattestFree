package com.mra.satpro.common;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

/*import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.mra.guaunampremium.FindGameAhorcadoActivity;
import com.mra.guaunampremium.FindGameGatoActivity;
import com.mra.guaunampremium.R;
import com.mra.guaunampremium.utilerias.Constantes;

import java.util.Random;

public class FcmMessagingService extends FirebaseMessagingService {


    private final String ADMIN_CHANNEL_ID ="admin_channel";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        final Intent intent;
        NotificationManager notificationManager;
        int notificationID;

        Log.e("remoteMessage", remoteMessage.getData().toString());
        String test[]={};
        test = remoteMessage.getData().toString().replace("{", "").replace("}", "").split(",");

        String numRep[] = {};
        String cadenaValida = "";
        numRep = test[0].split("=");

        for(int i=0; i<test.length; i++){
            if(test[i].contains("title")){
                numRep = test[i].split("=");
            }
        }

        Log.e("numRep", numRep[1]+"");
        notificationID = new Random().nextInt(3000);
        notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

        if(numRep[1].contains("Ahorcado")){
            intent = new Intent(this, FindGameAhorcadoActivity.class);
            intent.putExtra(Constantes.TIPO_JUEGO, "normal");
        }else if(numRep[1].contains("Gato")){
            intent = new Intent(this, FindGameGatoActivity.class);
            intent.putExtra(Constantes.TIPO_JUEGO, "normal");
        }else if(numRep[1].contains("ajusticiado")){
            intent = new Intent(this, FindGameAhorcadoActivity.class);
            intent.putExtra(Constantes.TIPO_JUEGO, "invitacion");
        }else if(numRep[1].contains("Tic-Tac-Toe")){
            intent = new Intent(this, FindGameGatoActivity.class);
            intent.putExtra(Constantes.TIPO_JUEGO, "invitacion");
        }else{
            intent = new Intent(this, FindGameAhorcadoActivity.class);
            intent.putExtra(Constantes.TIPO_JUEGO, "normal");
        }



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            setupChannels(notificationManager);
        }


        intent.putExtra(Constantes.EXTRA_NICK, "sinNotificacion");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this , 0, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(),
                R.drawable.ic_console);

        Uri notificationSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, ADMIN_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_console)
                .setLargeIcon(largeIcon)
                .setContentTitle(remoteMessage.getData().get("title"))
                .setContentText(remoteMessage.getData().get("message"))
                .setAutoCancel(true)
                .setSound(notificationSoundUri)
                .setContentIntent(pendingIntent);

        //Set notification color to match your app color template
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            notificationBuilder.setColor(getResources().getColor(R.color.colorPrimaryDark));
        }
        notificationManager.notify(notificationID, notificationBuilder.build());
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setupChannels(NotificationManager notificationManager){
        CharSequence adminChannelName = "New notification";
        String adminChannelDescription = "Device to devie notification";

        NotificationChannel adminChannel;
        adminChannel = new NotificationChannel(ADMIN_CHANNEL_ID,
                adminChannelName, NotificationManager.IMPORTANCE_HIGH);
        adminChannel.setDescription(adminChannelDescription);
        adminChannel.enableLights(true);
        adminChannel.setLightColor(Color.RED);
        adminChannel.enableVibration(true);

        if (notificationManager != null) {
            notificationManager.createNotificationChannel(adminChannel);
        }
    }
}*/
