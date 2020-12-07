package com.example.fall2020androidproject.fragments;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.provider.CalendarContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fall2020androidproject.R;
import com.example.fall2020androidproject.items.EventItem;

import java.util.ArrayList;
import java.util.Date;

import static androidx.core.content.PermissionChecker.PERMISSION_GRANTED;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment {
    // Used for checking permissions
    private final int PERMISSION_WRITE_CALENDAR = 100;

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
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
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
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("Permissions","Event clicked. Event: " + events.get(position).getEventName());
                // Check for / attempt to get access to permissions
                if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED){
                    Log.d("Permissions","Permissions not granted. Attempting to get permissions.");
                    if(ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.WRITE_CALENDAR)){
                        Log.d("Permissions","Permissions previously denied. Attempting to show an alert.");
                        final AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
                        alertDialog.setTitle("Add Calendar Event");
                        alertDialog.setMessage("Requesting permission to add event to calendar");
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                alertDialog.dismiss();
                                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_CALENDAR}, PERMISSION_WRITE_CALENDAR);
                            }
                        });
                        alertDialog.show();
                    }else{
                        Log.d("Permissions","Requesting permissions in else block.");
                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_CALENDAR}, PERMISSION_WRITE_CALENDAR);
                    }
                }else{
                    // Permissions are valid
                    Log.d("Permissions","Permissions granted.");
                    // Add a calendar event
                    Uri uri = Uri.parse("content://com.android.calendar/events");
                    Intent intent = new Intent(Intent.ACTION_INSERT, uri);
                    intent.putExtra(CalendarContract.Events.TITLE, events.get(position).getEventName());
                    intent.putExtra(CalendarContract.Events.EVENT_LOCATION, "the licc");
                    if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                        startActivity(intent);
                    }
                }
            }
        });

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

            Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.item_animation_slide_in);
            convertView.startAnimation(animation);

            return convertView;
        }
    }
    /**
     * Custom Adapter for the ViewPager
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