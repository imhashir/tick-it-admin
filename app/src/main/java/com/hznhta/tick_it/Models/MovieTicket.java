package com.hznhta.tick_it.Models;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hznhta.tick_it.Controllers.TicketController;
import com.hznhta.tick_it.Interfaces.OnActionCompletedListener;
import com.hznhta.tick_it.R;

public class MovieTicket extends Ticket {

    private String genre;
    private int length;

    private static EditText mMovieGenre;
    private static EditText mMovieLength;
    private static Button mAddButton;

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

    public static View getView(final Context context) {
        View v = getTicketView(context, MOVIE_TICKET);

        mMovieGenre = v.findViewById(R.id.id_input_genre);
        mMovieLength = v.findViewById(R.id.id_input_length);
        mAddButton = v.findViewById(R.id.id_add_ticket_button);

        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MovieTicket ticket = new MovieTicket(
                        sTicketName.getText().toString(),
                        Integer.parseInt(sTicketPrice.getText().toString()),
                        Integer.parseInt(sTicketSeats.getText().toString()),
                        sTicketPlace.getText().toString(),
                        sTicketDate.getText().toString(),
                        mMovieGenre.getText().toString(),
                        Integer.parseInt(mMovieLength.getText().toString()));
                TicketController.newInstance().addNewTicket(ticket, MOVIE_TICKET, new OnActionCompletedListener() {
                    @Override
                    public void onActionSucceed() {
                        Toast.makeText(context, "Ticket added!", Toast.LENGTH_LONG).show();
                        clearFields();
                    }

                    @Override
                    public void onActionFailed(String err) {
                        Toast.makeText(context, "", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        return v;
    }

    public static void clearFields() {
        clearParentFields();
        mMovieGenre.setText("");
        mMovieLength.setText("");
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
