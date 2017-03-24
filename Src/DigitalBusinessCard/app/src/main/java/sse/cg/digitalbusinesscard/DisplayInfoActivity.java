package sse.cg.digitalbusinesscard;

import sse.cg.digitalbusinesscard.beans.UserInfo;

import android.app.Activity;
import android.content.Intent;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.TextView;

public class DisplayInfoActivity extends Activity {

	private UserInfo userInfo;
	private GLSurfaceView glView; 
	private MyRenderer render;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_info);

        getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN ,
                      WindowManager.LayoutParams. FLAG_FULLSCREEN);

        Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		userInfo = (UserInfo) bundle.getSerializable("info");

		String cardName;
		if(userInfo == null)
			cardName ="MyUserInfoIsNULL";
		else
		 cardName = userInfo.getName();

        render = new MyRenderer(this, cardName);
        glView = new GLSurfaceView(this);
        glView.setRenderer(render);
        setContentView(glView);     

	}



	float sX;
	float sY;
	float baseValue;
	float touchDownTime;
	float startToMoveTime;
	
    @Override
	public boolean onTouchEvent(MotionEvent event) {
		float X = event.getX();
		float Y = event.getY();
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			touchDownTime = SystemClock.uptimeMillis();  

			touchDown(X, Y);
			break;
		case MotionEvent.ACTION_MOVE:{
			startToMoveTime = SystemClock.uptimeMillis(); 

			if (event.getPointerCount() == 2) {
	            float x = event.getX(0) - event.getX(1);
	            float y = event.getY(0) - event.getY(1);
	            float value = (float) Math.sqrt(x * x + y * y);
	            if (baseValue == 0) {
	                baseValue = value;
	            } 
	            else {
	                if (value - baseValue >= 10 || value - baseValue <= -10) {
	                    float scale = value / baseValue;
	                    render.card.setTimes(scale);
	                }
	            }
	        }

	        else{
	            if(startToMoveTime-touchDownTime<=500)
	        	touchMove(X, Y);
	            else
	            longPressDrag(X, Y);
	        }
			}
			break;
		case MotionEvent.ACTION_UP:
			touchUp(X, Y);
			break;
		}
		return true;
	}

	private void touchDown(float x2, float y2) {
		// TODO Auto-generated method stub

		sX = x2;
		sY = y2;
	}

	private void touchMove(float x2, float y2) {
		// TODO Auto-generated method stub
		float dx = x2 - sX;
		float dy = y2 - sY;

		render.card.setAngleY(render.card.getAngleY() + 45f * dx / (3.1415926f*150f));
		render.card.setAngleX(render.card.getAngleX() + 45f * dy / (3.1415926f*150f));
		sX = x2;
		sY = y2;
	}

	private void touchUp(float x2, float y2) {
		// TODO Auto-generated method stub

	}
	
	private void longPressDrag(float x2, float y2){

		float dx = (sX - x2)/10;
		float dy = (sY - y2)/10;
		render.card.setDragDist(dx,dy);
		
		sX = x2;
		sY = y2;
		
	}
	


	
}
