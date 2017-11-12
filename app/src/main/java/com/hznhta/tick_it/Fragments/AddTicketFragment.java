package com.hznhta.tick_it.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.Spinner;

import com.hznhta.tick_it.Models.MovieTicket;
import com.hznhta.tick_it.Models.ShowTicket;
import com.hznhta.tick_it.Models.SportsTicket;
import com.hznhta.tick_it.Models.Ticket;
import com.hznhta.tick_it.Models.TransportTicket;
import com.hznhta.tick_it.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddTicketFragment extends Fragment {

    @BindView(R.id.id_ticket_chooser_spinner) Spinner mTicketChooser;
    @BindView(R.id.ticket_fragment_container) FrameLayout mTicketContainer;

    private static final String KEY_TICKET = "AddTicketFragment.ticket";
    private static final String KEY_TYPE = "AddTicketFragment.type";
    private Ticket mTicket;
    private int mType;

    public static AddTicketFragment newInstance() {
        return new AddTicketFragment();
    }

    public static AddTicketFragment newInstance(Ticket ticket, int type) {
        Bundle args = new Bundle();
        args.putSerializable(KEY_TICKET, ticket);
        args.putInt(KEY_TYPE, type);
        AddTicketFragment fragment = new AddTicketFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_ticket, container, false);
        ButterKnife.bind(this, v);

        if(getArguments() != null) {
            mTicket = (Ticket) getArguments().getSerializable(KEY_TICKET);
            mType = getArguments().getInt(KEY_TYPE);
            mTicketChooser.setEnabled(false);
            switch (mType) {
                case Ticket.MOVIE_TICKET:
                    mTicketContainer.addView(MovieTicket.getView(getActivity()));
                    MovieTicket.populateTicket((MovieTicket) mTicket);
                    MovieTicket.setButtonAction(getActivity(), mTicket.getUid(), Ticket.BUTTON_UPDATE);
                    break;
                case Ticket.SHOW_TICKET:
                    mTicketContainer.addView(ShowTicket.getView(getActivity()));
                    ShowTicket.populateTicket((ShowTicket) mTicket);
                    ShowTicket.setButtonAction(getActivity(), mTicket.getUid(), Ticket.BUTTON_UPDATE);
                    break;
                case Ticket.SPORTS_TICKET:
                    mTicketContainer.addView(SportsTicket.getView(getActivity()));
                    SportsTicket.populateTicket((SportsTicket) mTicket);
                    SportsTicket.setButtonAction(getActivity(), mTicket.getUid(), Ticket.BUTTON_UPDATE);
                    break;
                case Ticket.TRANSPORT_TICKET:
                    mTicketContainer.addView(TransportTicket.getView(getActivity()));
                    TransportTicket.populateTicket((TransportTicket) mTicket);
                    TransportTicket.setButtonAction(getActivity(), mTicket.getUid(), Ticket.BUTTON_UPDATE);
                    break;
            }
        } else {
            mTicketChooser.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    mTicketContainer.removeAllViews();
                    switch (i) {
                        case 1:
                            mTicketContainer.addView(MovieTicket.getView(getActivity()));
                            MovieTicket.setButtonAction(getActivity(), null, Ticket.BUTTON_ADD);
                            break;
                        case 2:
                            mTicketContainer.addView(ShowTicket.getView(getActivity()));
                            ShowTicket.setButtonAction(getActivity(), null, Ticket.BUTTON_ADD);
                            break;
                        case 3:
                            mTicketContainer.addView(SportsTicket.getView(getActivity()));
                            SportsTicket.setButtonAction(getActivity(), null, Ticket.BUTTON_ADD);
                            break;
                        case 4:
                            mTicketContainer.addView(TransportTicket.getView(getActivity()));
                            TransportTicket.setButtonAction(getActivity(), null, Ticket.BUTTON_ADD);
                            break;
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }

        return v;
    }
}
