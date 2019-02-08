package com.hackathon.offlinemaps.RetrofitUtils;



import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
// https://maps.googleapis.com/maps/api/directions/json?
// origin=iiitm+campus&destination=haz+gwl&
// key=AIzaSyBIZgWCuEGpS7mkV7Ozx4HzwVjKE35tuL0
public interface ApiInterface {
    
    @GET("maps/api/directions/json")
    Call<ModelAllResults> getAllDirectionData(@Query("origin") String startLocation, @Query("destination")
            String endLocation , @Query("key") String key);
    @GET("/maps/api/place/autocomplete/json")
    Call<ModelAllResultsAutoPlaces> getAllAutoCompleteData(@Query("input")String place,@Query("key") String key);

}