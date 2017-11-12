package com.hznhta.tick_it.Models;

import android.content.Context;
import android.util.Log;
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

    private static EditText sNameTeam1;
    private static EditText sNameTeam2;
    private static Button sAddButton;

    private static final String TAG = "SportsTicket";

    public SportsTicket() {
        super();
        team1 = team2 = null;
    }

    public SportsTicket(String name, int price, int seats, String place, String dateTime, String team1, String team2) {
        super(name, price, seats, place, dateTime);
        this.team1 = team1;
        this.team2 = team2;
    }

    public static void populateTicket(SportsTicket ticket) {
        populateTicketView(ticket);
        sNameTeam1.setText(ticket.getTeam1() + "");
        sNameTeam2.setText(ticket.getTeam2() + "");
        sAddButton.setText("Update");
    }

    public static View getView(final Context context) {
        View v = getTicketView(context, SPORTS_TICKET);

        sNameTeam1 = v.findViewById(R.id.id_input_team1);
        sNameTeam2 = v.findViewById(R.id.id_input_team2);
        sAddButton = v.findViewById(R.id.id_add_ticket_button);

        return v;
    }

    public static void setButtonAction(final Context context, final String uid, final int action) {
        sAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SportsTicket ticket = new SportsTicket(
                        sTicketName.getText().toString(),
                        Integer.parseInt(sTicketPrice.getText().toString()),
                        Integer.parseInt(sTicketSeats.getText().toString()),
                        sTicketPlace.getText().toString(),
                        sTicketDate.getText().toString(),
                        sNameTeam1.getText().toString(),
                        sNameTeam2.getText().toString());

                ticket.setUid(uid);

                switch (action) {
                    case BUTTON_ADD:
                        TicketController.newInstance().addNewTicket(ticket, SPORTS_TICKET, new OnActionCompletedListener() {
                            @Override
                            public void onActionSucceed() {
                                Toast.makeText(context, "Ticket added!", Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onActionFailed(String err) {
                                Toast.makeText(context, "", Toast.LENGTH_LONG).show();
                            }
                        });
                        break;
                    case BUTTON_UPDATE:
                        TicketController.newInstance().updateTicket(ticket, SPORTS_TICKET, new OnActionCompletedListener() {
                            @Override
                            public void onActionSucceed() {
                                Toast.makeText(context, "Ticket Updated!", Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onActionFailed(String err) {
                                Log.wtf(TAG, err);
                                Toast.makeText(context, "Unable to update!", Toast.LENGTH_LONG).show();
                            }
                        });
                        break;
                }
            }
        });
    }

    public static void clearFields() {
        clearParentFields();
        sNameTeam2.setText("");
        sNameTeam1.setText("");
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
