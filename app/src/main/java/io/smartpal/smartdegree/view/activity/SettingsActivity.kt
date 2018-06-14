package io.smartpal.smartdegree.view.activity

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.AppCompatEditText
import io.smartpal.smartdegree.R
import io.smartpal.smartdegree.helpers.Prefs

import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        setupToolbar()
        setupFab()
    }


    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setupFab(){
        fab.setOnClickListener { view ->
            Prefs(baseContext).endpoint = findViewById<AppCompatEditText>(R.id.endpoint).text.toString()
            Snackbar.make(view, getString(R.string.endpoint_saved_message), Snackbar.LENGTH_LONG).show()
        }
    }
}
