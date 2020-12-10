package com.kerberos.todoit.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.kerberos.todoit.R;
import com.kerberos.todoit.ui.model.CalendarHeader;
import com.kerberos.todoit.ui.model.Day;
import com.kerberos.todoit.ui.model.EmptyDay;
import com.kerberos.todoit.ui.model.ViewModel;

import java.util.Calendar;
import java.util.List;

public class CalendarAdapter extends RecyclerView.Adapter {
    private final int HEADER_TYPE = 0;
    private final int EMPTY_TYPE = 1;
    private final int DAY_TYPE = 2;

    private List<Object> mCalendarList;

    public CalendarAdapter(List<Object> calendarList) {
        mCalendarList = calendarList;
    }

    public void setCalendarList(List<Object> calendarList) {
        mCalendarList = calendarList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) { //뷰타입 나누기
        Object item = mCalendarList.get(position);
        if (item instanceof Long) {
            return HEADER_TYPE; //날짜 타입
        } else if (item instanceof String) {
            return EMPTY_TYPE; // 비어있는 일자 타입
        } else {
            return DAY_TYPE; // 일자 타입

        }
    }

    // ViewHolder create!!
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        if (viewType == HEADER_TYPE) {
            HeaderViewHolder viewHolder = new HeaderViewHolder(inflater.inflate(R.layout.item_calendar_header, parent, false));

            StaggeredGridLayoutManager.LayoutParams params = (StaggeredGridLayoutManager.LayoutParams)viewHolder.itemView.getLayoutParams();
            params.setFullSpan(true);
            viewHolder.itemView.setLayoutParams(params);

            return viewHolder;
        } else if (viewType == EMPTY_TYPE) {
            return new EmptyViewHolder(inflater.inflate(R.layout.item_day_empty, parent, false));
        } else {
            return new DayViewHolder(inflater.inflate(R.layout.item_day, parent, false));
        }
    }

    // 데이터 넣어서 완성 시켜주기
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        int viewType = getItemViewType(position);

        if (viewType == HEADER_TYPE) {
            HeaderViewHolder holder = (HeaderViewHolder)viewHolder;
            Object item = mCalendarList.get(position);
            CalendarHeader model = new CalendarHeader();

            if (item instanceof Long) {
                model.setHeader((Long)item);
            }
            holder.bind(model);
        } else if (viewType == EMPTY_TYPE) {
            EmptyViewHolder holder = (EmptyViewHolder)viewHolder;
            EmptyDay model = new EmptyDay();
            holder.bind(model);
        } else if (viewType == DAY_TYPE) {
            DayViewHolder holder = (DayViewHolder)viewHolder;
            Object item = mCalendarList.get(position);
            Day model = new Day();
            if (item instanceof Calendar) {
                model.setCalendar((Calendar)item);
            }
            holder.bind(model);
        }
    }

    @Override
    public int getItemCount() {
        if (mCalendarList != null) {
            return mCalendarList.size();
        }
        return 0;
    }

    private class HeaderViewHolder extends RecyclerView.ViewHolder {
        TextView itemHeaderTitle;

        public HeaderViewHolder(@NonNull View itemView) {
            super(itemView);
            initView(itemView);
        }

        public void initView(View v) {
            itemHeaderTitle = (TextView)v.findViewById(R.id.item_header_title);
        }

        public void bind(ViewModel model) {
            String header = ((CalendarHeader)model).getHeader();
            itemHeaderTitle.setText(header);
        }
    }

    private class DayViewHolder extends RecyclerView.ViewHolder {
        TextView itemDay;

        public DayViewHolder(@NonNull View itemView) {
            super(itemView);
            initView(itemView);
        }

        public void initView(View v) {
            itemDay = (TextView)v.findViewById(R.id.item_day);
        }

        public void bind(ViewModel model) {
            String day = ((Day)model).getDay();
            itemDay.setText(day);
        }
    }

    private class EmptyViewHolder extends RecyclerView.ViewHolder {
        public EmptyViewHolder(@NonNull View itemView) {
            super(itemView);
            initView(itemView);
        }

        public void initView(View v) {

        }

        public void bind(ViewModel model) {

        }
    }
}
