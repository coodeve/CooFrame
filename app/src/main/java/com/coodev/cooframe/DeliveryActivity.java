package com.coodev.cooframe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.ArrayMap;
import android.view.ViewTreeObserver;

import com.coodev.base.module.ModuleManager;
import com.coodev.base.init.ModuleConfig;

import java.util.ArrayList;

public class DeliveryActivity extends AppCompatActivity {
    private ModuleManager mModuleManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_4_delivery);
        // add listener for delivery
        getWindow().getDecorView().getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (mModuleManager == null) {
                    mModuleManager = new ModuleManager();
                    mModuleManager.initModules(savedInstanceState, DeliveryActivity.this, getLayout4Module());
                }
            }
        });
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mModuleManager != null) {
            mModuleManager.onSaveInstanceState(outState);
        }
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (mModuleManager != null) {
            mModuleManager.onConfigurationChanged(newConfig);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mModuleManager != null) {
            mModuleManager.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mModuleManager != null) {
            mModuleManager.onPause();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mModuleManager != null) {
            mModuleManager.onStop();
        }
    }

    @Override
    protected void onDestroy() {
        if (mModuleManager != null) {
            mModuleManager.onDestroy();
        }
        super.onDestroy();
    }

    /**
     * 将布局id分别赋予对应的module
     *
     * @return module和对应的布局id集合
     */
    protected ArrayMap<String, ArrayList<Integer>> getLayout4Module() {
        ArrayMap<String, ArrayList<Integer>> pageMap = new ArrayMap<>();
        pageMap.put(ModuleConfig.activityModuleNames[0], new ArrayList<Integer>() {{
            add(R.id.media_test_layout);
        }});
        pageMap.put(ModuleConfig.activityModuleNames[1], new ArrayList<Integer>() {{
            add(R.id.widget_test_layout);
        }});
        return pageMap;
    }


}