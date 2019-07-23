package kennguch.github.instagram.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import kennguch.github.instagram.R;
import kennguch.github.instagram.adapter.PostAdapter;

public class HomeFragment extends Fragment {

    RecyclerView mRecyclerView;
    PostAdapter mAdapter;


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,container,false);


        mRecyclerView= view.findViewById(R.id.home_recycler_view);
        mAdapter = new PostAdapter();

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mAdapter);




        return view;

    }

}
