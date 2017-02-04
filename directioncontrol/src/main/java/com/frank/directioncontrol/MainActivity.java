package com.frank.directioncontrol;

import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.frank.directioncontrol.R;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private MyOnDirectionListenner mListenner;
    private DirectionView mView;
    private TextView mTextView;
    private MyView mMyView;
    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextView = (TextView) findViewById(R.id.textView);
        mView = (DirectionView) findViewById(R.id.directionView);
        mMyView = (MyView) findViewById(R.id.myView);
        mButton = (Button) findViewById(R.id.button);
        mListenner = new MyOnDirectionListenner();
//        mView = new DirectionView(this);
        mView.setOnDirectionListener(mListenner);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SecondActivity.class));
            }
        });
    }

    private class MyOnDirectionListenner implements DirectionView.OnDirectionListener {

        @Override
        public void OnDirectionChange(Direction direction) {
            Log.e(TAG, "OnDirectionChange: " + direction);
            switch (direction) {
                case left:
                    Toast.makeText(MainActivity.this, "left", Toast.LENGTH_SHORT).show();
                    break;
                case up:
                    Toast.makeText(MainActivity.this, "up", Toast.LENGTH_SHORT).show();
                    break;
                case rigth:
                    Toast.makeText(MainActivity.this, "rigth", Toast.LENGTH_SHORT).show();
                    break;
                case down:
                    Toast.makeText(MainActivity.this, "down", Toast.LENGTH_SHORT).show();
                    break;
                case left_up:
                    Toast.makeText(MainActivity.this, "left_up", Toast.LENGTH_SHORT).show();
                    break;
                case rigth_up:
                    Toast.makeText(MainActivity.this, "rigth_up", Toast.LENGTH_SHORT).show();
                    break;
                case left_down:
                    Toast.makeText(MainActivity.this, "left_down", Toast.LENGTH_SHORT).show();
                    break;
                case rigth_down:
                    Toast.makeText(MainActivity.this, "rigth_down", Toast.LENGTH_SHORT).show();
                    break;
                case none:
                    Toast.makeText(MainActivity.this, "none", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    Toast.makeText(MainActivity.this, "default", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }
}
