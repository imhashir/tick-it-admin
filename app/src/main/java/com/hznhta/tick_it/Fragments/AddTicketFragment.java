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
import com.hznhta.tick_it.Models.TransportTicket;
import com.hznhta.tick_it.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddTicketFragment extends Fragment {

    @BindView(R.id.id_ticket_chooser_spinner) Spinner mTicketChooser;
    @BindView(R.id.ticket_fragment_container) FrameLayout mTicketContainer;

    public static AddTicketFragment newInstance() {
        return new AddTicketFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_ticket, container, false);
        ButterKnife.bind(this, v);

        mTicketChooser.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mTicketContainer.removeAllViews();
                switch (i) {
                    case 1:
                        mTicketContainer.addView(new MovieTicket().getView(getActivity()));
                        break;
                    case 2:
                        mTicketContainer.addView(new ShowTicket().getView(getActivity()));
                        break;
                    case 3:
                        mTicketContainer.addView(new SportsTicket().getView(getActivity()));
                        break;
                    case 4:
                        mTicketContainer.addView(new TransportTicket().getView(getActivity()));
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        return v;
    }
}
