package sse.cg.digitalbusinesscard;

import java.io.FileNotFoundException;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.GLU;

public class MyRenderer implements Renderer {
    private Context context;

    public VirtualCard card;
    public String cardName;

    private float xrot;
    private float yrot;
    private float zrot;

    public MyRenderer(Context context, String name) {
        this.context = context;
        this.cardName = name;

        card = new VirtualCard();
    }

    public void onDrawFrame(GL10 gl) {

        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

        gl.glLoadIdentity();

        gl.glTranslatef(0.0f, 0.0f, -5.0f);

        gl.glScalef(0.8f, 0.8f, 0.8f);

        gl.glScalef(card.getTimes(), card.getTimes(), card.getTimes());

        card.draw(gl);

        xrot += 0.3f;
        yrot += 0.2f;
        zrot += 0.4f;
    }

    public void onSurfaceChanged(GL10 gl, int width, int height) {
        if (height == 0) {
            height = 1;
        }

        gl.glViewport(0, 0, width, height);

        gl.glMatrixMode(GL10.GL_PROJECTION);

        gl.glLoadIdentity();

        GLU.gluPerspective(gl, 45.0f, (float) width / (float) height, 0.1f, 100.0f);

        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    public void onSurfaceCreated(GL10 gl, EGLConfig config) {

        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_FASTEST);

        gl.glNormalPointer(GL10.GL_FLOAT, 0, card.normBuff);
        gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);
        gl.glClearColor(0, 0, 0, 1);

        gl.glShadeModel(GL10.GL_SMOOTH);

        gl.glClearDepthf(1.0f);

        gl.glEnable(GL10.GL_DEPTH_TEST);

        gl.glDepthFunc(GL10.GL_LEQUAL);

        gl.glDisable(GL10.GL_DITHER);

        try {
            card.loadTexture(gl, context, cardName);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        gl.glEnable(GL10.GL_TEXTURE_2D);
    }
}
