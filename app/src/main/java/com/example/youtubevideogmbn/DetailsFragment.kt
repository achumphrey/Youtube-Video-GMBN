package com.example.youtubevideogmbn

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.youtubevideogmbn.data.model.Item
import com.example.youtubevideogmbn.databinding.FragmentDetailsBinding
import com.google.gson.Gson
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId
import java.util.*
import java.util.concurrent.TimeUnit

class DetailsFragment : Fragment() {

    private var detailObjString: String? = ""
    private lateinit var videoItem: Item
    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // This will be null when the app is first launched.
        if (savedInstanceState != null) {
            detailObjString = savedInstanceState.getString("detailObj")
        }
    }

    // To preserve the id when orientation changes, this method will
    // be called after onPause() and before onDestroy(), in order
    // to save the current InstantState.
    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        savedInstanceState.putString("detailObj", detailObjString)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_details,
            container,
            false
        )

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        displayDetails()
    }

    //Calculate number of days
    private fun calculateNumOfDays(date: String?): String {
        val pattern = "yyyy-MM-dd"
        val sdf: DateFormat = SimpleDateFormat(pattern, Locale.UK)

        val z: ZoneId = ZoneId.of("Europe/London")
        val today: LocalDate = LocalDate.now(z)
        val currentDateValue: Date? = sdf.parse("$today")

        val startDateValue: Date? = date?.let { sdf.parse(date) } ?: currentDateValue

        val diff: Long = currentDateValue!!.time - (startDateValue!!.time)
        val numDays = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS)

        return numDays.toString()
    }

    // This will set the object on the list or index
    // to retrieve the object instance.
    fun setDetailObjString(objString: String) {
        this.detailObjString = objString
        val gson = Gson()
        this.videoItem = gson.fromJson(detailObjString, Item::class.java)
    }

    private fun displayDetails() {
        binding.videoObj = videoItem
        val duration: String = calculateNumOfDays(videoItem.snippet.publishedAt)
        //    binding.durationValue = getString(R.string.duration_value, duration)
        binding.durationValue = String.format("%s days", duration)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}