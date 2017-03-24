package sse.cg.digitalbusinesscard;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;

public class LoadingActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_loading);

        ImageView loadingIv = (ImageView) this.findViewById(R.id.logo_bg);

        AlphaAnimation animation = new AlphaAnimation(0.1f, 1.0f);
        animation.setDuration(3000);
        loadingIv.setAnimation(animation);

        animation.setAnimationListener(new AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                //TODO Auto-generated method stub
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                //TODO Auto-generated method stub
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //TODO Auto-generated method stub

                Intent it = new Intent(LoadingActivity.this, MainActivity.class);
                startActivity(it);

                finish();
            }
        });
    }
}
