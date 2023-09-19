package kr.co.episode.epilepsee;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.style.LineBackgroundSpan;

public class CustomMultipleDotSpan implements LineBackgroundSpan {
    private static final float DEFAULT_RADIUS = 0;
    private float radius;
    private int[] color;

    public CustomMultipleDotSpan() {
        this.radius = DEFAULT_RADIUS;
        this.color = new int[]{0};
    }

    public CustomMultipleDotSpan(int color) {
        this.radius = DEFAULT_RADIUS;
        this.color = new int[]{color};
    }

    public CustomMultipleDotSpan(float radius) {
        this.radius = radius;
        this.color = new int[]{0};
    }

    public CustomMultipleDotSpan(float radius, int[] color) {
        this.radius = radius;
        this.color = color;
    }

    @Override
    public void drawBackground(Canvas canvas, Paint paint, int left, int right, int top, int baseline, int bottom, CharSequence charSequence, int start, int end, int lineNum) {
        int total = (color.length > 2) ? 3 : color.length;
        int leftMost = (total - 1) * -12;

        for (int i = 0; i < total; i++) {
            int oldColor = paint.getColor();
            if (color[i] != 0) {
                paint.setColor(color[i]);
            }
            canvas.drawCircle(((left + right) / 2 - leftMost), bottom + radius, radius, paint);
            paint.setColor(oldColor);
            leftMost += 24;
        }
    }
}
