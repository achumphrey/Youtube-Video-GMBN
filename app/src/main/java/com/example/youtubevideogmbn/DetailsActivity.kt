package com.example.youtubevideogmbn

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.youtubevideogmbn.MainActivity.Companion.INTENT_MESSAGE

class DetailsActivity : AppCompatActivity() {

    private var detailObjString: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_activity)

        title = "Video Detail"

        detailObjString = intent.getStringExtra(INTENT_MESSAGE)

        initializeFragment()

    }

    fun initializeFragment(){

        // The fragment instance is created by retrieving the reference from
        // the layout's Fragment tag using the FragmentManager. Same can be
        // achieved by calling the constructor of the fragment if this option
        // was not available.
        val fragmentManager = supportFragmentManager
        val newDetailsFrag: DetailsFragment? = fragmentManager
            .findFragmentById(R.id.fragDetails) as DetailsFragment?

        if (newDetailsFrag != null) detailObjString?.let { newDetailsFrag.setDetailObjString (it) }
    }
}
