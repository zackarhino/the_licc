package com.example.fall2020androidproject.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.fall2020androidproject.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MainFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainFragment newInstance(String param1, String param2) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
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
        events.add(new EventItem("Event 1"));
        events.add(new EventItem("Event 2"));
        events.add(new EventItem("Event 3"));
        events.add(new EventItem("Event 4"));
        events.add(new EventItem("Event 5"));

        listView.setAdapter(new MenuListViewAdapter(getContext(), events));

        return view;
    }

    class MenuListViewAdapter extends ArrayAdapter<EventItem> {
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

            TextView tv = (TextView) convertView.findViewById(R.id.textView);
            tv.setText(item.getEventName());

            return convertView;
        }
    }
    class MenuViewPagerAdapter extends FragmentPagerAdapter{
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