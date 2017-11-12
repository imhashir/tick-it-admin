package com.hznhta.tick_it.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.hznhta.tick_it.Fragments.HomeFragment;
import com.hznhta.tick_it.R;

public class NavViewActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener{

    private FragmentManager mFragmentManager;
    private Fragment mFragment;

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
        mFragmentManager = getSupportFragmentManager();

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
        return false;
    }

    public void chooseNavItem(int id) {
        switch (id) {
            case R.id.menu_home:
                setFragmentView(HomeFragment.newInstance());
                break;
            case R.id.menu_add_ticket:
                break;
            case R.id.menu_manage_tickets:
                break;
            case R.id.menu_add_admin:
                break;
            case R.id.menu_credit_reqs:
                break;
        }
    }
}
