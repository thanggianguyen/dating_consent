package com.example.datingconsent.surveyresources;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.datingconsent.R;
import com.example.datingconsent.ui.MainActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class SurveySexFragment extends Fragment {

    private final Survey sexpreferences = MainActivity.getSexPreferenceSurvey();
    private final Survey datingpreferences = MainActivity.getDatingPreferenceSurvey();
    private CheckBox kiss,birthControl, tongue,
                     vaginal, anal, oral;
    private CheckBox[] bc;
    private TextView bcNote, bcSubnote;
    private RadioGroup analGroup, oralGroup;
    private Button doneButton,backButton;
    private RadioGroup datingSex;
    private String timeEdit;

    public SurveySexFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment\
        View rootView = inflater.inflate(R.layout.fragment_survey_sex, container, false);

        return rootView;
    }
    

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        /**
         * Instantiate java parameters with UI parameters
         */
        kiss = view.findViewById(R.id.sex_KissingCheck_checkbox);
        tongue = view.findViewById(R.id.sex_TongueCheck_checkbox);
        birthControl = view.findViewById(R.id.sex_BirthControlCheck_checkbox);
        bcNote = view.findViewById(R.id.sex_BirthControlNote_text);
        bcSubnote = view.findViewById(R.id.sex_BirthControlSubnote_text);
        vaginal = view.findViewById(R.id.sex_VaginalCheck_checkbox);
        anal = view.findViewById(R.id.sex_AnalCheck_checkbox);
        analGroup = view.findViewById(R.id.sex_Anal_radioGroup);
        oral = view.findViewById(R.id.sex_OralCheck_checkbox);
        oralGroup = view.findViewById(R.id.sex_Oral_radioGroup);
        doneButton = view.findViewById(R.id.sex_Done_button);
        backButton = view.findViewById(R.id.sex_Back_button);
        datingSex = view.findViewById(R.id.dating_Sex_RadioGroup);

        //Additional information prompted if kissing Checkbox is checked
        kiss.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    tongue.setVisibility(View.VISIBLE);
                } else {
                    tongue.setVisibility(View.GONE);
                }
            }
        });
        int[] checkIDs = {R.id.sex_BirthControl1_checkbox, R.id.sex_BirthControl2_checkbox,
                R.id.sex_BirthControl3_checkbox, R.id.sex_BirthControl4_checkbox,
                R.id.sex_BirthControl5_checkbox, R.id.sex_BirthControl6_checkbox,
                R.id.sex_BirthControl7_checkbox, R.id.sex_BirthControl8_checkbox,
                R.id.sex_BirthControl9_checkbox, R.id.sex_BirthControl10_checkbox,
                R.id.sex_BirthControl11_checkbox, R.id.sex_BirthControl12_checkbox};
        bc = new CheckBox[checkIDs.length];
        for (int i = 0; i < checkIDs.length; i++) {
            bc[i] = view.findViewById(checkIDs[i]);}
        //Additional information prompted if Birth Control Checkbox is checked
        birthControl.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    for (int i = 0; i < checkIDs.length; i++){
                        bc[i].setVisibility(View.VISIBLE);
                    }
                    bcNote.setVisibility(View.VISIBLE);
                    bcSubnote.setVisibility(View.VISIBLE);
                }
                else{
                    for (int i = 0; i < checkIDs.length; i++){
                        bc[i].setVisibility(View.GONE);
                    }
                    bcNote.setVisibility(View.GONE);
                    bcSubnote.setVisibility(View.GONE);
                }
            }
        });
        //Additional Radio Group will appear if Anal Checkbox is checked
        anal.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    analGroup.setVisibility(View.VISIBLE);
                }
                else{
                    analGroup.setVisibility(View.GONE);
                }
            }
        });
        //Additional Radio Group will appear if Oral Checkbox is checked
        oral.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    oralGroup.setVisibility(View.VISIBLE);
                }
                else{
                    oralGroup.setVisibility(View.GONE);
                }
            }
        });
        //If the host activity is SurveyModifier and sex preferences is created, call initializeComponentsForModifier().
        if (requireActivity() instanceof com.example.datingconsent.ui.SurveyModifier && sexpreferences.getResponse(0)!=null) {
            initializeComponentsForModifier(view);
            doneButton.setText("Update");
        }
        //If the host activity is something else (SurveyLauncher), make the back button invisible
        // (user has no choice but to fill out the survey)
        else {
            backButton.setVisibility(View.INVISIBLE);

            //Center the updateButton on the screen.
            ConstraintLayout layout = (ConstraintLayout)view.findViewById(R.id.sexsurvey_constraint_layout);
            ConstraintSet set = new ConstraintSet();
            set.clone(layout);
            set.connect(R.id.sex_Done_button, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, 0);
            set.applyTo(layout);
        }
        //Set the onClick action for the update button:
        doneButton = view.findViewById(R.id.sex_Done_button);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!kiss.isChecked() && !birthControl.isChecked() &&
                        !vaginal.isChecked() && !anal.isChecked() && !oral.isChecked())
                {
                    AlertDialog.Builder warning = new AlertDialog.Builder(requireActivity());

                    warning.setCancelable(true);
                    warning.setTitle("WARNING!");
                    warning.setMessage("No checkboxes are checked. This will update the Sex Checkbox in the Dating Survey to Unchecked. Do you want to Continue?");

                    warning.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    warning.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            datingpreferences.setResponse(3, "1");
                            Intent returnIntent = new Intent();
                            requireActivity().setResult(Activity.RESULT_OK, returnIntent);
                            requireActivity().finish();
                        }
                    });
                    warning.show();
                }
                else
                    doneButtonHandler(v);
                Calendar cal = Calendar.getInstance();
                SimpleDateFormat df = new SimpleDateFormat("MM/dd", Locale.getDefault());
                timeEdit = df.format(cal.getTime());
                SurveyDatingFragment.setTimeEdit(timeEdit);
            }
        });

    }

    /**
     * Initializes the settings for each component to the user's previous survey responses.
     * Only called if the parent activity is SurveyModifier (only called if the user has already filled out a survey).
     * @param view
     */
    private void initializeComponentsForModifier(View view) {
        //Set the selected checkbox for Kissing:
        if (sexpreferences.getResponse(0).contains("Yes"))
            kiss.setChecked(true);
        //Set the selected checkbox for Tongue:
        if (sexpreferences.getResponse(1).contains("Yes"))
            tongue.setChecked(true);
        //Set the selected checkbox for Birth Control Initiate:
        if (sexpreferences.getResponse(2).contains("Yes"))
            birthControl.setChecked(true);
        //Set the selected checkboxes for Birth Control:
        for (int i = 0; i < bc.length; i++) {
            if (sexpreferences.getResponse(3).contains(bc[i].getText().toString()))
                bc[i].setChecked(true);
        }
        //Set the selected checkbox for Vaginal:
        if (sexpreferences.getResponse(4).contains("Yes"))
            vaginal.setChecked(true);
        //Set the selected checkbox for Anal:
        if (sexpreferences.getResponse(5).contains("Yes"))
            anal.setChecked(true);
        //Set the selected checkbox for Oral:
        if (sexpreferences.getResponse(6).contains("Yes"))
            oral.setChecked(true);
        //Set the selected RadioButton of RadioGroup Anal Responses:
        int selectedIndexAnal;
        try { selectedIndexAnal = Integer.parseInt(sexpreferences.getResponse(7)); }
        catch (NumberFormatException e) { selectedIndexAnal = 0; }
        switch (selectedIndexAnal) {
            case 0: analGroup.check(R.id.sex_AnalGiving_radiobtn); break;
            case 1: analGroup.check(R.id.sex_AnalReceiving_radiobtn); break;
            case 2: analGroup.check(R.id.sex_AnalBoth_radiobtn); break;
        }
        //Set the selected RadioButton of RadioGroup Oral Responses:
        int selectedIndexOral;
        try { selectedIndexOral = Integer.parseInt(sexpreferences.getResponse(8)); }
        catch (NumberFormatException e) { selectedIndexOral = 0; }
        switch (selectedIndexOral) {
            case 0: oralGroup.check(R.id.sex_OralGiving_radiobtn); break;
            case 1: oralGroup.check(R.id.sex_OralReceiving_radiobtn); break;
            case 2: oralGroup.check(R.id.sex_OralBoth_radiobtn); break;
        }
        //Set the onClick action for the back button:
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backButtonHandler(v);
            }
        });
    }
    private void doneButtonHandler(View view) {
        //Set the response for Kissing (index 0 of the preferenceSurvey questions arrays) to the selected CheckBox's text fields:
        sexpreferences.setQuestion(0, kiss.getText().toString());
        String kissResponse = "";
            if (kiss.isChecked())
                kissResponse = "Yes";
            else
                kissResponse = "No";
        sexpreferences.setResponse(0, kissResponse);

        //Set the response for Tongue (index 1 of the preferenceSurvey questions arrays) to the selected CheckBox's text fields:
        sexpreferences.setQuestion(1, tongue.getText().toString());
        String tongueResponse = "";
            if (tongue.isChecked())
                tongueResponse = "Yes";
             else
                tongueResponse = "No";
        sexpreferences.setResponse(1, tongueResponse);

        //Set the response for Birth Control Initiate (index 2 of the preferenceSurvey questions arrays) to the selected CheckBox's text fields:
        sexpreferences.setQuestion(2, birthControl.getText().toString());
        String birthControlResponse = "";
            if (birthControl.isChecked())
                birthControlResponse = "Yes";
            else
                birthControlResponse = "No";
        sexpreferences.setResponse(2, birthControlResponse);

        //Set the response for Birth Control (index 3 of the preferenceSurvey questions arrays) to the selected CheckBoxes' text fields:
        sexpreferences.setQuestion(3, "2a) Types of Birth Control(s):");
        String bcResponse = "";
        for (CheckBox current : bc) {
            if (current.isChecked())
                bcResponse += current.getText().toString() + ", ";
        }
        if(bcResponse.equals("")){
            sexpreferences.setResponse(3, bcResponse.substring(0, bcResponse.length()));
        }
        else
        sexpreferences.setResponse(3, bcResponse.substring(0, bcResponse.length() - 2));

        //Set the response for Vaginal (index 4 of the preferenceSurvey questions arrays) to the selected CheckBox's text fields:
        sexpreferences.setQuestion(4, vaginal.getText().toString());
        String vaginalResponse = "";
            if (vaginal.isChecked())
                vaginalResponse = "Yes";
            else
                vaginalResponse = "No";
        sexpreferences.setResponse(4, vaginalResponse);

        //Set the response for Anal (index 5 of the preferenceSurvey questions arrays) to the selected CheckBox's text fields:
        sexpreferences.setQuestion(5, anal.getText().toString());
        String analResponse = "";
            if (anal.isChecked())
                analResponse = "Yes";
            else
                analResponse = "No";
        sexpreferences.setResponse(5, analResponse);

        //Set the response for Oral (index 6 of the preferenceSurvey questions arrays) to the selected CheckBox's text fields:
        sexpreferences.setQuestion(6, oral.getText().toString());
        String oralResponse = "";
            if (oral.isChecked())
                oralResponse = "Yes";
            else
                oralResponse = "No";
        sexpreferences.setResponse(6, oralResponse);

        //Set the response for Anal (index 7 of the preferenceSurvey questions arrays) to the selected radiobutton index of Anal Response:
        sexpreferences.setResponse(7, Integer.toString(analGroup.indexOfChild(requireView().findViewById(
                analGroup.getCheckedRadioButtonId()))));

        //Set the response for Oral (index 8 of the preferenceSurvey questions arrays) to the selected radiobutton index of Oral Response:
        sexpreferences.setResponse(8, Integer.toString(oralGroup.indexOfChild(requireView().findViewById(
                oralGroup.getCheckedRadioButtonId()))));

        //Save responses and quit the activity
        sexpreferences.saveToJson(requireActivity().getFilesDir().toString(), MainActivity.SEX_PREFERENCE_SURVEY_FILE_NAME);
        if (requireActivity() instanceof com.example.datingconsent.ui.SurveyLauncher) {
            Intent returnIntent = new Intent();
            requireActivity().setResult(Activity.RESULT_OK, returnIntent);
        }
        requireActivity().finish();
    }
    /**
     * Handles click events for the backButton.
     * Quits the parent activity.
     * @param view the back button
     */
    private void backButtonHandler(View view) {
        requireActivity().finish();
    }
}
