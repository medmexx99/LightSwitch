package at.ms;

import android.content.SharedPreferences;
import android.content.SharedPreferences.*;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.MultiSelectListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceGroup;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by Mex on 05.07.2016.
 */
public class MyPreferenceFragment extends PreferenceFragment implements OnSharedPreferenceChangeListener {

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
//        getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
        // set texts correctly
        initSummary(getPreferenceScreen());
    }

    @Override
    public void onResume() {
        getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
        super.onResume();

    }

    @Override
    public void onPause() {
        getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
        super.onPause();
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
                                          String key) {
        updatePrefSummary(findPreference(key));
    }

    private void initSummary(Preference p) {
        if (p instanceof PreferenceGroup) {
            PreferenceGroup pGrp = (PreferenceGroup) p;
            for (int i = 0; i < pGrp.getPreferenceCount(); i++) {
                initSummary(pGrp.getPreference(i));
            }
        } else {
            updatePrefSummary(p);
        }
    }

    private void updatePrefSummary(Preference p) {
        if (p instanceof ListPreference) {
            ListPreference listPref = (ListPreference) p;
            p.setSummary(listPref.getEntry());
        }
        if (p instanceof EditTextPreference) {
            EditTextPreference editTextPref = (EditTextPreference) p;
            if (p.getTitle().toString().toLowerCase().contains("password"))
            {
                p.setSummary("******");
            } else {
                p.setSummary(editTextPref.getText());
            }
        }
        if (p instanceof MultiSelectListPreference) {
            EditTextPreference editTextPref = (EditTextPreference) p;
            p.setSummary(editTextPref.getText());
        }
    }
}

/*
    public static final String KEY_network_address_couch = "network_address_couch";
    public static final String KEY_network_port_couch = "network_port_couch";
    public static final String KEY_network_address_diningtable = "network_address_diningtable";
    public static final String KEY_network_port_diningtable = "network_port_diningtable";

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
                                          String key) {
        try {
            String value = "";
            if (key.equals(KEY_network_address_couch)) {
                value = sharedPreferences.getString(KEY_network_address_couch, "");
                TcpClient.getInstance().setCouchAddress(InetAddress.getByName(value));
            }
            if (key.equals(KEY_network_address_diningtable)) {
                value = sharedPreferences.getString(KEY_network_address_diningtable, "");
                TcpClient.getInstance().setDiningTableAddress(InetAddress.getByName(value));
            }
            if (key.equals(KEY_network_port_couch)) {
                value = sharedPreferences.getString(KEY_network_port_couch, "");
                TcpClient.getInstance().setPortCouch(Integer.valueOf(value));
            }
            if (key.equals(KEY_network_port_diningtable)) {
                value = sharedPreferences.getString(KEY_network_port_diningtable, "");
                TcpClient.getInstance().setPortDiningTable(Integer.valueOf(value));
            }
            Preference pref = findPreference(key);
            if(pref != null && value != null) {
                pref.setSummary(value);
            }

        }catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
*/