package android.com.solutions.nerd.sailingsite.ui;

import android.com.solutions.nerd.sailingsite.R;
import android.os.Bundle;

import com.google.android.gms.maps.SupportMapFragment;

/**
 * Created by cberman on 12/16/2014.
 */
public class JourneyActivity extends BaseActivity {
    SupportMapFragment mapFragment;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_map);
    }
}
