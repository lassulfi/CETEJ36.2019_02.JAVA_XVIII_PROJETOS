package br.edu.utfpr.luisdanielassulfi.trilhadeaprendizado;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText editTextTechnologyName, editTextTechnologyDescription,
            editTextTechnologyPrerequirements, editTextTechnologyRequiredTime;

    private CheckBox checkBoxTechnologyMandatory;

    private RadioGroup technologiesRadioGroup;

    private Spinner spinnerPercentageKnown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findElements();
    }

    public void cleanButtonClick(View view) {
        editTextTechnologyName.setText(null);
        editTextTechnologyDescription.setText(null);
        editTextTechnologyPrerequirements.setText(null);
        editTextTechnologyRequiredTime.setText(null);

        checkBoxTechnologyMandatory.setChecked(false);

        technologiesRadioGroup.clearCheck();

        spinnerPercentageKnown.setSelection(0);

        editTextTechnologyName.requestFocus();

        Toast.makeText(this, R.string.clean_all_elements, Toast.LENGTH_SHORT).show();
    }

    public void saveButtonClick(View view) {
        String technologyName = editTextTechnologyName.getText().toString();
        String technologyDescription = editTextTechnologyDescription.getText().toString();
        String technologyPrerequirements = editTextTechnologyPrerequirements.getText().toString();
        String technologyRequiredTime = editTextTechnologyRequiredTime.getText().toString();

        boolean isMandatory = checkBoxTechnologyMandatory.isChecked();

        String percentageKnown = (String) spinnerPercentageKnown.getSelectedItem();

        if (isEditTextEmpty(technologyName)) {
            Toast.makeText(this, getString(R.string.technology_name_required),
                    Toast.LENGTH_SHORT).show();
            editTextTechnologyName.requestFocus();
            return;
        }

        if (isEditTextEmpty(technologyDescription)) {
            Toast.makeText(this, R.string.technology_description_required,
                    Toast.LENGTH_SHORT).show();
            editTextTechnologyDescription.requestFocus();
            return;
        }

        if (isEditTextEmpty(technologyPrerequirements)) {
            Toast.makeText(this, R.string.technology_prerequirements_required,
                    Toast.LENGTH_SHORT).show();
            editTextTechnologyPrerequirements.requestFocus();
            return;
        }

        if(isEditTextEmpty(technologyRequiredTime)) {
            Toast.makeText(this, R.string.technology_time_required,
                    Toast.LENGTH_SHORT).show();
            editTextTechnologyPrerequirements.requestFocus();
            return;
        }

        String technologyTrail = "";
        switch (technologiesRadioGroup.getCheckedRadioButtonId()) {
            case R.id.radioButtonBackendTechnology:
                technologyTrail = getString(R.string.backend_technology);
                break;
            case R.id.radioButtonTechnologyFrontend:
                technologyTrail = getString(R.string.technology_frontend);
                break;
            case R.id.radioButtonTechnologyDevops:
                technologyTrail = getString(R.string.technology_devops);
                break;
            default:
                Toast.makeText(this, getString(R.string.no_trail_selected),
                        Toast.LENGTH_SHORT).show();
                return;
        }

        String message = getString(R.string.technology_saved_with_success) + technologyName;

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private boolean isEditTextEmpty(String content) {
        return content == null || content.isEmpty();
    }

    private void findElements() {
        editTextTechnologyName = findViewById(R.id.editTextTechnologyName);
        editTextTechnologyDescription = findViewById(R.id.editTextTechnologyDescription);
        editTextTechnologyPrerequirements = findViewById(R.id.editTextTechnologyPrerequirements);
        editTextTechnologyRequiredTime = findViewById(R.id.editTextTechnologyRequiredTime);

        checkBoxTechnologyMandatory = findViewById(R.id.checkBoxTechnologyMandatory);

        technologiesRadioGroup = findViewById(R.id.radioGroupTechnologiesTrail);

        spinnerPercentageKnown = findViewById(R.id.spinnerPercentageKnown);
    }
}