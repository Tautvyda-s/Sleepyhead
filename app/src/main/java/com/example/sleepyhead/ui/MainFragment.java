package com.example.sleepyhead.ui;

import static android.content.Context.MODE_PRIVATE;
import static com.example.sleepyhead.ui.AddEditAlarmActivity.ADD_ALARM;
import static com.example.sleepyhead.ui.AddEditAlarmActivity.buildAddEditAlarmActivityIntent;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import com.example.sleepyhead.R;
import com.example.sleepyhead.adapter.AlarmsAdapter;
import com.example.sleepyhead.model.Alarm;
import com.example.sleepyhead.service.LoadAlarmsReceiver;
import com.example.sleepyhead.service.LoadAlarmsService;
import com.example.sleepyhead.util.AlarmUtils;
import com.example.sleepyhead.view.DividerItemDecoration;
import com.example.sleepyhead.view.EmptyRecyclerView;

import java.util.ArrayList;
import java.util.Objects;

public final class MainFragment extends Fragment
        implements LoadAlarmsReceiver.OnAlarmsLoadedListener {

    private LoadAlarmsReceiver mReceiver;
    private AlarmsAdapter mAdapter;
    private Switch switchTh;
    private SharedPreferences sharedPreferences;
    private String KEY_NIGHT_MODE="key_night_mode";
    private boolean isNightMode;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mReceiver = new LoadAlarmsReceiver(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View v = inflater.inflate(R.layout.fragment_main, container, false);

        final EmptyRecyclerView rv = v.findViewById(R.id.recycler);
        mAdapter = new AlarmsAdapter();
        rv.setEmptyView(v.findViewById(R.id.empty_view));
        rv.setAdapter(mAdapter);
        rv.addItemDecoration(new DividerItemDecoration(getContext()));
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setItemAnimator(new DefaultItemAnimator());




        final FloatingActionButton fab = v.findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            AlarmUtils.checkAlarmPermissions(getActivity());
            final Intent i = buildAddEditAlarmActivityIntent(getContext(), ADD_ALARM);
            startActivity(i);
            getActivity().overridePendingTransition(R.xml.slide_in_left, R.xml.slide_out_right);
        });

        final FloatingActionButton fab2 = v.findViewById(R.id.fab2);
        fab2.setOnClickListener(view -> {
            final Intent i = new Intent(getContext(), TotalScore.class);
            startActivity(i);
            getActivity().overridePendingTransition(R.xml.slide_in_right, R.xml.slide_out_left);

        });
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        final IntentFilter filter = new IntentFilter(LoadAlarmsService.ACTION_COMPLETE);
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(mReceiver, filter);
        LoadAlarmsService.launchLoadAlarmsService(getContext());
    }

    @Override
    public void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(mReceiver);
    }

    @Override
    public void onAlarmsLoaded(ArrayList<Alarm> alarms) {
        mAdapter.setAlarms(alarms);
    }
}
