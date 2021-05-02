package co.ghostnotes.sample.arcprogress

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.core.view.get
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import co.ghostnotes.sample.arcprogress.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = bindView()

        binding.slider.addOnChangeListener { _, value, _ ->
            binding.arcProgress.setPercent((value * 100).toInt())
        }
    }
}

fun <T : ViewDataBinding> AppCompatActivity.bindView(): T =
    DataBindingUtil.bind(getContentView())!!

private fun Activity.getContentView(): View =
    findViewById<ViewGroup>(android.R.id.content)[0]