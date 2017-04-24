package com.ekant.justbiz;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.widget.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class ModImageLoader
{
        ModMemoryCache memoryCache=new ModMemoryCache();
        ModFileCache fileCache;
        private Map<ImageView, String> imageViews= Collections.synchronizedMap(new WeakHashMap<ImageView, String>());
        ExecutorService executorService;

        public ModImageLoader(Context context)
        {
        fileCache=new ModFileCache(context);
        executorService= Executors.newFixedThreadPool(5);
        }

        final int stub_id= R.drawable.logo;
        final int dashboard_news=R.drawable.news_dashboard;
        final int newsandevent=R.drawable.news_events;
        final int newsdetails=R.drawable.news_details;
        final int banner=R.drawable.banner_icon;
        final int logo1=R.drawable.contact;
        final int profile=R.drawable.banner_icon;


        public void DisplayImage(String url, ImageView imageView)
        {

        imageViews.put(imageView, url);
        Bitmap bitmap=memoryCache.get(url);

        if(bitmap!=null) {
            //Bitmap b = Bitmap.createScaledBitmap(bitmap, 90, 90, false);
            //Bitmap b1 = getRoundedCornerBitmap(b, 6);
            imageView.setImageBitmap(bitmap);
        }
        else
        {
        queuePhoto(url, imageView);
        imageView.setImageResource(stub_id);
        }
        }


    //banner MyProfile

    public void profile(String url, ImageView imageView)
    {

        imageViews.put(imageView, url);
        Bitmap bitmap=memoryCache.get(url);

        if(bitmap!=null) {
            //Bitmap b = Bitmap.createScaledBitmap(bitmap, 90, 90, false);
            //Bitmap b1 = getRoundedCornerBitmap(b, 6);
            imageView.setImageBitmap(bitmap);
        }
        else
        {
            queuePhoto(url, imageView);
            imageView.setImageResource(profile);
        }
    }


    // banner in uploadImage
    public void banner(String url, ImageView imageView)
    {

        imageViews.put(imageView, url);
        Bitmap bitmap=memoryCache.get(url);

        if(bitmap!=null) {
            //Bitmap b = Bitmap.createScaledBitmap(bitmap, 90, 90, false);
            //Bitmap b1 = getRoundedCornerBitmap(b, 6);
            imageView.setImageBitmap(bitmap);
        }
        else
        {
            queuePhoto(url, imageView);
            imageView.setImageResource(banner);
        }
    }

    // logo  in uploadImage
    public void logo(String url, ImageView imageView)
    {

        imageViews.put(imageView, url);
        Bitmap bitmap=memoryCache.get(url);

        if(bitmap!=null) {
            //Bitmap b = Bitmap.createScaledBitmap(bitmap, 90, 90, false);
            //Bitmap b1 = getRoundedCornerBitmap(b, 6);
            imageView.setImageBitmap(bitmap);
        }
        else
        {
            queuePhoto(url, imageView);
            imageView.setImageResource(logo1);
        }
    }

    // news and event section DashBoard

    public void newsDashboard(String url, ImageView imageView)
    {

        imageViews.put(imageView, url);
        Bitmap bitmap=memoryCache.get(url);

        if(bitmap!=null)
        {
            imageView.setImageBitmap(bitmap);
        }
        else
        {
            queuePhoto(url, imageView);
            imageView.setImageResource(dashboard_news);
        }


    }

// news and event
    public void newsandevent(String url, ImageView imageView)
    {

        imageViews.put(imageView, url);
        Bitmap bitmap=memoryCache.get(url);

        if(bitmap!=null) {
            imageView.setImageBitmap(bitmap);
        }
        else
        {
            queuePhoto(url, imageView);
            imageView.setImageResource(newsandevent);
        }
    }


    //newsreader
    public void newsreader(String url, ImageView imageView)
    {

        imageViews.put(imageView, url);
        Bitmap bitmap=memoryCache.get(url);

        if(bitmap!=null) {

            imageView.setImageBitmap(bitmap);
        }
        else
        {
            queuePhoto(url, imageView);
            imageView.setImageResource(newsdetails);
        }
    }



    private void queuePhoto(String url, ImageView imageView)
        {
        PhotoToLoad p=new PhotoToLoad(url, imageView);
        executorService.submit(new PhotosLoader(p));
        }

    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, int pixels)
    {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = pixels;

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }

        public Bitmap getBitmap(String url)
        {
            File f = fileCache.getFile(url);

            //from SD cache
            Bitmap b = decodeFile(f);
            if(b != null) {


                return b;
            }

        //from web
            try
            {
            Bitmap bitmap=null;
            URL imageUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection)imageUrl.openConnection();
            conn.setConnectTimeout(30000);
            conn.setReadTimeout(30000);
            conn.setInstanceFollowRedirects(true);
            InputStream is=conn.getInputStream();
            OutputStream os = new FileOutputStream(f);
            ModUtils.CopyStream(is, os);
            os.close();
            bitmap = decodeFile(f);

            return bitmap;
            }
            catch (Throwable ex)
            {
                ex.printStackTrace();
            if(ex instanceof OutOfMemoryError)
            memoryCache.clear();
            return null;
            }
        }

private Bitmap decodeFile(File f)
        {
        try {

        BitmapFactory.Options o2 = new BitmapFactory.Options();
        return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
        } catch (FileNotFoundException e) {
        }
        return null;
        }

//Task for the queue
private class PhotoToLoad
{
    public String url;
    public ImageView imageView;
    public PhotoToLoad(String u, ImageView i){
        url=u;
        imageView=i;
    }
}

class PhotosLoader implements Runnable {
    PhotoToLoad photoToLoad;
    PhotosLoader(PhotoToLoad photoToLoad){
        this.photoToLoad=photoToLoad;
    }

    @Override
    public void run() {
        if(imageViewReused(photoToLoad))
            return;
        Bitmap bmp=getBitmap(photoToLoad.url);
        memoryCache.put(photoToLoad.url, bmp);
        if(imageViewReused(photoToLoad))
            return;
        BitmapDisplayer bd=new BitmapDisplayer(bmp, photoToLoad);
        Activity a=(Activity)photoToLoad.imageView.getContext();
        a.runOnUiThread(bd);
    }
}

    boolean imageViewReused(PhotoToLoad photoToLoad){
        String tag=imageViews.get(photoToLoad.imageView);
        if(tag==null || !tag.equals(photoToLoad.url))
            return true;
        return false;
    }

//Used to display bitmap in the UI thread
class BitmapDisplayer implements Runnable
{
    Bitmap bitmap;
    PhotoToLoad photoToLoad;
    public BitmapDisplayer(Bitmap b, PhotoToLoad p){bitmap=b;photoToLoad=p;}
    public void run()
    {
        if(imageViewReused(photoToLoad))
            return;
        if(bitmap!=null) {
            //Bitmap b = Bitmap.createScaledBitmap(bitmap, 90, 90, false);
            //Bitmap b1 = getRoundedCornerBitmap(b, 6);
            photoToLoad.imageView.setImageBitmap(bitmap);
        }
        else
            photoToLoad.imageView.setImageResource(profile);
    }
}

    public void clearCache() {
        memoryCache.clear();
        fileCache.clear();
    }


}
