package com.example.eswachta.ui.gallery;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.example.eswachta.databinding.FragmentGalleryBinding;

public class GalleryFragment extends Fragment {

    private FragmentGalleryBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        GalleryViewModel galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Find the button in the layout and set the click listener

        Button searchButton = binding.googlemapBtn; // Use binding to get the Button
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchLocationInGoogleMaps("Shukla E-waste Processor",28.20711,76.85781); // Change this to your desired location
            }
        });
        Button searchButton2 = binding.googlemapBtn1; // Use binding to get the Button
        searchButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchLocationInGoogleMaps("EWASTER",28.49730,77.09198); // Change this to your desired location
            }
        });

        return root;
    }

    private void searchLocationInGoogleMaps(String location,double lat,double lon) {

        Uri geoURI=Uri.parse("geo:"+lat+","+lon+"?q="+Uri.encode(location));
        Intent mapIntent = new Intent(android.content.Intent.ACTION_VIEW,geoURI);
//        PackageManager packageManager = getContext().getPackageManager();
//        if (mapIntent.resolveActivity(packageManager) != null) {
            startActivity(mapIntent); // Start the map activity
//        } else {
//            // Handle the case where no map app is installed
//            Toast.makeText(getContext(), "No map application available", Toast.LENGTH_SHORT).show();
//        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
