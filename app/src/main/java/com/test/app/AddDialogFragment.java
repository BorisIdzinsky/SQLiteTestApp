package com.test.app;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

/**
 * Created by boris on 04.02.16.
 *
 */
public class AddDialogFragment extends android.support.v4.app.DialogFragment {

    private static final String TITLE_KEY = "Title";
    private static final String SPECIALIST_KEY = "Specialist";

    private String title;
    private Specialist specialist;

    private EditText name;
    private EditText surname;
    private EditText yob;

    private Spinner position;
    private Spinner city;

    private TextInputLayout textInputLayoutName;
    private TextInputLayout textInputLayoutSurname;
    private TextInputLayout textInputLayoutYob;

    public static AddDialogFragment newInstance(String title, Specialist specialist) {
        AddDialogFragment fragment = new AddDialogFragment();

        Bundle args = new Bundle();
        args.putString(TITLE_KEY, title);
        args.putSerializable(SPECIALIST_KEY, specialist);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        getArgs();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_fragment, null);
        initializeViews(view);

        builder.setView(view).setTitle(title)
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton(R.string.save, null);

        return builder.create();
    }

    @Override
    public void onStart() {
        super.onStart();
        setPositiveButtonClickListener();
    }

    private void setPositiveButtonClickListener() {
        ((AlertDialog) getDialog()).getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isDataValid()) {
                    insertOrUpdate();
                    dismiss();
                    textInputLayoutName.setError(null);
                }
            }
        });
    }

    private void insertOrUpdate() {
        if (specialist == null) {
            ((MainActivity) getActivity()).insertRow(name.getText().toString(), surname.getText().toString(), Integer.valueOf(yob.getText().toString()), city.getSelectedItem().toString(), position.getSelectedItem().toString());
        } else {
            ((MainActivity) getActivity()).updateRow(specialist.getId(), name.getText().toString(), surname.getText().toString(), Integer.valueOf(yob.getText().toString()), city.getSelectedItem().toString(), position.getSelectedItem().toString());
        }
    }

    private void getArgs() {
        title = getArguments().getString(TITLE_KEY);
        specialist = (Specialist) getArguments().getSerializable(SPECIALIST_KEY);
    }

    private void initializeViews(View view) {
        initializeTextInputLayouts(view);
        initializeEditText(view);
        initializeSpinners(view);
        setCurrentValues();
    }

    private void initializeEditText(View view) {
        name = (EditText) view.findViewById(R.id.add_name);
        surname = (EditText) view.findViewById(R.id.add_surname);
        yob = (EditText) view.findViewById(R.id.add_year);
    }

    private void initializeTextInputLayouts(View view) {
        textInputLayoutName = (TextInputLayout) view.findViewById(R.id.text_input_layout_name);
        textInputLayoutSurname= (TextInputLayout) view.findViewById(R.id.text_input_layout_surname);
        textInputLayoutYob = (TextInputLayout) view.findViewById(R.id.text_input_layout_year);

        textInputLayoutName.setErrorEnabled(true);
        textInputLayoutSurname.setErrorEnabled(true);
        textInputLayoutYob.setErrorEnabled(true);
    }

    private void initializeSpinners(View view) {
        initializePosition(view);
        initializeCity(view);
    }

    private void initializePosition(View view) {
        position = (Spinner) view.findViewById(R.id.add_position);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.position, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        position.setAdapter(adapter);
    }

    private void initializeCity(View view) {
        city = (Spinner) view.findViewById(R.id.add_city);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.city, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        city.setAdapter(adapter);
    }

    private void setCurrentValues() {
        if (specialist != null) {
            name.setText(specialist.getName());
            surname.setText(specialist.getSurname());
            yob.setText(String.format("%d", specialist.getYob()));
        }
    }

    private boolean isDataValid() {
        boolean isValid;

        if (isNameValid() && isSurnameValid() && isYobValid()) {
            isValid = true;
        } else {
            isValid = false;
        }
        return isValid;
    }

    private boolean isNameValid() {
        boolean isValid;

        if (name.getText().toString().isEmpty()) {
            textInputLayoutName.setError(getString(R.string.error));
            isValid = false;
        } else {
            textInputLayoutName.setError(null);
            isValid = true;
        }
        return isValid;
    }

    private boolean isSurnameValid() {
        boolean isValid;

        if (surname.getText().toString().isEmpty()) {
            textInputLayoutSurname.setError(getString(R.string.error));
            isValid = false;
        } else {
            textInputLayoutSurname.setError(null);
            isValid = true;
        }
        return isValid;
    }

    private boolean isYobValid() {
        boolean isValid;

        if (!yob.getText().toString().matches("^\\d+$")) {
            textInputLayoutYob.setError(getString(R.string.error));
            isValid = false;
        } else {
            textInputLayoutYob.setError(null);
            isValid = true;
        }
        return isValid;
    }
}
