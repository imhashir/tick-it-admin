package com.hznhta.tick_it.Controllers;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.hznhta.tick_it.Interfaces.OnActionCompletedListener;
import com.hznhta.tick_it.Models.MovieTicket;
import com.hznhta.tick_it.Models.ShowTicket;
import com.hznhta.tick_it.Models.SportsTicket;
import com.hznhta.tick_it.Models.Ticket;
import com.hznhta.tick_it.Models.TransportTicket;

public class TicketController {

    private static final String[] types = {"movie", "show", "transport", "sports"};

    private OnActionCompletedListener mOnActionCompletedListener;
    private OnTicketsRetrievedListener mOnTicketsRetrievedListener;

    public interface OnTicketsRetrievedListener {
        void onTicketsRetrieved(Ticket ticket);
    }

    public static TicketController newInstance() {
        return new TicketController();
    }

    public void updateTicket(Ticket ticket, int type, OnActionCompletedListener listener) {
        mOnActionCompletedListener = listener;
        FirebaseDatabase.getInstance().getReference("tickets").child(types[type]).child(ticket.getUid()).setValue(ticket).addOnCompleteListener(new OnCompleteListener<Void>() {
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

    public void getTicketsList(final int type, OnTicketsRetrievedListener listener) {
        mOnTicketsRetrievedListener = listener;

        FirebaseDatabase.getInstance().getReference("tickets").child(types[type]).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Ticket ticket = null;
                switch (type) {
                    case Ticket.MOVIE_TICKET:
                        ticket = dataSnapshot.getValue(MovieTicket.class);
                        break;
                    case Ticket.SHOW_TICKET:
                        ticket = dataSnapshot.getValue(ShowTicket.class);
                        break;
                    case Ticket.SPORTS_TICKET:
                        ticket = dataSnapshot.getValue(SportsTicket.class);
                        break;
                    case Ticket.TRANSPORT_TICKET:
                        ticket = dataSnapshot.getValue(TransportTicket.class);
                        break;
                }
                ticket.setUid(dataSnapshot.getKey());
                mOnTicketsRetrievedListener.onTicketsRetrieved(ticket);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void deleteTicket(String uid, int type, final OnActionCompletedListener listener) {
        mOnActionCompletedListener = listener;
        FirebaseDatabase.getInstance()
                .getReference("tickets")
                .child(types[type])
                .child(uid)
                .setValue(null).addOnCompleteListener(new OnCompleteListener<Void>() {
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
