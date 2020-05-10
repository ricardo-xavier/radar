package xavier.ricardo.radar;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class CallApi extends AsyncTask<String, Void, String> {

    private ApiActivity contexto;
    private ProgressDialog progress;
    private String url;
    private String json;

    public CallApi(ApiActivity contexto, String url) {
        this.contexto = contexto;
        this.url = url;
    }

    public CallApi(ApiActivity contexto, String url, String json){
        this(contexto, url);
        this.json = json;
    }

    @Override
    protected void onPreExecute() {
        progress = new ProgressDialog((Context) contexto);
        progress.setMessage("Aguarde...");
        progress.show();
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        progress.dismiss();
        contexto.processaResposta(result);
    }


    @Override
    protected String doInBackground(String... strings) {

        try {

            URL u = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) u.openConnection();
            connection.setRequestProperty("User-Agent","Mozilla/5.0 ( compatible ) ");
            connection.setRequestProperty("Accept","*/*");

            connection.setDoInput(true);

            if (json != null) {
                // POST
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);
                connection.setRequestProperty("Content-type", "application/json");
                DataOutputStream printout = new DataOutputStream(connection.getOutputStream());
                printout.writeBytes(json);
                printout.flush();
                printout.close();
            }

            int status = connection.getResponseCode();
            InputStream inputStream;
            if ((status >= 400) && (status < 600)) {
                inputStream = connection.getErrorStream();
            } else {
                inputStream = connection.getInputStream();
            }

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            StringBuilder resultStr = new StringBuilder();
            while ((line = bufferedReader.readLine()) != null) {
                resultStr.append(line);
            }
            inputStream.close();

            return resultStr.toString();


        } catch (Exception e) {
            return "ERRO:" + e.getMessage();
        }

    }
}
