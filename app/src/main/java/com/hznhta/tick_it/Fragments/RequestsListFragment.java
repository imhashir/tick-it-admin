package com.hznhta.tick_it.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.hznhta.tick_it.Controllers.AccountsController;
import com.hznhta.tick_it.Interfaces.OnActionCompletedListener;
import com.hznhta.tick_it.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RequestsListFragment extends Fragment {

    private static final String TAG = "RequestsListFragment";

    @BindView(R.id.id_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.id_progress_bar)
    ProgressBar mProgressBar;

    private RequestAdapter mAdapter;
    private AccountsController mAccountsController;

    public interface OnItemDeleteListener {
        void onItemDeleted();
    }

    public static RequestsListFragment newInstance() {
        return new RequestsListFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAccountsController = new AccountsController();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_list, container, false);
        ButterKnife.bind(this, v);

        mAdapter = new RequestAdapter();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mAdapter);

        mAccountsController.fetchCreditRequests(new AccountsController.OnRequestsFetchedListener() {
            @Override
            public void onRequestFetched(String userId, String username, Long amount) {
                if(mProgressBar.getVisibility() == View.VISIBLE)
                    mProgressBar.setVisibility(View.GONE);
                mAdapter.addRequest(username, userId, amount);
            }

            @Override
            public void onNoRequestFound() {
                mProgressBar.setVisibility(View.GONE);
            }
        });

        return v;
    }

    private class RequestAdapter extends RecyclerView.Adapter<RequestHolder> {

        private List<String> mNamesList;
        private List<String> mUserIdList;
        private List<Long> mAmountList;

        public RequestAdapter() {
            mNamesList = new ArrayList<>();
            mUserIdList = new ArrayList<>();
            mAmountList = new ArrayList<>();
        }

        @Override
        public RequestHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(getActivity()).inflate(R.layout.single_credit_request, parent, false);
            return new RequestHolder(v);
        }

        @Override
        public void onBindViewHolder(RequestHolder holder, final int position) {
            holder.bindView(mNamesList.get(position), mUserIdList.get(position), mAmountList.get(position));
            holder.setOnItemDeleteListener(new OnItemDeleteListener() {
                @Override
                public void onItemDeleted() {
                    mNamesList.remove(position);
                    mUserIdList.remove(position);
                    mAmountList.remove(position);
                    notifyDataSetChanged();
                }
            });
        }

        public void addRequest(String name, String uid, Long amount) {
            mNamesList.add(name);
            mUserIdList.add(uid);
            mAmountList.add(amount);
            notifyDataSetChanged();
        }

        @Override
        public int getItemCount() {
            return mNamesList.size();
        }
    }

    private class RequestHolder extends RecyclerView.ViewHolder {

        private String mName;
        private String mUid;
        private Long mAmount;

        private TextView mUserNameText;
        private TextView mAmountText;
        private Button mAcceptButton;
        private Button mRejectButton;

        private OnItemDeleteListener mOnItemDeleteListener;

        public RequestHolder(View itemView) {
            super(itemView);
            mUserNameText = itemView.findViewById(R.id.id_username);
            mAmountText = itemView.findViewById(R.id.id_amount);
            mAcceptButton = itemView.findViewById(R.id.id_accept_req);
            mRejectButton = itemView.findViewById(R.id.id_reject_req);
        }

        public void setOnItemDeleteListener(OnItemDeleteListener onItemDeleteListener) {
            mOnItemDeleteListener = onItemDeleteListener;
        }

        public void bindView(String name, String uid, Long amount) {
            mName = name;
            mUid = uid;
            mAmount = amount;
            mUserNameText.setText(mName);
            mAmountText.setText(mAmount + "");

            mAcceptButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AccountsController.newInstance().handleCreditRequest(mUid, mAmount, true,  new OnActionCompletedListener() {
                        @Override
                        public void onActionSucceed() {
                            mOnItemDeleteListener.onItemDeleted();
                            Toast.makeText(getActivity(), "Deleted!", Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onActionFailed(String err) {
                            Log.wtf(TAG, err);
                        }
                    });
                }
            });

            mRejectButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AccountsController.newInstance().handleCreditRequest(mUid, mAmount, false,  new OnActionCompletedListener() {
                        @Override
                        public void onActionSucceed() {
                            mOnItemDeleteListener.onItemDeleted();
                            Toast.makeText(getActivity(), "Deleted!", Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onActionFailed(String err) {
                            Log.wtf(TAG, err);
                        }
                    });
                }
            });
        }
    }
}
