package com.example.androidlabs;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherForecast extends AppCompatActivity {
    private TextView minTemp, maxTemp, curTemp, uvRate, longView, latView;
    private ProgressBar progressBar;
    private ImageView weatherImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);

        ForecastQuery networkThread = new ForecastQuery();
        networkThread.execute("http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=7e943c97096a9784391a981c4d878b22&mode=xml&units=metric", "http://openweathermap.org/img/w/", "http://api.openweathermap.org/data/2.5/uvi?appid=7e943c97096a9784391a981c4d878b22&lat=45.348945&lon=-75.759389");

        minTemp = findViewById(R.id.minTemp);
        maxTemp = findViewById(R.id.maxTemp);
        curTemp = findViewById(R.id.currentTemp);
        weatherImage = findViewById(R.id.weatherView);
        uvRate = findViewById(R.id.uvRating);
        longView = findViewById(R.id.longView);
        latView = findViewById(R.id.latView);


        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);


    }

    private class ForecastQuery extends AsyncTask<String, Integer, String> {
        public String valAtt, minAtt, maxAtt, iconName, lat, lon;
        public Bitmap bitmap;
        public double aDouble ;

        @Override
        protected String doInBackground(String... params) {

            try {
                String myUrl = params[0];

                //create the network connection:


                URL url = new URL(myUrl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                // Starts the query
                conn.connect();


                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(false);
                XmlPullParser parser = Xml.newPullParser();
                //return conn.getInputStream();
                InputStream inStream = conn.getInputStream();
                parser.setInput(inStream, null);
                // parser.nextTag();


                while (parser.getEventType() != XmlPullParser.END_DOCUMENT) {
                    if (parser.getEventType() == XmlPullParser.START_TAG) {
                        String tagName = parser.getName();
                        if (tagName.equals("temperature")) {
                            valAtt = parser.getAttributeValue(null, "value");
                            Log.e("AsyncTask", "Found parameter value: " + valAtt);
                            Thread.sleep(500);
                            publishProgress(25);
                            // parameter = parser.getText();


                            minAtt = parser.getAttributeValue(null, "min");
                            Log.e("AsyncTask", "Found parameter min: " + minAtt);
                            Thread.sleep(500);
                            publishProgress(50);
                            //minAtt = parser.getText();

                            maxAtt = parser.getAttributeValue(null, "max");
                            Log.e("AsyncTask", "Found parameter max: " + maxAtt);
                            Thread.sleep(500);
                            publishProgress(75); //tell android to call onProgressUpdate with 2 as parameter*/

                        } else if (tagName.equals("weather")) {

                            iconName = parser.getAttributeValue(null, "icon");
                            Log.e("AsyncTask", "Found parameter icon: " + iconName);
                        }else if(parser.getName().equals("coord")){
                            lat = parser.getAttributeValue(null, "lat");
                            Log.e("AsyncTask", "Found parameter min: " + lat);
                            lon =parser.getAttributeValue(null, "lon");
                            Log.e("AsyncTask", "Found parameter min: " + lon);


                    }

                    }
                    parser.next();
                }
                conn.disconnect();

                String image = iconName + ".png";
                boolean option = isExist(image);
                if (option) {
                     Log.i("Bitmap","image exists");
                    conn = (HttpURLConnection) new URL("http://openweathermap.org/img/w/" + image).openConnection();
                    conn.connect();
                    int responseCode = conn.getResponseCode();
                    if (responseCode == 200) {
                        bitmap = BitmapFactory.decodeStream(conn.getInputStream());
                    }


                } else {
                      Log.i("Bitmap","image does not ecist");

                    FileOutputStream outputStream = openFileOutput(image, Context.MODE_PRIVATE);
                    bitmap.compress(Bitmap.CompressFormat.PNG, 80,outputStream);
                    outputStream.flush();
                    outputStream.close();
                    conn.disconnect();
                    Thread.sleep(500);
                    publishProgress(100);
                }

                URL UVurl = new URL("http://api.openweathermap.org/data/2.5/uvi?appid=7e943c97096a9784391a981c4d878b22&lat=45.348945&lon=-75.759389");
                HttpURLConnection conn1 = (HttpURLConnection)UVurl.openConnection();
                inStream = conn1.getInputStream();

                BufferedReader reader = new BufferedReader(new InputStreamReader(inStream));
                StringBuilder sb = new StringBuilder();
                String line= null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line+"\n");
                }

                String result = sb.toString();
                JSONObject jObject = new JSONObject(result);
                aDouble = jObject.getDouble("value");
                Log.i("UV is:", ""+ aDouble);


            } catch (Exception ex) {
                Log.e("Crash!!", ex.getMessage());

            }
            return "Finished task";
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            Log.i("AsyncTaskExample", "update:" + values[0]);
            //messageBox.setText("At step:" + values[0]);
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setProgress(25);
            progressBar.setProgress(50);
            progressBar.setProgress(75);
            progressBar.setProgress(75);
            progressBar.setMax(100);


        }

        @Override
        protected void onPostExecute(String args) {
            //the parameter String s will be "Finished task" from line 27

            progressBar.setVisibility(View.INVISIBLE);

            minTemp.setText(" |Minimum temp: " + minAtt);
            curTemp.setText(" Current temp: " + valAtt);
            maxTemp.setText(" |Maximum temp: " + maxAtt);

            latView.setText("latitude: " + lat);
            longView.setText(" |longitude: " + lon);
            uvRate.setText("UV rating: " + aDouble);
            weatherImage.setImageBitmap(bitmap);
        }

        private boolean isExist(String fname) {
            File file = getBaseContext().getFileStreamPath(fname);
            return file.exists();
        }
    }
}