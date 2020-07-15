package br.edu.utfpr.luisdanielassulfi.trilhadeaprendizado;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import br.edu.utfpr.luisdanielassulfi.trilhadeaprendizado.enums.IntentConstants;
import br.edu.utfpr.luisdanielassulfi.trilhadeaprendizado.enums.ResultConstants;
import br.edu.utfpr.luisdanielassulfi.trilhadeaprendizado.model.Technology;

public class MainActivity extends AppCompatActivity {

    private EditText editTextTechnologyName, editTextTechnologyDescription,
            editTextTechnologyPrerequirements, editTextTechnologyRequiredTime;

    private CheckBox checkBoxTechnologyMandatory;

    private RadioGroup technologiesRadioGroup;

    private Spinner spinnerPercentageKnown;

    private int mode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findElements();

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle != null) {
            mode = bundle.getInt(IntentConstants.MODE.getValue(), 0);
        }

        setTitle(getString(R.string.create_technology));
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
        boolean isValid = true;

        String technologyName = editTextTechnologyName.getText().toString();
        String technologyDescription = editTextTechnologyDescription.getText().toString();
        String technologyPrerequirements = editTextTechnologyPrerequirements.getText().toString();
        String technologyRequiredTime = editTextTechnologyRequiredTime.getText().toString();
        double time = 0.0;
        boolean isMandatory = checkBoxTechnologyMandatory.isChecked();
        String percentageKnownLabel = (String) spinnerPercentageKnown.getSelectedItem();
        double percentageKnown = Double.valueOf(percentageKnownLabel.split(" ")[0]);

        if (isEditTextEmpty(technologyName)) {
            Toast.makeText(this, getString(R.string.technology_name_required),
                    Toast.LENGTH_SHORT).show();
            editTextTechnologyName.requestFocus();
            isValid = false;
        }

        if (isEditTextEmpty(technologyDescription)) {
            Toast.makeText(this, R.string.technology_description_required,
                    Toast.LENGTH_SHORT).show();
            editTextTechnologyDescription.requestFocus();
            isValid = false;
        }

        if (isEditTextEmpty(technologyPrerequirements)) {
            Toast.makeText(this, R.string.technology_prerequirements_required,
                    Toast.LENGTH_SHORT).show();
            editTextTechnologyPrerequirements.requestFocus();
            isValid = false;
        }

        if(isEditTextEmpty(technologyRequiredTime)) {
            Toast.makeText(this, R.string.technology_time_required,
                    Toast.LENGTH_SHORT).show();
            editTextTechnologyPrerequirements.requestFocus();
            isValid = false;
        } else {
            time = Double.parseDouble(technologyRequiredTime);
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
                isValid = false;
        }

        if(isValid && mode == ResultConstants.ADD_TECHNOLOGY.getValue()) {
            Technology technology = new Technology(technologyName, technologyDescription,
                    technologyPrerequirements, time, isMandatory, technologyTrail, percentageKnown);

            String message = getString(R.string.technology_saved_with_success) + technologyName;

            Intent intent = new Intent();
            intent.putExtra(IntentConstants.NEW_TECHNOLOGY.getValue(), technology);
            intent.putExtra(IntentConstants.ADD_TECHNOLOGY_STATUS.getValue(), ResultConstants.SUCCESS.getValue());
            intent.putExtra(IntentConstants.ADD_TECHNOLOGY_MESSAGE.getValue(), message);

            finishWithSuccess(intent);
        }
    }

    @Override
    public void onBackPressed() {
        finishByCanceling();
    }

    private void finishWithSuccess(Intent intent) {
        intent.putExtra(IntentConstants.ADD_TECHNOLOGY_STATUS.getValue(), ResultConstants.SUCCESS.getValue());
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    private void finishByCanceling() {
        Intent intent = new Intent();
        intent.putExtra(IntentConstants.ADD_TECHNOLOGY_STATUS.getValue(), ResultConstants.CANCELED.getValue());
        setResult(Activity.RESULT_CANCELED, intent);
        finish();
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