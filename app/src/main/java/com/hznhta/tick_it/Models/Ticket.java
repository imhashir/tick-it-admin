package com.hznhta.tick_it.Models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.hznhta.tick_it.R;

public abstract class Ticket {

    public final static int MOVIE_TICKET = 0;
    public final static int SHOW_TICKET = 1;
    public final static int TRANSPORT_TICKET = 2;
    public final static int SPORTS_TICKET = 3;

    private String uid;
    private String name;
    private int price;
    private int seats;
    private String place;
    private String dateTime;

    protected static EditText sTicketName;
    protected static EditText sTicketPrice;
    protected static EditText sTicketSeats;
    protected static EditText sTicketPlace;
    protected static EditText sTicketDate;

    public Ticket() {
        this.name = null;
        this.price = 0;
        this.seats = 0;
        this.place = null;
        this.dateTime = null;
    }

    public Ticket(String name, int price, int seats, String place, String dateTime) {
        this.name = name;
        this.price = price;
        this.seats = seats;
        this.place = place;
        this.dateTime = dateTime;
    }

    protected static View getTicketView(Context context, int type) {
        int layoutId = R.layout.fragment_ticket_movie;
        switch (type) {
            case MOVIE_TICKET:
                layoutId = R.layout.fragment_ticket_movie;
                break;
            case SHOW_TICKET:
                layoutId = R.layout.fragment_ticket_show;
                break;
            case TRANSPORT_TICKET:
                layoutId = R.layout.fragment_ticket_transport;
                break;
            case SPORTS_TICKET:
                layoutId = R.layout.fragment_ticket_sports;
                break;
        }
        View v = LayoutInflater.from(context).inflate(layoutId, null, false);
        sTicketName = v.findViewById(R.id.id_input_name);
        sTicketDate = v.findViewById(R.id.id_input_date);
        sTicketPlace = v.findViewById(R.id.id_input_place);
        sTicketPrice = v.findViewById(R.id.id_input_price);
        sTicketSeats = v.findViewById(R.id.id_input_seats);
        return v;
    }

    public static void clearParentFields() {
        sTicketName.setText("");
        sTicketDate.setText("");
        sTicketPlace.setText("");
        sTicketPrice.setText("");
        sTicketSeats.setText("");
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
}
