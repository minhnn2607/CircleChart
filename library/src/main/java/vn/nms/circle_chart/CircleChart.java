package vn.nms.circle_chart;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;

import java.util.ArrayList;
import java.util.List;

public class CircleChart extends View {
    private final int DEFAULT_MAX_VALUE = 10000;
    private final int DEFAULT_VALUE = 0;
    private final float DEFAULT_FONT_SIZE = 16f;
    private final float DEFAULT_DASH_SIZE = 15f;
    private final float DEFAULT_START_DEGREE = -225f;
    private final float DEFAULT_END_DEGREE = 45f;
    private final float DEFAULT_ARROW_LENGTH = 15f;
    private final float DEFAULT_ARROW_WIDTH = 8f;
    private final float DEFAULT_ARROW_HEAD_LENGTH = 5f;
    private final int DEFAULT_COLOR = getColor(0, 0, 0);
    private final int DEFAULT_COLOR_BACKGROUND = getColor(197, 197, 197);
    private final float DEFAULT_DASH_ANGLE = 0.5f;
    private final float DEFAULT_DASH_SPACE = 3f;
    private final int DEFAULT_DURATION = 1000; //1 seconds
    private final float DEFAULT_MARGIN = 10f; //1 seconds
    private final int MAX_COLOR_PROGRESS_R = 239;
    private final int MIN_COLOR_PROGRESS_R = 127;
    private final int MAX_COLOR_PROGRESS_G = 79;
    private final int MAX_COLOR_PROGRESS_B = 83;


    private float maxValue;
    private float currentValue = 0f;
    private int totalFontSize;
    private int totalFontColor;
    private int arrowColor;
    private int normalColor;
    private int progressColor;
    private int dashLength;
    private int duration;
    private int topMargin, botMargin;

    private String topText;
    private int topFontSize;
    private int topFontColor;

    private String targetText;
    private int targetFontSize;
    private int targetFontColor;

    private String botText;
    private int botFontSize;
    private int botFontColor;

    private Paint normalPaint;
    private Paint progressPaint;
    private Paint progressHighPaint;
    private Paint arrowPaint;
    private TextPaint textPaint;
    private float startDegree;
    private float endDegree;
    RectF oval = new RectF();

    private float drawValue = currentValue;
    private float lastValue;
    private float dashAngle;
    private float dashSpaceAngle;
    private int arrowLength, arrowWidth, arrowHeadLength;
    private ValueAnimator valueAnimator;
    private Interpolator interpolator = new AccelerateDecelerateInterpolator();

    private Path onPath, highPath, offPath, arrowPath;

    public CircleChart(Context context) {
        super(context);
        init(context, null);
    }

    public CircleChart(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CircleChart(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, @Nullable AttributeSet attrs) {
        TypedArray attributes = context.obtainStyledAttributes(attrs,
                R.styleable.CircleChart);
        if (attributes != null) {
            maxValue = attributes.getInt(R.styleable.CircleChart_sc_maxValue, DEFAULT_MAX_VALUE);
            currentValue = attributes.getInt(R.styleable.CircleChart_sc_progressValue, DEFAULT_VALUE);

            totalFontSize = attributes.getDimensionPixelSize(R.styleable.CircleChart_sc_totalFontSize,
                    convertDpToPixel(DEFAULT_FONT_SIZE, context));
            totalFontColor = attributes.getColor(R.styleable.CircleChart_sc_totalFontColor,
                    DEFAULT_COLOR);

            topText = attributes.getString(R.styleable.CircleChart_sc_topText);
            if (topText == null) topText = "";
            topFontSize = attributes.getDimensionPixelSize(R.styleable.CircleChart_sc_topFontSize,
                    convertDpToPixel(DEFAULT_FONT_SIZE, context));
            topFontColor = attributes.getColor(R.styleable.CircleChart_sc_topFontColor,
                    DEFAULT_COLOR);

            targetText = attributes.getString(R.styleable.CircleChart_sc_targetText);
            if (targetText == null) targetText = "";
            targetFontSize = attributes.getDimensionPixelSize(R.styleable.CircleChart_sc_targetFontSize,
                    convertDpToPixel(DEFAULT_FONT_SIZE, context));
            targetFontColor = attributes.getColor(R.styleable.CircleChart_sc_targetFontColor,
                    DEFAULT_COLOR);

            botText = attributes.getString(R.styleable.CircleChart_sc_botText);
            if (botText == null) botText = "";
            botFontSize = attributes.getDimensionPixelSize(R.styleable.CircleChart_sc_botFontSize,
                    convertDpToPixel(DEFAULT_FONT_SIZE, context));
            botFontColor = attributes.getColor(R.styleable.CircleChart_sc_botFontColor,
                    DEFAULT_COLOR);

            arrowColor = attributes.getColor(R.styleable.CircleChart_sc_arrowColor,
                    DEFAULT_COLOR);
            normalColor = attributes.getColor(R.styleable.CircleChart_sc_dashColor,
                    DEFAULT_COLOR_BACKGROUND);
            progressColor = attributes.getColor(R.styleable.CircleChart_sc_dashProgressColor,
                    DEFAULT_COLOR);
            dashLength = attributes.getDimensionPixelSize(R.styleable.CircleChart_sc_dashLength,
                    convertDpToPixel(DEFAULT_DASH_SIZE, context));

            arrowLength = attributes.getDimensionPixelSize(R.styleable.CircleChart_sc_arrowLength,
                    convertDpToPixel(DEFAULT_ARROW_LENGTH, context));
            arrowWidth = attributes.getDimensionPixelSize(R.styleable.CircleChart_sc_arrowWidth,
                    convertDpToPixel(DEFAULT_ARROW_WIDTH, context));
            arrowHeadLength = attributes.getDimensionPixelSize(R.styleable.CircleChart_sc_arrowHeadLength,
                    convertDpToPixel(DEFAULT_ARROW_HEAD_LENGTH, context));

            dashAngle = attributes.getFloat(R.styleable.CircleChart_sc_dashWidthAngle, DEFAULT_DASH_ANGLE);
            dashSpaceAngle = attributes.getFloat(R.styleable.CircleChart_sc_dashSpaceAngle, DEFAULT_DASH_SPACE);

            startDegree = attributes.getFloat(R.styleable.CircleChart_sc_startAngle, DEFAULT_START_DEGREE);
            endDegree = attributes.getFloat(R.styleable.CircleChart_sc_endAngle, DEFAULT_END_DEGREE);
            duration = attributes.getInt(R.styleable.CircleChart_sc_duration, DEFAULT_DURATION);

            topMargin = attributes.getDimensionPixelSize(R.styleable.CircleChart_sc_marginTop,
                    convertDpToPixel(DEFAULT_MARGIN, context));
            botMargin = attributes.getDimensionPixelSize(R.styleable.CircleChart_sc_marginBot,
                    convertDpToPixel(DEFAULT_MARGIN, context));
            attributes.recycle();
        }

        onPath = new Path();
        offPath = new Path();
        highPath = new Path();
        arrowPath = new Path();
        normalPaint = new Paint();
        normalPaint.setStyle(Paint.Style.STROKE);
        normalPaint.setColor(normalColor);
        normalPaint.setStrokeWidth(dashLength);
        normalPaint.setAntiAlias(true);

        progressPaint = new Paint();
        progressPaint.setStyle(Paint.Style.STROKE);
        progressPaint.setColor(progressColor);
        progressPaint.setStrokeWidth(dashLength);
        progressPaint.setAntiAlias(true);

        progressHighPaint = new Paint();
        progressHighPaint.setStyle(Paint.Style.STROKE);
        progressHighPaint.setColor(totalFontColor);
        progressHighPaint.setStrokeWidth(dashLength);
        progressHighPaint.setAntiAlias(true);

        arrowPaint = new Paint();
        arrowPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        arrowPaint.setColor(arrowColor);
        arrowPaint.setStrokeWidth(1);
        arrowPaint.setAntiAlias(true);

        textPaint = new TextPaint();
        textPaint.setAntiAlias(true);
        textPaint.setTextAlign(Paint.Align.LEFT);

        initValueAnimator();
    }

    @Override
    public void onDraw(Canvas canvas) {
        drawBackground(canvas);
        drawProgress(canvas);
        drawTotalText("" + (int) drawValue, canvas);
        drawBottomText(canvas);
    }

    private void drawTotalText(String text, Canvas canvas) {
        Rect rect = new Rect();
        canvas.getClipBounds(rect);
        int cHeight = rect.height();
        int cWidth = rect.width();

        textPaint.setTextSize(totalFontSize);
        textPaint.setColor(totalFontColor);
        textPaint.getTextBounds(text, 0, text.length(), rect);
        float totalX = cWidth / 2f - rect.width() / 2f - rect.left;
        float totalY = cHeight / 2f + rect.height() / 2f - rect.bottom;
        float totalH = rect.height();
        canvas.drawText(text, totalX, totalY, textPaint);

        textPaint.setTextSize(topFontSize);
        textPaint.setColor(topFontColor);
        textPaint.getTextBounds(topText, 0, topText.length(), rect);
        float topX = cWidth / 2f - rect.width() / 2f - rect.left;
        float topY = cHeight / 2f - totalH / 2f - rect.height() / 2f - rect.bottom - topMargin;
        canvas.drawText(topText, topX, topY, textPaint);

    }

    private void drawBottomText(Canvas canvas) {
        Rect rect = new Rect();
        canvas.getClipBounds(rect);
        int cHeight = rect.height();
        int cWidth = rect.width();

        textPaint.setTextSize(botFontSize);
        textPaint.setColor(botFontColor);
        textPaint.getTextBounds(botText, 0, botText.length(), rect);
        float levelX = cWidth / 2f - rect.width() / 2f - rect.left;
        float levelY = cHeight - rect.height() / 2f - rect.bottom;
        float levelH = rect.height();
        canvas.drawText(botText, levelX, levelY, textPaint);

        textPaint.setTextSize(targetFontSize);
        textPaint.setColor(targetFontColor);
        textPaint.getTextBounds(targetText, 0, targetText.length(), rect);
        textPaint.getTextBounds(targetText, 0, targetText.length(), rect);
        float goalX = cWidth / 2f - rect.width() / 2f - rect.left;
        float goalY = cHeight - levelH - rect.height() / 2f - rect.bottom - botMargin;

        canvas.drawText(targetText, goalX, goalY, textPaint);
    }

    @Override
    protected void onSizeChanged(int width, int height, int oldW, int oldH) {
        float padding = dashLength / 2f + arrowLength / 2f;
        oval.set(padding, padding, width - padding, height - padding);
        super.onSizeChanged(width, height, oldW, oldH);
    }

    private void drawBackground(Canvas canvas) {
        offPath.reset();
        for (float i = startDegree; i < endDegree; i += dashSpaceAngle) {
            offPath.addArc(oval, i, dashAngle);
        }
        canvas.drawPath(offPath, normalPaint);
    }

    private void drawProgress(Canvas canvas) {
        onPath.reset();
        highPath.reset();

        float lastDegree = 0;

        List<Float> highValueList = new ArrayList<>();
        float chartValue = (drawValue > maxValue) ? maxValue : drawValue;
        for (float i = startDegree;
             i <= (chartValue / maxValue) * (endDegree - startDegree) + startDegree; i += dashSpaceAngle) {
            if (i >= 0.1f * (endDegree - startDegree) && i <= endDegree) {
                highValueList.add(i);
            } else {
                onPath.addArc(oval, i, dashAngle);
            }

            lastDegree = i;
        }

        //Draw progress
        canvas.drawPath(onPath, progressPaint);

        if (highValueList.size() > 0) {
            int index = 0;
            int colorRange = (MAX_COLOR_PROGRESS_R - MIN_COLOR_PROGRESS_R) / highValueList.size();
            for (float i : highValueList) {
                int newColor = getColor(MIN_COLOR_PROGRESS_R + index * colorRange,
                        MAX_COLOR_PROGRESS_G, MAX_COLOR_PROGRESS_B);
                progressHighPaint.setColor(newColor);
                canvas.drawArc(oval, i, dashAngle, false, progressHighPaint);
                index++;
                if (index == highValueList.size()) {
                    arrowPaint.setColor(newColor);
                }
            }
        }else{
            arrowPaint.setColor(arrowColor);
        }

//        canvas.drawPath(highPath, progressHighPaint);

        //Draw arrow
        if (drawValue <= 0) {
            lastDegree = startDegree;
        }
        arrowPath.reset();
        float radius = canvas.getWidth() / 2f - dashLength / 2f;
        float centerX = canvas.getWidth() / 2f;
        float centerY = canvas.getHeight() / 2f;
        float arrowAngel = (lastDegree + dashAngle / 2f) * (float) Math.PI / 180;

        float xPos = centerX + (float) Math.cos(arrowAngel) * radius - arrowLength / 2f;
        float yPos = centerY + (float) Math.sin(arrowAngel) * radius - arrowWidth / 2f;

        float tailLength = arrowLength - arrowHeadLength;
        PointF p0 = new PointF(xPos, yPos);
        PointF p1 = new PointF(xPos + tailLength, yPos);
        PointF p2 = new PointF(xPos + arrowLength, yPos + arrowWidth / 2f);
        PointF p3 = new PointF(xPos + tailLength, yPos + arrowWidth);
        PointF p4 = new PointF(xPos, yPos + arrowWidth);

        arrowPath.moveTo(p0.x, p0.y);
        arrowPath.lineTo(p1.x, p1.y);
        arrowPath.lineTo(p2.x, p2.y);
        arrowPath.lineTo(p3.x, p3.y);
        arrowPath.lineTo(p4.x, p4.y);
        arrowPath.lineTo(p0.x, p0.y);
        arrowPath.close();

        Matrix mMatrix = new Matrix();
        RectF bounds = new RectF();
        arrowPath.computeBounds(bounds, true);
        mMatrix.postRotate((180 + lastDegree), bounds.centerX(), bounds.centerY());
        arrowPath.transform(mMatrix);

        canvas.drawPath(arrowPath, arrowPaint);
    }

    public void setCurrentValue(float currentValue) {
        this.currentValue = currentValue;
        animateValue();

    }

    private void animateValue() {
        if (valueAnimator != null) {
            valueAnimator.setFloatValues(lastValue, currentValue);
            valueAnimator.setDuration(duration);
            valueAnimator.start();
        }
    }


    private class ValueAnimatorListenerImp implements ValueAnimator.AnimatorUpdateListener {
        @Override
        public void onAnimationUpdate(ValueAnimator valueAnimator) {
            drawValue = (Float) valueAnimator.getAnimatedValue();
            lastValue = drawValue;
            invalidate();
        }
    }

    private void initValueAnimator() {
        valueAnimator = new ValueAnimator();
        valueAnimator.setInterpolator(interpolator);
        valueAnimator.addUpdateListener(new ValueAnimatorListenerImp());
    }

    public void setTarget(String targetText) {
        this.targetText = targetText;
        invalidate();
    }

    public void setLevel(String levelText) {
        this.botText = levelText;
        invalidate();
    }

    public void setMaxVue(float max) {
        this.maxValue = max;
        invalidate();
    }

    public int getColor(int R, int G, int B) {
        return 0xff000000 | (R << 16) | (G << 8) | B;
    }

    public static int convertDpToPixel(float dp, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return (int)px;
    }

}
