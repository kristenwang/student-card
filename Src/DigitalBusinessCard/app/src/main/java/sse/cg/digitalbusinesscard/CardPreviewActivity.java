package sse.cg.digitalbusinesscard;

import java.io.FileOutputStream;
import java.util.Hashtable;

import sse.cg.digitalbusinesscard.beans.UserInfo;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.LinearLayout;

public class CardPreviewActivity extends Activity {
    private UserInfo userInfo;
    private EditText et_name, et_gender, et_age, et_company, et_address, et_tel, et_email, et_career;
    private ImageView qrCode;
    private LinearLayout ll_card_info;
    private Button btn_confirm;
    public String TextureSelected;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_card_preview);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        TextureSelected = (String) bundle.getString("TextureSelected");
        userInfo = (UserInfo) bundle.getSerializable("info");

        init();
    }

    private void GetandSaveCurrentImage(String cardname) {

        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        int w = display.getWidth();
        int h = display.getHeight();

        Bitmap Bmp = Bitmap.createBitmap(w, h, Config.ARGB_8888);

        View decorview = this.getWindow().getDecorView();
        decorview.setDrawingCacheEnabled(true);
        Bmp = decorview.getDrawingCache();

        try {

            String path = Environment.getExternalStorageDirectory() + "/" + cardname + ".jpeg";

            FileOutputStream fos = null;
            fos = new FileOutputStream(path);
            if (null != fos) {
                Bmp.compress(Bitmap.CompressFormat.JPEG, 90, fos);
                fos.flush();
                fos.close();

                Toast.makeText(CardPreviewActivity.this, "已保存", Toast.LENGTH_LONG).show();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void init() {

        et_name = (EditText) findViewById(R.id.et_card_name);
        et_gender = (EditText) findViewById(R.id.et_card_gender);
        et_age = (EditText) findViewById(R.id.et_card_age);
        et_company = (EditText) findViewById(R.id.et_card_company);
        et_address = (EditText) findViewById(R.id.et_card_address);
        et_tel = (EditText) findViewById(R.id.et_card_tel);
        et_email = (EditText) findViewById(R.id.et_card_email);
        et_career = (EditText) findViewById(R.id.et_card_career);


        ll_card_info = (LinearLayout) findViewById(R.id.card_info);

        int resId1 = getResources().getIdentifier(TextureSelected, "drawable", "sse.cg.digitalbusinesscard");
        Drawable drawable = getResources().getDrawable(resId1);
        ll_card_info.setBackgroundDrawable(drawable);

        btn_confirm = (Button) findViewById(R.id.btn_confirm);

        String name = userInfo.getName();
        String gender = userInfo.getGender();
        int age = userInfo.getAge();
        String company = userInfo.getCompany();
        String address = userInfo.getAddress();
        String tel = userInfo.getTel();
        String email = userInfo.getEmail();
        String career = userInfo.getCareer();

        et_name.setText(name);
        et_gender.setText(gender);
        et_age.setText("" + age);
        et_company.setText(company);
        et_address.setText(address);
        et_tel.setText(tel);
        et_email.setText(email);
        et_career.setText(career);

        String infoStr = et_name.getText().toString();
        qrCode = (ImageView) findViewById(R.id.imageView1);
        createQRImage(infoStr);

        btn_confirm.setOnClickListener(new OnClickListener() {
            String cardName = et_name.getText().toString();

            @Override
            public void onClick(View v) {
                btn_confirm.setVisibility(View.INVISIBLE);

                Intent it = new Intent(CardPreviewActivity.this, MainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("TextureSelected", TextureSelected);
                bundle.putSerializable("info", userInfo);
                it.putExtras(bundle);
                startActivity(it);

                GetandSaveCurrentImage(cardName + "f");

                btn_confirm.setVisibility(View.VISIBLE);
            }
        });


    }

    public void createQRImage(String url) {
        int QR_WIDTH = 600;
        int QR_HEIGHT = 600;
        try {
            if (url == null || "".equals(url) || url.length() < 1) {
                return;
            }
            Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");

            BitMatrix bitMatrix = new QRCodeWriter().encode(url, BarcodeFormat.QR_CODE, QR_WIDTH, QR_HEIGHT, hints);
            int[] pixels = new int[QR_WIDTH * QR_HEIGHT];

            for (int y = 0; y < QR_HEIGHT; y++) {
                for (int x = 0; x < QR_WIDTH; x++) {
                    if (bitMatrix.get(x, y)) {
                        pixels[y * QR_WIDTH + x] = 0xff000000;
                    } else {
                        pixels[y * QR_WIDTH + x] = 0xffffffff;
                    }
                }
            }

            Bitmap bitmap = Bitmap.createBitmap(QR_WIDTH, QR_HEIGHT, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, QR_WIDTH, 0, 0, QR_WIDTH, QR_HEIGHT);

            qrCode.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }
}
