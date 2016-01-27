package scca.hou_scca_timing_app;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ScrollView;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Element;


import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    String url = "http://houscca.com/solo/results/results_live.htm";
    Document doc = null;
    TextView title = null;
    TextView textView2 = null;
    String test1 = "";
    String test2 = "";
    String body = "";
    Elements rows;


    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //Add a part to where you pull jsoup here

        title = (TextView) findViewById(R.id.title);
        textView2 = (TextView) findViewById(R.id.textView2);

        new DataGrabber().execute();


        //TextView htmlTextView = (TextView)findViewById(R.id.html_text);
        // htmlTextView.setText(Html.fromHtml(htmlText, null, null));

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    //New class for the Asynctask, where the data will be fetched in the background
    private class DataGrabber extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try {
                // Connect to the web site
                doc = Jsoup.connect(url).get();

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            //This is where we update the UI with the acquired data
            if (doc != null) {
                title.setText(doc.title().toString());
                rows = doc.select("tr");
                for (Element row : rows) {

                    String line = "-----------";
                    body = body + "\n" + line;

                    Elements tdElements = row.select("th");
                    body = body + "\n" + tdElements.text();


                    Elements row2 = row.select("td");
                    body = body + "\n" + row2.text();

                    Elements titleRow = row.select("td");
                    if (titleRow.size() == 12 ){
                        for ( Element cell : titleRow){
                            Log.d("len", cell.text());
                        }
                        Log.d("class", titleRow.get(1).text());
                        Log.d("len", String.valueOf(titleRow.size()));
                    }

                }
                // Testing for cells

                //Log.d("Cell", titleRow.get(0).text());
                textView2.setText(body);

            } else {
                title.setText("FAILURE");
            }


            //Elements tdElements = doc.select("td");
            //Log.d("tdElements", tdElements);
            //String test1 = tdElements.get(30).text();
            //textView2.setText(test1);

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://scca.hou_scca_timing_app/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://scca.hou_scca_timing_app/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
