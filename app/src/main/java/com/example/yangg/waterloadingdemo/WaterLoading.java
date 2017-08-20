package com.example.yangg.waterloadingdemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.print.PrintAttributes;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by yangg on 2017/7/2.
 */

public class WaterLoading extends TextView {

    private BitmapShader shader;
    private Drawable wave;
    private int waveHeight;
    private Matrix margins;
    private int waveWidth;
    private int h;

    public WaterLoading(Context context) {
        this(context, null);
    }

    public WaterLoading(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WaterLoading(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();

    }

    private void init() {
        //让当前空间文本字体文件喂红色
        this.setTextColor(Color.RED);
        //让当前的textview喂美术字体  从资产目录中那出字体
        Typeface fromAsset = Typeface.createFromAsset(getResources().getAssets(), "Satisfy-Regular.ttf");
        setTypeface(fromAsset);
        /**
         * 作色器,,专门用于绘制这个效果的东西
         * //一个图片,只放到了字上面
         * 效果类似与配器
         *
         * 准备一个配枪:着色器
         *
         */

        /**
         * 矩阵可以山西爱你师徒的平移旋转小姑
         *
         */
        margins = new Matrix();

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        careateShader();

        //定义初值
        shaderX = 0;
        shaderY = waveHeight / 2;
        this.h = h;
    }

    private void careateShader() {
        /***
         * drawable 和bitmap 有什么区别?
         * Bitmap表示的一个位图
         * Drawable表示的是图形,抽象概念
         */
        Bitmap orignalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.wave);
        int waveWidth2 = orignalBitmap.getWidth();
        int waveHeight2 = orignalBitmap.getWidth();


        /**
         * 方法2
         *
         */
        //通过getResurce获取的Drawable是没有边界的需要手动设置边界
        wave = getResources().getDrawable(R.drawable.wave);
        //图片的原始狂傲
        waveWidth = wave.getIntrinsicWidth();
        //图片的原始狂傲
        waveHeight = wave.getIntrinsicHeight();
        Bitmap bitmap = Bitmap.createBitmap(waveWidth, waveHeight, Bitmap.Config.ARGB_8888);//3带有他的原有透明度
//创建月诶个画布,为了将wrave 的图片数据写入到bitmap中
        Canvas canvas = new Canvas(bitmap);
        //画布必须有一个初始颜色,不然无法将数据数据写入
        canvas.drawColor(getCurrentTextColor());
        //如果wave没有便捏,则cavas无法进行回执
        wave.setBounds(0, 0, waveWidth, waveHeight);
        wave.draw(canvas);

        /**
         * Clamp 使用原来的额那张图片整体不i做变化,他会把剩余空间,做一个拉伸
         * REPEAT:将原来的图片辅助无数分
         * MIRROR:镜像,将原来的图片竞相后,写入后,在镜像,无线循环
         */

        /**喷枪  :着色器
         * 1,bitmap
         * 模式2,x方向一直平移:x方向的模式:无数的复制
         * 3,y轴方向向下移动:y方向的模式:不用复制
         *
         */
        shader = new BitmapShader(bitmap, Shader.TileMode.REPEAT, Shader.TileMode.CLAMP);
        //利用着色器进行作色
        getPaint().setShader(shader);
    }

    //初值
    private float shaderX;
    private float shaderY;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        shaderX += 5;
        shaderY += 1;
        //移动一个 loading的高度
        if (shaderY > waveHeight / 2 + h) {
            shaderY = -waveHeight / 2;
        }
        //让bitmaphader不断向上和吓吓移动
        margins.setTranslate(shaderX, shaderY);
        shader.setLocalMatrix(margins);
        invalidate();
    }
}
