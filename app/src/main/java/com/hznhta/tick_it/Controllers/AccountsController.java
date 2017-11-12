package com.hznhta.tick_it.Controllers;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hznhta.tick_it.Models.User;

public class AccountsController {

    private OnActionCompletedListener mOnActionCompletedListener;

    public static AccountsController newInstance() {
        return new AccountsController();
    }

    public interface OnActionCompletedListener {
        void onActionSucceed();
        void onActionFailed(String err);
    }

    public void signInAdmin(String email, String password, OnActionCompletedListener listener) {
        mOnActionCompletedListener = listener;
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
                                        mOnActionCompletedListener.onActionSucceed();
                                    } else {
                                        FirebaseAuth.getInstance().signOut();
                                        mOnActionCompletedListener.onActionFailed("User is not Admin!");
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                    FirebaseAuth.getInstance().signOut();
                                    mOnActionCompletedListener.onActionFailed("Unable to Sign In!");
                                }
                            });
                        } else {
                            mOnActionCompletedListener.onActionFailed("Invalid Credentials!");
                        }
                    }
                });
    }

    public void createNewAdmin(final String name, final String email, String password, final String address, final OnActionCompletedListener listener) {
        mOnActionCompletedListener = listener;
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    DatabaseReference reference = FirebaseDatabase.getInstance()
                            .getReference("users");
                    User user = new User(name, email, address);
                    reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()) {
                                listener.onActionSucceed();
                            } else {
                                FirebaseAuth.getInstance().getCurrentUser().delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        FirebaseAuth.getInstance().signOut();
                                        listener.onActionFailed(task.getException().toString());
                                    }
                                });
                            }
                        }
                    });
                } else {
                    listener.onActionFailed(task.getException().toString());
                }
            }
        });
    }

}
