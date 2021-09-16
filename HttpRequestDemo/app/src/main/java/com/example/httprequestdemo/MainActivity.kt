package com.example.httprequestdemo

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.httprequestdemo.databinding.ActivityMainBinding
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.net.HttpURLConnection
import java.net.URL


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var disposable:CompositeDisposable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        disposable= CompositeDisposable()

        binding.fetchBtn.setOnClickListener{
            val url = binding.textURL.text.toString()
            if(!url.isNullOrEmpty()){
                createObservable(url)
                    .subscribeOn(Schedulers.computation())   // UpStream
                    .observeOn(AndroidSchedulers.mainThread()) // DownStream
                    .subscribe(createObserver())
            }
        }



    }

    private fun createObservable(urlPath:String): Single<Bitmap>{
       return Single.create<Bitmap> {
            // 1. Declare a URL Connection
            val url = URL(urlPath)
            val conn = url.openConnection() as HttpURLConnection
            // 2. Open InputStream to connection
            conn.connect()
            val stream = conn.inputStream
            // 3. Download and decode the bitmap using BitmapFactory
            val bitmap = BitmapFactory.decodeStream(stream)
            stream.close()
            // 4. Send the bitmap to the observer if Success
            it.onSuccess(bitmap)

        }
    }

    private fun createObserver(): SingleObserver<Bitmap> {

       return object : SingleObserver<Bitmap> {
            override fun onSubscribe(d: Disposable) {
                Log.d(TAG, "onSubscribe")
                disposable.add(d)
            }

            override fun onSuccess(t: Bitmap) {
                Log.d(TAG, "onSuccess")
                binding.imageView.setImageBitmap(t)
                disposable.clear()

            }

            override fun onError(e: Throwable) {
                Log.d(TAG, "onError"+e.toString())
                Toast.makeText(applicationContext,"Something wrong has happened",Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}

















