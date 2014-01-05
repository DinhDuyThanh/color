package com.example.color;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity {

	TextView touchedXY, invertedXY, imgSize, colorRGB;
	ImageView imgSource1, imgSource2;
	float eventX , eventY;
	ShapeDrawable mDrawable;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_main);
		
		touchedXY = (TextView) findViewById(R.id.xy);
		invertedXY = (TextView) findViewById(R.id.invertedxy);
		imgSize = (TextView) findViewById(R.id.size);
		colorRGB = (TextView) findViewById(R.id.colorrgb);
		imgSource1 = (ImageView) findViewById(R.id.source1);
		imgSource2 = (ImageView) findViewById(R.id.source2);
		imgSource1.setOnTouchListener(imgSourceOnTouchListener);
		
	
//		LinearLayout l = (LinearLayout) findViewById(R.layout.activity_main);
//		GraphicsView gn = new GraphicsView(this);
	//	l.addView(gn);
//		MyCustomPanel view = new MyCustomPanel(this);
//		ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
//		addContentView(view, params);
//		view.setOnTouchListener(imgSourceOnTouchListener);
	}


	OnTouchListener imgSourceOnTouchListener = new OnTouchListener() {

		@Override
		public boolean onTouch(View view, MotionEvent event) {

			 eventX = event.getX();
			 eventY = event.getY();
			
			float[] eventXY = new float[] { eventX, eventY };

			Matrix invertMatrix = new Matrix();
			((ImageView) view).getImageMatrix().invert(invertMatrix);

			invertMatrix.mapPoints(eventXY);
			int x = Integer.valueOf((int) eventXY[0]);
			int y = Integer.valueOf((int) eventXY[1]);
//			imgSource2.layout(x, x+40, y, y+40);
			LinearLayout.LayoutParams mParams = (LinearLayout.LayoutParams) imgSource2.getLayoutParams();
            mParams.leftMargin = x-20;
            mParams.topMargin = y-340;
            imgSource2.setLayoutParams(mParams);
			int x2 = (int)imgSource2.getLeft();
			int y2 = (int)imgSource2.getTop();
			touchedXY.setText("Tọa độ ảnh di chuyển: "+x2+"/"+y2);
			
//			touchedXY.setText("Tọa độ(float): " + String.valueOf(eventX)
//					+ " / " + String.valueOf(eventY));
			invertedXY.setText("Tọa độ(int): " + String.valueOf(x) + " / "
					+ String.valueOf(y));

			Drawable imgDrawable = ((ImageView) view).getDrawable();
			Bitmap bitmap = ((BitmapDrawable) imgDrawable).getBitmap();

			imgSize.setText("Kích thước: " + String.valueOf(bitmap.getWidth())
					+ " / " + String.valueOf(bitmap.getHeight()));

			// Limit x, y range within bitmap
			if (x < 0) {
				x = 0;
			} else if (x > bitmap.getWidth() - 1) {
				x = bitmap.getWidth() - 1;
			}

			if (y < 0) {
				y = 0;
			} else if (y > bitmap.getHeight() - 1) {
				y = bitmap.getHeight() - 1;
			}

			int touchedRGB = bitmap.getPixel(x, y);
			int R = Color.red(touchedRGB);
			int G =Color.green(touchedRGB);
			int B = Color.blue(touchedRGB);
			colorRGB.setText("Màu: " + "#" + Integer.toHexString(touchedRGB));
			colorRGB.setText("Màu RGB: R:"+R+" G:"+G+" B:"+B);
			colorRGB.setTextColor(touchedRGB);

			return true;
		}
	};

	public class MyCustomPanel extends View{
		public MyCustomPanel(Context context){
			super(context);
		}
		@Override
		public void draw(Canvas canvas){
			Paint paint = new Paint();
			paint.setColor(Color.GREEN);
			paint.setStrokeWidth(6);
			canvas.drawLine(10,10,50,50,paint);
	        paint.setColor(Color.RED);
	        
	        canvas.drawLine(50, 50, 90, 10, paint);
	        canvas.drawCircle(50, 50, 3, paint);
	        
	        canvas.drawCircle(eventX,eventY,3,paint);
		}
	}
}