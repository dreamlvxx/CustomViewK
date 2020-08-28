package com.dream.customview.verify

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class SlideView : View{

    private  var listener : DragListener? = null

    private lateinit var backgroudRect : RectF
    private lateinit var backgroundPaint : Paint
    private lateinit var slideK : RectF
    private lateinit var slidKPaint : Paint

    private var slideKDIstance = 0f

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

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        backgroudRect.set(0f,measuredHeight.toFloat(),measuredWidth.toFloat(),0f)
        slideK.set(slideKDIstance,0f,slideKDIstance + 100f,measuredHeight.toFloat())
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawRect(backgroudRect,backgroundPaint)
        canvas?.drawRect(slideK,slidKPaint)
    }

    private fun initView(){
        backgroundPaint = Paint()
        backgroundPaint.color = Color.RED
        backgroundPaint.style = Paint.Style.FILL

        backgroudRect = RectF()

        slideK = RectF()

        slidKPaint = Paint()
        slidKPaint.color = Color.BLUE
        slidKPaint.style = Paint.Style.FILL
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when(event?.action){
            MotionEvent.ACTION_DOWN ->{

            }
            MotionEvent.ACTION_MOVE ->{
                slideKDIstance = event.x
                slideK.set(slideKDIstance,height.toFloat(),slideKDIstance + 100,0f)
                listener?.drag(slideKDIstance)
                invalidate()
            }
            MotionEvent.ACTION_UP ->{

            }
        }
        return true
    }

    interface DragListener{
        fun drag(distance : Float)
    }

    fun setListener(listener : DragListener){
        this.listener = listener
    }

}