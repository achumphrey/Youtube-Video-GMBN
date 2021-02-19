package com.example.youtubevideogmbn

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.youtubevideogmbn.data.model.Item
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId
import java.util.*
import java.util.concurrent.TimeUnit


/**
 * A simple [Fragment] subclass.
 */
class DetailsFragment : Fragment() {

    private var detailObjString: String? = ""
    private lateinit var videoItem: Item
    lateinit var videoDesc: TextView
    lateinit var video_title: TextView
    lateinit var datePubText: TextView
    lateinit var imageView: ImageView
    lateinit var duration_txt: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // This will be null when the app is first launched.

        // This will be null when the app is first launched.
        if (savedInstanceState != null) {
            detailObjString = savedInstanceState.getString("detailobj")
        }
    }

    // To preserve the id when orientation changes
    // usually called after onPause() and before onDestroy().
    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        savedInstanceState.putString("detailobj", detailObjString)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onStart() {
        super.onStart()

       val view: View? = view

        if (view != null){

            videoDesc = view.findViewById(R.id.videoDesc)
            video_title = view.findViewById(R.id.video_title)
            datePubText = view.findViewById(R.id.datePubText)
            imageView = view.findViewById(R.id.imageView)
            duration_txt = view.findViewById(R.id.duration_txt)
        }

        displayDetails()
    }

    //Calculate number of days
    private fun calculateNumOfDays(date: String?) : String{
        val pattern = "yyyy-MM-dd"
        val sdf: DateFormat = SimpleDateFormat(pattern, Locale.UK)

        val z: ZoneId = ZoneId.of("Europe/London")
        val today: LocalDate = LocalDate.now(z)
        val currentDateValue: Date? = sdf.parse("$today")

        val startDateValue: Date? = date?.let { sdf.parse(date)} ?: currentDateValue

        val diff: Long = currentDateValue!!.time - (startDateValue!!.time)
        val numDays = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS)

        return numDays.toString()
    }

    private fun formatDate(date: String): String{
        val pattern = "yyyy-MM-dd"
        val sdf: DateFormat = SimpleDateFormat(pattern, Locale.UK)
        val dateParsed: Date? =  sdf.parse(date)

        return dateParsed?.let { sdf.format(it) }?: " "
    }

    // This will set the object on the list or index
    // to retrieve the object instance.
    fun setDetailObjString(objString: String) {
        this.detailObjString = objString
        val gson = Gson()
        this.videoItem = gson.fromJson(detailObjString, Item::class.java)
    }

    fun displayDetails(){

        videoDesc.text = videoItem.snippet.description
        video_title.text = videoItem.snippet.title
        datePubText.text = formatDate(videoItem.snippet.publishedAt)
        //    datePubText.text = (videoItem.snippet.publishedAt.substring(0, 10))

        val duration: String = videoItem.let { calculateNumOfDays(videoItem.snippet.publishedAt) }

        duration_txt.text = getString(R.string.duration_value, duration)
        //    duration_txt.text = String.format("%s days", duration)

        Picasso.get()
            .load(videoItem.snippet.thumbnails.high.url)
            .error(R.drawable.ic_launcher_background)
            .into(imageView)

    }
}