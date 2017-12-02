package com.hznhta.tick_it.Controllers;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hznhta.tick_it.Interfaces.OnActionCompletedListener;
import com.hznhta.tick_it.Models.User;

public class AccountsController {

    private OnRequestsFetchedListener mOnRequestsFetchedListener;
    private OnActionCompletedListener mOnActionCompletedListener;

    private FirebaseDatabase mDatabase;

    private static final String TAG = "AccountsController";

    public AccountsController() {
        mDatabase = FirebaseDatabase.getInstance();
    }

    public interface OnRequestsFetchedListener {
        void onRequestFetched(String userId, String username, Long amount);
        void onNoRequestFound();
    }

    public static AccountsController newInstance() {
        return new AccountsController();
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

    public void fetchCreditRequests(OnRequestsFetchedListener listener) {
        mOnRequestsFetchedListener = listener;
        mDatabase.getReference("userCreditList").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot creditDataSnapshot) {
                for(final DataSnapshot snapshot : creditDataSnapshot.getChildren()) {
                    mDatabase.getReference("users").child(snapshot.getKey()).child("name").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot nameDataSnapshot) {
                            mOnRequestsFetchedListener.onRequestFetched(snapshot.getKey(), nameDataSnapshot.getValue().toString(), (Long) snapshot.getValue());
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mDatabase.getReference("userCreditList").orderByKey().limitToFirst(1).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.getChildrenCount() == 0) {
                    mOnRequestsFetchedListener.onNoRequestFound();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void handleCreditRequest(final String userId, final Long amount, final boolean action, OnActionCompletedListener listener) {
        mOnActionCompletedListener = listener;
        mDatabase.getReference("userCreditList").child(userId).setValue(null).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()) {
                    if (action) {
                        mDatabase.getReference("userCreditAccounts").child(userId).setValue(amount).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()) {
                                    mOnActionCompletedListener.onActionSucceed();
                                }
                            }
                        });
                    } else {
                        mOnActionCompletedListener.onActionSucceed();
                    }
                } else {
                    mOnActionCompletedListener.onActionFailed("Unable to complete task.");
                    Log.wtf(TAG, task.getException());
                }
            }
        });
    }

    public void createNewAdmin(final String name, final String email, String password, final String address, OnActionCompletedListener listener) {
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
                                FirebaseDatabase.getInstance().getReference("admins")
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .setValue("true")
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                mOnActionCompletedListener.onActionSucceed();
                                            }
                                        });
                            } else {
                                FirebaseAuth.getInstance().getCurrentUser().delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        FirebaseAuth.getInstance().signOut();
                                        mOnActionCompletedListener.onActionFailed(task.getException().toString());
                                    }
                                });
                            }
                        }
                    });
                } else {
                    mOnActionCompletedListener.onActionFailed(task.getException().toString());
                }
            }
        });
    }

}
