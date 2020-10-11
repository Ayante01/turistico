package cr.ac.ucr.turistico.api;

import cr.ac.ucr.turistico.models.PlaceResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public interface PlacesService {

    @Headers("Content-Type: application/json")
    @GET("place")
    Call<PlaceResponse> getPlaces();

    @Headers("Content-Type: application/json")
    @GET("place/{id}")
    Call<Character> getPlace(@Path("id") int id);
}
