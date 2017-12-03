package com.hznhta.tick_it.Fragments;

import android.content.Intent;
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
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.hznhta.tick_it.Activities.EditTicketActivity;
import com.hznhta.tick_it.Controllers.TicketController;
import com.hznhta.tick_it.Interfaces.OnActionCompletedListener;
import com.hznhta.tick_it.Models.Ticket;
import com.hznhta.tick_it.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TicketListFragment extends Fragment {

    private static final String TYPE_ARG = "TicketListFragment.type";
    private static final String TAG = "TicketListFragment";

    @BindView(R.id.id_recycler_view) RecyclerView mRecyclerView;
    @BindView(R.id.id_progress_bar) ProgressBar mProgressBar;

    private TicketAdapter mAdapter;

    private int mType;

    public static TicketListFragment newInstance(int type) {
        Bundle args = new Bundle();
        args.putInt(TYPE_ARG, type);
        TicketListFragment fragment = new TicketListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public interface OnItemDeleteListener {
        void onItemDeleted();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_list, container, false);
        ButterKnife.bind(this, v);
        mType = getArguments().getInt(TYPE_ARG);

        mAdapter = new TicketAdapter();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mAdapter);

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(mAdapter != null && mAdapter.mTicketList != null)
            mAdapter.mTicketList.clear();
        TicketController.newInstance().getTicketsList(mType, new TicketController.OnTicketsRetrievedListener() {
            @Override
            public void onTicketsRetrieved(Ticket ticket) {
                mAdapter.addTicket(ticket);
                mAdapter.notifyDataSetChanged();
                mProgressBar.setVisibility(View.GONE);
            }

            @Override
            public void onNoTicketsRetrieved() {
                mProgressBar.setVisibility(View.GONE);
            }
        });
    }

    private class TicketAdapter extends RecyclerView.Adapter<TicketHolder> {

        private List<Ticket> mTicketList;

        public TicketAdapter() {
            mTicketList = new ArrayList<>();
        }

        @Override
        public TicketHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(getActivity()).inflate(R.layout.single_ticket_layout, parent, false);
            ButterKnife.bind(this, v);
            return new TicketHolder(v);
        }

        @Override
        public void onBindViewHolder(TicketHolder holder, final int position) {
            holder.bindView(mTicketList.get(position));
            holder.setOnItemDeleteListener(new OnItemDeleteListener() {
                @Override
                public void onItemDeleted() {
                    mTicketList.remove(position);
                    notifyDataSetChanged();
                }
            });
        }

        public void addTicket(Ticket ticket) {
            mTicketList.add(ticket);
        }

        @Override
        public int getItemCount() {
            return mTicketList.size();
        }
    }

    private class TicketHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener{

        private Ticket mTicket;
        private OnItemDeleteListener mOnItemDeleteListener;

        private TextView mTextView;
        private ImageView mDeleteButton;

        public TicketHolder(View itemView) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.id_ticket_name);
            mDeleteButton = itemView.findViewById(R.id.id_delete_ticket_button);
            itemView.setOnClickListener(this);
        }

        public void bindView(Ticket ticket) {
            mTicket = ticket;
            mTextView.setText(mTicket.getName());
            mDeleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TicketController.newInstance().deleteTicket(mTicket.getUid(), mType, new OnActionCompletedListener() {
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

        @Override
        public void onClick(View view) {
            Intent i = EditTicketActivity.newIntent(getActivity(), mTicket, mType);
            startActivity(i);
        }

        public void setOnItemDeleteListener(OnItemDeleteListener onItemDeleteListener) {
            mOnItemDeleteListener = onItemDeleteListener;
        }
    }
}
