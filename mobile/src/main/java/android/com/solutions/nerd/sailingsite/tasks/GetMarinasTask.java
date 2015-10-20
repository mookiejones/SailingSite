package android.com.solutions.nerd.sailingsite.tasks;


import android.com.solutions.nerd.sailingsite.model.Marina;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cberman on 12/29/2014.
 */
public class GetMarinasTask extends AsyncTask<String, Void, List<Marina>> {
    public interface GetMarinasListener {
        void onMarinasRetrieved(List<Marina> results);
    }

    private GetMarinasListener mListener;

    public GetMarinasTask(GetMarinasListener listener) {
        mListener = listener;
    }


    private String getUrl(String urlString){
        return getUrlBuffer(urlString).toString();
    }
    private StringBuffer getUrlBuffer(String urlString){
        // TODO Auto-generated method stub

        StringBuffer chaine = new StringBuffer("");
        try{
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestProperty("User-Agent", "");
            connection.setRequestMethod("POST");
            connection.setDoInput(true);
            connection.connect();

            InputStream inputStream = connection.getInputStream();

            BufferedReader rd = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while ((line = rd.readLine()) != null) {
                chaine.append(line);
            }

        } catch (IOException e) {
            // writing exception to log
            e.printStackTrace();
        }

        return chaine;
    }
    @Override
    protected List<Marina> doInBackground(String... params) {
        List<Marina> mMarinas = new ArrayList<Marina>();
        InputStream rr = null;
        JSONArray jarray=null;
        for (String url : params) {

            try {
                String result = getUrl(url);

                long len = result.length();
                JSONObject json = new JSONObject(result);
                JSONArray array = json.optJSONArray("marinas");
                if (array!=null){
                    for(int i = 0;i<array.length();i++){
                        JSONObject object = array.optJSONObject(i);
                        Marina marina = jsonObjectToMarina(object);
                        if(marina!=null){
                            mMarinas.add(marina);
                        }
                    }
                }

            } catch (JSONException e) {
                Log.e("JSON Parser", "Error parsing data " + e.toString()); }


        }
        return mMarinas;
    }

    private Marina jsonObjectToMarina(JSONObject object) {
        Marina result=null;
        try {
            result = new Marina(object);
        }catch(Exception e){
            e.printStackTrace();
        }
        return result;
    }
    @Override
    protected void onPostExecute(List<Marina> result) {



        mListener.onMarinasRetrieved(result);
    }

}
