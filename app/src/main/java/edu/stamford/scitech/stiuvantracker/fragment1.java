package edu.stamford.scitech.stiuvantracker;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;


public class fragment1 extends Fragment {

    ListView lv;
    private Button testButton;
    String ret = "";
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
                "(A)Airport Link-Stamford from 8.10AM-04.10PM *EVERY HOUR*",
                "(B)MRT-Stamford from 7.45AM *ONLY MRT*",
                "(C)Bansuan-Stamford from 8.00AM-4.00PM *EVERY HOUR*",
                "(D)Stamford-Airport/Bansuan from 9.45AM-6.45PM *EVERY HOUR*",

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
                //Toast.makeText(getActivity(),
                        //"Position :" + itemPosition + "  ListItem : " + itemValue, Toast.LENGTH_LONG)
                        //.show();

                if(itemPosition == 0){
                    itemValue = "Airport Link";
                    Toast.makeText(getActivity(),
                            itemValue+" selected, go to map tab to view", Toast.LENGTH_LONG)
                            .show();
                }
                else if(itemPosition == 1){
                    itemValue = "MRT";
                    Toast.makeText(getActivity(),
                            itemValue+" selected, go to map tab to view", Toast.LENGTH_LONG)
                            .show();
                }
                else if(itemPosition == 2){
                    itemValue = "Bansuan";
                    Toast.makeText(getActivity(),
                            itemValue+" selected, go to map tab to view", Toast.LENGTH_LONG)
                            .show();
                }
                else if(itemPosition == 3){
                    itemValue = "Stamford";
                    Toast.makeText(getActivity(),
                            itemValue+" selected, go to map tab to view", Toast.LENGTH_LONG)
                            .show();
                }

                try {
                    FileOutputStream fileout = getActivity().openFileOutput("mytextfile.txt", getActivity().MODE_PRIVATE);
                    OutputStreamWriter outputWriter = new OutputStreamWriter(fileout);
                    outputWriter.write(itemValue.toString());
                    outputWriter.close();

                    //display file saved message
                    //Toast.makeText(getActivity(), "File saved successfully!",
                      //      Toast.LENGTH_SHORT).show();

                } catch (Exception e) {
                    e.printStackTrace();
                }

                //fragment2.newInstance();
                //Intent intent = new Intent(getActivity(), fragment2.class);
                //intent.putExtra("channel", itemValue);
                //startActivity(intent);
            }
        });

        testButton = (Button) getActivity().findViewById(R.id.button2);
        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(getActivity(), "clicked",
                //Toast.LENGTH_SHORT).show();

                try {
                    InputStream inputStream = getActivity().openFileInput("mytextfile.txt");

                    if (inputStream != null) {
                        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                        String receiveString = "";
                        StringBuilder stringBuilder = new StringBuilder();

                        while ((receiveString = bufferedReader.readLine()) != null) {
                            stringBuilder.append(receiveString);
                        }

                        inputStream.close();
                        ret = stringBuilder.toString();

                        Toast.makeText(getActivity(), ret,
                                Toast.LENGTH_SHORT).show();
                    }
                } catch (FileNotFoundException e) {
                    Log.e("login activity", "File not found: " + e.toString());
                } catch (IOException e) {
                    Log.e("login activity", "Can not read file: " + e.toString());
                }
            }
        });
    }
}