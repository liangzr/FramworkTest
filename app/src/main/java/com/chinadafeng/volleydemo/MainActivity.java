package com.chinadafeng.volleydemo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.LruCache;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EventBus.getDefault().register(this);

        ImageView imageView = (ImageView) this.findViewById(R.id.image);

        RequestQueue requestQueue = Volley.newRequestQueue(getApplication());
        GsonRequest<Person> gsonRequest = new GsonRequest<Person>("http://liangzr.me/test", Person.class,
                new Response.Listener<Person>() {
                    @Override
                    public void onResponse(Person response) {
                        Log.d(TAG, "Name : " + response.getName());
                        Log.d(TAG, "ID : " + response.getId());
                        Log.d(TAG, "Gender : " + response.getGender());
                        Log.d(TAG, "IPAddress : " + response.getIpAddress());
                        Log.d(TAG, "Email : " + response.getEmail());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, error.getMessage(), error);
                    }
                });
        requestQueue.add(gsonRequest);

        ImageLoader imageLoader = new ImageLoader(requestQueue, new MyImageCache());
        ImageLoader.ImageListener listener = ImageLoader.getImageListener(imageView, R.mipmap.ic_launcher, R.mipmap.ic_launcher);
        imageLoader.get("https://avatars3.githubusercontent.com/u/3992942?v=3&s=460", listener, 800, 800);

        assert imageView != null;
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SecondActivity.class));
            }
        });

    }

    public class MyImageCache implements ImageLoader.ImageCache {

        private LruCache<String, Bitmap> mCache;

        public MyImageCache() {
            int maxSize = 8 * 1024 * 1024;
            mCache = new LruCache<String, Bitmap>(maxSize) {
                @Override
                protected int sizeOf(String key, Bitmap value) {
                    return value.getRowBytes() * value.getHeight();
                }
            };
        }

        @Override
        public Bitmap getBitmap(String url) {
            return mCache.get(url);
        }

        @Override
        public void putBitmap(String url, Bitmap bitmap) {
            mCache.put(url, bitmap);
        }
    }

    @Subscribe
    public void onEventBackground(TestEvent event) {
        Toast.makeText(this, "我是" + this.toString() +event.getName(), Toast.LENGTH_LONG).show();
    }

    @Subscribe
    public void onEventBackground(String s) {
        Toast.makeText(this, "我是" + this.toString() + "\n收到的字符串是：" +s, Toast.LENGTH_LONG).show();
    }

    @Subscribe
    public void onEventMainThread(Integer i) {
        switch (i) {
            case 0:
                Toast.makeText(this,"厉害厉害，这和handle差不多了啊",Toast.LENGTH_LONG).show();
                break;
            case 1:
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
