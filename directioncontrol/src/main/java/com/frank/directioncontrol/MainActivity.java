package com.frank.directioncontrol;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.frank.directioncontrol.R;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private MyOnDirectionListenner mListenner;
    private DirectionView mView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mView = (DirectionView) findViewById(R.id.directionView);
        mListenner = new MyOnDirectionListenner();
//        mView = new DirectionView(this);
        mView.setOnDirectionListener(mListenner);
    }

    private class MyOnDirectionListenner implements DirectionView.OnDirectionListener {

        @Override
        public void OnDirectionChange(Direction direction) {
            Log.e(TAG, "OnDirectionChange: " + direction);
            switch (direction) {
                case left:
                    Toast.makeText(MainActivity.this, "左", Toast.LENGTH_SHORT).show();
                    break;
                case up:
                    Toast.makeText(MainActivity.this, "上", Toast.LENGTH_SHORT).show();
                    break;
                case rigth:
                    Toast.makeText(MainActivity.this, "右", Toast.LENGTH_SHORT).show();
                    break;
                case down:
                    Toast.makeText(MainActivity.this, "下", Toast.LENGTH_SHORT).show();
                    break;
                case none:
                    Toast.makeText(MainActivity.this, "无", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    Toast.makeText(MainActivity.this, "默认", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }
}
