package com.facultyofengineeringcu12.covid_19_pneomonia;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class MultipleDropdownActivity extends AppCompatActivity {

    private Spinner facultySpinner;
    private Spinner departmentSpinner;
    private Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiple_dropdown2);

        // Initialize views
        facultySpinner = findViewById(R.id.spinner_Faculty);
        departmentSpinner = findViewById(R.id.spinner_Department);
        submitButton = findViewById(R.id.button_submit);

        // Set up faculty spinner
        ArrayAdapter<CharSequence> facultyAdapter = ArrayAdapter.createFromResource(
                this, R.array.faculty_array, android.R.layout.simple_spinner_item);
        facultyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        facultySpinner.setAdapter(facultyAdapter);

        // Set up department spinner
        ArrayAdapter<CharSequence> departmentAdapter = ArrayAdapter.createFromResource(
                this, R.array.departments_empty_array, android.R.layout.simple_spinner_item);
        departmentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        departmentSpinner.setAdapter(departmentAdapter);

        // Set up faculty spinner listener
        facultySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedFaculty = parent.getItemAtPosition(position).toString();
                updateDepartmentSpinner(selectedFaculty);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        // Set up submit button click listener
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectedFaculty = facultySpinner.getSelectedItem().toString();
                String selectedDepartment = departmentSpinner.getSelectedItem().toString();
                // Perform any actions you need with the selected faculty and department
                Toast.makeText(MultipleDropdownActivity.this,
                        "Selected Faculty: " + selectedFaculty +
                                "\nSelected Department: " + selectedDepartment,
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateDepartmentSpinner(String selectedFaculty) {
        ArrayAdapter<CharSequence> departmentAdapter = null;
        switch (selectedFaculty) {
            case "Faculty of Science":
                departmentAdapter = ArrayAdapter.createFromResource(
                        this, R.array.departments_science, android.R.layout.simple_spinner_item);
                break;
            case "Faculty of Arts":
                departmentAdapter = ArrayAdapter.createFromResource(
                        this, R.array.departments_arts, android.R.layout.simple_spinner_item);
                break;
            case "Faculty of Business Administration":
                departmentAdapter = ArrayAdapter.createFromResource(
                        this, R.array.departments_business_administration, android.R.layout.simple_spinner_item);
                break;
            case "Faculty of Social Sciences":
                departmentAdapter = ArrayAdapter.createFromResource(
                        this, R.array.departments_social_sciences, android.R.layout.simple_spinner_item);
                break;
            case "Faculty of Biological Sciences":
                departmentAdapter = ArrayAdapter.createFromResource(
                        this, R.array.departments_biological_sciences, android.R.layout.simple_spinner_item);
                break;
            case "Faculty of Law":
                departmentAdapter = ArrayAdapter.createFromResource(
                        this, R.array.departments_law, android.R.layout.simple_spinner_item);
                break;
            case "Faculty of Engineering and Technology":
                departmentAdapter = ArrayAdapter.createFromResource(
                        this, R.array.departments_engineering_technology, android.R.layout.simple_spinner_item);
                break;

        }

        if (departmentAdapter != null) {
            departmentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            departmentSpinner.setAdapter(departmentAdapter);
        }
    }
}