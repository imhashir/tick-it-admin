package com.hznhta.tick_it.Controllers;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AccountsController {

    private OnAdminSignedInListener mOnAdminSignedInListener;

    public static AccountsController newInstance() {
        return new AccountsController();
    }

    public interface OnAdminSignedInListener {
        void onSignInSuccessfull();
        void onSignInFailed(String err);
    }

    public void signInAdmin(String email, String password, OnAdminSignedInListener listener) {
        mOnAdminSignedInListener = listener;
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            FirebaseDatabase.getInstance()
                                    .getReference("admins")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    if(dataSnapshot != null) {
                                        mOnAdminSignedInListener.onSignInSuccessfull();
                                    } else {
                                        FirebaseAuth.getInstance().signOut();
                                        mOnAdminSignedInListener.onSignInFailed("User is not Admin!");
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                    FirebaseAuth.getInstance().signOut();
                                    mOnAdminSignedInListener.onSignInFailed("Unable to Sign In!");
                                }
                            });
                        } else {
                            mOnAdminSignedInListener.onSignInFailed("Invalid Credentials!");
                        }
                    }
                });
    }

}
