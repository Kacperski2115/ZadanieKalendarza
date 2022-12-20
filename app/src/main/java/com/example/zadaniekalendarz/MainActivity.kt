package com.example.zadaniekalendarz

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.CalendarView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import java.time.LocalDate
import java.time.ZoneId
import java.util.*

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val calendar = findViewById<CalendarView>(R.id.calendar)
        val startbutton = findViewById<Button>(R.id.StartDateButton)
        val endbutton = findViewById<Button>(R.id.EndDateButton)
        val startshow = findViewById<TextView>(R.id.StartDateText)
        val endshow = findViewById<TextView>(R.id.EndDateText)
        val show = findViewById<TextView>(R.id.show)
        val showbutton=findViewById<Button>(R.id.ShowButton)

        calendar.minDate = System.currentTimeMillis()
        calendar.maxDate = LocalDate.now().plusYears(2).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
        calendar.setFirstDayOfWeek(Calendar.MONDAY);

        val startdate = mutableListOf<Int>(0,0,0)
        val enddate = mutableListOf<Int>(0,0,0)
        val data = arrayListOf<Int>(0,0,0)

        calendar.setOnDateChangeListener(){CalendarView, dzien, mies, rok ->
            data[0] = dzien
            data[1] = mies +1
            data[2] = rok
        }

        startbutton.setOnClickListener {
            startdate[0] = data[0]
            startdate[1] = data[1]
            startdate[2] = data[2]
            startshow.text = "Data wyjazdu " +data[0].toString()+"-"+data[1].toString()+"-"+data[2].toString();
        }
        endbutton.setOnClickListener {
            enddate[0] = data[0]
            enddate[1] = data[1]
            enddate[2] = data[2]
            endshow.text =
                "Data przyjazdu " + data[0].toString() + "-" + data[1].toString() + "-" + data[2].toString();
        }
            showbutton.setOnClickListener {
                if(startdate[0] > enddate[0] || startdate[2] > enddate[2] && startdate[1] == enddate[1])
                    show.text = "Nieprawidłowa data-Data wyjazdu musi być wcześniej niż data przyjazdu"
                else {
                    val dataendshow = (enddate[0] * 360) + (enddate[1] * 30) + enddate[2]
                    val datastartshow = (startdate[0] * 360) + (startdate[1] * 30) + startdate[2]
                    val datashow = dataendshow.toChar() - datastartshow.toChar()
                    show.text = "Wyjazd trwa " + datashow.toString() + " dni"
                }
        }
    }
}