package com.example.zadaniekalendarz

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import java.util.*
import java.util.Calendar
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat

//Kacper Michalak 2pr

@Suppress("DEPRECATION")
var StartDate : Long=1
var EndDate : Long=1
class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val startbutton = findViewById<Button>(R.id.StartButton)
        val startshow = findViewById<TextView>(R.id.StartDateText)
        val endshow = findViewById<TextView>(R.id.EndDateText)
        val Length = findViewById<TextView>(R.id.Length)
        val LengthButton=findViewById<Button>(R.id.LengthButton)
        val calendar=Calendar.getInstance()
        val mindate= calendar.timeInMillis
        val maxdate= calendar.timeInMillis + 63113904000
        val constraints= CalendarConstraints.Builder()
            .setValidator(DateValidatorPointForward.now())
            .setStart(mindate)
            .setEnd(maxdate)
            .build()

        startbutton.setOnClickListener {
            val calendar = MaterialDatePicker.Builder.dateRangePicker()
                .setCalendarConstraints(constraints)
                .setTitleText("Wybierz date wyjazdu i przyjazdu")
                .build()
            calendar.show(supportFragmentManager, "Kalendarz")
            calendar.addOnPositiveButtonClickListener { datePicked->
                StartDate = datePicked.first
                EndDate = datePicked.second
                startshow.text="Data twojego wyjazdu to: "+ConvertDate(StartDate)
                endshow.text="Data twojego przyjazdu to: "+ConvertDate(EndDate)
            }
            LengthButton.setOnClickListener {
                val StartDateSub = StartDate/1000/60/60/24
                val EndDateSub = EndDate/1000/60/60/24
                val DatesSub = (EndDateSub-StartDateSub).toInt()
                if(DatesSub==1){
                    Length.text="Twój wyjazd będzie trwał " + DatesSub.toString() + " dzień"
                }else  Length.text = "Twój wyjazd będzie trwał " + DatesSub.toString() + " dni"
            }
        }
    }
    private fun ConvertDate(date: Long) :String{
        val datetoconvert=Date(date)
        val format=SimpleDateFormat(
            "dd-MM-yyyy"
        )
        return format.format(datetoconvert)
    }
}