package com.adamsapp.rateme;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import link.fls.swipestack.SwipeStack;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragHot extends Fragment {

    SwipeStack swipeStack;


    public FragHot() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_frag_hot, container, false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        swipeStack = (SwipeStack)view.findViewById(R.id.swipe_stack);
        swipeStack.setAdapter(new SwipeAdapter(view.getContext()));
        swipeStack.setListener(new SwipeStack.SwipeStackListener() {
            @Override
            public void onViewSwipedToLeft(int position) {
                Toast.makeText(view.getContext(), "Disliked", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onViewSwipedToRight(int position) {
                Toast.makeText(view.getContext(), "Liked", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStackEmpty() {
                swipeStack.setAdapter(new SwipeAdapter(view.getContext()));
                Toast.makeText(view.getContext(), "Empty", Toast.LENGTH_SHORT).show();
            }
        });
    }
    
}
