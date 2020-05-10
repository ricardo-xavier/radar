package xavier.ricardo.radar;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.widget.EditText;

import com.google.gson.Gson;

class MyLocationListener implements LocationListener {

    private MainActivity contexto;

    public MyLocationListener(MainActivity contexto) {
        this.contexto = contexto;
    }

    @Override
    public void onLocationChanged(Location location) {

        EditText edtId = contexto.findViewById(R.id.edtId);
        EditText edtLatitude = contexto.findViewById(R.id.edtLatitude);
        EditText edtLongitude = contexto.findViewById(R.id.edtLongitude);

        edtLatitude.setText(String.valueOf(location.getLatitude()));
        edtLongitude.setText(String.valueOf(location.getLongitude()));

        String url = "http://ricardoxavier.no-ip.org/radarws/radarws/atualiza";
        Gson gson = new Gson();
        Individuo individuo = new Individuo();
        individuo.setId(edtId.getText().toString());
        individuo.setLatitude(location.getLatitude());
        individuo.setLongitude(location.getLongitude());
        String json = gson.toJson(individuo);
        new CallApi(contexto, url, json).execute();

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

}
