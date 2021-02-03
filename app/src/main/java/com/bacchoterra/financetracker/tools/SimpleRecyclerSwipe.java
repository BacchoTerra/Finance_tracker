package com.bacchoterra.financetracker.tools;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.bacchoterra.financetracker.model.Stock;

public class SimpleRecyclerSwipe {

    //Recycler itens
    private final RecyclerView recyclerView;

    //Model
    private Stock stock;

    public SimpleRecyclerSwipe(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }

    public void swipe (SwipeListener swipeListener){

        ItemTouchHelper.Callback itemCallback = new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {

                int DRAG_FLAGS = ItemTouchHelper.ACTION_STATE_IDLE;
                int SWIPE_FLAGS = ItemTouchHelper.END;

                return makeMovementFlags(DRAG_FLAGS, SWIPE_FLAGS);
            }

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                swipeListener.onSwiped(viewHolder.getAdapterPosition(),viewHolder);


            }
        };

        new ItemTouchHelper(itemCallback).attachToRecyclerView(recyclerView);
    }


    public interface SwipeListener {

        public void onSwiped(int position, RecyclerView.ViewHolder viewHolder);


    }
}
