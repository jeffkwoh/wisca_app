package ritwik.graphapp;

import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import ritwik.graphapp.NfcUtils.MifareUltralightTagTester;

public class WeightLog extends AppCompatActivity /*implements OnChartGestureListener, OnChartValueSelectedListener*/ {
    NfcAdapter mNfcAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight_log);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        //I added this if statement to keep the selected fragment when rotating the device
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new HomeFragment()).commit();
        }
    }

    // TODO: Create NFC Interface for encapsulation and to ensure activity implements NFC methods
    public NfcAdapter getNfcAdapter() {
        if (mNfcAdapter == null) {
            mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
        }
        return mNfcAdapter;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);

    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();

        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(intent.getAction())) {
            Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);

            // Build Fragment
            Fragment homeFragment = new HomeFragment();
            Bundle mBundle =  new Bundle();
            mBundle.putString("nfcData", "passed data");
            homeFragment.setArguments(mBundle);

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, homeFragment).commit();

//            Parcelable[] rawMessages =
//                    intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
//            if (rawMessages != null) {
//                NdefMessage[] messages = new NdefMessage[rawMessages.length];
//                for (int i = 0; i < rawMessages.length; i++) {
//                    messages[i] = (NdefMessage) rawMessages[i];
//                }
//                // Process the messages array.
//
//            }
        }
    }

    public String readNfc(){
        MifareUltralightTagTester tester = new MifareUltralightTagTester();
        NfcAdapter nfcAdapter = getNfcAdapter();
        return null;
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;

                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        selectedFragment = new HomeFragment();
                        break;
                    case R.id.navigation_weightTracker:
                        selectedFragment = new WeightTrackerFragment();
                        break;
                    case R.id.navigation_Visit:
                        selectedFragment = new AppointmentsFragment();
                        break;
                    case R.id.navigation_calendar:
                        selectedFragment = new CalendarFragment();
                        break;
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        selectedFragment).commit();

                return true;
            }
    };



}
