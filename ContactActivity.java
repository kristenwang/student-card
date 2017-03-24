package sse.cg.digitalbusinesscard;

import java.security.Permission;
import java.security.Permissions;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import sse.cg.digitalbusinesscard.beans.UserInfo;
import sse.cg.digitalbusinesscard.utils.FileUtils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ContactActivity extends Activity {

    private ListView listView;
    private Context context;
    private List<String> list_Name;
    private String name = "";
    private static final int D_DELETE = 0, D_DELETE_ALL = 1, D_NEW_CONTACT = 2, D_DIAL = 3;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        context = ContactActivity.this;
        init();
    }

    private void init() {


        listView = (ListView) findViewById(R.id.lv_contact);
        list_Name = getData();
        listView.setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_expandable_list_item_1, list_Name));

        listView.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int arg2, long arg3) {

                name = list_Name.get(arg2);

                View mContent = LayoutInflater.from(context).inflate(R.layout.list_items, null);

                ListView lvOp = (ListView) mContent.findViewById(R.id.lv_contact);
                lvOp.setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_expandable_list_item_1, getDialogOpData()));
                lvOp.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> arg0, View arg1,
                                            int arg2, long arg3) {
                        switch (arg2) {
                            case 0:
                                //delete
                                mHandler.obtainMessage(D_DELETE).sendToTarget();
                                break;

                            case 1:
                                //delete all
                                mHandler.obtainMessage(D_DELETE_ALL).sendToTarget();
                                break;

                            case 2:
                                //new contact
                                Intent it = new Intent(ContactActivity.this, AddInfoActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("mine", "false");
                                it.putExtras(bundle);
                                startActivity(it);
                                mHandler.obtainMessage(D_NEW_CONTACT).sendToTarget();

                                break;

                            case 3:
                                //dial tel
                                mHandler.obtainMessage(D_DIAL).sendToTarget();
                                break;

                            default:
                                break;
                        }
                    }
                });

                dialog = new AlertDialog.Builder(context).create();
                dialog.setTitle("Please choose..");
                dialog.setView(mContent);
                dialog.show();

                return false;
            }
        });

        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                Intent intent = new Intent();
                intent.setClass(ContactActivity.this, DisplayInfoActivity.class);

                name = list_Name.get(arg2);

                UserInfo userInfo = FileUtils.getUserInfo(context, name);
                Bundle bundle = new Bundle();
                bundle.putSerializable("info", userInfo);
                intent.putExtras(bundle);

                startActivity(intent);
                name = "";
            }
        });

    }

    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case D_DELETE:
                    FileUtils.deleteOneContact(name, context);
                    invalidateAll();
                    break;

                case D_DELETE_ALL:
                    FileUtils.deleteAllContacts(context);
                    invalidateAll();
                    break;

                case D_NEW_CONTACT:
                    invalidateAll();
                    break;

                case D_DIAL:
                    dialTel(name, context);
                    invalidateAll();
                    break;


                default:
                    break;
            }
        }

    };

    private void invalidateList() {
        list_Name = getData();
        listView.setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_expandable_list_item_1, list_Name));
    }

    private void invalidateAll() {
        name = "";
        invalidateList();
        dialog.dismiss();
    }

    private List<String> getDialogOpData() {
        List<String> result = new ArrayList<String>();
        result.add("删除");
        result.add("清空");
        result.add("新建");
        result.add("呼叫");
        return result;
    }

    @Override
    protected void onResume() {
        Log.d("ContactActivity", "onResume");
        invalidateList();
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.d("ContactActivity", "onPause");
        super.onPause();
    }

    private List<String> getData() {
        Set<String> sNameSet = FileUtils.getUserInfoAllNameViaSP(context);
        List<String> result = new ArrayList<String>(sNameSet);
        Collections.sort(result);
        return result;
    }


    public void dialTel(String name, Context context) {
        UserInfo userInfo = FileUtils.getUserInfo(context, name);
        String tel = userInfo.getTel();
        tel = tel.trim();

        if (tel != null && !tel.equals("")) {

            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + tel));
            if(PackageManager.PERMISSION_GRANTED == checkCallingPermission("android.permission.CALL_PHONE")){
                startActivity(intent);
            }
        }

    }
}
