package com.hznhta.tick_it.Fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.hznhta.tick_it.Activities.AddAdminActivity;
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
                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                dialog.setTitle("Create New Account");
                dialog.setMessage("Creating new account will automatically log you out of your current account. Click OK to proceed or CANCEL to cancel current operation.");
                dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseAuth.getInstance().signOut();
                        startActivity(AddAdminActivity.newIntent(getActivity()));
                    }
                });
                dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                dialog.show();
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

        mAddTicketButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityWithFragment(R.id.menu_add_ticket);
            }
        });

        return v;
    }

    public void startActivityWithFragment(int i) {
        startActivity(NavViewActivity.newIntent(getActivity(), i));
    }

}
