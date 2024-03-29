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

public class SignInFragment extends Fragment {

    private static final String TAG = "SignInFragment";

    @BindView(R.id.id_admin_email) EditText mEmailInput;
    @BindView(R.id.id_admin_password) EditText mPasswordInput;
    @BindView(R.id.id_login_button) Button mSignInButton;

    private AlertDialog mLoadingDialog;

    private View v;


    public static SignInFragment newInstance() {
        return new SignInFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_sign_in, container, false);
        ButterKnife.bind(this, v);
        mLoadingDialog = new AlertDialog.Builder(getActivity())
                .setTitle("Signing In...")
                .setView(new ProgressBar(getActivity()))
                .create();
        mSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mEmailInput.getText().length() > 0 && mPasswordInput.length() > 0) {
                    if(Patterns.EMAIL_ADDRESS.matcher(mEmailInput.getText().toString()).matches()) {
                        mLoadingDialog.show();
                        AccountsController.newInstance().signInAdmin(mEmailInput.getText().toString(), mPasswordInput.getText().toString(), new OnActionCompletedListener() {
                            @Override
                            public void onActionSucceed() {
                                mLoadingDialog.dismiss();
                                Log.i(TAG, "Signed In!");
                                startActivity(NavViewActivity.newIntent(getActivity(), null));
                                getActivity().finish();
                            }

                            @Override
                            public void onActionFailed(String err) {
                                mLoadingDialog.dismiss();
                                Snackbar.make(container, err, Snackbar.LENGTH_LONG).show();
                            }
                        });
                    } else {
                        mEmailInput.setError(getString(R.string.error_email_format));
                    }
                } else {
                    Snackbar.make(getActivity().getCurrentFocus(), R.string.error_empty_fields, Snackbar.LENGTH_LONG).show();
                }
            }
        });
        return v;
    }
}
