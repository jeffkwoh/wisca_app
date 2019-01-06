package ritwik.graphapp;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static ritwik.graphapp.NfcUtils.NfcConstants.BUNDLE_PATIENT_WEIGHT_KEY;
import static ritwik.graphapp.NfcUtils.NfcConstants.BUNDLE_WHEELCHAIR_WEIGHT_KEY;
import static ritwik.graphapp.NfcUtils.NfcConstants.PATIENT_WEIGHT_PREFIX;
import static ritwik.graphapp.NfcUtils.NfcConstants.WHEELCHAIR_WEIGHT_PREFIX;

public class WeightLog extends AppCompatActivity implements NfcWriter /*implements OnChartGestureListener, OnChartValueSelectedListener*/ {
    NfcAdapter mNfcAdapter = null;
    IntentFilter[] intentFiltersArray = null;
    String[][] techListsArray = null;
    PendingIntent pendingIntent = null;
    Integer weightToWrite = null;

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

        // Set up data structures for foreground dispatch
        pendingIntent = PendingIntent.getActivity(
                this,
                0,
                new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP),
                0);
        IntentFilter ndef = new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED);
        try {
            ndef.addDataType("text/plain");    /* Handles only text/plain dispatches */
        } catch (IntentFilter.MalformedMimeTypeException e) {
            throw new RuntimeException("fail", e);
        }
        intentFiltersArray = new IntentFilter[]{ndef,};
        techListsArray = new String[][]{new String[]{Ndef.class.getName()}};
    }

    // TODO: Create NFC Interface for encapsulation and to ensure activity implements NFC methods
    public NfcAdapter getNfcAdapter() {
        if (mNfcAdapter == null) {
            mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
        }
        return mNfcAdapter;
    }

    public void writeWheelchairWeightToTag(int weight) {
        weightToWrite = weight;
    }

    private NdefMessage createUpdatedMessage(int weight, NdefMessage currentMessage) {
        // checks for null messages
        if (currentMessage == null) {
            return null;
        }
        // TODO: Add guards to verify message is a Wisca message.

        List<NdefRecord> recordList = new ArrayList<>();
        recordList.add(NdefRecord.createTextRecord(
                "en",
                " :" + String.valueOf(weight)));
        // Retains only non wheelchair-weight tag data
        for (NdefRecord record : currentMessage.getRecords()) {
            if (isPrefixedBy(record.getPayload(), WHEELCHAIR_WEIGHT_PREFIX)) {
                continue;
            }
            recordList.add(record);
        }
        return new NdefMessage(recordList.toArray(new NdefRecord[0]));
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);

    }

    @Override
    public void onPause() {
        super.onPause();
        getNfcAdapter().disableForegroundDispatch(this);
    }


    /**
     * TODO: Add guard clause in the scenario that device is not nfc enabled.
     */
    @Override
    protected void onResume() {
        super.onResume();

        getNfcAdapter().enableForegroundDispatch(
                this,
                pendingIntent,
                intentFiltersArray,
                techListsArray);

        Intent intent = getIntent();

        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(intent.getAction())) {
            // Write to NFC
            if (weightToWrite != null) {
                Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
                NdefMessage message = (NdefMessage) intent
                        .getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES)[0];
                NdefMessage messageToWrite = createUpdatedMessage(weightToWrite, message);
                try {
                    Ndef ndef = Ndef.get(tag);
                    ndef.connect();
                    ndef.writeNdefMessage(messageToWrite);
                    ndef.close();
                    // clear the write buffer so it won't rewrite.
                    weightToWrite = null;
                } catch (FormatException | IOException e) {
                    e.printStackTrace();
                }

            } else {
                // Build Fragment for NFC READ to Graph
                Bundle mBundle = getNfcBundle(intent);
                Fragment weightTrackerFragment = new WeightTrackerFragment();
                weightTrackerFragment.setArguments(mBundle);

                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, weightTrackerFragment).commit();
            }
        }
    }

    /**
     * Precondtiion: intent's action must be ACTION_NDEF_DISCOVERED
     *
     * @param intent
     * @return
     */
    @NonNull
    private Bundle getNfcBundle(Intent intent) {
        Bundle nfcBundle = new Bundle();
        ArrayList<String> tokens = new ArrayList<>();
        Parcelable[] rawMessages =
                intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
        // TODO: Validate that message is from a NFC tag that only has text data
        if (rawMessages != null) {
            NdefMessage[] messages = new NdefMessage[rawMessages.length];
            for (int i = 0; i < rawMessages.length; i++) {
                messages[i] = (NdefMessage) rawMessages[i];
            }

            for (NdefMessage message : messages) {
                for (NdefRecord record : message.getRecords()) {
                    byte[] payload = record.getPayload();
                    // Strips first 3 bytes of payload which should be metadata
                    String humanReadablePayload = payload.length < 2 ?
                            "" : new String(payload).substring(3);
                    tokens.add(humanReadablePayload);
                }
            }
        }

        ArrayList<String> patientWeightDateData = filterByPrefix(tokens, PATIENT_WEIGHT_PREFIX);
        ArrayList<String> wheelchairWeightData = filterByPrefix(tokens, WHEELCHAIR_WEIGHT_PREFIX);

        nfcBundle.putStringArrayList(BUNDLE_PATIENT_WEIGHT_KEY, patientWeightDateData);
        nfcBundle.putStringArrayList(BUNDLE_WHEELCHAIR_WEIGHT_KEY, wheelchairWeightData);

        return nfcBundle;
    }

    private boolean isPrefixedBy(byte[] payload, char prefix) {
        String token = new String(payload);

        List<String> wrapper = new ArrayList<>();
        wrapper.add(token);
        // If result is an empty list, then the prefix is not present in token
        return !filterByPrefix(wrapper, prefix).isEmpty();
    }

    private ArrayList<String> filterByPrefix(List<String> tokens, char prefix) {
        ArrayList<String> stringList = new ArrayList<>();
        for (String token : tokens) {
            int index = token.indexOf(prefix);
            // Only adds strings with the prefix. Also ignores strings like "   <prefix>"
            if (index != -1 && index + 1 < token.length()) {
                stringList.add(token.substring(index + 1));
            }
        }
        return stringList;
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
