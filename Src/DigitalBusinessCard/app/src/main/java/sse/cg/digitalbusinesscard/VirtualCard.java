package sse.cg.digitalbusinesscard;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;
import android.os.Environment;

public class VirtualCard {
    private FloatBuffer vertexBuffer;
    private FloatBuffer texBuffer;
    public FloatBuffer normBuff;

    private float[] vertices = {
            -1.77f, -1.0f, 0.0f,
            1.77f, -1.0f, 0.0f,
            -1.77f, 1.0f, 0.0f,
            1.77f, 1.0f, 0.0f
    };

    float[] texCoords = {
            0.0f, 1.0f,
            1.0f, 1.0f,
            0.0f, 0.0f,
            1.0f, 0.0f
    };

    float norms[] = new float[]{
            // FRONT    
            0f, 0f, 1f,
            0f, 0f, 1f,
            0f, 0f, 1f,
            0f, 0f, 1f,
            // BACK    
            0f, 0f, -1f,
            0f, 0f, -1f,
            0f, 0f, -1f,
            0f, 0f, -1f,
            // LEFT    
            -1f, 0f, 0f,
            -1f, 0f, 0f,
            -1f, 0f, 0f,
            -1f, 0f, 0f,
            // RIGHT    
            1f, 0f, 0f,
            1f, 0f, 0f,
            1f, 0f, 0f,
            1f, 0f, 0f,
            // TOP    
            0f, 1f, 0f,
            0f, 1f, 0f,
            0f, 1f, 0f,
            0f, 1f, 0f,
            // BOTTOM    
            0f, -1f, 0f,
            0f, -1f, 0f,
            0f, -1f, 0f,
            0f, -1f, 0f
    };
    int[] textureIDs = new int[2];

    private float angleX = 0;
    private float angleY = 0;
    private float times = 1.0f;

    private float x_shift = 0.0f;
    private float y_shift = 0.0f;

    public void setDragDist(float dx, float dy) {
        this.x_shift = dx;
        this.y_shift = dy;
    }

    public float getX_shift() {
        return this.x_shift;
    }

    public float getY_shift() {
        return this.y_shift;
    }

    public float getTimes() {
        return times;
    }

    public void setTimes(float t) {
        this.times = t;
    }

    public float getAngleX() {
        return angleX;
    }

    public void setAngleX(float angleX) {
        if (angleX > 360 || angleX < -360) {
            this.angleX = angleX % 360;
        } else {
            this.angleX = angleX;
        }
    }

    public float getAngleY() {
        return angleY;
    }

    public void setAngleY(float angleY) {
        if (angleY > 360 || angleY < -360) {
            this.angleY = angleY % 360;
        } else {
            this.angleY = angleY;
        }
    }

    public FloatBuffer makeFloatBuffer(float[] arr) {
        ByteBuffer bb = ByteBuffer.allocateDirect(arr.length * 4);
        bb.order(ByteOrder.nativeOrder());
        FloatBuffer fb = bb.asFloatBuffer();
        fb.put(arr);
        fb.position(0);
        return fb;
    }

    public VirtualCard() {

        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
        vbb.order(ByteOrder.nativeOrder());
        vertexBuffer = vbb.asFloatBuffer();
        vertexBuffer.put(vertices);
        vertexBuffer.position(0);

        ByteBuffer tbb = ByteBuffer.allocateDirect(texCoords.length * 4);
        tbb.order(ByteOrder.nativeOrder());
        texBuffer = tbb.asFloatBuffer();
        texBuffer.put(texCoords);
        texBuffer.position(0);

        normBuff = makeFloatBuffer(norms);
    }

    public void draw(GL10 gl) {
        gl.glRotatef(angleY + 180, 0f, 1f, 0f);
        gl.glRotatef(-angleX, 1f, 0f, 0f);

        gl.glFrontFace(GL10.GL_CCW);
        gl.glEnable(GL10.GL_CULL_FACE);
        gl.glCullFace(GL10.GL_BACK);

        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
        gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
        gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, texBuffer);

        gl.glPushMatrix();
        gl.glTranslatef(0.0f, 0.0f, 0.0f);
        gl.glBindTexture(GL10.GL_TEXTURE_2D, textureIDs[1]);
        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
        gl.glPopMatrix();

        gl.glPushMatrix();
        gl.glRotatef(180.0f, 0.0f, 1.0f, 0.0f);
        gl.glTranslatef(0.0f, 0.0f, 0.0f);
        gl.glBindTexture(GL10.GL_TEXTURE_2D, textureIDs[0]);
        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
        gl.glPopMatrix();

        gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glDisable(GL10.GL_CULL_FACE);
    }


    public void loadTexture(GL10 gl, Context context, String cardName) throws FileNotFoundException {
        gl.glGenTextures(1, textureIDs, 0);

        gl.glBindTexture(GL10.GL_TEXTURE_2D, textureIDs[0]);

        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);

        String path = Environment.getExternalStorageDirectory() + "/" + cardName + "f.jpeg";
        FileInputStream fis = new FileInputStream(path);
        Bitmap bmp;
        try {

            bmp = BitmapFactory.decodeStream(fis);
        } finally {
            try {
                fis.close();
            } catch (IOException e) {
            }
        }

        GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bmp, 0);

        gl.glBindTexture(GL10.GL_TEXTURE_2D, textureIDs[1]);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);

        Bitmap bmp2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.card_back);

        GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bmp2, 0);
    }
}

