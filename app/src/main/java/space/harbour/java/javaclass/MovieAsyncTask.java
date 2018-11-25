package space.harbour.java.javaclass;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGetHC4;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class MovieAsyncTask extends AsyncTask<Void, Void, Void> {
    private RecyclerView view;
    private ProgressDialog progressDialog;
    InputStream inputStream = null;
    private List<String> movieList;
    String result = "";

    public MovieAsyncTask(RecyclerView view, Context context){
        this.view = view;
        progressDialog = new ProgressDialog(context);
        movieList = new ArrayList<>();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog.setMessage("Downloading your data...");
        progressDialog.show();
        progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            public void onCancel(DialogInterface arg0) {
                MovieAsyncTask.this.cancel(true);
            }
        });
    }

    @Override
    protected Void doInBackground(Void... voids) {
        getValuesFromApi();

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(movieList);

        view.setAdapter(adapter);
    }

    private void getValuesFromApi() {
        String url_select = "https://api.myjson.com/bins/nfvfi";

        try {
            HttpClient httpClient = new DefaultHttpClient();

            HttpGetHC4 httpPost = new HttpGetHC4(url_select);
            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();

            inputStream = httpEntity.getContent();
        } catch (UnsupportedEncodingException e1) {
            Log.e("UnsupportedEncoding", e1.toString());
            e1.printStackTrace();
        } catch (ClientProtocolException e2) {
            Log.e("ClientProtocol", e2.toString());
            e2.printStackTrace();
        } catch (IllegalStateException e3) {
            Log.e("IllegalState", e3.toString());
            e3.printStackTrace();
        } catch (IOException e4) {
            Log.e("IO", e4.toString());
            e4.printStackTrace();
        }

        try {
            BufferedReader bReader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"), 8);
            StringBuilder sBuilder = new StringBuilder();

            String line;
            while ((line = bReader.readLine()) != null) {
                sBuilder.append(line + "\n");
            }

            inputStream.close();
            result = sBuilder.toString();

        } catch (Exception e) {
            Log.e("Reading", "Error converting result " + e.toString());
        }

        try {
            JSONArray jArray = new JSONArray(result);
            for(int i = 0; i < jArray.length(); i++) {
                JSONObject jObject = jArray.getJSONObject(i);
                movieList.add(jObject.getString("Title"));
            }
            this.progressDialog.dismiss();
        } catch (JSONException e) {
            Log.e("JSON", "Error: " + e.toString());
        }
    }
}
