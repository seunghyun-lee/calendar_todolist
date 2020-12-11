package com.kerberos.todoit.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.kerberos.todoit.R;
import com.kerberos.todoit.ui.adapter.CalendarAdapter;
import com.kerberos.todoit.utils.DBHelper;
import com.kerberos.todoit.utils.DateUtil;
import com.kerberos.todoit.utils.Keys;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import static android.content.ContentValues.TAG;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    public int mCenterPosition;
    private long mCurrentTime;
    public ArrayList<Object> mCalendarList = new ArrayList<>();

    public TextView textView;
    public RecyclerView recyclerView;
    private CalendarAdapter mAdapter;
    private StaggeredGridLayoutManager manager;

    private DBHelper dbHelper;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        Context context = inflater.getContext();
        dbHelper = new DBHelper(context);
        initView(rootView);
        initSet(rootView);
        setRecycler();

        return rootView;
    }

    public void initView(View v) {
        textView = (TextView)v.findViewById(R.id.title);
        recyclerView = (RecyclerView)v.findViewById(R.id.calendar);
    }

    public void initSet(View v) {
        initCalendarList(v);
    }

    public void initCalendarList(View v) {
        GregorianCalendar cal = new GregorianCalendar();
        setCalendarList(v, cal);
    }

    public void setTitle(View v, long time) {
        textView = (TextView)v.findViewById(R.id.title);
        textView.setText(DateUtil.getDate(time, DateUtil.CALENDAR_HEADER_FORMAT));
    }

    private void setRecycler() {

        if (mCalendarList == null) {
            Log.w(TAG, "No Query, not initializing RecyclerView");
        }

        manager = new StaggeredGridLayoutManager(7, StaggeredGridLayoutManager.VERTICAL);

        mAdapter = new CalendarAdapter(mCalendarList);

        mAdapter.setCalendarList(mCalendarList);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(mAdapter);

        if (mCenterPosition >= 0) {
            recyclerView.scrollToPosition(mCenterPosition);
        }
    }

    public void setCalendarList(View v, GregorianCalendar cal) {
        setTitle(v, cal.getTimeInMillis());
        ArrayList<Object> calendarList = new ArrayList<>();

        for (int i = -300 ; i < 300 ; i++) {
            try {
                GregorianCalendar calendar = new GregorianCalendar(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + i, 1, 0, 0, 0);
                if (i == 0) {
                    mCenterPosition = calendarList.size();
                }

                calendarList.add(calendar.getTimeInMillis());

                int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;         //해당 월에 시작하는 요일 -1 을 하면 빈칸을 구할 수 있겠죠 ?
                int max = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);     // 해당 월에 마지막 요일

                for (int j = 0 ; j < dayOfWeek ; j++) {
                    calendarList.add(Keys.EMPTY);
                }
                for (int j = 1 ; j <= max ; j++) {
                    calendarList.add(new GregorianCalendar(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), j));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            mCalendarList = calendarList;
        }
    }
}