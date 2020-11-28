package com.example.fall2020androidproject.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.fall2020androidproject.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MenuImageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MenuImageFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_IMAGEVIEW_ID = "imageview_id";

    // TODO: Rename and change types of parameters
    private int imageviewID;

    public MenuImageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param imageviewID ImageView ID
     * @return A new instance of fragment MenuImageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MenuImageFragment newInstance(int imageviewID) {
        MenuImageFragment fragment = new MenuImageFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_IMAGEVIEW_ID, imageviewID);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            imageviewID = getArguments().getInt(ARG_IMAGEVIEW_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_menu_image, container, false);

        ImageView imageView = view.findViewById(R.id.menuImageView);
        if(getArguments() != null){
            imageView.setImageResource(this.imageviewID);
        }

        return view;
    }
}