package com.hznhta.tick_it.Activities;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.hznhta.tick_it.Fragments.AddAdminFragment;
import com.hznhta.tick_it.SingleFragmentActivity;

/**
 * Created by imhashir on 11/12/17.
 */

public class AddAdminActivity extends SingleFragmentActivity {

    @Override
    public Fragment createFragment() {
        return AddAdminFragment.newInstance();
    }

    public static Intent newIntent(Context context) {
        Intent i = new Intent(context, AddAdminActivity.class);
        return i;
    }
}
