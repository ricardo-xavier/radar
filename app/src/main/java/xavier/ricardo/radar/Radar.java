package xavier.ricardo.radar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class Radar extends View {

    private Context context;
    private int xCentro = 250;
    private int yCentro = 250;

    public Radar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        Populacao populacao = (Populacao) getTag();

        int cor = Color.BLACK;
        switch (populacao.getEu().getStatus()) {
            case "suspeito":
                cor = Color.YELLOW;
                break;
            case "infectado":
                cor = Color.RED;
                break;
            case "recuperado":
                cor = Color.GREEN;
                break;
        }

        canvas.drawCircle(xCentro, yCentro, 3, new Paint(cor));

        Paint paint = new Paint(Color.LTGRAY);
        paint.setStyle(Paint.Style.STROKE);

        canvas.drawCircle(xCentro, yCentro, 50, paint);
        canvas.drawCircle(xCentro, yCentro, 100, paint);
        canvas.drawCircle(xCentro, yCentro, 150, paint);
        canvas.drawCircle(xCentro, yCentro, 200, paint);
        canvas.drawCircle(xCentro, yCentro, 250, paint);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        setMeasuredDimension(500, 500);

    }

}
