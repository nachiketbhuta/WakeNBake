package nshmadhani.com.wakenbake.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import nshmadhani.com.wakenbake.R;
import nshmadhani.com.wakenbake.Activities.TiffinPlacesActivity;
import nshmadhani.com.wakenbake.Models.TiffinPlaces;

public class TiffinPlacesListAdapter extends RecyclerView.Adapter
        <TiffinPlacesListAdapter.ListViewHolder>{

    private List<TiffinPlaces> mTiffinPlacesList;
    private Context context;

    public TiffinPlacesListAdapter(List<TiffinPlaces> tiffinPlacesList, Context context) {
        this.mTiffinPlacesList = tiffinPlacesList;
        this.context = context;
    }

    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_places, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ListViewHolder holder, int position) {

        final TiffinPlaces mTiffinPlace = mTiffinPlacesList.get(position);
        holder.mTiffinName.setText(mTiffinPlace.getmTiffinName());
        Picasso.with(context)
                .load(R.drawable.no_image)
                .into(holder.mTiffinImage);

        final Uri imageURL = Uri.parse(String.valueOf(R.drawable.user_image));

        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, TiffinPlacesActivity.class);
                intent.putExtra("tiffin_name", mTiffinPlace.getmTiffinName());
                intent.putExtra("tiffin_food", mTiffinPlace.getmTiffinFoodItems());
                intent.putExtra("tiffin_ratings", mTiffinPlace.getmTiffinRatings());
                intent.putExtra("tiffin_number", mTiffinPlace.getmTiffinNumber());
                intent.putExtra("tiffin_id", mTiffinPlace.getmTiffinId());
                intent.putExtra("tiffin_url", imageURL);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mTiffinPlacesList.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {

        private ImageView mTiffinImage;
        private TextView mTiffinName;
        //private TextView mTimeStamp;
        private CardView mCardView;

        public ListViewHolder(View itemView) {
            super(itemView);

            mTiffinImage = itemView.findViewById(R.id.listPlaceImage);
            mTiffinName = itemView.findViewById(R.id.listPlaceName);
            //mTimeStamp = itemView.findViewById(R.id.time_stamp);
            mCardView = itemView.findViewById(R.id.list_card_view);
        }
    }
}
