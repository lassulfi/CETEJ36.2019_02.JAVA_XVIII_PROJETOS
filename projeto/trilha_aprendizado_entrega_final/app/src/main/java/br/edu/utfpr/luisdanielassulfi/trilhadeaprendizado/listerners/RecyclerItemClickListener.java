package br.edu.utfpr.luisdanielassulfi.trilhadeaprendizado.listerners;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class RecyclerItemClickListener extends RecyclerView.SimpleOnItemTouchListener {

    private RecyclerView recyclerView;
    private OnItemClickListener listenerOnItemClick;
    private GestureDetector gestureDetector;

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
        void onLongItemClick(View view, int position);
    }

    public RecyclerItemClickListener(Context context, final RecyclerView recyclerView,
                                     OnItemClickListener listener) {
        this.recyclerView = recyclerView;
        this.listenerOnItemClick = listener;
        gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener(){

            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                if(child != null && listenerOnItemClick != null) {
                    listenerOnItemClick.onItemClick(child,
                            recyclerView.getChildAdapterPosition(child));
                    return true;
                }

                return false;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                if(child != null && listenerOnItemClick != null) {
                    listenerOnItemClick.onLongItemClick(child,
                            recyclerView.getChildAdapterPosition(child));
                }
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
        return gestureDetector.onTouchEvent(e);
    }
}
