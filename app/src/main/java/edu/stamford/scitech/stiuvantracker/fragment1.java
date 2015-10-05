package edu.stamford.scitech.stiuvantracker;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


public class fragment1 extends Fragment {

    ListView lv;
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment Fragment1.
     */
    public static fragment1 newInstance() {
        return new fragment1();
    }

    public fragment1() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fragment1, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        lv = (ListView) getActivity().findViewById(R.id.lv);

        String[] values = new String[] {
                "Airport Link",
                "MRT",
                "Bansuan",
                "Stamford",
                "Schedule Item Placeholder",
                "Schedule Item Placeholder",
                "Schedule Item Placeholder",
                "Schedule Item Placeholder",
                "Schedule Item Placeholder",
                "Schedule Item Placeholder",
                "Schedule Item Placeholder",
                "Schedule Item Placeholder",
                "Schedule Item Placeholder",
                "Schedule Item Placeholder",
                "Schedule Item Placeholder",
                "Schedule Item Placeholder",
                "Schedule Item Placeholder",
                "Schedule Item Placeholder",
                "Schedule Item Placeholder",
                "Schedule Item Placeholder"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, android.R.id.text1, values);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // ListView Clicked item index
                int itemPosition = position;
                // ListView Clicked item value
                String itemValue = (String) lv.getItemAtPosition(position);
                // Show Alert
                Toast.makeText(getActivity(),
                        "Position :" + itemPosition + "  ListItem : " + itemValue, Toast.LENGTH_LONG)
                        .show();

                //Intent intent = new Intent(getActivity(), fragment2.class);
                //intent.putExtra("channel", itemValue);
                //startActivity(intent);
            }
        });
    }
}