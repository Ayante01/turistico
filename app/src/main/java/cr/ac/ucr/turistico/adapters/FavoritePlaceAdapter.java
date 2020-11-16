package cr.ac.ucr.turistico.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cr.ac.ucr.turistico.PlaceActivity;
import cr.ac.ucr.turistico.R;
import cr.ac.ucr.turistico.fragments.FavoritePlaceFragment;
import cr.ac.ucr.turistico.models.Lugar;
import cr.ac.ucr.turistico.models.UsuarioLugar;

public class FavoritePlaceAdapter extends RecyclerView.Adapter<FavoritePlaceAdapter.ViewHolder> implements ItemClickListener {

    private Context context;
    private ArrayList<Lugar> places;
    FavoritePlaceFragment favoritePlaceFragment;
    private ArrayList<Button> buttons = new ArrayList<>();

    FirebaseAuth aAuth;
    FirebaseDatabase fbDatabase;
    DatabaseReference placesRef;

    DatabaseReference refUsersLikes;
    private ArrayList<String> placesID = new ArrayList<>();

    private String uId = "";

    private int dbNumLikes;
    private ArrayList<UsuarioLugar> dbUserPlace;

    public FavoritePlaceAdapter(Context context, FavoritePlaceFragment favoritePlaceFragment) {
        this.places = new ArrayList<>();
        this.context = context;
        this.favoritePlaceFragment = favoritePlaceFragment;

        fbDatabase = FirebaseDatabase.getInstance();
        refUsersLikes = fbDatabase.getReference("UPLikes");
        placesRef = fbDatabase.getReference("places");
        aAuth = FirebaseAuth.getInstance();

        this.dbUserPlace = new ArrayList<>();
    }

    @NonNull
    @Override
    public FavoritePlaceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_place, parent, false);
        return new ViewHolder(view, this);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoritePlaceAdapter.ViewHolder holder, int position) {
        Lugar place = places.get(position);

        holder.tvPlaceName.setText(place.getPlace());
        holder.tvProvince.setText(place.getProvince());
        holder.tvLikes.setText("" + place.getLikes());

        Glide.with(context)
                .load(place.getImage())
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.ivPlace);

        holder.btnLike.setBackground(ContextCompat.getDrawable(context, R.drawable.ic_heart_red));
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
        switch (view.getId()) {
            case R.id.cv_place_card:
                Intent intent = new Intent(context, PlaceActivity.class);
                Lugar place = places.get(position);
                intent.putExtra(context.getString(R.string.place_name), place.getPlace());
                context.startActivity(intent);
                break;
            case R.id.btn_like:
                Lugar place2 = places.get(position);

                    buttons.get(position).setBackground(ContextCompat.getDrawable(context, R.drawable.ic_heart));
                    quitLike(place2);
                break;
            default:
                break;
        }
    }


    private void quitLike(Lugar place) {
        uId = aAuth.getCurrentUser().getUid();
        dbNumLikes = 0;
        dbNumLikes = place.getLikes() - 1;
        place.setLikes(dbNumLikes);

        Map<String, Object> placeUpdate = new HashMap<>();
        placeUpdate.put(uId + "_" + place.getPlace(), null);
        refUsersLikes.updateChildren(placeUpdate);
        placeUpdate.put(place.getPlace() + "/likes", dbNumLikes);
        placesRef.updateChildren(placeUpdate);

        favoritePlaceFragment.favoritePlacesFragment.setupViewPager();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ItemClickListener listener;
        private final CardView cvPlaceCard;
        private final ImageView ivPlace;
        private final TextView tvPlaceName;
        private final TextView tvProvince;
        private final TextView tvLikes;
        public final Button btnLike;

        public ViewHolder(@NonNull View view, ItemClickListener listener) {
            super(view);
            this.listener = listener;

            cvPlaceCard = view.findViewById(R.id.cv_place_card);

            ivPlace = view.findViewById(R.id.iv_place);

            tvPlaceName = view.findViewById(R.id.tv_place_name);
            tvProvince = view.findViewById(R.id.tv_place_province);
            tvLikes = view.findViewById(R.id.tv_likes);
            btnLike = view.findViewById(R.id.btn_like);
            buttons.add(btnLike);
            cvPlaceCard.setOnClickListener(this);
            btnLike.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.onClick(view, getLayoutPosition());
        }
    }
}
