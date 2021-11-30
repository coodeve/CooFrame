package com.coodev.base.module;

import android.app.Activity;
import android.os.Bundle;
import android.view.ViewGroup;

import androidx.collection.SparseArrayCompat;

/**
 * Activity delivery
 * three params:
 * Activity ,ViewGroup and saveInstanceState
 */
public class ModuleContext {
    private Activity mContext;
    private SparseArrayCompat<ViewGroup> mViewGroups;
    private Bundle mSaveInstanceState;

    public void setContext(Activity context) {
        mContext = context;
    }

    public void setSaveInstanceState(Bundle saveInstanceState) {
        mSaveInstanceState = saveInstanceState;
    }

    public void setViewGroups(SparseArrayCompat<ViewGroup> viewGroups) {
        mViewGroups = viewGroups;
    }
}
