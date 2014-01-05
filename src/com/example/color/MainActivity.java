package com.example.color;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {

	TextView touchedXY, invertedXY, imgSize, colorRGB;
	ImageView imgSource1, imgSource2;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		touchedXY = (TextView) findViewById(R.id.xy);
		invertedXY = (TextView) findViewById(R.id.invertedxy);
		imgSize = (TextView) findViewById(R.id.size);
		colorRGB = (TextView) findViewById(R.id.colorrgb);
		imgSource1 = (ImageView) findViewById(R.id.source1);

		imgSource1.setOnTouchListener(imgSourceOnTouchListener);
	}

	OnTouchListener imgSourceOnTouchListener = new OnTouchListener() {

		@Override
		public boolean onTouch(View view, MotionEvent event) {

			float eventX = event.getX();
			float eventY = event.getY();
			float[] eventXY = new float[] { eventX, eventY };

			Matrix invertMatrix = new Matrix();
			((ImageView) view).getImageMatrix().invert(invertMatrix);

			invertMatrix.mapPoints(eventXY);
			int x = Integer.valueOf((int) eventXY[0]);
			int y = Integer.valueOf((int) eventXY[1]);

			touchedXY.setText("Tọa độ(float): " + String.valueOf(eventX)
					+ " / " + String.valueOf(eventY));
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

			colorRGB.setText("Màu: " + "#" + Integer.toHexString(touchedRGB));
			colorRGB.setTextColor(touchedRGB);

			return true;
		}
	};

}