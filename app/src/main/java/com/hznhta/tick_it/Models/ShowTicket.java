package com.hznhta.tick_it.Models;

import android.content.Context;
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

public class ShowTicket extends Ticket {
    private String endingTime;

    private static final String TAG = "ShowTicket";

    private static EditText sEndingTime;
    private static Button sAddButton;

    private static AlertDialog mAddProgressDialog;

    public ShowTicket() {
        super();
        this.endingTime = null;
    }

    public ShowTicket(String name, int price, int seats, String place, String dateTime, String endingTime) {
        super(name, price, seats, place, dateTime);
        this.endingTime = endingTime;
    }

    public static void populateTicket(ShowTicket ticket) {
        populateTicketView(ticket);
        sEndingTime.setText(ticket.getEndingTime());
        mAddProgressDialog.setTitle("Updating Ticket...");
        sAddButton.setText("Update");
    }

    public static View getView(final Context context) {
        View v = getTicketView(context, SHOW_TICKET);

        mAddProgressDialog = new android.support.v7.app.AlertDialog.Builder(context)
                .setView(new ProgressBar(context)).setTitle("Adding Ticket...").create();

        sEndingTime = v.findViewById(R.id.id_input_end_time);
        sAddButton = v.findViewById(R.id.id_add_ticket_button);

        return v;
    }

    public static void setButtonAction(final Context context, final String uid, final int action) {
        sAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAddProgressDialog.show();
                ShowTicket ticket = new ShowTicket(
                        sTicketName.getText().toString(),
                        Integer.parseInt(sTicketPrice.getText().toString()),
                        Integer.parseInt(sTicketSeats.getText().toString()),
                        sTicketPlace.getText().toString(),
                        sTicketDate.getText().toString(),
                        sEndingTime.getText().toString());
                ticket.setUid(uid);
                switch (action) {
                    case BUTTON_ADD:
                        TicketController.newInstance().addNewTicket(ticket, SHOW_TICKET, new OnActionCompletedListener() {
                            @Override
                            public void onActionSucceed() {
                                mAddProgressDialog.dismiss();
                                Toast.makeText(context, "Ticket added!", Toast.LENGTH_LONG).show();
                                clearFields();
                            }

                            @Override
                            public void onActionFailed(String err) {
                                mAddProgressDialog.dismiss();
                                Toast.makeText(context, "", Toast.LENGTH_LONG).show();
                            }
                        });
                        break;
                    case BUTTON_UPDATE:
                        TicketController.newInstance().updateTicket(ticket, SHOW_TICKET, new OnActionCompletedListener() {
                            @Override
                            public void onActionSucceed() {
                                mAddProgressDialog.dismiss();
                                Toast.makeText(context, "Ticket updated!", Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onActionFailed(String err) {
                                mAddProgressDialog.dismiss();
                                Toast.makeText(context, "Update failed!", Toast.LENGTH_LONG).show();
                                Log.wtf(TAG, err);
                            }
                        });
                        break;
                }
            }
        });
    }

    public static void clearFields() {
        clearParentFields();
        sEndingTime.setText("");
    }

    public String getEndingTime() {
        return endingTime;
    }

    public void setEndingTime(String endingTime) {
        this.endingTime = endingTime;
    }
}
