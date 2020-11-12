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
import androidx.appcompat.app.AppCompatActivity;
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
import cr.ac.ucr.turistico.models.User;

public class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.ViewHolder> implements ItemClickListener {

    private Context context;
    private ArrayList<Lugar> places = new ArrayList<>();
    boolean liked = false;
    private ArrayList<Button> buttons = new ArrayList<>();

    FirebaseAuth aAuth;
    FirebaseDatabase fbDatabase;
    DatabaseReference likesUserRef;
    DatabaseReference placesRef;

    /**
     * estas variables son necesarias para la actualización de los likes
     * para crear la tabla y obtener info del objeto PlacesLiked
     * también a los array se le asigna lo que el PlaceFragment obtiene de Firebase
     * */
    private int idPlaceDB;
    private String  namePlace = "";
    String uId = "";
    int position = -1;

    private ArrayList<Integer> dbPlace = new ArrayList<>();
    private ArrayList<String> usersDB = new ArrayList<>();
    private int dbNumLikes = 0;

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

        aAuth = FirebaseAuth.getInstance();
        fbDatabase = FirebaseDatabase.getInstance();
        likesUserRef = fbDatabase.getReference("UPLikes");
        placesRef = fbDatabase.getReference("places");

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

    /**
     * Este metodo recibe 2 array, limpia los que ya estan creados para que no se repitan o se
     * guarden dobles y les asigna los que vienen por parametros
     * */
    public void addDBLikes(ArrayList<Integer> dbIdPlace, ArrayList<String> dbUsers) {
        this.dbPlace.clear();
        this.usersDB.clear();
        this.dbPlace.addAll(dbIdPlace);
        this.usersDB.addAll(dbUsers);
        Log.e("Array ", "Likes "+dbIdPlace+ " usuario "+usersDB);

        notifyDataSetChanged();
    }

    @Override
    public void onClick(View view, int position) {
        switch (view.getId()){
            case R.id.cv_place_card:
                Intent intent = new Intent(context, PlaceActivity.class);
                Lugar place = places.get(position);
                intent.putExtra(context.getString(R.string.place_name), place.getPlace());
                context.startActivity(intent);
                break;
            case R.id.btn_like:
                if (liked == false) {
                    buttons.get(position).setBackground(ContextCompat.getDrawable(context, R.drawable.ic_heart_red));
                    liked = true;

                    Lugar place2 = places.get(position);
                    idPlaceDB = place2.getId();
                    namePlace = place2.getPlace();
                    dbNumLikes = place2.getLikes();
                    addLike();
                }else {
                    buttons.get(position).setBackground(ContextCompat.getDrawable(context, R.drawable.ic_heart));
                    liked = false;

                    Lugar place2 = places.get(position);
                    idPlaceDB = place2.getId();
                    namePlace = place2.getPlace();
                    dbNumLikes = place2.getLikes();
                    quitLike();
                }
                break;
            default:
                break;
        }
    }

    /**
     * addLike actualizay añade los likes de la tabla places y de la tabla intermedia placesLiked
     * */
    private void addLike() {
        uId = aAuth.getCurrentUser().getUid();
        PlacesLiked placesLiked = new PlacesLiked();

        dbNumLikes = dbNumLikes+1;
        Map<String, Object> placeUpdate = new HashMap<>();
        placeUpdate.put(namePlace+"/likes", dbNumLikes);
        placesRef.updateChildren(placeUpdate);

        placesLiked.setUserID(uId);
        placesLiked.setPlaceID(idPlaceDB);
        likesUserRef.child(uId+"_"+namePlace).setValue(placesLiked);
    }

    /**
     * quitLike actualiza y quita los likes de la tabla places y de la tabla intermedia placesLiked
     * */
    private void quitLike() {
        dbNumLikes = dbNumLikes-1;
        Map<String, Object> placeUpdate = new HashMap<>();
        placeUpdate.put(uId+"_"+namePlace, null);
        likesUserRef.updateChildren(placeUpdate);
        placeUpdate.put(namePlace+"/likes", dbNumLikes);
        placesRef.updateChildren(placeUpdate);
        return;
    }

    private boolean searchUser() {
        for(String user: usersDB)
        {
            if(this.uId.equals(user)){
                position = usersDB.indexOf(uId);
                return true;
            }
        }
        return false;

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

