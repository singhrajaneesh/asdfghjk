package com.ekant.justbiz;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
public class ModHelper
{
    public static void getListViewSize(ListView myListView,int value)
    {
        if(value == 1)
        {
            AdapterSaveTags myListAdapter = (AdapterSaveTags) myListView.getAdapter();
            if (myListAdapter == null) {
                return;
            }

            int totalHeight = 0;
            for (int size = 0; size < myListAdapter.getCount(); size++)
            {
                View listItem = myListAdapter.getView(size, null, myListView);
                listItem.measure(0, 0);
                totalHeight += listItem.getMeasuredHeight();

            }
            ViewGroup.LayoutParams params = myListView.getLayoutParams();
            params.height = totalHeight + (myListView.getDividerHeight() * (myListAdapter.getCount() - 1));
            myListView.setLayoutParams(params);
        }
        else if(value == 4)
        {
            AdapterSaveGiveTags myListAdapter = (AdapterSaveGiveTags) myListView.getAdapter();
            if (myListAdapter == null)
            {
                return;
            }

            int totalHeight = 0;
            for (int size = 0; size < myListAdapter.getCount(); size++)
            {
                View listItem = myListAdapter.getView(size, null, myListView);
                listItem.measure(0, 0);
                totalHeight += listItem.getMeasuredHeight()+20;
            }
            ViewGroup.LayoutParams params = myListView.getLayoutParams();
            params.height = totalHeight + (myListView.getDividerHeight() * (myListAdapter.getCount() - 1));
            myListView.setLayoutParams(params);
        }
        else
        {
            AdapterLiveMessage myListAdapter = (AdapterLiveMessage) myListView.getAdapter();
            if (myListAdapter == null) {
                return;
            }

            int totalHeight = 0;
            for (int size = 0; size < myListAdapter.getCount(); size++)
            {
                View listItem = myListAdapter.getView(size, null, myListView);
                listItem.measure(0, 0);
                totalHeight += listItem.getMeasuredHeight();

            }


            ViewGroup.LayoutParams params = myListView.getLayoutParams();
            params.height = totalHeight + (myListView.getDividerHeight() * (myListAdapter.getCount() - 1));
            myListView.setLayoutParams(params);
        }


    }



}
