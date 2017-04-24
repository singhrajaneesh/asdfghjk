package com.ekant.justbiz;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by ekant on 27/05/16.
 */
public class AdapterSearch extends BaseAdapter {
    private static LayoutInflater inflater=null;
    private Activity activity;
    public Context con;
    private ArrayList<HashMap<String, String>> data;

    public AdapterSearch(Activity a, ArrayList<HashMap<String, String>> d, Context context)
    {
        activity = a;
        data = d;
        con = context;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }
    @Override
    public int getCount() {
        return data.size();
    }

    /**
     * Get the data item associated with the specified position in the data set.
     *
     * @param position Position of the item whose data we want within the adapter's
     *                 data set.
     * @return The data at the specified position.
     */
    @Override
    public Object getItem(int position) {

            return position;
    }

    /**
     * Get the row id associated with the specified position in the list.
     *
     * @param position The position of the item within the adapter's data set whose row id we want.
     * @return The id of the item at the specified position.
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * Get a View that displays the data at the specified position in the data set. You can either
     * create a View manually or inflate it from an XML layout file. When the View is inflated, the
     * parent View (GridView, ListView...) will apply default layout parameters unless you use
     * {@link LayoutInflater#inflate(int, ViewGroup, boolean)}
     * to specify a root view and to prevent attachment to the root.
     *
     * @param position    The position of the item within the adapter's data set of the item whose view
     *                    we want.
     * @param convertView The old view to reuse, if possible. Note: You should check that this view
     *                    is non-null and of an appropriate type before using. If it is not possible to convert
     *                    this view to display the correct data, this method can create a new view.
     *                    Heterogeneous lists can specify their number of view types, so that this View is
     *                    always of the right type (see {@link #getViewTypeCount()} and
     *                    {@link #getItemViewType(int)}).
     * @param parent      The parent that this view will eventually be attached to
     * @return A View corresponding to the data at the specified position.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if (convertView == null) vi = inflater.inflate(R.layout.search_activity_listview, null);
        HashMap<String, String> Category = new HashMap<String, String>();
        Category =  data.get(position);
        TextView searchcategory = (TextView) vi.findViewById(R.id.search_category);
        searchcategory.setText(Category.get("Category"));

        TextView subcategory = (TextView) vi.findViewById(R.id.search_subcategory);
        subcategory.setText(Category.get("SubCategory"));
        TextView tags = (TextView) vi.findViewById(R.id.search_tags);
        tags.setText(Category.get("Vertical"));







        // ImageView backgroundimage = (ImageView) vi.findViewById(R.id.categoryimage);
        // backgroundimage.setImageResource(R.drawable.splash);
        // String backgroundurl = imageurl+Category.get("Image");
        // imageLoader.DisplayImage(backgroundurl, backgroundimage);
        return vi;
    }
}
