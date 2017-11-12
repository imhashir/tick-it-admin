package com.hznhta.tick_it.Activities;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.hznhta.tick_it.Fragments.TicketListFragment;
import com.hznhta.tick_it.SingleFragmentActivity;

public class TicketListActivity extends SingleFragmentActivity {

    private static final String EXTRA_TYPE = "TicketListActivity.type";

    @Override
    public Fragment createFragment() {
        return TicketListFragment.newInstance(getIntent().getExtras().getInt(EXTRA_TYPE));
    }

    public static Intent newIntent(Context context, int type) {
        Intent i = new Intent(context, TicketListActivity.class);
        i.putExtra(EXTRA_TYPE, type);
        return i;
    }

}
