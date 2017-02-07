package com.frank.directioncontrol;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;

/**
 * Created by zhongchao on 2017/2/4.
 */
public class ThirdActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        Fragment f1 = new Fragment1();
        getFragmentManager().beginTransaction().replace(R.id.fragment, f1);

    }
}
