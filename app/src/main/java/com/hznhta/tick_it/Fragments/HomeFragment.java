package com.hznhta.tick_it.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.hznhta.tick_it.Activities.NavViewActivity;
import com.hznhta.tick_it.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeFragment extends Fragment {

    private View v;
    @BindView(R.id.id_add_ticket_button) Button mAddTicketButton;
    @BindView(R.id.id_manage_tickets_button) Button mManageTicketButton;
    @BindView(R.id.id_add_admin_button) Button mAddAdminButton;
    @BindView(R.id.id_manage_credit_button) Button mManageCreditButton;

    public static Fragment newInstance() {
        return new HomeFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, v);

        mAddTicketButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityWithFragment(R.id.menu_add_ticket);
            }
        });

        mAddAdminButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityWithFragment(R.id.menu_add_admin);
            }
        });

        mManageTicketButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityWithFragment(R.id.menu_manage_tickets);
            }
        });

        mManageCreditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityWithFragment(R.id.menu_credit_reqs);
            }
        });

        return v;
    }

    public void startActivityWithFragment(int i) {
        startActivity(NavViewActivity.newIntent(getActivity(), i));
        getActivity().finish();
    }

}
