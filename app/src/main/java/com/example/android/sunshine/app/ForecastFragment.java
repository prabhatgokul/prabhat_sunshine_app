package com.example.android.sunshine.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class ForecastFragment extends Fragment {

    //Create ArrayAdapter object
    public ArrayAdapter<String> mForecastAdapter;


    public ForecastFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Add this line in order for this fragment to handle menu events.
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.forecastfragment, menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will automatically
        // handle clicks on the Home/up button, so long as you specify a parent activity
        // in AndroidManifext.xml.
        int id = item.getItemId();
        if (id == R.id.action_refresh) {
            updateWeather();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        /* Sample data - will be useful when creating Eye Atlas
        //Create array of forecast items
        String [] forecastArray = {
                "Today - Sunny - 88/63",
                "Tomorrow - Foggy - 40/46",
                "Weds - Cloudy - 72/63",
                "Thurs - Rainy - 64/51",
                "Fri - Foggy - 70/46",
                "Sat - Sunny - 76/68"
        };

        //Convert string array into ArrayList
        List<String> weekForecast = new ArrayList<String>(Arrays.asList(forecastArray));
        */

        //Create Forecast Adapter (ArrayAdapter)
        mForecastAdapter = new ArrayAdapter<String>(
                getActivity(), // The current context (this activity)
                R.layout.list_item_forecast, // The name of the layout ID
                R.id.list_item_forecast_textview, // The ID of the textview to populate
                new ArrayList<String>()); // Empty ArrayList (I guess will be filled somewhere :S)

        //Set fragment_main
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        //Create ListView object by finding view by Id
        ListView listView = (ListView) rootView.findViewById(R.id.listview_forecast);

        //Set the adapter with the listView
        listView.setAdapter(mForecastAdapter);

        // Handle the click event when list item is clicked
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the forecast that has been clicked by getting position, which was given
                // when this method was called
                String forecast = mForecastAdapter.getItem(position);
                Log.v("setOnItemClicked", "forecast String: " + forecast);

                // getActivity() - context
                // DetailActivity.class - the component class that is to be used for the intent
                // .putExtra - add extra data to the intent
                // Intent.EXTRA_TEXT - The name of the extra data
                // forecast - The bundle data value (see String forecast above)
                Intent intent = new Intent(getActivity(), DetailActivity.class)
                        .putExtra(Intent.EXTRA_TEXT, forecast);

                // Important!! Without it activity won't start
                startActivity(intent);


                // Toast to show which item was clicked
                // getActivity() gives context
                // forecast is the resource ID of the string to display
                // Toast.LENGTH_SHORT is the duration
                //.show() shows the view for the specified duration
                //Toast.makeText(getActivity(),forecast, Toast.LENGTH_SHORT).show();

            }
        });


        return rootView;
    }

    private void updateWeather() {

        // Copied and pasted - don't know exactly what's happening
        String location = Utility.getPreferredLocation(getActivity());
        new FetchWeatherTask(getActivity(), mForecastAdapter).execute(location);
    }

    @Override
    public void onStart() {
        super.onStart();
        updateWeather();
    }

}
