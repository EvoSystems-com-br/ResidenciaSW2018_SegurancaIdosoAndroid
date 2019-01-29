package com.scopus.ifsp.projetofinalteste.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.scopus.ifsp.projetofinalteste.R;

import java.util.ArrayList;
import java.util.List;

public class calendarFragment extends Fragment {

    public calendarFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_calendar, container, false);
        CalendarView calendarView = v.findViewById(R.id.calendarView);
        calendarView.showCurrentMonthPage();

        List<EventDay> events = new ArrayList<>();

        calendarView.setEvents(events);
        return v;
    }
}
