package com.hznhta.tick_it.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.hznhta.tick_it.Activities.NavViewActivity;
import com.hznhta.tick_it.Controllers.AccountsController;
import com.hznhta.tick_it.Interfaces.OnActionCompletedListener;
import com.hznhta.tick_it.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddAdminFragment extends Fragment {

    private static final String TAG = "AddAdminFragment";

    @BindView(R.id.id_admin_name) EditText mAdminName;
    @BindView(R.id.id_admin_email) EditText mAdminEmail;
    @BindView(R.id.id_admin_password) EditText mAdminPassword;
    @BindView(R.id.id_admin_address) EditText mAdminAddress;
    @BindView(R.id.id_create_account_button) Button mCreateAccountButton;

    public static Fragment newInstance() {
        return new AddAdminFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_admin, container, false);
        ButterKnife.bind(this, v);

        mCreateAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AccountsController.newInstance().createNewAdmin(
                        mAdminName.getText().toString(),
                        mAdminEmail.getText().toString(),
                        mAdminPassword.getText().toString(),
                        mAdminAddress.getText().toString(),
                        new OnActionCompletedListener() {
                            @Override
                            public void onActionSucceed() {
                                Log.i(TAG, "Account Created successfully");
                                startActivity(NavViewActivity.newIntent(getActivity(), null));
                                getActivity().finish();
                            }

                            @Override
                            public void onActionFailed(String err) {
                                Log.wtf(TAG, err);
                            }
                        });
            }
        });

        return v;
    }
}
