package com.example.unlockpattern

import android.content.Context
import android.util.AttributeSet
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.MotionEvent
import android.view.View

class PatternView(context: Context, attrs: AttributeSet?) : View(context, attrs) {

    var onPatternCompleted: ((List<Int>) -> Unit)? = null //for saving pattern as a list

    private val paint = Paint() //for drawing the points
    private val linePaint = Paint() //for drawing the lines

    private val circles = mutableListOf<Circle>()
    private val pattern = mutableListOf<Int>() // list of pattern id-s
    private val patternPoints = mutableListOf<Pair<Float, Float>>() //list of patterns cordinates

    private var currentX: Float = 0f // present cordinates x
    private var currentY: Float = 0f // present cordinates y
    private var isTouching: Boolean = false // is finger on screen?

    init { // style
        setBackgroundColor(Color.WHITE)
        paint.style = Paint.Style.FILL
        paint.color = Color.BLACK

        linePaint.style = Paint.Style.STROKE
        linePaint.color = Color.BLUE
        linePaint.strokeWidth = 5f
    }
    // if screen measurments are changing then, doing 3x3 and then putting points to the middle
    override fun onSizeChanged(width: Int, height: Int, oldWidth: Int, oldHeight: Int) {
        super.onSizeChanged(width, height, oldWidth, oldHeight)
        circles.clear()

        val cellWidth = width / 3
        val cellHeight = height / 3

        var id = 1
        for (row in 0 until 3) {
            for (col in 0 until 3) {
                val x = (col * cellWidth + cellWidth / 2).toFloat()
                val y = (row * cellHeight + cellHeight / 2).toFloat()
                circles.add(Circle(x, y, 50f, id))
                id++
            }
        }
    }
    //
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (patternPoints.size >= 1) { // at least 1 point?
            // going throgh writing the lines in x y start end pairs
            for (i in 0 until patternPoints.size - 1) {
                val (startX, startY) = patternPoints[i]
                val (endX, endY) = patternPoints[i + 1]
                canvas.drawLine(startX, startY, endX, endY, linePaint)
            }
            // line from last point to present location
            if (isTouching) {
                val (lastX, lastY) = patternPoints[patternPoints.size - 1]
                canvas.drawLine(lastX, lastY, currentX, currentY, linePaint)
            }
        }
        // drawing the circles
        for (circle in circles) {
            canvas.drawCircle(circle.x, circle.y, circle.radius, paint)
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        currentX = event.x
        currentY = event.y

        when (event.action) {
            // putting finger on -> starting position
            MotionEvent.ACTION_DOWN -> {
                isTouching = true
                handleTouch(currentX, currentY)
            }
            // moving
            MotionEvent.ACTION_MOVE -> {
                isTouching = true
                handleTouch(currentX, currentY)
            }
            // taking finger off -> stopping
            MotionEvent.ACTION_UP -> {
                isTouching = false
                onPatternCompleted?.invoke(pattern.toList())
                pattern.clear()
                patternPoints.clear()
                invalidate()
            }
        }
        invalidate() // updating after every touch
        return true
    }
    // checking is finger on some point (checking all the points) and adding if it is
    private fun handleTouch(touchX: Float, touchY: Float) {
        for (circle in circles) {
            if (circle.contains(touchX, touchY) && !pattern.contains(circle.id)) {
                pattern.add(circle.id)
                patternPoints.add(Pair(circle.x, circle.y))
                break
            }
        }
    }

    // checking is finger on circle
    data class Circle(val x: Float, val y: Float, val radius: Float, val id: Int) {
        fun contains(touchX: Float, touchY: Float): Boolean {
            val dx = x - touchX
            val dy = y - touchY
            return dx * dx + dy * dy <= radius * radius
        }
    }
}
