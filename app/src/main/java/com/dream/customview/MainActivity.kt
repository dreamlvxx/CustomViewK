package com.dream.customview

import android.animation.ValueAnimator
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.dream.customview.verify.SlideView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        slide.setListener(object : SlideView.DragListener {
            override fun drag(distance: Float) {
                Log.e("xxx", "drag: $distance")
                puzzle.setDistance(distance)
            }
        })

        val valueni = ValueAnimator.ofFloat(0f,100f).apply {
            duration = 500
            addUpdateListener {
                Log.e("xxx", "onCreate: value = " + it.animatedValue)
            }
            start()
        }
    }
}