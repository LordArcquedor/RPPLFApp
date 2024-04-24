package rp.plf;
import android.content.Context;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import androidx.appcompat.widget.AppCompatImageView;

public class ZoomableImageView extends AppCompatImageView {

    private Matrix matrix;
    private ScaleGestureDetector scaleGestureDetector;

    private PointF lastTouchPoint;
    private float lastScaleFactor = 0f;

    public ZoomableImageView(Context context) {
        super(context);
        init();
    }

    public ZoomableImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ZoomableImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        matrix = new Matrix();
        scaleGestureDetector = new ScaleGestureDetector(getContext(), new ScaleListener());
        setScaleType(ScaleType.MATRIX);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        scaleGestureDetector.onTouchEvent(event);

        PointF currentTouchPoint = new PointF(event.getX(), event.getY());

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastTouchPoint = currentTouchPoint;
                break;

            case MotionEvent.ACTION_MOVE:
                float deltaX = currentTouchPoint.x - lastTouchPoint.x;
                float deltaY = currentTouchPoint.y - lastTouchPoint.y;

                matrix.postTranslate(deltaX, deltaY);
                setImageMatrix(matrix);

                lastTouchPoint = currentTouchPoint;
                break;
        }

        return true;
    }

    private float getMatrixScaleFactor() {
        float[] matrixValues = new float[9];
        matrix.getValues(matrixValues);
        return matrixValues[Matrix.MSCALE_X];
    }
    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        // Variables de translation maximale de l'image
        private float maxTranslateX;
        private float maxTranslateY;

        @Override
        public boolean onScaleBegin(ScaleGestureDetector detector) {
            // Calcul des translations maximales en fonction de la taille de l'image et de l'écran
            float imageWidth = getDrawable().getIntrinsicWidth();
            float imageHeight = getDrawable().getIntrinsicHeight();
            float viewWidth = getWidth();
            float viewHeight = getHeight();

            float scaleX = imageWidth / viewWidth;
            float scaleY = imageHeight / viewHeight;

            maxTranslateX = (scaleX - 1) * viewWidth / 2f;
            maxTranslateY = (scaleY - 1) * viewHeight / 2f;

            return true;
        }



        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            float scaleFactor = detector.getScaleFactor();
            float focusX = detector.getFocusX();
            float focusY = detector.getFocusY();

            float currentScale = getMatrixScaleFactor();

            if ((currentScale < 5f && scaleFactor > 1f) || (currentScale > 1f && scaleFactor < 1f)) {
                // Appliquer le zoom seulement si le facteur de zoom est inférieur à 5 (x5) lors du zoom avant
                // ou supérieur à 1 lors du zoom arrière.
                matrix.postScale(scaleFactor, scaleFactor, focusX, focusY);

                // Récupérer les translations actuelles de la matrice
                float[] matrixValues = new float[9];
                matrix.getValues(matrixValues);
                float translateX = matrixValues[Matrix.MTRANS_X];
                float translateY = matrixValues[Matrix.MTRANS_Y];

                // Vérifier et limiter les translations de l'image
                if (translateX > maxTranslateX) {
                    translateX = maxTranslateX;
                } else if (translateX < -maxTranslateX) {
                    translateX = -maxTranslateX;
                }

                if (translateY > maxTranslateY) {
                    translateY = maxTranslateY;
                } else if (translateY < -maxTranslateY) {
                    translateY = -maxTranslateY;
                }

                matrixValues[Matrix.MTRANS_X] = translateX;
                matrixValues[Matrix.MTRANS_Y] = translateY;
                matrix.setValues(matrixValues);

                setImageMatrix(matrix);
            }

            return true;
        }
    }

}
