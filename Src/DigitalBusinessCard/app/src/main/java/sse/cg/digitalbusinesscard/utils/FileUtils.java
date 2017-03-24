package sse.cg.digitalbusinesscard.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.HashSet;
import java.util.Set;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import sse.cg.digitalbusinesscard.beans.UserInfo;

public class FileUtils {
    private static final String index = "InDeX";
    private static final String TAG = "FileUtils";

    private static void showToast(String str, Context context) {
        Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
    }

    public static void saveUserInfo(UserInfo userInfo, Context context, String name) {
        if (name.equals(index)) {
            showToast("Illegal User Name", context);
            Log.d(TAG, "Illegal");
            return;
        }

        String userInfo_Base64 = encodeBase64(userInfo);
        if (userInfo_Base64.equals("")) {
            Log.e(TAG, "ENCODE not success");
            return;
        }
        Log.e(TAG, "userInfo base64 String is: " + userInfo_Base64);

        saveUserInfoViaSP(name, userInfo_Base64, context);

        showToast("Successfully Stored", context);
        Log.i("ok", "存储成功");
    }

    public static UserInfo getUserInfo(Context context, String name) {
        UserInfo userInfo = null;

        if (name.equals(index)) {
            showToast("Illegal User Name", context);
            Log.d(TAG, "Illegal");
            return null;
        }

        String userInfo_Base64 = getUserInfoViaSP(name, context);
        Log.e(TAG, "UserInfo Base64 String is : " + userInfo_Base64);

        userInfo = decodeBase64(userInfo_Base64);

        if (userInfo == null) {
            showToast("No User Info Yet", context);
            return null;
        }
        return userInfo;
    }

    public static String encodeBase64(UserInfo userInfo) {

        String userInfo_Base64 = "";
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {

            ObjectOutputStream oos = new ObjectOutputStream(baos);

            oos.writeObject(userInfo);

            userInfo_Base64 = new String(Base64.encode(baos.toByteArray(), Base64.DEFAULT));

        } catch (IOException e) {
            // TODO Auto-generated
            Log.d(TAG, "has exception");
        }
        return userInfo_Base64;
    }

    public static UserInfo decodeBase64(String obj) {
        UserInfo userInfo = null;


        byte[] base64 = Base64.decode(obj.getBytes(), Base64.DEFAULT);

        ByteArrayInputStream bais = new ByteArrayInputStream(base64);
        try {

            ObjectInputStream bis = new ObjectInputStream(bais);
            try {

                userInfo = (UserInfo) bis.readObject();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } catch (StreamCorruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return userInfo;
    }

    public static boolean saveUserInfoViaSP(String name, String obj, Context context) {

        SharedPreferences preferences = context.getSharedPreferences("users", Context.MODE_PRIVATE);

        Set<String> sNames = preferences.getStringSet(index, null);
        if (sNames == null) {
            sNames = new HashSet<String>();
        }

        sNames.add(name);
        Editor editor = preferences.edit();
        editor.putString(name, obj);
        editor.putStringSet(index, sNames);
        editor.commit();

        return true;
    }

    public static String getUserInfoViaSP(String name, Context context) {

        SharedPreferences preferences = context.getSharedPreferences("users", Context.MODE_PRIVATE);

        String obj = preferences.getString(name, "");
        if (obj.equals("")) {

        }
        return obj;
    }

    public static Set<String> getUserInfoAllNameViaSP(Context context) {

        SharedPreferences preferences = context.getSharedPreferences("users", Context.MODE_PRIVATE);

        Set<String> sNames = preferences.getStringSet(index, null);
        if (sNames == null) {
            sNames = new HashSet<String>();
            Log.e(TAG, "List Index is null");
        }

        return sNames;
    }

    public static boolean deleteAllContacts(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("users", Context.MODE_PRIVATE);

        Set<String> sNames = new HashSet<String>();

        Editor editor = preferences.edit();
        editor.putStringSet(index, sNames);
        editor.commit();

        return true;
    }

    public static boolean deleteOneContact(String name, Context context) {
        Set<String> sNames = new HashSet<String>();
        sNames = getUserInfoAllNameViaSP(context);

        boolean flag = sNames.remove(name);
        if (flag) {
            showToast("删除成功", context);
        } else {
            showToast("删除失败", context);
        }

        return true;
    }

}
