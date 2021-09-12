package ir.mjahanbazi.customradiogroupusingdraw

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
        buttonGroup.setButtonHeight(60)
        buttonGroup.setOverallWidth(500)
        buttonGroup.setCornerRadius(15f)
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
                    addRule(RelativeLayout.CENTER_IN_PARENT)
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
