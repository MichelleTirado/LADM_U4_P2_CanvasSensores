package mx.tecnm.tepic.ladm_u4_practica1

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity(), SensorEventListener {
    var lienzo : Lienzo ?= null

    lateinit var sensorManager: SensorManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lienzo = Lienzo(this)
        setContentView(lienzo)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

        sensorManager.registerListener(this,
            sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
            SensorManager.SENSOR_DELAY_NORMAL)

        sensorManager.registerListener(this,
            sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY),
            SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }

    override fun onSensorChanged(event: SensorEvent) {
        if(event.sensor.type == Sensor.TYPE_ACCELEROMETER){
            lienzo!!.sun.setX(lienzo!!.sun.x.toInt()-((event.values[0].toInt())*10))
            lienzo!!.sun.setY(lienzo!!.sun.y.toInt()+((event.values[1].toInt())*10))
            lienzo!!.invalidate()
        }

        if(event.sensor.type == Sensor.TYPE_PROXIMITY){
            if(event.values[0]<3){
                lienzo!!.normal.setVisible(false)
                lienzo!!.oscurece.setVisible(true)
                lienzo!!.sun.setVisible(false)
                lienzo!!.invalidate()
            }

            if(event.values[0]>=3){
                lienzo!!.normal.setVisible(true)
                lienzo!!.oscurece.setVisible(false)
                lienzo!!.sun.setVisible(true)
                lienzo!!.invalidate()
            }

        }
    }
}