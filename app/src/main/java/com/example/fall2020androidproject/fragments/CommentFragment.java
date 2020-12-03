package com.example.fall2020androidproject.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fall2020androidproject.R;
import com.example.fall2020androidproject.items.CommentItem;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CommentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CommentFragment extends Fragment {
    public CommentFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment CommentFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CommentFragment newInstance() {
        CommentFragment fragment = new CommentFragment();
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
        View view = inflater.inflate(R.layout.fragment_comment, container, false);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.comments);

        ArrayList<CommentItem> comments = new ArrayList<>();

        // Add the comment
        comments.add(new CommentItem(R.drawable.ic_baseline_music_note_24, "person", "comment"));
        comments.add(new CommentItem(0, "person2", "comment2"));
        comments.add(new CommentItem(0, "person3", "comment3"));
        comments.add(new CommentItem(0, "person4", "comment4"));

        CommentAdapter adapter = new CommentAdapter(comments);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        return view;
    }

    /**
     * Custom Adapter for the RecyclerView
     * @author Zachary Allard
     */
    class CommentAdapter extends RecyclerView.Adapter{
        ArrayList<CommentItem> comments = new ArrayList<>();

        public CommentAdapter(ArrayList<CommentItem> comments){
            this.comments = comments;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.comment_row, null);
            CommentViewHolder viewHolder = new CommentViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            final CommentItem currentItem = comments.get(position);
            final CommentViewHolder viewHolder = (CommentViewHolder)holder;
            viewHolder.image.setImageResource(currentItem.getImage_id());
            viewHolder.username.setText(currentItem.getUsername());
            viewHolder.comment.setText(currentItem.getComment());
        }

        @Override
        public int getItemCount() {
            if(comments == null){
                return 0;
            }
            return comments.size();
        }
    }

    /**
     * Custom ViewHolder for the RecyclerView
     * @author Zachary Allard
     */
    class CommentViewHolder extends RecyclerView.ViewHolder{
        protected ImageView image;
        protected TextView username;
        protected TextView comment;

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            this.image = itemView.findViewById(R.id.image);
            this.username = itemView.findViewById(R.id.username);
            this.comment = itemView.findViewById(R.id.comment);
        }
    }
}