package com.example.user.prognoza;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gregor on 20.10.2015.
 */
public class ImageAdapter extends PagerAdapter {

    Context context;
//    private int[] GalImages = new int[] {
//            R.drawable.one,
//            R.drawable.two,
//            R.drawable.three
//    };

    String[] slike = new String[] {"http://vreme.kilogramz.com/web_uv10_HRv8_03.gif",
            "http://vreme.kilogramz.com/prognoza_veter_20151020_03.gif",
            "http://vreme.kilogramz.com/prognoza_veter_20151020_06.gif"};
    AsyncTask<String, Void, List<Bitmap>> slikeTask = new DownloadImageTask().execute(slike);

    ImageAdapter(Context context){
        this.context=context;

    }
    @Override
    public int getCount() {
        try {
            return slikeTask.get().size();
        } catch(Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((ImageView) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        ImageView imageView = new ImageView(context);
//        int padding = context.getResources().getDimensionPixelSize(R.dimen.activity_horizontal_margin);
//        imageView.setPadding(padding, padding, padding, padding);
        imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

        try {
                imageView.setImageBitmap(slikeTask.get().get(position));
        } catch(Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        ((ViewPager) container).addView(imageView, position);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((ImageView) object);
    }

    private class DownloadImageTask extends AsyncTask<String, Void, List<Bitmap>> {
        ImageView bmImage;

        public DownloadImageTask() {
        }

        @Override
        protected List<Bitmap> doInBackground(String... urls) {
//            String urldisplay = urls[0];
            List<Bitmap> mIcon11 = new ArrayList<Bitmap>();
            for(String urldisplay : urls)
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11.add(BitmapFactory.decodeStream(in));
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }
        @Override
        protected void onPostExecute(List<Bitmap> result) {
//            bmImage.setImageBitmap(result);
            System.out.println("st. slik: " + result.size());
        }
    }
}
