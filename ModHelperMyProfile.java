package com.ekant.justbiz;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
public class ModHelperMyProfile
{
    public static void getListViewSize(ListView myListView, int value)
    {

        if(value == 1)
        {
            AdapterSaveTagsMyprofile myListAdapter = (AdapterSaveTagsMyprofile) myListView.getAdapter();
            if (myListAdapter == null) {
                return;
            }

            int totalHeight = 0;

            for (int size = 0; size < myListAdapter.getCount(); size++)
            {
                View listItem = myListAdapter.getView(size, null, myListView);
                listItem.measure(0, 0);
                totalHeight += listItem.getMeasuredHeight()+60;
            }
            ViewGroup.LayoutParams params = myListView.getLayoutParams();
            params.height = totalHeight + (myListView.getDividerHeight() * (myListAdapter.getCount()));
            myListView.setLayoutParams(params);
        }
        else if(value == 2)
        {
            AdapterSaveTagsMyprofile myListAdapter1 = (AdapterSaveTagsMyprofile) myListView.getAdapter();
            if (myListAdapter1 == null)
            {
                System.out.println("totalHeight:"+"0");
                return;
            }

            int totalHeight = 0;
            for (int size = 0; size < myListAdapter1.getCount() ; size++)
            {
                View listItem = myListAdapter1.getView(size, null, myListView);
                listItem.measure(0, 0);
                totalHeight += listItem.getMeasuredHeight()+100


                ;

            }
            System.out.println("totalHeight:"+totalHeight);
            ViewGroup.LayoutParams params = myListView.getLayoutParams();
            params.height =  totalHeight + (myListView.getDividerHeight() * (myListAdapter1.getCount()-1));
            myListView.setLayoutParams(params);
        }
        else if(value == 3)
        {
            AdapterFriendGetTags myListAdapter1 = (AdapterFriendGetTags) myListView.getAdapter();
            if (myListAdapter1 == null)
            {
                return;
            }

            int totalHeight = 0;

            for (int size = 0; size < myListAdapter1.getCount() ; size++)
            {
                View listItem = myListAdapter1.getView(size, null, myListView);
                listItem.measure(0, 0);
                totalHeight += listItem.getMeasuredHeight()+40;

            }
            ViewGroup.LayoutParams params = myListView.getLayoutParams();
            params.height =  totalHeight + (myListView.getDividerHeight() * (myListAdapter1.getCount()-1));
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
