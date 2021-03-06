package android.com.solutions.nerd.sailingsite.ui;

import android.os.AsyncTask;
import android.util.JsonReader;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static android.com.solutions.nerd.sailingsite.util.LogUtils.LOGI;

/**
 * Created by mookie on 1/8/15.
 */
@SuppressWarnings("unused")
public class WeatherClass extends AsyncTask<String,Void,WeatherClass.WeatherObject> {

    private static final String TAG=WeatherClass.class.getSimpleName();
    public WeatherClass(WeatherListener listener){
        mListener = listener;
    }
    private static final String base_weather_url = "http://api.wunderground.com/api/b52ad4185dacf690/conditions/q/";

    private InputStream getUrlContent(String urlString){
        try {
            String POST_PARAMS="";
            URL url = new URL("http://some-server");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");

            conn.setDoOutput(true);
            // For POST only - BEGIN
            /*
            OutputStream os = conn.getOutputStream();
            os.write(POST_PARAMS.getBytes());
            os.flush();
            os.close();
            */
            // read the response

            int responseCode = conn.getResponseCode();
            LOGI(TAG, "POST Response Code :: " + responseCode);

            if (responseCode == HttpURLConnection.HTTP_OK) { //success
                InputStream stream = conn.getInputStream();
                return stream;

            } else {
                LOGI(TAG, "POST request did not work.");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
// read the response
    }
    /**
     * Override this method to perform a computation on a background thread. The
     * specified parameters are the parameters passed to {@link #execute}
     * by the caller of this task.
     * <p/>
     * This method can call {@link #publishProgress} to publish updates
     * on the UI thread.
     *
     * @param params The parameters of the task.
     * @return A result, defined by the subclass of this task.
     * @see #onPreExecute()
     * @see #onPostExecute
     * @see #publishProgress
     */
    @Override
    protected WeatherObject doInBackground(String... params) {
        WeatherObject response = null;

        // first parameter should be latitude
        // second param should be longitude
        String url = getWeatherUrl(params);

        InputStream stream=getUrlContent(url);
        try {
            response=readWeather(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }


    private WeatherObject readWeather(InputStream in) throws IOException {


        JsonReader reader = new JsonReader(new InputStreamReader(in,"UTF-8"));
        WeatherObject result = new WeatherObject();
        result.readWeather(reader);
        return result;
    }

    private String getWeatherUrl(final String[] params){
        String base = base_weather_url;
        return base + String.valueOf(params[0]) + "," + String.valueOf(params[1])+".json";
    }

    /**
     * <p>Runs on the UI thread after {@link #doInBackground}. The
     * specified result is the value returned by {@link #doInBackground}.</p>
     * <p/>
     * <p>This method won't be invoked if the task was cancelled.</p>
     *
     * @param weather The result of the operation computed by {@link #doInBackground}.
     * @see #onPreExecute
     * @see #doInBackground
     * @see #onCancelled(Object)
     */
    @Override
    protected void onPostExecute(WeatherObject weather) {
        super.onPostExecute(weather);
        mListener.Callback(weather);
    }

    public void setListener(WeatherListener listener){
        mListener = listener;
    }
    public static WeatherListener mListener;

    public interface WeatherListener{
        public void Callback(WeatherObject weather);
    }

    public class WeatherObject
    {

        public WeatherObject(){}
        public void readWeather(final JsonReader reader)throws IOException{

            reader.beginObject();
            while(reader.hasNext()){
                String name = reader.nextName();
                switch(name){
                    case current_observation:
                        readObservation(reader);

                        break;
                    default:
                        reader.skipValue();
                        break;
                }

            }
            reader.endObject();

        }
        private void readObservation(final JsonReader reader) throws IOException{
            reader.beginObject();
            while(reader.hasNext()){
                String name = reader.nextName();
                switch(name){
                    case wind_dir:
                        mWindDir = reader.nextString();
                        break;
                    case temp_f:
                        mTempF=reader.nextString();
                        break;
                    case temp_c:
                        mTempC=reader.nextString();
                        break;
                    case wind_degrees:
                        mWindDegrees=reader.nextString();
                        break;
                    case wind_mph:
                        mWindMPH = reader.nextString();
                        break;
                    case wind_kph:
                        mWindKPH = reader.nextString();
                        break;
                    case wind_string:
                        mWindDescription=reader.nextString();
                        break;
                    case wind_gust_kph:
                        mWindGustKPH = reader.nextString();
                        break;
                    case wind_gust_mph:
                        mWindGustMPH = reader.nextString();
                        break;
                    case station_id:
                        mStationID = reader.nextString();
                        break;
                    case state:
                        mState = reader.nextString();
                        break;
                    case pressure_in:
                        mPressure_IN = reader.nextString();
                        break;
                    case pressure_mb:
                        mPressure_MB= reader.nextString();
                        break;
                    case pressure_trend:
                        mPressure_Trend = reader.nextString();
                        break;
                    case dewpoint_string:
                        mDewpointDescription = reader.nextString();
                        break;
                    case dewpoint_c:
                        mDewpoint_c = reader.nextString();break;
                    case dewpoint_f:
                        mDewpoint_f = reader.nextString();break;
                    case icon_url:
                        icon = reader.nextString();break;

                    default:
                        reader.skipValue();
                        break;

                }
            }
            reader.endObject();
        }
        public String icon;
        public String mDewpoint_c;
        public String mDewpoint_f;
        public String mDewpointDescription;
        private static final String dewpoint_string="dewpoint_string";
        private static final String dewpoint_f="dewpoint_f";
        private static final String dewpoint_c="dewpoint_c";
        private static final String icon_url="icon_url";

        private static final String country = "country";
        private static final String country_name = "country_name";
        private static final String lat = "lat";
        private static final String lon = "lon";


        private static final String state = "state";
        private static final String city = "city";
        public  String mStationID;
        public  String mWindDir;
        public  String mWindDescription;
        public  String mWindMPH;
        public  String mWindKPH;
        public  String mWindGustMPH;
        public  String mWindGustKPH;
        public  String mTempF;
        public  String mTempC;
        public String mWindDegrees;


        private String mState;
        private String mCity;
        private String mPressure_MB;
        private String mPressure_IN;
        private String mPressure_Trend;

        private static final String current_observation = "current_observation";
        private static final String display_location = "display_location";
        private static final String observation_location = "observation_location";
        private static final String station_id = "station_id";
        private static final String observation_time = "observation_time";

        private static final String temp_f = "temp_f";
        private static final String temp_c="temp_c";

        private static final String wind_string="wind_string";
        private static final String wind_dir="wind_dir";
        private static final String wind_degrees="wind_degrees";
        private static final String wind_mph="wind_mph";
        private static final String wind_kph="wind_kph";
        private static final String wind_gust_kph="wind_gust_kph";
        private static final String wind_gust_mph="wind_gust_mph";

        private static final String pressure_mb="pressure_mb";
        private static final String pressure_in="pressure_in";
        private static final String pressure_trend="pressure_trend";


        public static final String Degree_Symbol = " °";

    }
}
