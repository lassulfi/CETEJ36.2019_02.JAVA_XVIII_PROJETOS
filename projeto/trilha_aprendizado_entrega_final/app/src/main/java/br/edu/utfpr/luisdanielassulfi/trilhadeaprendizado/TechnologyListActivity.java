package br.edu.utfpr.luisdanielassulfi.trilhadeaprendizado;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

import br.edu.utfpr.luisdanielassulfi.trilhadeaprendizado.adapter.TechnologyAdapter;
import br.edu.utfpr.luisdanielassulfi.trilhadeaprendizado.enums.IntentConstants;
import br.edu.utfpr.luisdanielassulfi.trilhadeaprendizado.enums.ResultConstants;
import br.edu.utfpr.luisdanielassulfi.trilhadeaprendizado.enums.SortingConstants;
import br.edu.utfpr.luisdanielassulfi.trilhadeaprendizado.model.Technology;

public class TechnologyListActivity extends AppCompatActivity
        implements TechnologyAdapter.ClickAdapterListener {

    private static final String SHARED_PREFERENCES_FILE =
            "br.edu.utfpr.luisdanielassulfi.trilhadeaprendizado.sharedpreferences.SORTING_PREFERENCES";
    private static final String SORTING_METHOD = "SORTING_METHOD";
    private static final String DARK_MODE = "DARK_MODE";

    private RecyclerView recyclerViewTechnologies;
    private RecyclerView.LayoutManager layoutManager;
    private TechnologyAdapter technologyAdapter;

    private ArrayList<Technology> technologies;

    private ActionMode mActionMode;
    private int mSelectedPosition = -1;

    private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
            MenuInflater inflater = actionMode.getMenuInflater();
            inflater.inflate(R.menu.selected_technology_menu, menu);

            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.menuItemEditTechnology:
                    editTechnology();
                    actionMode.finish();
                    return true;
                case R.id.menuItemDeleteTechnology:
                    deleteTechnology();
                    actionMode.finish();
                    return true;
                default:
                    return false;
            }
        }

        @Override
        public void onDestroyActionMode(ActionMode actionMode) {
            final RecyclerView.ViewHolder viewHolder = recyclerViewTechnologies
                    .findViewHolderForAdapterPosition(mSelectedPosition);
            if(viewHolder != null) {
                viewHolder.itemView.setBackgroundColor(Color.TRANSPARENT);
            }
            technologyAdapter.setCheckedPosition(-1);

            mActionMode = null;
        }
    };

    private int sortingOption = SortingConstants.TECHNOLOGY_NAME_ASC.getValue();
    private boolean nightModeOption = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_technology_list);

        recyclerViewTechnologies = findViewById(R.id.recyclerViewTechnologies);

        technologies = new ArrayList<>();
        technologyAdapter = new TechnologyAdapter(this, technologies, this);

        layoutManager = new LinearLayoutManager(this);

        recyclerViewTechnologies.setAdapter(technologyAdapter);
        recyclerViewTechnologies.setLayoutManager(layoutManager);
        recyclerViewTechnologies.setHasFixedSize(true);
        recyclerViewTechnologies.addItemDecoration(new DividerItemDecoration(this,
                LinearLayout.VERTICAL));

        registerForContextMenu(recyclerViewTechnologies);

        getSortingPreferences();

        getNightModePreference();
    }

    public void showAboutActivity(MenuItem menuItem) {
        Intent intent = new Intent(this, AboutActivity.class);
        startActivity(intent);
    }

    public void showAddActivity(MenuItem menuItem) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(IntentConstants.MODE.getValue(), ResultConstants.ADD_TECHNOLOGY.getValue());
        startActivityForResult(intent, ResultConstants.ADD_TECHNOLOGY.getValue());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode == Activity.RESULT_OK) {
            Bundle bundle = data.getExtras();
            if(bundle != null) {
                String message = "";
                if(requestCode == ResultConstants.ADD_TECHNOLOGY.getValue()) {
                    message = bundle.getString(IntentConstants.ADD_TECHNOLOGY_MESSAGE.getValue());

                    Technology technology = (Technology) bundle
                            .getParcelable(IntentConstants.NEW_TECHNOLOGY.getValue());
                    technologies.add(technology);
                    technologyAdapter.notifyDataSetChanged();
                } else {
                    message = bundle.getString(IntentConstants.UPDATE_TECHNOLOGY_MESSAGE.getValue());

                    Technology updatedTechnology = bundle.getParcelable(IntentConstants.SELECTED_TECHNOLOGY.getValue());

                    technologies.set(mSelectedPosition, updatedTechnology);

                    technologyAdapter.notifyDataSetChanged();

                    disableActionMode();
                }
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.technology_list_menu, menu);
        MenuItem darkModeMenuItem = menu.findItem(R.id.menuItemDarkMode);
        darkModeMenuItem.setChecked(nightModeOption);

        MenuItem sortMenuItem;
        if(sortingOption == SortingConstants.TECHNOLOGY_NAME_ASC.getValue()) {
            sortMenuItem = menu.findItem(R.id.menuItemSortByTechAsc);
        } else if (sortingOption == SortingConstants.TECHNOLOGY_NAME_DESC.getValue()) {
            sortMenuItem = menu.findItem(R.id.menuItemSortByTechDesc);
        } else if (sortingOption == SortingConstants.TRAIL_ASC.getValue()) {
            sortMenuItem = menu.findItem(R.id.menuItemSortByTrailAsc);
        } else if (sortingOption == SortingConstants.TRAIL_DESC.getValue()) {
            sortMenuItem = menu.findItem(R.id.menuItemSortByTrailDesc);
        } else {
            return false;
        }

        sortMenuItem.setChecked(true);
        return true;
    }

    @Override
    public void onRowLongClicked(int position) {
        mSelectedPosition = position;
        enableActionMode(position);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuItemSortByTechAsc:
                setSortingPreferences(SortingConstants.TECHNOLOGY_NAME_ASC.getValue());
                item.setChecked(true);
                return true;
            case R.id.menuItemSortByTechDesc:
                setSortingPreferences(SortingConstants.TECHNOLOGY_NAME_DESC.getValue());
                item.setChecked(true);
                return true;
            case R.id.menuItemSortByTrailAsc:
                setSortingPreferences(SortingConstants.TRAIL_ASC.getValue());
                item.setChecked(true);
                return true;
            case R.id.menuItemSortByTrailDesc:
                setSortingPreferences(SortingConstants.TRAIL_DESC.getValue());
                item.setChecked(true);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void menuItemDarkModeClick(MenuItem menuItem) {
        menuItem.setChecked(!menuItem.isChecked());
        setNightModePreference(menuItem.isChecked());
    }

    private void enableActionMode(int position) {
        if(mActionMode == null) {
            mActionMode = startSupportActionMode(mActionModeCallback);
        }

        toggleSelection(position);
    }

    private void disableActionMode() {
        mActionMode = null;
        mSelectedPosition = -1;
    }

    private void toggleSelection(int position) {
        technologyAdapter.toggleSelection(position);
    }

    private void deleteTechnology() {
        int position = technologyAdapter.getCheckedPosition();
        technologies.remove(position);

        technologyAdapter.notifyDataSetChanged();

        disableActionMode();
    }

    private void editTechnology() {
        mSelectedPosition = technologyAdapter.getCheckedPosition();
        Technology technology = technologyAdapter.getSelectedItem();
        showEditActivity(technology);
    }

    private void showEditActivity(Technology technology) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(IntentConstants.MODE.getValue(), ResultConstants.EDIT_TECHNOLOGY.getValue());
        intent.putExtra(IntentConstants.SELECTED_TECHNOLOGY.getValue(), technology);

        startActivityForResult(intent, ResultConstants.EDIT_TECHNOLOGY.getValue());
    }

    private void getSortingPreferences() {
        SharedPreferences preferences = getSharedPreferences(SHARED_PREFERENCES_FILE,
                Context.MODE_PRIVATE);
        this.sortingOption = preferences.getInt(SORTING_METHOD, this.sortingOption);

        sortTechnologyList(this.sortingOption);
    }

    private void setSortingPreferences(int sortingOption) {
        SharedPreferences preferences = getSharedPreferences(SHARED_PREFERENCES_FILE,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(SORTING_METHOD, sortingOption);
        editor.commit();

        this.sortingOption = sortingOption;

        sortTechnologyList(this.sortingOption);
    }

    private void getNightModePreference() {
        SharedPreferences preferences = getSharedPreferences(SHARED_PREFERENCES_FILE,
                Context.MODE_PRIVATE);
        this.nightModeOption = preferences.getBoolean(DARK_MODE, this.nightModeOption);

        setNightModePreference(this.nightModeOption);
    }

    private void setNightModePreference(boolean nightModePreference) {
        SharedPreferences preferences = getSharedPreferences(SHARED_PREFERENCES_FILE,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(DARK_MODE, nightModePreference);
        editor.commit();

        setNightMode(nightModePreference);
    }

    private void sortTechnologyList(int sortingOption) {
        if(sortingOption == SortingConstants.TECHNOLOGY_NAME_ASC.getValue()) {
            sortTechnologyListBy(Technology.technologyNameComparatorAsc);
            return;
        } else if (sortingOption == SortingConstants.TECHNOLOGY_NAME_DESC.getValue()) {
            sortTechnologyListBy(Technology.technologyNameComparatorDesc);
            return;
        } else if (sortingOption == SortingConstants.TRAIL_ASC.getValue()) {
            sortTechnologyListBy(Technology.trailComparatorAsc);
            return;
        } else if (sortingOption == SortingConstants.TRAIL_DESC.getValue()) {
            sortTechnologyListBy(Technology.trailComparatorDesc);
            return;
        } else {
            return;
        }
    }

    private void sortTechnologyListBy(Comparator<Technology> comparator) {
        Collections.sort(technologies, comparator);
        technologyAdapter.notifyDataSetChanged();
    }

    private void setNightMode(boolean value) {
        if(value) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
        getDelegate().applyDayNight();
    }
}