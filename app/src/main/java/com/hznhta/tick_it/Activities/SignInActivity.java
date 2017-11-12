package com.hznhta.tick_it.Activities;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.hznhta.tick_it.Fragments.SignInFragment;
import com.hznhta.tick_it.SingleFragmentActivity;

public class SignInActivity extends SingleFragmentActivity {
    @Override
    public Fragment createFragment() {
        return SignInFragment.newInstance();
    }

    public static Intent newIntent(Context context) {
        Intent i = new Intent(context, SignInActivity.class);
        return i;
    }
}
