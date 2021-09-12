package ir.mjahanbazi.customradiogroupusingdraw


import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.Rect
import android.text.TextPaint
import android.text.TextUtils
import android.widget.RadioGroup
import android.widget.RadioGroup.OnCheckedChangeListener
import androidx.appcompat.widget.AppCompatRadioButton
import ir.mjahanbazi.customradiogroupusingdraw.R

class CustomRadioButton : AppCompatRadioButton, OnCheckedChangeListener {
    private  var label: String
    private var cornerRadiusTopLeft: Float = 0f
    private var cornerRadiusTopRight: Float = 0f
    private var cornerRadiusBottomRight: Float = 0f
    private var cornerRadiusBottomLeft: Float = 0f
    private var paintText: TextPaint = object : TextPaint() {
        init {
            style = Style.FILL
            color = getContext().resources.getColor(R.color.button_text, null)
            isFakeBoldText = true
            textAlign = Align.LEFT
            isAntiAlias = true
            textSize = 30f
        }
    }
    private var paintActive: Paint = object : Paint() {
        init {
            color = getContext().resources.getColor(R.color.button_active_fill, null)
            style = Style.FILL
            isAntiAlias = true
        }
    }
    private var paintInactive: Paint = object : Paint() {
        init {
            color = getContext().resources.getColor(R.color.button_inactive_fill, null)
            style = Style.FILL
            isAntiAlias = true
        }
    }
    private var bounds: Rect? = null

    constructor(
        context: Context,
        cornerRadiusTopLeft: Float,
        cornerRadiusTopRight: Float,
        cornerRadiusBottomLeft: Float,
        cornerRadiusBottomRight: Float,
        label: String
    ) : super(context) {
        this.cornerRadiusTopLeft = cornerRadiusTopLeft
        this.cornerRadiusTopRight = cornerRadiusTopRight
        this.cornerRadiusBottomRight = cornerRadiusBottomRight
        this.cornerRadiusBottomLeft = cornerRadiusBottomLeft
        this.label = label
        setWillNotDraw(false)
        bounds = Rect()
    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (cornerRadiusBottomLeft != 0f && cornerRadiusTopLeft != 0f) {
            //first button
            val path = object : Path() {
                init {
                    //top left
                    arcTo(
                        0f,
                        0f,
                        cornerRadiusTopLeft,
                        cornerRadiusTopLeft,
                        180f,
                        90f,
                        false
                    )
                    lineTo(width.toFloat(), 0f)
                    lineTo(width.toFloat(), height.toFloat())
                    lineTo(cornerRadiusTopLeft, height.toFloat())
                    //bottom left
                    arcTo(
                        0f,
                        height.toFloat() - cornerRadiusTopLeft,
                        cornerRadiusTopLeft,
                        height.toFloat(),
                        90f,
                        90f,
                        false
                    )
                }
            }
            if (isChecked) {
                canvas!!.drawPath(path, paintActive)
            } else {
                canvas!!.drawPath(path, paintInactive)
            }
        } else if (cornerRadiusBottomRight != 0f && cornerRadiusTopRight != 0f) {
            //last button
            val path = object : Path() {
                init {
                    //top right
                    arcTo(
                        (width - cornerRadiusBottomRight).toFloat(),
                        0f,
                        width.toFloat(),
                        cornerRadiusBottomRight.toFloat(),
                        270f, 90f, false
                    )
                    //bottom right
                    arcTo(
                        (width - cornerRadiusBottomRight).toFloat(),
                        (height - cornerRadiusBottomRight).toFloat(),
                        width.toFloat(),
                        height.toFloat(),
                        0f,
                        90f,
                        false
                    )
                    lineTo(0f, height.toFloat())
                    lineTo(0f, 0f)
                    lineTo((width - cornerRadiusBottomRight).toFloat(), 0f)
                }
            }
            if (isChecked) {
                canvas!!.drawPath(path, paintActive)
            } else {
                canvas!!.drawPath(path, paintInactive)
            }
        } else {
            //others
            if (isChecked) {
                canvas!!.drawRect(
                    0f,
                    0f,
                    width.toFloat(),
                    height.toFloat(),
                    paintActive
                )
            } else {
                canvas!!.drawRect(
                    0f,
                    0f,
                    width.toFloat(),
                    height.toFloat(),
                    paintInactive
                )
            }
        }
        val ellipsizedText = TextUtils.ellipsize(
            label.toString(),
            paintText,
            (width - 0.3 * width).toFloat(),
            TextUtils.TruncateAt.END
        ) as String
        paintText.getTextBounds(ellipsizedText, 0, ellipsizedText.length, bounds)
        val x = width / 2f - bounds!!.width() / 2f - bounds!!.left
        val y = height / 2f + bounds!!.height() / 2f - bounds!!.bottom
        canvas.drawText(ellipsizedText, x, y, paintText)
    }

    override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
        invalidate()
    }
}
