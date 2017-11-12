package com.hznhta.tick_it.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.hznhta.tick_it.Activities.TicketListActivity;
import com.hznhta.tick_it.Models.Ticket;
import com.hznhta.tick_it.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChooseCategoryFragment extends Fragment {

    @BindView(R.id.id_movies_cat) Button mMovieOption;
    @BindView(R.id.id_show_cat) Button mShowOption;
    @BindView(R.id.id_sports_cat) Button mSportsOption;
    @BindView(R.id.id_travel_cat) Button mTransportOption;

    public static Fragment newInstance() {
        return new ChooseCategoryFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_categories_list, container, false);
        ButterKnife.bind(this, v);

        mMovieOption.setOnClickListener(new CategoryOnClickListener(Ticket.MOVIE_TICKET));
        mShowOption.setOnClickListener(new CategoryOnClickListener(Ticket.SHOW_TICKET));
        mSportsOption.setOnClickListener(new CategoryOnClickListener(Ticket.SPORTS_TICKET));
        mTransportOption.setOnClickListener(new CategoryOnClickListener(Ticket.TRANSPORT_TICKET));

        return v;
    }

    private class CategoryOnClickListener implements View.OnClickListener{
        public int mType;

        public CategoryOnClickListener(int type) {
            mType = type;
        }

        @Override
        public void onClick(View view) {
            Intent i = TicketListActivity.newIntent(getActivity(), mType);
            startActivity(i);
        }
    }
}
