package io.smartpal.smartdegree.view.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.support.v4.widget.NestedScrollView
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import io.smartpal.smartdegree.R
import io.smartpal.smartdegree.view.fragment.CheckFragment
import io.smartpal.smartdegree.view.fragment.ScanFragment
import io.smartpal.smartdegree.view.fragment.StatusFragment
import com.shuhart.stepview.StepView

class MainActivity : AppCompatActivity(), View.OnTouchListener, ViewPager.OnPageChangeListener {

    private lateinit var stepView: StepView
    private lateinit var mSectionsPagerAdapter: SectionsPagerAdapter
    private lateinit var mViewPager: ViewPager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupToolbar()
        setupContentView()
        setupStepView()


    }

    private fun setupContentView() {
        findViewById<NestedScrollView>(R.id.nested_scroll_view).isFillViewport = true
    }

    private fun setupToolbar() {
        setSupportActionBar(findViewById(R.id.toolbar))
    }

    private fun setupStepView() {
        stepView = findViewById(R.id.step_view)
        mViewPager = findViewById(R.id.container)

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager)

        // Set up the ViewPager with the sections adapter.
        mViewPager.adapter = mSectionsPagerAdapter
        mViewPager.addOnPageChangeListener(this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_navigation, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                startActivity(Intent(this,SettingsActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    /**
     * A [FragmentPagerAdapter] that returns a fragment corresponding to
     * one of the tabs.
     */
    inner class SectionsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): Fragment {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).


            return when (position) {
                0 ->  ScanFragment.newInstance()
                1 -> CheckFragment.newInstance()
                2 -> StatusFragment.newInstance()
                else -> CheckFragment.newInstance()
            }
        }

        override fun getCount(): Int {
            // Show 3 total pages.
            return 3
        }
    }

    fun firstItem() {
        mViewPager.currentItem = 0
    }

    fun nextItem() {
        mViewPager.currentItem = mViewPager.currentItem + 1
    }


    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        stepView.go(position, true)
    }

    override fun onPageScrollStateChanged(state: Int) = Unit

    override fun onPageSelected(position: Int) = Unit

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouch(view: View?, p1: MotionEvent?): Boolean {
       return true
    }


}