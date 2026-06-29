package com.example.unitconverter;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Spinner spCategory, spFrom, spTo;
    EditText edtValue;
    Button btnConvert;
    ImageButton btnSwap;
    TextView txtResult;

    String[] categories = {
            "Length",
            "Weight",
            "Temperature",
            "Area",
            "Volume",
            "Speed",
            "Time"
    };
    String[] lengthUnits = {
            "Millimeter",
            "Centimeter",
            "Meter",
            "Kilometer",
            "Inch",
            "Foot",
            "Yard",
            "Mile"
    };

    String[] weightUnits = {
            "Milligram",
            "Gram",
            "Kilogram",
            "Ton",
            "Ounce",
            "Pound"
    };

    String[] temperatureUnits = {
            "Celsius",
            "Fahrenheit",
            "Kelvin"
    };

    String[] areaUnits = {
            "Square Millimeter",
            "Square Centimeter",
            "Square Meter",
            "Square Kilometer",
            "Hectare",
            "Acre"
    };
    String[] volumeUnits = {
            "Milliliter",
            "Liter",
            "Cubic Meter",
            "Gallon"
    };

    String[] speedUnits = {
            "Meter/Second",
            "Kilometer/Hour",
            "Mile/Hour"
    };

    String[] timeUnits = {
            "Second",
            "Minute",
            "Hour",
            "Day"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spCategory = findViewById(R.id.spCategory);
        spFrom = findViewById(R.id.spFrom);
        spTo = findViewById(R.id.spTo);

        edtValue = findViewById(R.id.edtValue);
        btnConvert = findViewById(R.id.btnConvert);
        btnSwap = findViewById(R.id.btnSwap);
        txtResult = findViewById(R.id.txtResult);

        btnSwap.setOnClickListener(v -> swapUnits());
        ArrayAdapter<String> categoryAdapter =
                new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_spinner_item,
                        categories
                );

        categoryAdapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );

        spCategory.setAdapter(categoryAdapter);
        updateUnitSpinners(lengthUnits);

        spCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                switch (position) {

                    case 0:
                        updateUnitSpinners(lengthUnits);
                        break;

                    case 1:
                        updateUnitSpinners(weightUnits);
                        break;

                    case 2:
                        updateUnitSpinners(temperatureUnits);
                        break;

                    case 3:
                        updateUnitSpinners(areaUnits);
                        break;

                    case 4:
                        updateUnitSpinners(volumeUnits);
                        break;

                    case 5:
                        updateUnitSpinners(speedUnits);
                        break;

                    case 6:
                        updateUnitSpinners(timeUnits);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btnConvert.setOnClickListener(v -> convertUnits());
    }
    private void updateUnitSpinners(String[] units) {

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                units
        );

        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );

        spFrom.setAdapter(adapter);
        spTo.setAdapter(adapter);
    }
    private void convertUnits() {

        String input = edtValue.getText().toString().trim();

        if (input.isEmpty()) {
            txtResult.setText("Please enter a value.");
            return;
        }

        double value = Double.parseDouble(input);

        String category = spCategory.getSelectedItem().toString();
        String from = spFrom.getSelectedItem().toString();
        String to = spTo.getSelectedItem().toString();

        double result = 0;

        switch (category) {

            case "Length":
                result = convertLength(value, from, to);
                break;

            case "Weight":
                result = convertWeight(value, from, to);
                break;

            case "Temperature":
                result = convertTemperature(value, from, to);
                break;

            case "Area":
                result = convertArea(value, from, to);
                break;

            case "Volume":
                result = convertVolume(value, from, to);
                break;

            case "Speed":
                result = convertSpeed(value, from, to);
                break;

            case "Time":
                result = convertTime(value, from, to);
                break;
        }

        txtResult.setText(String.valueOf(result));
    }
    private double convertLength(double value, String from, String to) {

        // Convert everything to meters first
        double meters = value;

        switch (from) {

            case "Centimeter":
                meters = value / 100;
                break;

            case "Kilometer":
                meters = value * 1000;
                break;

            case "Meter":
                meters = value;
                break;
        }

        // Convert meters to target unit
        switch (to) {

            case "Centimeter":
                return meters * 100;

            case "Kilometer":
                return meters / 1000;

            default:
                return meters;
        }
    }
    private double convertWeight(double value, String from, String to) {

        double grams = value;

        switch (from) {

            case "Kilogram":
                grams = value * 1000;
                break;

            case "Gram":
                grams = value;
                break;
        }

        switch (to) {

            case "Kilogram":
                return grams / 1000;

            default:
                return grams;
        }
    }

    private double convertArea(double value, String from, String to) {

        double squareMeters = value;

        switch (from) {
            case "Square Millimeter":
                squareMeters = value / 1_000_000;
                break;

            case "Square Centimeter":
                squareMeters = value / 10_000;
                break;

            case "Square Meter":
                squareMeters = value;
                break;

            case "Square Kilometer":
                squareMeters = value * 1_000_000;
                break;

            case "Hectare":
                squareMeters = value * 10_000;
                break;

            case "Acre":
                squareMeters = value * 4046.8564224;
                break;
        }

        switch (to) {

            case "Square Millimeter":
                return squareMeters * 1_000_000;

            case "Square Centimeter":
                return squareMeters * 10_000;

            case "Square Kilometer":
                return squareMeters / 1_000_000;

            case "Hectare":
                return squareMeters / 10_000;

            case "Acre":
                return squareMeters / 4046.8564224;

            default:
                return squareMeters;
        }
    }

    private double convertVolume(double value, String from, String to) {

        double liters = value;

        switch (from) {

            case "Milliliter":
                liters = value / 1000;
                break;

            case "Liter":
                liters = value;
                break;

            case "Cubic Meter":
                liters = value * 1000;
                break;

            case "Gallon":
                liters = value * 3.78541;
                break;
        }

        switch (to) {

            case "Milliliter":
                return liters * 1000;

            case "Cubic Meter":
                return liters / 1000;

            case "Gallon":
                return liters / 3.78541;

            default:
                return liters;
        }
    }

    private double convertSpeed(double value, String from, String to) {

        double ms = value;

        switch (from) {

            case "Kilometer/Hour":
                ms = value / 3.6;
                break;

            case "Meter/Second":
                ms = value;
                break;

            case "Mile/Hour":
                ms = value * 0.44704;
                break;
        }

        switch (to) {

            case "Kilometer/Hour":
                return ms * 3.6;

            case "Mile/Hour":
                return ms / 0.44704;

            default:
                return ms;
        }
    }

    private double convertTime(double value, String from, String to) {

        double seconds = value;

        switch (from) {

            case "Second":
                seconds = value;
                break;

            case "Minute":
                seconds = value * 60;
                break;

            case "Hour":
                seconds = value * 3600;
                break;

            case "Day":
                seconds = value * 86400;
                break;
        }

        switch (to) {

            case "Minute":
                return seconds / 60;

            case "Hour":
                return seconds / 3600;

            case "Day":
                return seconds / 86400;

            default:
                return seconds;
        }
    }
    private double convertTemperature(double value, String from, String to) {

        double celsius;

        switch (from) {

            case "Fahrenheit":
                celsius = (value - 32) * 5 / 9;
                break;

            case "Kelvin":
                celsius = value - 273.15;
                break;

            default:
                celsius = value;
        }

        switch (to) {

            case "Fahrenheit":
                return (celsius * 9 / 5) + 32;

            case "Kelvin":
                return celsius + 273.15;

            default:
                return celsius;
        }
    }
    private void swapUnits() {

        // Swap spinner selections
        int fromPosition = spFrom.getSelectedItemPosition();
        int toPosition = spTo.getSelectedItemPosition();

        spFrom.setSelection(toPosition);
        spTo.setSelection(fromPosition);

        // Swap input and result
        String input = edtValue.getText().toString();
        String result = txtResult.getText().toString();

        edtValue.setText(result);
        txtResult.setText(input);

    }
}