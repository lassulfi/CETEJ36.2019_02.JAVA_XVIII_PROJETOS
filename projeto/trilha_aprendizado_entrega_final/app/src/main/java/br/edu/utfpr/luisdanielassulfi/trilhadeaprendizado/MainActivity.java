package br.edu.utfpr.luisdanielassulfi.trilhadeaprendizado;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
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

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        findElements();

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle != null) {
            mode = bundle.getInt(IntentConstants.MODE.getValue(), ResultConstants.ADD_TECHNOLOGY.getValue());
            if(mode == ResultConstants.ADD_TECHNOLOGY.getValue()) {
                setTitle(getString(R.string.create_technology));
            } else {
                Technology technology = bundle.getParcelable(IntentConstants.SELECTED_TECHNOLOGY.getValue());
                setValuesToViewComponents(technology);
                setTitle(getString(R.string.edit_technology));
            }
        }

    }

    public void cleanMenuItemClick(MenuItem menuItem) {
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

    public void saveMenuItemClick(MenuItem menuItem) {
        boolean isValid = true;

        String technologyName = editTextTechnologyName.getText().toString();
        String technologyDescription = editTextTechnologyDescription.getText().toString();
        String technologyPrerequirements = editTextTechnologyPrerequirements.getText().toString();
        String technologyRequiredTime = editTextTechnologyRequiredTime.getText().toString();
        double time = 0.0;
        boolean isMandatory = checkBoxTechnologyMandatory.isChecked();
        String percentageKnownLabel = (String) spinnerPercentageKnown.getSelectedItem();
        double percentageKnown = Double.valueOf(percentageKnownLabel.split(" ")[0]);

        StringBuilder builder = new StringBuilder();

        if (isEditTextEmpty(technologyName)) {
            editTextTechnologyName.requestFocus();
            builder.append(getString(R.string.technology_name_required));
            builder.append(System.getProperty(getString(R.string.line_separator)));
            isValid = false;
        }

        if (isEditTextEmpty(technologyDescription)) {
            editTextTechnologyDescription.requestFocus();
            builder.append(getString(R.string.technology_description_required));
            builder.append(System.getProperty(getString(R.string.line_separator)));
            isValid = false;
        }

        if (isEditTextEmpty(technologyPrerequirements)) {
            builder.append(getString(R.string.technology_prerequirements_required));
            builder.append(System.getProperty(getString(R.string.line_separator)));
            editTextTechnologyPrerequirements.requestFocus();
            isValid = false;
        }

        if(isEditTextEmpty(technologyRequiredTime)) {
            builder.append(getString(R.string.technology_time_required));
            builder.append(System.getProperty(getString(R.string.line_separator)));
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
                builder.append(getString(R.string.no_trail_selected));
                builder.append(System.getProperty(getString(R.string.line_separator)));
                isValid = false;
        }

        if(isValid) {
            Technology technology = new Technology(technologyName, technologyDescription,
                    technologyPrerequirements, time, isMandatory, technologyTrail, percentageKnown);

            Intent intent = new Intent();
            if(mode == ResultConstants.ADD_TECHNOLOGY.getValue()) {
                String message = getString(R.string.technology_saved_with_success);

                intent.putExtra(IntentConstants.NEW_TECHNOLOGY.getValue(), technology);
                intent.putExtra(IntentConstants.ADD_TECHNOLOGY_STATUS.getValue(), ResultConstants.SUCCESS.getValue());
                intent.putExtra(IntentConstants.ADD_TECHNOLOGY_MESSAGE.getValue(), message);
            } else {
                String message = getString(R.string.tecnology_updated_with_success);

                intent.putExtra(IntentConstants.SELECTED_TECHNOLOGY.getValue(), technology);
                intent.putExtra(IntentConstants.UPDATE_TECHNOLOGY_STATUS.getValue(), ResultConstants.SUCCESS.getValue());
                intent.putExtra(IntentConstants.UPDATE_TECHNOLOGY_MESSAGE.getValue(), message);
            }

            finishWithSuccess(intent);
        } else {
            Toast.makeText(this, builder.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        finishByCanceling();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        return true;
    }

    private void finishWithSuccess(Intent intent) {
        if(mode == ResultConstants.ADD_TECHNOLOGY.getValue()) {
            intent.putExtra(IntentConstants.ADD_TECHNOLOGY_STATUS.getValue(), ResultConstants.SUCCESS.getValue());
        } else {
            intent.putExtra(IntentConstants.UPDATE_TECHNOLOGY_STATUS.getValue(), ResultConstants.SUCCESS.getValue());
        }
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

    private void setValuesToViewComponents(Technology technology) {
        editTextTechnologyName.setText(technology.getName());
        editTextTechnologyDescription.setText(technology.getDescription());
        editTextTechnologyPrerequirements.setText(technology.getPreRequirements());
        editTextTechnologyRequiredTime.setText(String.valueOf(technology.getTime()));
        checkBoxTechnologyMandatory.setChecked(technology.isMandatory());

        String trail = technology.getTrail();
        if(!trail.isEmpty()) {
            if(trail.equals(getString(R.string.backend_technology))) {
                technologiesRadioGroup.check(R.id.radioButtonBackendTechnology);
            }
            if(trail.equals(getString(R.string.technology_frontend))) {
                technologiesRadioGroup.check(R.id.radioButtonTechnologyFrontend);
            }
            if(trail.equals(getString(R.string.technology_devops))) {
                technologiesRadioGroup.check(R.id.radioButtonTechnologyDevops);
            }
        }

        int spinnerPosition = (int) (technology.getPercentageKnown() / 10);
        spinnerPercentageKnown.setSelection(spinnerPosition);
    }
}