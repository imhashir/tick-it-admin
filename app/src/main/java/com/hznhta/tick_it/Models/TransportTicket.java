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

public class TransportTicket extends Ticket {

    private String source;
    private String destination;
    private String arrivalTime;

    private static final String TAG = "TransportTicket";

    private static EditText sSource;
    private static EditText sDestination;
    private static EditText sArrivalTime;
    private static Button sAddButton;

    private static AlertDialog mAddProgressDialog;

    public TransportTicket() {
        super();
        source = destination = arrivalTime = null;
    }

    public TransportTicket(String name, int price, int seats, String place, String dateTime, String source, String destination, String arrivalTime) {
        super(name, price, seats, place, dateTime);
        this.source = source;
        this.destination = destination;
        this.arrivalTime = arrivalTime;
    }

    public static void populateTicket(TransportTicket ticket) {
        populateTicketView(ticket);
        mAddProgressDialog.setTitle("Updating Ticket...");
        sSource.setText(ticket.getSource() + "");
        sDestination.setText(ticket.getDestination() + "");
        sArrivalTime.setText(ticket.getArrivalTime() + "");
        sAddButton.setText("Update");
    }

    public static View getView(final Context context) {
        View v = getTicketView(context, TRANSPORT_TICKET);

        mAddProgressDialog = new AlertDialog.Builder(context)
                .setView(new ProgressBar(context)).setTitle("Adding Ticket...").create();

        sSource = v.findViewById(R.id.id_input_source);
        sDestination = v.findViewById(R.id.id_input_dest);
        sArrivalTime = v.findViewById(R.id.id_input_arrival_time);
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
                        sTicketDate.getText().length() > 0 &&
                        sSource.getText().length() > 0 &&
                        sDestination.getText().length() > 0 &&
                        sArrivalTime.getText().length() > 0) {
                    mAddProgressDialog.show();
                    TransportTicket ticket = new TransportTicket(
                            sTicketName.getText().toString(),
                            Integer.parseInt(sTicketPrice.getText().toString()),
                            Integer.parseInt(sTicketSeats.getText().toString()),
                            sTicketPlace.getText().toString(),
                            sTicketDate.getText().toString(),
                            sSource.getText().toString(),
                            sDestination.getText().toString(),
                            sArrivalTime.getText().toString());

                    ticket.setUid(uid);

                    switch (action) {
                        case BUTTON_ADD:
                            TicketController.newInstance().addNewTicket(ticket, TRANSPORT_TICKET, new OnActionCompletedListener() {
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
                                    Toast.makeText(context, "Failed to add ticket!", Toast.LENGTH_LONG).show();
                                }
                            });
                            break;
                        case BUTTON_UPDATE:
                            TicketController.newInstance().updateTicket(ticket, TRANSPORT_TICKET, new OnActionCompletedListener() {
                                @Override
                                public void onActionSucceed() {
                                    mAddProgressDialog.dismiss();
                                    Toast.makeText(context, "Ticket Updated!", Toast.LENGTH_LONG).show();
                                }

                                @Override
                                public void onActionFailed(String err) {
                                    mAddProgressDialog.dismiss();
                                    Toast.makeText(context, "Failed to update", Toast.LENGTH_LONG).show();
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
        sSource.setText("");
        sDestination.setText("");
        sArrivalTime.setText("");
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
