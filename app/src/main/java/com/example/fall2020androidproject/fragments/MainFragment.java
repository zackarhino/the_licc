package com.example.fall2020androidproject.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.fall2020androidproject.R;
import com.example.fall2020androidproject.items.EventItem;

import java.util.ArrayList;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment {

    public MainFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment MainFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        // ViewPager Code
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.menuViewPager);
        viewPager.setAdapter(new MenuViewPagerAdapter(getChildFragmentManager()));

        // ListView Code
        ListView listView = (ListView) view.findViewById(R.id.eventListView);
        ArrayList<EventItem> events = new ArrayList<>();

        // Add the events
        events.add(new EventItem(getString(R.string.event_pr), "3-4-2077"));
        events.add(new EventItem(getString(R.string.event_mt_jazz), "3-14-2077"));
        events.add(new EventItem(getString(R.string.event_ms), "3-25-2077"));
        events.add(new EventItem(getString(R.string.event_licc), "5-1-2077"));
        events.add(new EventItem(getString(R.string.event_tritone), "5-7-2077"));

        listView.setAdapter(new MenuListViewAdapter(getContext(), events));

        return view;
    }

    /**
     * Custom ArrayAdapter for the ListView
     * @author Zachary Allard
     */
    static class MenuListViewAdapter extends ArrayAdapter<EventItem> {
        public MenuListViewAdapter(@NonNull Context context, @NonNull ArrayList<EventItem> objects) {
            super(context, 0, objects);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            EventItem item = getItem(position);
            if(convertView == null){
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_event, parent, false);
            }

            TextView eventName = (TextView) convertView.findViewById(R.id.eventName);
            TextView eventDate = (TextView) convertView.findViewById(R.id.eventDate);
            eventName.setText(item.getEventName());
            eventDate.setText(item.getEventDate());

            return convertView;
        }
    }
    /**
     * Custom Adapter for the ListView
     * @author Zachary Allard
     */
    static class MenuViewPagerAdapter extends FragmentPagerAdapter{
        public MenuViewPagerAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return 3;
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0: return MenuImageFragment.newInstance(R.drawable.the_licc);
                case 1: return MenuImageFragment.newInstance(R.drawable.the_licc_dance);
                case 2: return MenuImageFragment.newInstance(R.drawable.the_licc_party);
                default: return MenuImageFragment.newInstance(R.drawable.ic_baseline_music_note_24);
            }
        }
    }
}