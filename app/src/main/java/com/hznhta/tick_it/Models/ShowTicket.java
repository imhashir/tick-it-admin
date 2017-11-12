package com.hznhta.tick_it.Models;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hznhta.tick_it.Controllers.TicketController;
import com.hznhta.tick_it.Interfaces.OnActionCompletedListener;
import com.hznhta.tick_it.R;

public class ShowTicket extends Ticket {
    private String endingTime;

    private static EditText mEndingTime;
    private static Button mAddButton;

    public ShowTicket() {
        super();
        this.endingTime = null;
    }

    public ShowTicket(String name, int price, int seats, String place, String dateTime, String endingTime) {
        super(name, price, seats, place, dateTime);
        this.endingTime = endingTime;
    }

    public static View getView(final Context context) {
        View v = getTicketView(context, SHOW_TICKET);

        mEndingTime = v.findViewById(R.id.id_input_end_time);
        mAddButton = v.findViewById(R.id.id_add_ticket_button);

        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowTicket ticket = new ShowTicket(
                        sTicketName.getText().toString(),
                        Integer.parseInt(sTicketPrice.getText().toString()),
                        Integer.parseInt(sTicketSeats.getText().toString()),
                        sTicketPlace.getText().toString(),
                        sTicketDate.getText().toString(),
                        mEndingTime.getText().toString());
                TicketController.newInstance().addNewTicket(ticket, SHOW_TICKET, new OnActionCompletedListener() {
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
        mEndingTime.setText("");
    }

    public String getEndingTime() {
        return endingTime;
    }

    public void setEndingTime(String endingTime) {
        this.endingTime = endingTime;
    }
}
