package cr.ac.ucr.turistico.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

import cr.ac.ucr.turistico.PlaceActivity;
import cr.ac.ucr.turistico.R;
import cr.ac.ucr.turistico.models.Lugar;

public class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.ViewHolder> implements ItemClickListener {
    private Context context;
    private ArrayList<Lugar> places = new ArrayList<>();

    public PlaceAdapter(Context context, ArrayList<Lugar> places) {
        this.context = context;
        this.places = places;
    }

    public PlaceAdapter(Context context) {
        this.places = new ArrayList<>();
        this.context = context;
    }

    @NonNull
    @Override
    public PlaceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_place , parent, false);
        return new ViewHolder(view, this);
    }

    @Override
    public void onBindViewHolder(@NonNull PlaceAdapter.ViewHolder holder, int position) {
        Lugar place = places.get(position);

        holder.tvPlaceName.setText(place.getPlace());
        holder.tvProvince.setText(place.getProvince());
        holder.tvLikes.setText("0");

        Glide.with(context)
                .load(place.getImage())
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.ivPlace);

    }

    @Override
    public int getItemCount() {
        return places != null ? places.size() : 0;
    }

    public void addPlaces(ArrayList<Lugar> places) {

        this.places.addAll(places);

        notifyDataSetChanged();
    }

    @Override
    public void onClick(View view, int position) {
        Intent intent = new Intent(context, PlaceActivity.class);

        Lugar place = places.get(position);

        intent.putExtra(context.getString(R.string.place_name), place.getPlace());

        context.startActivity(intent);
    }

    // Esta clase se encarga de obtener los elementos del layout
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ItemClickListener listener;
        private final CardView cvPlaceCard;
        private final ImageView ivPlace;
        private final TextView tvPlaceName;
        private final TextView tvProvince;
        private final TextView tvLikes;

        public ViewHolder(@NonNull View view, ItemClickListener listener) {
            super(view);

            this.listener = listener;

            cvPlaceCard = view.findViewById(R.id.cv_place_card);

            ivPlace = view.findViewById(R.id.iv_place);

            tvPlaceName = view.findViewById(R.id.tv_place_name);
            tvProvince = view.findViewById(R.id.tv_place_province);
            tvLikes = view.findViewById(R.id.tv_likes);
        }
        @Override
        public void onClick(View view) {
            listener.onClick(view, getLayoutPosition());
        }
    }
}

interface ItemClickListener {
    void onClick(View view, int position);
}
