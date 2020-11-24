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
import cr.ac.ucr.turistico.fragments.PlaceFragment;
import cr.ac.ucr.turistico.models.Lugar;
import cr.ac.ucr.turistico.models.PlacesLiked;
import cr.ac.ucr.turistico.models.UsuarioLugar;

public class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.ViewHolder> implements ItemClickListener {

    private Context context;
    private ArrayList<Lugar> places;
    private ArrayList<Button> buttons = new ArrayList<>();
    PlaceFragment placeFragment;

    FirebaseAuth aAuth;
    FirebaseDatabase fbDatabase;
    DatabaseReference placesRef;

    DatabaseReference refUsersLikes;
    private ArrayList<String> placesID = new ArrayList<>();

    /**
     * estas variables son necesarias para la actualización de los likes
     * para crear la tabla y obtener info del objeto PlacesLiked
     * también a los array se le asigna lo que el PlaceFragment obtiene de Firebase
     */

    private String uId = "";

    private int dbNumLikes;
    private ArrayList<UsuarioLugar> dbUserPlace;

    public PlaceAdapter(Context context) {
        this.places = new ArrayList<>();
        this.context = context;
        this.placeFragment = placeFragment;

        fbDatabase = FirebaseDatabase.getInstance();
        refUsersLikes = fbDatabase.getReference("UPLikes");
        placesRef = fbDatabase.getReference("places");
        dbUserPlace = new ArrayList<>();

        refUsersLikes.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    String idUser = ds.child("userID").getValue(String.class);
                    String dbPlaceID = ds.child("placeID").getValue(String.class);
                    placesID.add(dbPlaceID);
                    UsuarioLugar userPlace = new UsuarioLugar();
                    userPlace.setIdPlace(dbPlaceID);
                    userPlace.setIdUser(idUser);
                    dbUserPlace.add(userPlace);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.i("DataSnapshot: ", error.getMessage());
            }
        });
    }

    public void refreshDb() {
        refUsersLikes.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dbUserPlace.clear();
                placesID.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    String idUser = ds.child("userID").getValue(String.class);
                    String dbPlaceID = ds.child("placeID").getValue(String.class);
                    placesID.add(dbPlaceID);
                    UsuarioLugar userPlace = new UsuarioLugar();
                    userPlace.setIdPlace(dbPlaceID);
                    userPlace.setIdUser(idUser);
                    dbUserPlace.add(userPlace);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.i("DataSnapshot: ", error.getMessage());
            }
        });
    }

    @NonNull
    @Override
    public PlaceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_place, parent, false);

        aAuth = FirebaseAuth.getInstance();
        fbDatabase = FirebaseDatabase.getInstance();

        return new ViewHolder(view, this);
    }

    @Override
    public void onBindViewHolder(@NonNull PlaceAdapter.ViewHolder holder, int position) {
        Lugar place = places.get(position);

        holder.tvPlaceName.setText(place.getPlace());
        holder.tvProvince.setText(place.getProvince());
        holder.tvLikes.setText(""+place.getLikes());

        Glide.with(context)
                .load(place.getImage())
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.ivPlace);

        uId = aAuth.getCurrentUser().getUid();
        refreshDb();
        for(UsuarioLugar userPlace : dbUserPlace){
            if(uId.equals(userPlace.getIdUser()) && Integer.toString(place.getId()).equals(userPlace.getIdPlace())){
                holder.btnLike.setBackground(ContextCompat.getDrawable(context, R.drawable.ic_heart_red));
            }
        }
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
                if(consultDbPlaces(place2).equals("empty") || consultDbPlaces(place2).equals("add") ){
                    buttons.get(position).setBackground(ContextCompat.getDrawable(context, R.drawable.ic_heart_red));
                    addLike(place2);
                    refreshDb();

                }if(consultDbPlaces(place2).equals("delete")) {
                    buttons.get(position).setBackground(ContextCompat.getDrawable(context, R.drawable.ic_heart));
                    quitLike(place2);
                    refreshDb();
                }
                break;
            default:
                break;
        }
    }

    private String consultDbPlaces(Lugar place) {
        if(placesID.size() > 0){
            for (String id : placesID) {
                if (id.equals(Integer.toString(place.getId()))) {
                    return "delete";
                }
            }
            return "add";
        }
        return "empty";
    }

    /**
     * addLike actualizay añade los likes de la tabla places y de la tabla intermedia placesLiked
     */
    private void addLike(Lugar place) {
        uId = aAuth.getCurrentUser().getUid();
        PlacesLiked placesLiked = new PlacesLiked();

        dbNumLikes = 0;
        dbNumLikes = place.getLikes() + 1;
        place.setLikes(dbNumLikes);

        Map<String, Object> placeUpdate = new HashMap<>();
        placeUpdate.put(place.getPlace() + "/likes", dbNumLikes);
        placesRef.updateChildren(placeUpdate);

        placesLiked.setUserID(uId);
        placesLiked.setPlaceID(Integer.toString(place.getId()));
        refUsersLikes.child(uId + "_" + place.getPlace()).setValue(placesLiked);

    }

    /**
     * quitLike actualiza y quita los likes de la tabla places y de la tabla intermedia placesLiked
     */
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

interface ItemClickListener {
    void onClick(View view, int position);
}

