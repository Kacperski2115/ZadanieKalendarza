package com.example.zadaniekalendarz

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.CalendarView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.alpha
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId
import java.util.*
import kotlin.math.absoluteValue

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val calendar = findViewById<CalendarView>(R.id.calendarView)
        val startbutton = findViewById<Button>(R.id.btnstart)
        val endbutton = findViewById<Button>(R.id.btnend)
        val startshow = findViewById<TextView>(R.id.pocz)
        val endshow = findViewById<TextView>(R.id.koniec)
        val show = findViewById<TextView>(R.id.show)

        calendar.minDate = System.currentTimeMillis()
        calendar.maxDate = LocalDate.now().plusYears(2).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
        calendar.setFirstDayOfWeek(Calendar.MONDAY);

        val dataPocz = mutableListOf<Int>(0,0,0)
        val dataKon = mutableListOf<Int>(0,0,0)
        val data = arrayListOf<Int>(0,0,0)

        calendar.setOnDateChangeListener(){CalendarView, dzien, mies, rok ->
            data[0] = dzien
            data[1] = mies +1
            data[2] = rok
        }

        startbutton.setOnClickListener {
            dataPocz[0] = data[0]
            dataPocz[1] = data[1]
            dataPocz[2] = data[2]
            startshow.text = "Początek wyjazdu: " +data[0].toString()+"-"+data[1].toString()+"-"+data[2].toString();
            calendar.selectedDateVerticalBar
        }
        endbutton.setOnClickListener {
            dataKon[0] = data[0]
            dataKon[1] = data[1]
            dataKon[2] = data[2]
            endshow.text = "Koniec wyjazdu: " +data[0].toString()+"-"+data[1].toString()+"-"+data[2].toString();

            if(dataPocz[0] != 0 && dataKon[0] != 0)
                if(dataPocz[0] > dataKon[0] || dataPocz[2] > dataKon[2] && dataPocz[1] == dataKon[1])
                    show.text = "Nie można sie cofać w czasie :)"
                else{
                    val dataendshow=(dataKon[0]*360) + (dataKon[1]*30) + dataKon[2]
                    val datastartshow=(dataPocz[0]*360) + (dataPocz[1]*30) + dataPocz[2]
                    val datashow=dataendshow.toChar()-datastartshow.toChar()
                    show.text="Wyjazd trwa "+datashow.toString()+" dni"

                }
        }
    }
}