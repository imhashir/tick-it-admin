package com.hznhta.tick_it.Activities;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.hznhta.tick_it.Fragments.AddTicketFragment;
import com.hznhta.tick_it.Models.Ticket;
import com.hznhta.tick_it.SingleFragmentActivity;

public class EditTicketActivity extends SingleFragmentActivity {

    private static final String KEY_TICKET = "EditTicketActivity.ticket";
    private static final String KEY_TYPE = "EditTicketActivity.type";

    public static Intent newIntent(Context context, Ticket ticket, int type) {
        Intent i = new Intent(context, EditTicketActivity.class);
        i.putExtra(KEY_TYPE, type);
        i.putExtra(KEY_TICKET, ticket);
        return i;
    }

    @Override
    public Fragment createFragment() {
        return AddTicketFragment.newInstance((Ticket) getIntent().getExtras().getSerializable(KEY_TICKET), getIntent().getExtras().getInt(KEY_TYPE));
    }
}
