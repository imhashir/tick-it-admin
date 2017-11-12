package com.hznhta.tick_it.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.hznhta.tick_it.Activities.NavViewActivity;
import com.hznhta.tick_it.Controllers.AccountsController;
import com.hznhta.tick_it.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignInFragment extends Fragment {

    private static final String TAG = "SignInFragment";

    @BindView(R.id.id_admin_email) EditText mEmailInput;
    @BindView(R.id.id_admin_password) EditText mPasswordInput;
    @BindView(R.id.id_login_button) Button mSignInButton;

    private View v;


    public static SignInFragment newInstance() {
        return new SignInFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_sign_in, container, false);
        ButterKnife.bind(this, v);
        mSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AccountsController.newInstance().signInAdmin(mEmailInput.getText().toString(), mPasswordInput.getText().toString(), new AccountsController.OnAdminSignedInListener() {
                    @Override
                    public void onSignInSuccessfull() {
                        Log.i(TAG, "Signed In!");
                        startActivity(NavViewActivity.newIntent(getActivity(), null));
                        getActivity().finish();
                    }

                    @Override
                    public void onSignInFailed(String err) {
                        Snackbar.make(v, err, Snackbar.LENGTH_LONG).show();
                    }
                });
            }
        });
        return v;
    }
}
