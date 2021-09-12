package ir.mjahanbazi.customradiogroupusingdraw


import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.widget.RadioGroup

open class CustomRadioGroup : RadioGroup {
    private var radioGroupBackground: Int = 0
    private var overallWidth: Int = 0
    private var buttonHeight: Int = 0
    private var cornerRadius: Float = 0f
    private var dividerWidth: Int = 0
    private var paint: Paint = object : Paint() {
        init {
            style = Style.FILL
            isAntiAlias = true
        }
    }

    constructor(context: Context?) : super(context) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    private fun init() {
        setWillNotDraw(false)
        orientation = HORIZONTAL
        setDividerWidth(3)
    }

    fun setCornerRadius(cornerRadius: Float) {
        this.cornerRadius = cornerRadius
        val padding = cornerRadius.toInt()
        setPadding(padding, padding, padding, padding)
    }

    fun setRadioGroupBgColor(color: Int) {
        this.radioGroupBackground = color
    }

    fun setDividerWidth(dividerWidth: Int) {
        this.dividerWidth = dividerWidth
    }

    fun setButtonHeight(buttonHeight: Int) {
        this.buttonHeight = buttonHeight
    }

    fun setOverallWidth(overallWidth: Int) {
        this.overallWidth = overallWidth
    }

    private var buttonList: ArrayList<String> = ArrayList()

    fun addButton(buttonLabel: String) {
        if (childCount > 0) {
            removeAllViews()
        }
        buttonList.add(buttonLabel)
        val buttonWidth = overallWidth / buttonList.size
        var counter = 0
        while (counter < buttonList.size) {
            if (counter == 0) {
                val button = CustomRadioButton(
                    context,
                    cornerRadius,
                    0f,
                    cornerRadius,
                    0f,
                    buttonList.get(counter)
                )
                addView(button, object : LayoutParams(buttonWidth, buttonHeight) {
                    init {
                        setMargins(dividerWidth, dividerWidth, dividerWidth, dividerWidth)
                    }
                })
            } else if (counter == buttonList.size - 1) {
                val button = CustomRadioButton(
                    context,
                    0f,
                    cornerRadius,
                    0f,
                    cornerRadius,
                    buttonList.get(counter)
                )
                addView(button, object : LayoutParams(buttonWidth, buttonHeight) {
                    init {
                        setMargins(0, dividerWidth, dividerWidth, dividerWidth)
                    }
                })
            } else {
                val button = CustomRadioButton(
                    context,
                    0f,
                    0f,
                    0f,
                    0f,
                    buttonList.get(counter)
                )
                addView(button, object : LayoutParams(buttonWidth, buttonHeight) {
                    init {
                        setMargins(0, dividerWidth, dividerWidth, dividerWidth)
                    }
                })
            }
            counter++
        }
    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        paint.color = radioGroupBackground
        val path = object : Path() {
            init {
                //top left
                arcTo(
                    cornerRadius,
                    cornerRadius,
                    cornerRadius * 2,
                    cornerRadius * 2,
                    180f,
                    90f,
                    false
                )
                //top right
                arcTo(
                    width - 2 * cornerRadius,
                    cornerRadius,
                    width - cornerRadius,
                    cornerRadius * 2,
                    270f,
                    90f,
                    false
                )
                //bootom right
                arcTo(
                    width - 2 * cornerRadius,
                    height - cornerRadius * 2,
                    width - cornerRadius,
                    height - cornerRadius,
                    0f,
                    90f,
                    false
                )
                //bottom left
                arcTo(
                    cornerRadius,
                    height - cornerRadius * 2,
                    cornerRadius * 2,
                    height - cornerRadius,
                    90f,
                    90f,
                    false
                )
            }
        }
        canvas!!.drawPath(path, paint)
    }
}
