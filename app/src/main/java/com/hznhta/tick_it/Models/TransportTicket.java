package com.hznhta.tick_it.Models;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hznhta.tick_it.Controllers.TicketController;
import com.hznhta.tick_it.Interfaces.OnActionCompletedListener;
import com.hznhta.tick_it.R;

public class TransportTicket extends Ticket {

    private String source;
    private String destination;
    private String arrivalTime;

    private static EditText mSource;
    private static EditText mDestination;
    private static EditText mArrivalTime;
    private static Button mAddButton;

    public TransportTicket() {
        super();
        source = destination = arrivalTime = null;
    }

    public TransportTicket(int type, String name, int price, int seats, String place, String dateTime, String source, String destination, String arrivalTime) {
        super(type, name, price, seats, place, dateTime);
        this.source = source;
        this.destination = destination;
        this.arrivalTime = arrivalTime;
    }

    public static View getView(final Context context) {
        View v = getTicketView(context, TRANSPORT_TICKET);

        mSource = v.findViewById(R.id.id_input_source);
        mDestination = v.findViewById(R.id.id_input_dest);
        mArrivalTime = v.findViewById(R.id.id_input_arrival_time);
        mAddButton = v.findViewById(R.id.id_add_ticket_button);

        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TransportTicket ticket = new TransportTicket(
                        Ticket.MOVIE_TICKET,
                        sTicketName.getText().toString(),
                        Integer.parseInt(sTicketPrice.getText().toString()),
                        Integer.parseInt(sTicketSeats.getText().toString()),
                        sTicketPlace.getText().toString(),
                        sTicketDate.getText().toString(),
                        mSource.getText().toString(),
                        mDestination.getText().toString(),
                        mArrivalTime.getText().toString());
                TicketController.newInstance().addNewTicket(ticket, new OnActionCompletedListener() {
                    @Override
                    public void onActionSucceed() {
                        Toast.makeText(context, "Ticket added!", Toast.LENGTH_LONG).show();
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

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }
}
