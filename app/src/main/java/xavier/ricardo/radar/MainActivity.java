package xavier.ricardo.radar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity implements ApiActivity {

    private static final int READ_PHONE_STATE = 1;
    private static final int ACCESS_LOCATION = 2;
    private static final int INTERNET = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        verificaPermissaoId();
    }

    private void verificaPermissaoId() {
        EditText edtId = findViewById(R.id.edtId);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            edtId.setText("SEM PERMISSÂO");
            ActivityCompat.requestPermissions(this,
                    new String[] { Manifest.permission.READ_PHONE_STATE },
                    READ_PHONE_STATE);
        } else {
            permitidoId();
        }
    }

    @SuppressLint("MissingPermission")
    private void permitidoId() {
        EditText edtId = findViewById(R.id.edtId);
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        String deviceId = telephonyManager.getDeviceId();
        edtId.setText(deviceId);
        verificaPermissaoInternet();
    }

    private void verificaPermissaoGps() {
        EditText edtLatitude = findViewById(R.id.edtLatitude);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            edtLatitude.setText("SEM PERMISSÂO");
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    ACCESS_LOCATION);
        } else {
            permitidoGps();
        }
    }

    @SuppressLint("MissingPermission")
    private void permitidoGps() {
        EditText edtLatitude = findViewById(R.id.edtLatitude);
        edtLatitude.setText("PERMISSÂO CONCEDIDA");
        EditText edtLongitude = findViewById(R.id.edtLongitude);
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        MyLocationListener locationListener = new MyLocationListener(this);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 30000, 0, locationListener);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 30000, 0, locationListener);
    }

    private void verificaPermissaoInternet() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[] { Manifest.permission.INTERNET },
                    INTERNET);
        } else {
            permitidoInternet();
        }
    }

    private void permitidoInternet() {
        verificaPermissaoGps();
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {

            case READ_PHONE_STATE: {
                EditText edtId = findViewById(R.id.edtId);
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    permitidoId();
                } else {
                    edtId.setText("PERMISSÂO NÃO CONCEDIDA");
                }
                break;
            }

            case ACCESS_LOCATION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    permitidoGps();
                } else {
                    EditText edtLatitude = findViewById(R.id.edtLatitude);
                    edtLatitude.setText("PERMISSÂO NÃO CONCEDIDA");
                }
                break;
            }

            case INTERNET: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    permitidoInternet();
                }
                break;
            }

        }
    }

    @Override
    public void processaResposta(String resposta) {

        if (resposta.startsWith("ERRO:")) {
            Toast.makeText(this, resposta.substring(5), Toast.LENGTH_LONG).show();
            return;
        }

        if (resposta.equals("ok")) {
            EditText edtId = findViewById(R.id.edtId);
            String url = "http://ricardoxavier.no-ip.org/radarws/radarws/vizinhanca/"
                    + edtId.getText().toString();
            new CallApi(this, url).execute();
            return;
        }

        ListView lvVizinhanca = findViewById(R.id.lvVizinhanca);
        Gson gson = new Gson();
        Vizinhanca vizinhanca = gson.fromJson(resposta, Vizinhanca.class);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, vizinhanca.getVizinhos());
        lvVizinhanca.setAdapter(adapter);

    }
}
