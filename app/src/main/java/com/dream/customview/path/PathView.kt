package com.dream.customview.path

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import kotlin.properties.Delegates


/**
 * https://www.jianshu.com/p/766584c0aeee
 */
class PathView : View {

    val paint = Paint().apply {
        color = Color.RED
        style = Paint.Style.STROKE
        strokeWidth = 10f
    }
    val path = Path()
    lateinit var newPath : Path
    lateinit var pathMeasure : PathMeasure
    var mLength by Delegates.notNull<Float>()

    constructor(context: Context?) : super(context){
        initView()
    }
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs){
        initView()
    }
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ){
        initView()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        newPath.reset()
        val start: Float
        val stop: Float
        if (animValue <= 1) {
            start = 0f
            stop = mLength * animValue
        } else {
            start = (animValue - 1) * mLength
            stop = mLength
        }
        pathMeasure.getSegment(start, stop, newPath, true)
        canvas?.drawPath(newPath,paint)
    }


    var animValue by Delegates.notNull<Float>()

    fun anim(){
        val valueAnim = ValueAnimator.ofFloat(0f,2f).apply {
            duration = 3000
            addUpdateListener {
                animValue = it.animatedValue as Float
                postInvalidate()
            }
            repeatCount = ValueAnimator.INFINITE
            start()
        }

    }

    fun initView(){
        newPath = Path()
        path.addCircle(500f,500f,100f,Path.Direction.CW)

        pathMeasure = PathMeasure(path,false)
        anim()
        mLength = pathMeasure.length
    }

}