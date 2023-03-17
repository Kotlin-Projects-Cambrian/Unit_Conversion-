package ca.lokendra.unitconversionapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView


class MainActivity : AppCompatActivity() {
    private lateinit var  _input : EditText
    private lateinit var _unitInput : Spinner
    private lateinit var _unitOutput : Spinner
    private lateinit var _convertButton : Button
    private lateinit var _output : TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        _input = findViewById(R.id.inputField)
        _output = findViewById(R.id.outputField)
        _unitInput=findViewById(R.id.unitSpinner)
        _unitOutput=findViewById(R.id.unitSpinner2)
        _convertButton =  findViewById(R.id.convertButton)



        val units = arrayOf("miles","kilometer","meter","inches")
        val unitAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, units)
        unitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        _unitInput.adapter = unitAdapter
        _unitOutput.adapter = unitAdapter


        fun convertUnits(inputValue: Double, inputUnit: String, outputUnit: String): Double {
            // Define conversion rates for different units
            val conversionRates = mapOf(
                "miles" to mapOf(
                    "kilometer" to 1.60934,
                    "meter" to 1609.34,
                    "inches" to 63360.0
                ),
                "kilometer" to mapOf(
                    "miles" to 0.621371,
                    "meter" to 1000.0,
                    "inches" to 39370.1
                ),
                "meter" to mapOf(
                    "miles" to 0.000621371,
                    "kilometer" to 0.001,
                    "inches" to 39.3701
                ),
                "inches" to mapOf(
                    "miles" to 0.0000157828,
                    "kilometer" to 0.0000254,
                    "meter" to 0.0254
                )
            )

            var inputValueInMeters = inputValue

            // Convert input value to meters (the base unit)
            when (inputUnit) {
                "miles" -> inputValueInMeters *= conversionRates[inputUnit]!!["meter"]!!
                "kilometer" -> inputValueInMeters *= conversionRates[inputUnit]!!["meter"]!!
                "inches" -> inputValueInMeters *= conversionRates[inputUnit]!!["meter"]!! / 100.0
            }

            // Convert from meters to output unit
            when (outputUnit) {
                "miles" -> return inputValueInMeters / conversionRates[outputUnit]!!["meter"]!!
                "kilometer" -> return inputValueInMeters / conversionRates[outputUnit]!!["meter"]!!
                "inches" -> return inputValueInMeters * 100.0 / conversionRates[outputUnit]!!["meter"]!!
            }

            return inputValueInMeters
        }

        _convertButton.setOnClickListener {
            val inputValue = _input.text.toString().toDouble()
            val inputUnit = _unitInput.selectedItem.toString()
            val outputUnit = _unitOutput.selectedItem.toString()

            // Call the conversion function
            val result = convertUnits(inputValue, inputUnit, outputUnit)

            // Display the result in the output field
            _output.text = result.toString()
        }
    }
}