package com.hznhta.tick_it.Activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.hznhta.tick_it.Fragments.AddTicketFragment;
import com.hznhta.tick_it.Fragments.ChooseCategoryFragment;
import com.hznhta.tick_it.Fragments.HomeFragment;
import com.hznhta.tick_it.Fragments.RequestsListFragment;
import com.hznhta.tick_it.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NavViewActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener{

    private FragmentManager mFragmentManager;
    private Fragment mFragment;
    private Context mContext;
    @BindView(R.id.id_nav_view) NavigationView mNavigationView;
    @BindView(R.id.id_nav_drawer) DrawerLayout mDrawerLayout;

    private static final String FRAGMENT_EXTRA = "NavViewActivity.Fragment";

    public static Intent newIntent(Context context, Integer fragmentId) {
        Intent i = new Intent(context, NavViewActivity.class);
        i.putExtra(FRAGMENT_EXTRA, fragmentId == null ? -1 : fragmentId);
        return i;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(FirebaseAuth.getInstance().getCurrentUser() == null) {
            startActivity(SignInActivity.newIntent(this));
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_view);
        ButterKnife.bind(this);
        mFragmentManager = getSupportFragmentManager();
        mContext = this;
        mNavigationView.setNavigationItemSelectedListener(this);

        if(getIntent().getExtras() != null) {
            int fragmentId = getIntent().getExtras().getInt(FRAGMENT_EXTRA);
            if(fragmentId != -1) {
                chooseNavItem(fragmentId);
            } else {
                setFragmentView(HomeFragment.newInstance());
            }
        } else {
            setFragmentView(HomeFragment.newInstance());
        }
    }

    public void setFragmentView(Fragment fragment) {
        mFragment = mFragmentManager.findFragmentById(R.id.nav_fragment_container);
        if(mFragment == null) {
            mFragment = fragment;
            mFragmentManager.beginTransaction()
                    .add(R.id.nav_fragment_container, fragment)
                    .commit();
        } else {
            mFragment = fragment;
            mFragmentManager.beginTransaction()
                    .replace(R.id.nav_fragment_container, fragment)
                    .commit();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        chooseNavItem(item.getItemId());
        return true;
    }

    public void chooseNavItem(int id) {
        switch (id) {
            case R.id.menu_home:
                setFragmentView(HomeFragment.newInstance());
                break;
            case R.id.menu_add_ticket:
                setFragmentView(AddTicketFragment.newInstance());
                break;
            case R.id.menu_manage_tickets:
                setFragmentView(ChooseCategoryFragment.newInstance());
                break;
            case R.id.menu_add_admin:
                AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                dialog.setTitle("Create New Account");
                dialog.setMessage("Creating new account will automatically log you out of your current account. Click OK to proceed or CANCEL to cancel current operation.");
                dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseAuth.getInstance().signOut();
                        startActivity(AddAdminActivity.newIntent(mContext));
                    }
                });
                dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                dialog.show();
                break;
            case R.id.menu_credit_reqs:
                setFragmentView(RequestsListFragment.newInstance());
                break;
            default:
                setFragmentView(HomeFragment.newInstance());
        }
        mDrawerLayout.closeDrawers();
    }
}
