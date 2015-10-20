package android.com.solutions.nerd.sailingsite.util;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class JSONParser {
    static InputStream iStream = null;
    static JSONArray jarray = null;
    static String json = "";

    public JSONParser() {
    }

    public JSONArray getJSONFromUrl(String url) {




        StringBuilder builder = new StringBuilder();
//        HttpClient client = new DefaultHttpClient();
  //      HttpGet httpGet = new HttpGet(url);
  //      HttpTransport httpTransport = new HttpTransport() {
    //        @Override
    //        protected LowLevelHttpRequest buildRequest(String s, String s1) throws IOException {
     //           return null;
      //      }
//        }

        try {

  //          HttpResponse response = client.execute(httpGet);

    //        int statusCode = response.getStatusCode();
      //      if (statusCode == 200) {
        //        InputStream content = response.getContent();
          //      BufferedReader reader = new BufferedReader(new InputStreamReader(content));
                String line;
            //    while ((line = reader.readLine()) != null) {
              //      builder.append(line);
               // }
//            } else {
 //               Log.e("==>", "Failed to download file");
   //         }
        } catch (Exception e) {
            e.printStackTrace();
        } // Parse String to JSON object
        try {
            jarray = new JSONArray(builder.toString());
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }
        // return JSON Object
        return jarray;
    }
}



