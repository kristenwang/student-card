package sse.cg.digitalbusinesscard;

import sse.cg.digitalbusinesscard.beans.UserInfo;
import sse.cg.digitalbusinesscard.utils.FileUtils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

    private final static int SCANNIN_GREQUEST_CODE = 1;

    private String userNameTemp;
    private Bitmap userQRbitmap;

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        Button btn_qrCode = (Button) findViewById(R.id.btn_qrCode);
        btn_qrCode.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, CaptureActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivityForResult(intent, SCANNIN_GREQUEST_CODE);
            }
        });
        context = MainActivity.this;

        Button btn_my_card = (Button) findViewById(R.id.btn_my_card);
        Button btn_add_my_card = (Button) findViewById(R.id.btn_add_my_card);
        Button btn_contact_list = (Button) findViewById(R.id.btn_contact_list);
        Button btn_add_contact = (Button) findViewById(R.id.btn_add_contact);
        Button btn_exit = (Button) findViewById(R.id.btn_exit);

        btn_my_card.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent it = new Intent(MainActivity.this, DisplayInfoActivity.class);

                UserInfo userInfo = FileUtils.getUserInfo(context, "我");

                Bundle bundle = new Bundle();
                bundle.putSerializable("info", userInfo);
                it.putExtras(bundle);
                startActivity(it);
            }
        });


        btn_add_my_card.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent it = new Intent(MainActivity.this, AddInfoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("isQRCodeScan", "false");
                bundle.putString("mine", "true");
                it.putExtras(bundle);
                startActivity(it);
            }
        });

        btn_contact_list.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent it = new Intent(MainActivity.this, ContactActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("name", "This is from MainActivity!");
                it.putExtras(bundle);
                startActivity(it);
            }
        });

        btn_add_contact.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent it = new Intent(MainActivity.this, AddInfoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("isQRCodeScan", "false");
                bundle.putString("mine", "false");
                it.putExtras(bundle);
                startActivity(it);
            }
        });

        btn_exit.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                android.os.Process.killProcess(android.os.Process.myPid());
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case SCANNIN_GREQUEST_CODE:
                if (resultCode == RESULT_OK) {
                    Bundle bundle = data.getExtras();

                    userNameTemp = bundle.getString("result");
                    userQRbitmap = (Bitmap) data.getParcelableExtra("bitmap");
                }
                break;
        }
    }

}
