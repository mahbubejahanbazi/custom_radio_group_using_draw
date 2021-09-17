# Custom RadioGroup with Kotlin
A custom RadioGroup class with modification in :
- Background color
- Text color
- Text Size
- Button height
- Button width
- Overall width of component
- Default color of button
- Checked color of button
- Button corner rdaius

## Custom RadioGroup using draw path
## Tech Stack

Kotlin

<p align="center">
  <img src="https://github.com/mahbubejahanbazi/custom_radio_group_using_draw/blob/main/images/default.jpg" />
</p>



<p align="center">
  <img src="https://github.com/mahbubejahanbazi/custom_radio_group_using_draw/blob/main/images/checked.jpg" />
</p>

## Source code

CustomRadioButton.kt
```kotlin
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
            textSize = 50f
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
```
CustomRadioGroup.kt
```kotlin
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
```
MainActivity.kt
```kotlin
import android.os.Build
import android.os.Bundle
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val root: RelativeLayout = findViewById(R.id.main_activity_root);
        val buttonGroup = CustomRadioGroup(this)
        buttonGroup.setButtonHeight(160)
        buttonGroup.setOverallWidth(700)
        buttonGroup.setCornerRadius(15f)
        buttonGroup.setDividerWidth(8)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            buttonGroup.setRadioGroupBgColor(resources.getColor(R.color.bg_radio_group,null))
        }else {
          buttonGroup.setRadioGroupBgColor(resources.getColor(R.color.bg_radio_group))
        }
        buttonGroup.addButton("Saturday")
        buttonGroup.addButton("Sunday")
        buttonGroup.addButton("Monday")
        buttonGroup.addButton("Tuesday")
        buttonGroup.id = ViewCompat.generateViewId()
        root.addView(
            buttonGroup,
            object : RelativeLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT) {
                init {
                    addRule(RelativeLayout.ALIGN_PARENT_TOP)
                    addRule(RelativeLayout.CENTER_HORIZONTAL)
                    topMargin=150
                }
            }
        )
        buttonGroup.setOnCheckedChangeListener { group, checkedId ->
            val radioButton = group.findViewById(checkedId) as CustomRadioButton
            val index = group.indexOfChild(radioButton)
            when (index) {
                0 -> {
                    Toast.makeText(this, "Saturday", Toast.LENGTH_SHORT).show()
                }
                1 -> {
                    Toast.makeText(this, "Sunday", Toast.LENGTH_SHORT).show()
                }
                2 -> {
                    Toast.makeText(this, "Monday", Toast.LENGTH_SHORT).show()
                }
                3 -> {
                    Toast.makeText(this, "Tuesday", Toast.LENGTH_SHORT).show()
                }
                else -> {
                }

            }
        }
    }
}
```
## Contact
mjahanbazi@protonmail.com