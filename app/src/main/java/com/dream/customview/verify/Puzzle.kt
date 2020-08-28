package com.dream.customview.verify

import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.util.AttributeSet
import android.view.View
import com.dream.customview.R


/**
 * 参考链接
 * https://juejin.im/post/6844904149075787784#comment
 */

class Puzzle : View{

    private lateinit var bgPaint : Paint
    private lateinit var slideKPaint : Paint


    private lateinit var bgBitMapPaint : Paint

    private lateinit var pathPaint : Paint

    private lateinit var mPuzzlePaint : Paint

    private var mPuzzleDistance : Float = 0.0f


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
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val bd = context.getDrawable(R.drawable.huoying) as BitmapDrawable
        val bm = bd.bitmap
        val bitmap = getbackground(bm)

        canvas?.save()
        canvas?.drawBitmap(bitmap,0f,0f, bgBitMapPaint)
        canvas?.restore()

        canvas?.save()
        val puzzle = getPuzzleBitmap(bm)
        canvas?.drawBitmap(puzzle,-700f + mPuzzleDistance,0f,null)
        canvas?.restore()
    }

    fun setDistance(distance : Float){
        mPuzzleDistance = distance
        invalidate()
    }

    private fun initView(){
        bgPaint = Paint()

        slideKPaint = Paint()

        pathPaint = Paint()
        pathPaint.color = Color.RED

        bgBitMapPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        bgBitMapPaint.isFilterBitmap = true
        bgBitMapPaint.isDither = true

        mPuzzlePaint = Paint(Paint.ANTI_ALIAS_FLAG or Paint.DITHER_FLAG).apply {
            color = Color.WHITE
            maskFilter = BlurMaskFilter(5f, BlurMaskFilter.Blur.NORMAL)
        }
    }

    private fun getbackground(bitmap : Bitmap) : Bitmap{
        val newbitmap = Bitmap.createBitmap(measuredWidth,measuredHeight,Bitmap.Config.ARGB_8888)
        val canvas = Canvas(newbitmap)
        canvas.save()
        val src = Rect(0,0,measuredWidth,measuredWidth)
        val dst = RectF(0f,0f,measuredWidth.toFloat(),measuredHeight.toFloat())
        canvas.drawBitmap(bitmap,src,dst,null)
        canvas.restore()
        canvas.save()
        val path = Path()
        path.addCircle(800f, 600f, 100f, Path.Direction.CW)
        canvas.drawPath(path,pathPaint)
        return newbitmap
    }


    //滑块Bitmap
    private fun getPuzzleBitmap(bitmap: Bitmap) : Bitmap {
        val newBitmap = Bitmap.createBitmap(measuredWidth,measuredHeight,Bitmap.Config.ARGB_8888)
        val canvas = Canvas(newBitmap)
        canvas.save()
        val path = Path()
        path.addCircle(800f, 600f, 100f, Path.Direction.CW)
        canvas.drawPath(path,mPuzzlePaint)
        //利用PorterDuffXfermode混合出一个滑块。（但其实大小还是bitmap的大小只不过混合过后其他地方看不见了）
        mPuzzlePaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        val src = Rect(0,0,measuredWidth,measuredHeight)
        val dst = RectF(0f,0f,measuredWidth.toFloat(),measuredHeight.toFloat())
        canvas.drawBitmap(bitmap,src,dst,mPuzzlePaint)
        mPuzzlePaint.xfermode = null
        canvas.restore()
        val paint = Paint().apply {
            color = Color.WHITE
            style = Paint.Style.STROKE
            strokeWidth = 1f
        }
        canvas.drawPath(path,paint)
        return newBitmap
    }

}