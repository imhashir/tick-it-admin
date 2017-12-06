package com.hznhta.tick_it.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

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

    private AlertDialog mLoadingDialog;

    public static Fragment newInstance() {
        return new AddAdminFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_admin, container, false);
        ButterKnife.bind(this, v);

        mLoadingDialog = new AlertDialog.Builder(getActivity())
                .setView(new ProgressBar(getActivity()))
                .setTitle("Creating Account...")
                .create();

        mCreateAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mAdminName.getText().length() > 0 &&
                        mAdminName.getText().length() > 0 &&
                        mAdminPassword.getText().length() > 0 &&
                        mAdminAddress.getText().length() > 0) {
                    if(Patterns.EMAIL_ADDRESS.matcher(mAdminEmail.getText().toString()).matches()) {
                        mLoadingDialog.show();
                        AccountsController.newInstance().createNewAdmin(
                                mAdminName.getText().toString(),
                                mAdminEmail.getText().toString(),
                                mAdminPassword.getText().toString(),
                                mAdminAddress.getText().toString(),
                                new OnActionCompletedListener() {
                                    @Override
                                    public void onActionSucceed() {
                                        mLoadingDialog.dismiss();
                                        Log.i(TAG, "Account Created successfully");
                                        startActivity(NavViewActivity.newIntent(getActivity(), null));
                                        getActivity().finish();
                                    }

                                    @Override
                                    public void onActionFailed(String err) {
                                        mLoadingDialog.dismiss();
                                        Log.wtf(TAG, err);
                                    }
                                });
                    } else {
                        mAdminEmail.setError(getString(R.string.error_email_format));
                    }
                } else {
                    Snackbar.make(getView(), R.string.error_empty_fields, Snackbar.LENGTH_LONG).show();
                }
            }
        });

        return v;
    }
}
