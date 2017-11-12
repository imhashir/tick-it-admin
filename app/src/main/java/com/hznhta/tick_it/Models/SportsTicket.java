package com.hznhta.tick_it.Models;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hznhta.tick_it.Controllers.TicketController;
import com.hznhta.tick_it.Interfaces.OnActionCompletedListener;
import com.hznhta.tick_it.R;

public class SportsTicket extends Ticket {

    private String team1;
    private String team2;

    private static EditText mNameTeam1;
    private static EditText mNameTeam2;
    private static Button mAddButton;

    public SportsTicket() {
        super();
        team1 = team2 = null;
    }

    public SportsTicket(String name, int price, int seats, String place, String dateTime, String team1, String team2) {
        super(name, price, seats, place, dateTime);
        this.team1 = team1;
        this.team2 = team2;
    }

    public static View getView(final Context context) {
        View v = getTicketView(context, SPORTS_TICKET);

        mNameTeam1 = v.findViewById(R.id.id_input_team1);
        mNameTeam2 = v.findViewById(R.id.id_input_team2);
        mAddButton = v.findViewById(R.id.id_add_ticket_button);

        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SportsTicket ticket = new SportsTicket(
                        sTicketName.getText().toString(),
                        Integer.parseInt(sTicketPrice.getText().toString()),
                        Integer.parseInt(sTicketSeats.getText().toString()),
                        sTicketPlace.getText().toString(),
                        sTicketDate.getText().toString(),
                        mNameTeam1.getText().toString(),
                        mNameTeam2.getText().toString());
                TicketController.newInstance().addNewTicket(ticket, SPORTS_TICKET, new OnActionCompletedListener() {
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
        mNameTeam2.setText("");
        mNameTeam1.setText("");
    }

    public String getTeam1() {
        return team1;
    }

    public void setTeam1(String team1) {
        this.team1 = team1;
    }

    public String getTeam2() {
        return team2;
    }

    public void setTeam2(String team2) {
        this.team2 = team2;
    }
}
