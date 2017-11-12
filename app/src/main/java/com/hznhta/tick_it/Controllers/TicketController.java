package com.hznhta.tick_it.Controllers;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.hznhta.tick_it.Interfaces.OnActionCompletedListener;
import com.hznhta.tick_it.Models.Ticket;

public class TicketController {

    private static final String[] types = {"movie", "show", "transport", "sports"};

    public static TicketController newInstance() {
        return new TicketController();
    }

    private OnActionCompletedListener mOnActionCompletedListener;

    public void addNewTicket(Ticket ticket, int type, OnActionCompletedListener listener) {
        mOnActionCompletedListener = listener;

        FirebaseDatabase.getInstance().getReference("tickets").child(types[type]).push().setValue(ticket).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()) {
                    mOnActionCompletedListener.onActionSucceed();
                } else {
                    mOnActionCompletedListener.onActionFailed(task.getException().toString());
                }
            }
        });
    }

}
