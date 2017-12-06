package com.hznhta.tick_it.Models;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.hznhta.tick_it.Controllers.TicketController;
import com.hznhta.tick_it.Interfaces.OnActionCompletedListener;
import com.hznhta.tick_it.R;

public class MovieTicket extends Ticket {

    private static final String TAG = "MovieTicket";

    private String genre;
    private int length;

    private static AlertDialog mAddProgressDialog;

    private static EditText sMovieGenre;
    private static EditText sMovieLength;
    private static Button sAddButton;

    public MovieTicket() {
        super();
        genre = null;
        length = 0;
    }

    public MovieTicket(String name, int price, int seats, String place, String dateTime, String genre, int length) {
        super(name, price, seats, place, dateTime);
        this.genre = genre;
        this.length = length;
    }

    public static void populateTicket(MovieTicket ticket) {
        populateTicketView(ticket);
        sMovieGenre.setText(ticket.getGenre() + "");
        sMovieLength.setText(ticket.getLength() + "");
        mAddProgressDialog.setTitle("Updating Ticket...");
        sAddButton.setText("Update");
    }

    public static View getView(final Context context) {
        View v = getTicketView(context, MOVIE_TICKET);

        mAddProgressDialog = new AlertDialog.Builder(context)
                .setView(new ProgressBar(context)).setTitle("Adding Ticket...").create();

        sMovieGenre = v.findViewById(R.id.id_input_genre);
        sMovieLength = v.findViewById(R.id.id_input_length);
        sAddButton = v.findViewById(R.id.id_add_ticket_button);

        return v;
    }

    public static void setButtonAction(final Context context, final String uid, final int action) {
        sAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sTicketName.getText().length() > 0 &&
                        sTicketPrice.getText().length() > 0 &&
                        sTicketSeats.getText().length() > 0 &&
                        sTicketPlace.getText().length() > 0 &&
                        sMovieGenre.getText().length() > 0 &&
                        sTicketDate.getText().length() > 0) {
                    mAddProgressDialog.show();
                    MovieTicket ticket = new MovieTicket(
                            sTicketName.getText().toString(),
                            Integer.parseInt(sTicketPrice.getText().toString()),
                            Integer.parseInt(sTicketSeats.getText().toString()),
                            sTicketPlace.getText().toString(),
                            sTicketDate.getText().toString(),
                            sMovieGenre.getText().toString(),
                            Integer.parseInt(sMovieLength.getText().toString()));
                    ticket.setUid(uid);
                    switch (action) {
                        case BUTTON_ADD:
                            TicketController.newInstance().addNewTicket(ticket, MOVIE_TICKET, new OnActionCompletedListener() {
                                @Override
                                public void onActionSucceed() {
                                    mAddProgressDialog.dismiss();
                                    Toast.makeText(context, "Ticket added!", Toast.LENGTH_LONG).show();
                                    clearFields();
                                }

                                @Override
                                public void onActionFailed(String err) {
                                    mAddProgressDialog.dismiss();
                                    Log.wtf(TAG, err);
                                    Toast.makeText(context, "Operation failed", Toast.LENGTH_LONG).show();
                                }
                            });
                            break;
                        case BUTTON_UPDATE:
                            TicketController.newInstance().updateTicket(ticket, MOVIE_TICKET, new OnActionCompletedListener() {
                                @Override
                                public void onActionSucceed() {
                                    mAddProgressDialog.dismiss();
                                    Toast.makeText(context, "Ticket updated!", Toast.LENGTH_LONG).show();
                                }

                                @Override
                                public void onActionFailed(String err) {
                                    mAddProgressDialog.dismiss();
                                    Log.wtf(TAG, err);
                                    Toast.makeText(context, "Update failed!", Toast.LENGTH_LONG).show();
                                }
                            });
                            break;
                    }
                } else {
                    Snackbar.make(((Activity)context).getCurrentFocus(), R.string.error_empty_fields, Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }

    public static void clearFields() {
        clearParentFields();
        sMovieGenre.setText("");
        sMovieLength.setText("");
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }
}
