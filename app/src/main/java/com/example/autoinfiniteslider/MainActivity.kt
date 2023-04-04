package com.example.autoinfiniteslider

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import java.lang.Math.abs

class MainActivity : AppCompatActivity() {

    private lateinit var viewPager2:ViewPager2
    private lateinit var handler:Handler
    private lateinit var adapter: ImageAdapter
    private lateinit var imageList: ArrayList<Int>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
        setUpTransformer()
        viewPager2.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                handler.removeCallbacks(runnable) // item is selected removing the callback
                handler.postDelayed(runnable,2000)
            }
        })
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(runnable)
    }

    override fun onResume() {
        super.onResume()
        handler.postDelayed(runnable,2000)
    }

        private val runnable=Runnable{
            viewPager2.currentItem=viewPager2.currentItem+1 // INCREMENTING THE CUREENT ITEM

        }

    private fun setUpTransformer() {
        val transformer=CompositePageTransformer()
        transformer.addTransformer(MarginPageTransformer(40))
        transformer.addTransformer { page, position ->
            val r=1-abs(position)
            page.scaleY=0.85f+r*0.14f // scaling middle item
        }
        viewPager2.setPageTransformer(transformer)
    }

    private fun init(){
        viewPager2=findViewById(R.id.viewPager2)
        handler=Handler(Looper.myLooper()!!)

        imageList= ArrayList()

        imageList.add(R.drawable.lord_ganesha)
        imageList.add(R.drawable.lord_brahma)
        imageList.add(R.drawable.lord_vishnu)
        imageList.add(R.drawable.lord_shiva)
        imageList.add(R.drawable.lord_ram)
        imageList.add(R.drawable.lord_hanuman)
        imageList.add(R.drawable.lord_parshuram)
        imageList.add(R.drawable.maa_durga)
        imageList.add(R.drawable.maa_kali)
        imageList.add(R.drawable.karn)

        adapter=ImageAdapter(imageList,viewPager2)

        viewPager2.adapter=adapter
        viewPager2.offscreenPageLimit=3
        viewPager2.clipToPadding=false
        viewPager2.clipChildren=false
        viewPager2.getChildAt(0).overScrollMode=RecyclerView.OVER_SCROLL_NEVER


    }
}