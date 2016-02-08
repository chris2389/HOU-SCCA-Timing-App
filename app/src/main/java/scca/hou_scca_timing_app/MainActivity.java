package scca.hou_scca_timing_app;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class MainActivity extends AppCompatActivity
{

    String url = "http://houscca.com/solo/results/results_live.htm";
    Document doc = null;
    TextView title = null;
    TextView textView2 = null;
    TextView textView3 = null;
    TextView carclass = null;
    TextView cartype = null;
    TextView carcolor = null;
    TextView time1 = null;
    TextView time2 = null;
    TextView time3 = null;
    TextView time4 = null;
    TextView besttime = null;

    String carClass = "";
    String driverName = "";
    String carNumber = "";
    String carType = "";
    String carColor = "";
    String Time1 = "";
    String Time2= "";
    String Time3= "";
    String Time4= "";
    String bestTime= "";
    String driverResults = "";
    Elements rows;


    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //Add a part to where you pull jsoup here

        title = (TextView) findViewById(R.id.title);
        textView2 = (TextView) findViewById(R.id.textView2);
        textView3 = (TextView) findViewById(R.id.textView3);
        carclass = (TextView) findViewById(R.id.carclass);
        cartype = (TextView) findViewById(R.id.cartype);
        carcolor = (TextView) findViewById(R.id.carcolor);
        time1 = (TextView) findViewById(R.id.time1);
        time2 = (TextView) findViewById(R.id.time2);
        time3 = (TextView) findViewById(R.id.time3);
        time4 = (TextView) findViewById(R.id.time4);
        besttime = (TextView) findViewById(R.id.besttime);

        new DataGrabber().execute();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    //New class for the Asynctask, where the data will be fetched in the background
    private class DataGrabber extends AsyncTask<Void, Void, Void>
    {

        @Override
        protected Void doInBackground(Void... params)
        {
            try
            {
                // Connect to the web site
                doc = Jsoup.connect(url).get();

            }

            catch (IOException e)
            {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result)
        {
            //This is where we update the UI with the acquired data
            if (doc != null)
            {
                title.setText(doc.title().toString());
                rows = doc.select("tr");
                for (Element row : rows)
                {
                    Elements tdElements = row.select("th");
                    if(tdElements.size() == 8)
                    { //Class rows will have 8
                        carClass = carClass + "\n" + tdElements.get(0).text();
                        carclass.setText(carClass);

                        carNumber = carNumber + "\n" ;
                        driverName = driverName + "\n" ;
                        carType = carType + "\n";
                        carColor = carColor + "\n";
                        Time1 = Time1 + "\n" ;
                        Time2 = Time2 + "\n" ;
                        Time3 = Time3 + "\n";
                        Time4 = Time4+ "\n" ;
                        bestTime = bestTime + "\n" ;
                        carclass.setText(carClass);
                        textView2.setText(carNumber);
                        textView3.setText(driverName);
                        cartype.setText(carType);
                        //carcolor.setText(carColor);
                        time1.setText(Time1);
                        time2.setText(Time2);
                        time3.setText(Time3);
                        time4.setText(Time4);
                        besttime.setText(bestTime);
                    }

                    Elements driverRow = row.select("td");

                    if (driverRow.size() == 12 )
                    { // The driver will always have 12 cells
                        carNumber = carNumber + "\n" + driverRow.get(2).text();
                        driverName = driverName + "\n" + driverRow.get(3).text();
                        carType = carType + "\n" + driverRow.get(4).text();
                        //carColor = carColor + "\n" + driverRow.get(5).text();
                        Time1 = Time1 + "\n" + driverRow.get(6).text();
                        Time2 = Time2 + "\n" + driverRow.get(7).text();
                        Time3 = Time3 + "\n" + driverRow.get(8).text();
                        Time4 = Time4+ "\n" + driverRow.get(9).text();
                        bestTime = bestTime + "\n" + driverRow.get(10).text();

                        carClass = carClass + "\n";
                        carclass.setText(carClass);
                        textView2.setText(carNumber);
                        textView3.setText(driverName);
                        cartype.setText(carType);
                        //carcolor.setText(carColor);
                        time1.setText(Time1);
                        time2.setText(Time2);
                        time3.setText(Time3);
                        time4.setText(Time4);
                        besttime.setText(bestTime);
                    }
                }
            }

            else
            {
                title.setText("FAILURE");
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart()
    {
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
    public void onStop()
    {
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