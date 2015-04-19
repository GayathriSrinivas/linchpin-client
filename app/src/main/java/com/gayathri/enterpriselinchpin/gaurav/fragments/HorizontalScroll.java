package com.gayathri.enterpriselinchpin.gaurav.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gayathri.enterpriselinchpin.R;
import com.gayathri.enterpriselinchpin.gaurav.beans.EmployeeRowItem;
import com.gayathri.enterpriselinchpin.gaurav.utils.HorizontalListView;
import com.gayathri.enterpriselinchpin.gaurav.utils.ImageLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

//import android.view.Lin.LayoutParams;


public class HorizontalScroll extends Fragment implements AdapterView.OnItemClickListener {
    public static final String MESSAGE = "DIRECTORY_MESSAGE";
    private static String[] dataObjects;
    private EmployeeRowItem[] rowItems;
    private String fbImageUrl = "https://s3-us-west-1.amazonaws.com/cmpe295b/Profile/";

    // TODO: Rename and change types of parameters
    private String message;


    //  private OnFragmentInteractionListener mListener;
    public interface OnImageSelectedListener {
        public void onImageSelectedListener(int position);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        System.out.println("Inside Horizontal Scroll On create activity");
        Bundle args = getArguments();
        System.out.println("Arguments " + args.getString(MESSAGE));
        //TextView v = new TextView(this);

        try {
            //convert String into JSON array
            JSONObject obj = new JSONObject(args.getString(MESSAGE));

            //convert into JSON array
            JSONArray directory = obj.getJSONArray("directory");
            System.out.println(directory.length());
            rowItems = new EmployeeRowItem[directory.length()];
            for (int i = 0; i < directory.length(); i++) {
                EmployeeRowItem rowItem = new EmployeeRowItem(fbImageUrl + directory.getJSONObject(i).getString("fbusername") + ".jpg", directory.getJSONObject(i).getString("fname"), null,directory.getJSONObject(i).getString("emp_department"));
                rowItems[i] = rowItem;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private BaseAdapter mAdapter = new BaseAdapter() {
        private View.OnClickListener mOnButtonClicked = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                System.out.println("Image Clicked");
                System.out.println(v.getTag());
                try {

                    ((OnImageSelectedListener)getActivity()).onImageSelectedListener((int)v.getTag());
                    v.setFocusable(true);
                }catch (ClassCastException e){ e.printStackTrace();}
            }
        };

        @Override
        public int getCount() {
            return rowItems.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View retval = LayoutInflater.from(parent.getContext()).inflate(R.layout.orghier_item, null);
            TextView title = (TextView) retval.findViewById(R.id.title);
            title.setText(rowItems[position].getEmpName());
            TextView label_horlist = (TextView) getActivity().findViewById(R.id.label_horlist);
            label_horlist.setText(rowItems[position].getEmpDept());
            ImageView empImageView = (ImageView) retval.findViewById(R.id.image);
            Context c = parent.getContext();
            ImageLoader imgLoader = new ImageLoader(c);
            imgLoader.DisplayImage(rowItems[position].getImageId(), empImageView);
            empImageView.setTag(position);
            empImageView.setOnClickListener(mOnButtonClicked);

            //set scroll arrows
            return retval;
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_horizontal_scroll, container, false);

        LinearLayout rl = (LinearLayout) v.findViewById(R.id.dirLayout);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int) LinearLayout.LayoutParams.WRAP_CONTENT,(int) LinearLayout.LayoutParams.WRAP_CONTENT);
        params.leftMargin = 50;
        params.topMargin = 50;
        TextView tv = new TextView(container.getContext());
        tv.setText("Android Test");
        tv.setLayoutParams(new LayoutParams(params));
        tv.setBackgroundColor(Color.RED);
        //v.addView(tv);
        ((LinearLayout) v).addView(tv);
        return v;
    }

    public void onStart() {
        super.onStart();
        HorizontalListView listview = (HorizontalListView) getActivity().findViewById(R.id.listview);

        listview.setAdapter(mAdapter);

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
       /* if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }*/
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
     /*   try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        } */
    }

    @Override
    public void onDetach() {
        super.onDetach();
        //  mListener = null;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        try {
            ((OnImageSelectedListener) getActivity()).onImageSelectedListener(position);
        } catch (ClassCastException cce) {
        }


    }
}
