package com.example.youtubevideogmbn

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.youtubevideogmbn.MainActivity.Companion.INTENT_MESSAGE
import com.example.youtubevideogmbn.data.model.Item
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.detail_activity.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId
import java.util.*
import java.util.concurrent.TimeUnit

class DetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_activity)

        title = "Video Detail"

        val gson = Gson()
        val videoItem = gson.fromJson(intent.getStringExtra(INTENT_MESSAGE), Item::class.java)

        videoDesc.text = videoItem.snippet.description
        video_title.text = videoItem.snippet.title
        datePubText.text = formatDate(videoItem.snippet.publishedAt)
    //    datePubText.text = (videoItem.snippet.publishedAt.substring(0, 10))

        val duration: String? = videoItem?.let { calculateNumOfDays(videoItem.snippet.publishedAt) }

        duration_txt.text = getString(R.string.duration_value, duration)
    //    duration_txt.text = String.format("%s days", duration)

        Picasso.get()
            .load(videoItem.snippet.thumbnails.high.url)
            .error(R.drawable.ic_launcher_background)
            .into(imageView)
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
}
