package domel.ecampus;

import android.content.Context;
import android.os.Environment;
import android.view.Gravity;
import android.widget.Toast;

import java.io.File;


public class Tools{

    public final static String ASSETS_PREFIX = "//android_asset/";
    public final static int ASSETS_PREFIX_LEN = 16;

    /* Checks if external storage is available for read and write */
    public static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /* Checks if external storage is available to at least read */
    public static boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

    public static boolean fileExists(Context context, String fname){
        File file = context.getFileStreamPath(fname);
        return file.exists();
    }

    public static void toast(Context context, String str){

        Toast toast = Toast.makeText(context, str, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.BOTTOM, 0, 16);
        toast.show();

    }

}
