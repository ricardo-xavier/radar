package xavier.ricardo.radar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

public class SituacaoActivity extends AppCompatActivity implements ApiActivity {

    private static final int READ_PHONE_STATE = 1;
    private static final int ACCESS_LOCATION = 2;
    private static final int INTERNET = 3;

    private Individuo eu = new Individuo();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_situacao);
    }

    private void verificaPermissaoId() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[] { Manifest.permission.READ_PHONE_STATE },
                    READ_PHONE_STATE);
        } else {
            permitidoId();
        }
    }

    @SuppressLint("MissingPermission")
    private void permitidoId() {
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        String deviceId = telephonyManager.getDeviceId();
        eu.setId(deviceId);
        verificaPermissaoInternet();
    }

    private void verificaPermissaoGps() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    ACCESS_LOCATION);
        } else {
            permitidoGps();
        }
    }

    @SuppressLint("MissingPermission")
    private void permitidoGps() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        MyLocationListener locationListener = new MyLocationListener(this);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 60000, 0, locationListener);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 60000, 0, locationListener);
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

    private void radar(String situacao) {
        eu.setStatus(situacao);
        verificaPermissaoId();
    }

    public void normal(View v) {
        radar("normal");
    }

    public void suspeito(View v) {
        radar("suspeito");
    }

    public void infectado(View v) {
        radar("infectado");
    }

    public void recuperado(View v) {
        radar("recuperado");
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
                    Toast.makeText(this, "Sem permissão ID", Toast.LENGTH_LONG).show();
                }
                break;
            }

            case ACCESS_LOCATION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    permitidoGps();
                } else {
                    Toast.makeText(this, "Sem permissão GPS", Toast.LENGTH_LONG).show();
                }
                break;
            }

            case INTERNET: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    permitidoInternet();
                } else {
                    Toast.makeText(this, "Sem permissão internet", Toast.LENGTH_LONG).show();
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

        Intent intent = new Intent(this, RadarActivity.class);
        intent.putExtra("id", eu.getId());
        intent.putExtra("situacao", eu.getStatus());
        intent.putExtra("vizinhanca", resposta);
        startActivity(intent);

    }

    public Individuo getEu() {
        return eu;
    }
}
