package android.com.solutions.nerd.sailingsite.ui;

import android.com.solutions.nerd.sailingsite.R;
import android.os.Bundle;

import com.github.machinarius.preferencefragment.PreferenceFragment;


/**

 /**
 * Created by cberman on 12/17/2014.
 */
public class SettingsActivity extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }
}
