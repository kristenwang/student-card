package sse.cg.digitalbusinesscard;

import java.util.List;
import java.util.Map;
import java.util.Set;

import sse.cg.digitalbusinesscard.beans.UserInfo;
import sse.cg.digitalbusinesscard.utils.FileUtils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;

public class AddInfoActivity extends Activity {

    private EditText et_name, et_gender, et_age, et_company, et_address, et_tel, et_email, et_career;
    private Button btn_next_or_confirm;
    private Spinner spinner;
    private Context context;
    public String TextureSelected;

    int whichNext = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_info);

        context = AddInfoActivity.this;
        init();

        spinner = (Spinner) findViewById(R.id.s_texture);

        List<Map<String, Object>> spinnerData = SpinnerAdapter.getSpinnerData();

        SimpleAdapter spinnerAdapter = new SimpleAdapter(this, spinnerData, R.layout.spinner, new String[]{"log", "listName"}, new int[]{R.id.image, R.id.text});

        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                String nameString = ((Map<String, Object>) spinner.getItemAtPosition(position)).get("listName").toString();

                TextureSelected = nameString;

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });
    }

    private void init() {

        et_name = (EditText) findViewById(R.id.et_name);
        et_gender = (EditText) findViewById(R.id.et_gender);
        et_age = (EditText) findViewById(R.id.et_age);
        et_company = (EditText) findViewById(R.id.et_company);
        et_address = (EditText) findViewById(R.id.et_address);
        et_tel = (EditText) findViewById(R.id.et_tel);
        et_email = (EditText) findViewById(R.id.et_email);
        et_career = (EditText) findViewById(R.id.et_career);

        btn_next_or_confirm = (Button) findViewById(R.id.btn_next_or_confirm);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String mine = bundle.getString("mine", "");
        String isQRCodeScan = bundle.getString("isQRCodeScan", "");
        if (mine.equals("true")) {
            et_name.setText("我");
            et_name.setFocusable(false);

            Set<String> sName = FileUtils.getUserInfoAllNameViaSP(context);
            if (sName.contains("我")) {
                UserInfo userInfoTemp = FileUtils.getUserInfo(context, "我");
                String name = userInfoTemp.getName();
                String gender = userInfoTemp.getGender();
                int age = userInfoTemp.getAge();
                String company = userInfoTemp.getCompany();
                String address = userInfoTemp.getAddress();
                String tel = userInfoTemp.getTel();
                String email = userInfoTemp.getEmail();
                String career = userInfoTemp.getCareer();

                et_name.setText(name);
                et_gender.setText(gender);
                et_age.setText("" + age);
                et_company.setText(company);
                et_address.setText(address);
                et_tel.setText(tel);
                et_email.setText(email);
                et_career.setText(career);

            }
            ;

        } else {
            if (isQRCodeScan.equals("true")) {
                String getScanName = bundle.getString("result");
                et_name.setText(getScanName);
            }

        }

        btn_next_or_confirm.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                LinearLayout ll1;
                LinearLayout ll2;

                switch (whichNext) {
                    case 1:
                        ll1 = (LinearLayout) findViewById(R.id.l_name);
                        ll1.setVisibility(View.INVISIBLE);
                        ll2 = (LinearLayout) findViewById(R.id.l_gender);
                        ll2.setVisibility(View.VISIBLE);
                        whichNext++;
                        break;
                    case 2:
                        ll1 = (LinearLayout) findViewById(R.id.l_gender);
                        ll1.setVisibility(View.INVISIBLE);
                        ll2 = (LinearLayout) findViewById(R.id.l_age);
                        ll2.setVisibility(View.VISIBLE);
                        whichNext++;
                        break;
                    case 3:
                        ll1 = (LinearLayout) findViewById(R.id.l_age);
                        ll1.setVisibility(View.INVISIBLE);
                        ll2 = (LinearLayout) findViewById(R.id.l_company);
                        ll2.setVisibility(View.VISIBLE);
                        whichNext++;
                        break;
                    case 4:
                        ll1 = (LinearLayout) findViewById(R.id.l_company);
                        ll1.setVisibility(View.INVISIBLE);
                        ll2 = (LinearLayout) findViewById(R.id.l_address);
                        ll2.setVisibility(View.VISIBLE);
                        whichNext++;
                        break;
                    case 5:
                        ll1 = (LinearLayout) findViewById(R.id.l_address);
                        ll1.setVisibility(View.INVISIBLE);
                        ll2 = (LinearLayout) findViewById(R.id.l_tel);
                        ll2.setVisibility(View.VISIBLE);
                        whichNext++;
                        break;
                    case 6:
                        ll1 = (LinearLayout) findViewById(R.id.l_tel);
                        ll1.setVisibility(View.INVISIBLE);
                        ll2 = (LinearLayout) findViewById(R.id.l_email);
                        ll2.setVisibility(View.VISIBLE);
                        whichNext++;
                        break;
                    case 7:
                        ll1 = (LinearLayout) findViewById(R.id.l_email);
                        ll1.setVisibility(View.INVISIBLE);
                        ll2 = (LinearLayout) findViewById(R.id.l_career);
                        ll2.setVisibility(View.VISIBLE);
                        whichNext++;
                        break;
                    case 8:
                        ll1 = (LinearLayout) findViewById(R.id.l_career);
                        ll1.setVisibility(View.INVISIBLE);
                        ll2 = (LinearLayout) findViewById(R.id.l_spinner);
                        ll2.setVisibility(View.VISIBLE);
                        btn_next_or_confirm.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_add_comfirm));
                        whichNext++;
                        break;
                    case 9:
                        String name = et_name.getText().toString();
                        String gender = et_gender.getText().toString();
                        int age = Integer.parseInt(et_age.getText().toString());
                        String company = et_company.getText().toString();
                        String address = et_address.getText().toString();
                        String tel = et_tel.getText().toString();
                        String email = et_email.getText().toString();
                        String career = et_career.getText().toString();

                        UserInfo userInfo = new UserInfo(name, age, address, gender, company, career, tel, email);
                        FileUtils.saveUserInfo(userInfo, context, name);

                        Intent it = new Intent(AddInfoActivity.this, CardPreviewActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("TextureSelected", TextureSelected);
                        bundle.putSerializable("info", userInfo);
                        it.putExtras(bundle);
                        startActivity(it);
                        break;
                }

            }
        });

    }

}
