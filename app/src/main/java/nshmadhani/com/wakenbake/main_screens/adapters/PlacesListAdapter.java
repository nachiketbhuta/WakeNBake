package nshmadhani.com.wakenbake.main_screens.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import nshmadhani.com.wakenbake.R;
import nshmadhani.com.wakenbake.main_screens.models.Places;

/**
 * Created by Nachiket on 03-Feb-18.
 */

public class PlacesListAdapter extends RecyclerView.Adapter<PlacesListAdapter.ListViewHolder> {



    private List<Places> mData;

    public PlacesListAdapter(List<Places> data) {
        mData = data;
    }

    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater li = LayoutInflater.from(parent.getContext());
        View view = li.inflate(R.layout.places_item_list,parent,false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ListViewHolder holder, int position) {
        holder.bindView(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    public class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mTextView;
        private ImageView mImageView;

        public ListViewHolder(View itemView) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.placeText);
            mImageView = itemView.findViewById(R.id.placeImage);
            itemView.setOnClickListener(this);
        }

        public void bindView (Places places) {


            //Name of the Places
            mTextView.setText(places.getName());
            //Images of the places
            //mImageView.set
        }

        @Override
        public void onClick(View view) {

        }
    }
}
