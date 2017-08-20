package com.example.yangg.waterloadingdemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by yangg on 2017/7/2.
 */

public class WaterLoading2 extends TextView {


    private Matrix matrix;
    private int hight;
    private int waveHeight;
    private BitmapShader shader;

    public WaterLoading2(Context context) {
        this(context, null);
    }

    public WaterLoading2(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WaterLoading2(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    //初始化数据
    private void init() {

        //设置当前的空间文字颜色为hongse
        this.setTextColor(Color.RED);
        //让当前的Textview的字体为美术字体
        Typeface typeface = Typeface.createFromAsset(getResources().getAssets(), "Satisfy-Regular.ttf");
        setTypeface(typeface);
        //定义一个矩阵  Matrix :矩阵,可以实现视图的平移旋转等效果
        matrix = new Matrix();
    }
    //准备一个"喷枪"
    //着色器

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        //创建一个着色器

        this.hight = h;
        createShader();
    }

    private void createShader() {
        //通过getResources().方法获取的Drawable 是没有边界的,需要手动设置边界
        Drawable wave = getResources().getDrawable(R.drawable.wave);
        //指:图片的原始宽度高
        int waveWidth = wave.getIntrinsicWidth();
        waveHeight = wave.getIntrinsicHeight();
        Bitmap bitmap = Bitmap.createBitmap(waveWidth, waveHeight, Bitmap.Config.ARGB_8888);
        //创建一个画布,为了将wave的图片数据写入到Bitmap
        Canvas canvas = new Canvas(bitmap);
        //画布必须有一个颜色,则canvas 无法进行绘制
        canvas.drawColor(Color.GREEN);
        //如果wave没有边界,否则无定法将图片数据写入
        wave.setBounds(0, 0, waveWidth, waveHeight);
        wave.draw(canvas);
        //CLAMP:使用原来的拿着那个图片整体
        //REPEAT:将原来的图片复制无视份
        //MIRROR:镜像,将原来的额图片镜像后,写入,再镜像
        shader = new BitmapShader(bitmap, Shader.TileMode.REPEAT, Shader.TileMode.CLAMP);
        //利用着着色器,进行着色
        getPaint().setShader(shader);
        shaderX = 0;
        shaderY = -waveHeight / 2;

    }

    private float shaderX;
    private float shaderY;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        shaderX += 3;
        shaderY += 0.8;
        if (shaderY > -waveHeight / 2 + hight) {
            shaderY = -waveHeight / 2;
        }
        //让BitmapShader不断想下和向右移动
        matrix.setTranslate(shaderX, shaderY);
        //为着色器设置Matrix,就可以实现着色器的移动
        shader.setLocalMatrix(matrix);
        invalidate();

    }
}
