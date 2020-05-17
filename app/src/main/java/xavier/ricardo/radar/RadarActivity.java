package xavier.ricardo.radar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.gson.Gson;

public class RadarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radar);

        Populacao populacao = new Populacao();
        Intent intent = getIntent();

        Gson gson = new Gson();
        String jsonVizinhanca = intent.getStringExtra("vizinhanca");
        Vizinhanca vizinhanca = gson.fromJson(jsonVizinhanca, Vizinhanca.class);
        populacao.setVizinhanca(vizinhanca);

        String situacao = intent.getStringExtra("situacao");

        Individuo eu = new Individuo();
        eu.setStatus(situacao);

        populacao.setEu(eu);

        View vwRadar = findViewById(R.id.vwRadar);
        vwRadar.setTag(populacao);
    }
}
