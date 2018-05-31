package com.example.chandrakantchaturvedi.epaisedemo;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by chandrakantchaturvedi on 30/5/18.
 */

public class Utils {
    public static boolean isDialogShowing;

    /**
     * Is network available boolean.
     *
     * @param context the context
     * @return the boolean
     */
    public static boolean isNetworkAvailable(Context context) {

        boolean available = false;
        if (context != null) {
            ConnectivityManager ConnectMgr = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            if (ConnectMgr == null) {
                return false;
            }
            NetworkInfo NetInfo = ConnectMgr.getActiveNetworkInfo();
//        if (NetInfo == null) {
//            return false;
//        }

            available = NetInfo != null && NetInfo.isConnected();
            // return NetInfo.isConnected();
        }
        return available;
    }

    public static void showAlert(Context context, String title, String message) {
        if (Utils.isDialogShowing) {
            return;
        } else {
            try {
                if (((Activity) context).isFinishing() == false) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle(title).setMessage(message).setCancelable(false);
                    builder.setPositiveButton("Ok",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Utils.isDialogShowing = false;
                                    dialog.cancel();
                                }
                            });
                    builder.show();
                    Utils.isDialogShowing = true;
                }
            } catch (Exception e) {
                Log.e("Exception", "Dialog exception", e);
            }

        }
    }

    public static void CopyStream(InputStream is, OutputStream os)
    {
        final int buffer_size=1024;
        try
        {
            byte[] bytes=new byte[buffer_size];
            for(;;)
            {
                int count=is.read(bytes, 0, buffer_size);
                if(count==-1)
                    break;
                os.write(bytes, 0, count);
            }
        }
        catch(Exception ex){}
    }
    public static String getTime(String time) {
        if (time != null) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            if (time.toUpperCase().contains("T") && time.toUpperCase().contains("Z")) {
                time = time.toUpperCase().replace("T", " ");
                time = time.toUpperCase().replace("Z", "");
                String[] str = time.split("\\.");

                if (str.length != 0) {
                    return str[0];
                }
            }

            try {
                Date date = new Date(time);
                time = simpleDateFormat.format(date);
            } catch (Exception e) {
                //
            }
        }
        return time;
    }
}
