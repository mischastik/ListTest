package com.example.balda.listtest;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.ViewGroup;
import android.content.Context;

/**
 * Created by balda on 09.02.2017.
 */

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListViewHolder> {
    private int mNumberItems;
    private ListItemClickListener mOnClickListener;

    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }

    public ListAdapter(int numberOfItems, ListItemClickListener listener) {
        mNumberItems = numberOfItems;
        mOnClickListener = listener;
    }

    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.main_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        ListViewHolder viewHolder = new ListViewHolder(view);

        return viewHolder;
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    @Override
    public void onBindViewHolder(ListViewHolder holder, int position) {
        holder.bind(position);
    }

    public class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvName;
        TextView tvType;
        TextView tvCity;
        ImageView ivLogo;

        public ListViewHolder(View itemView) {
            super(itemView);

            tvName = (TextView)itemView.findViewById(R.id.tv_item_name);
            tvType = (TextView)itemView.findViewById(R.id.tv_type);
            tvCity = (TextView)itemView.findViewById(R.id.tv_location_city);
            ivLogo = (ImageView)itemView.findViewById(R.id.imageViewLogo);
        }

        void bind(int idx) {
            tvName.setText("Bier #" + idx);
            if (idx % 3 == 0)
                tvType.setText("Helles");
            if (idx % 3 == 1)
                tvType.setText("Pils");
            if (idx % 3 == 2)
                tvType.setText("Dunkles");
            tvCity.setText("Salzburg");
            ivLogo.setImageResource(R.drawable.ic_stiegl_bier_logo);
        }


        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onListItemClick(clickedPosition);
        }
    }
}
