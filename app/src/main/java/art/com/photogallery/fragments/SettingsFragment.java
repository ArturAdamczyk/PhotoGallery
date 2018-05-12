package art.com.photogallery.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import art.com.photogallery.Params.ExifParams;
import art.com.photogallery.Params.Params;
import art.com.photogallery.R;
import art.com.photogallery.helpers.CustomAlertDialog;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;

public class SettingsFragment extends Fragment {

    @BindView(R.id.radioButtonFilterB)
    RadioButton radioButtonFilterB;
    @BindView(R.id.radioButtonSortA)
    RadioButton radioButtonSortA;
    @BindView(R.id.radioButtonSortC)
    RadioButton radioButtonSortC;
    @BindView(R.id.radioButtonSortG)
    RadioButton radioButtonSortG;
    @BindView(R.id.radioButtonFilterC)
    RadioButton radioButtonFilterC;
    @BindView(R.id.radioButtonFilterD)
    RadioButton radioButtonFilterD;
    @BindView(R.id.radioButtonFilterA)
    RadioButton radioButtonFilterA;
    @BindView(R.id.radioButtonSortE)
    RadioButton radioButtonSortE;
    @BindView(R.id.radioButtonSortB)
    RadioButton radioButtonSortB;
    @BindView(R.id.radioButtonSortD)
    RadioButton radioButtonSortD;
    @BindView(R.id.radioButtonSortF)
    RadioButton radioButtonSortF;
    @BindView(R.id.radioButtonSortH)
    RadioButton radioButtonSortH;
    @BindView(R.id.textViewFilterA)
    TextView textViewFilterA;
    @BindView(R.id.textViewFilterB)
    TextView textViewFilterB;
    @BindView(R.id.textViewFilterC)
    TextView textViewFilterC;
    @BindView(R.id.textViewFilterD)
    TextView textViewFilterD;

    private Callback delegate;

    private String filterOption = Params.EMPTY_VALUE;
    private String filterConstraint = Params.EMPTY_VALUE;
    private String sortOption = Params.EMPTY_VALUE;


    public interface Callback {
        void onFilterOptionChanged(String filterOption, String filterConstraint);
        void onSortOptionChanged(String sortOption);
    }

    public static SettingsFragment newInstance() {
        SettingsFragment fragmentSettings = new SettingsFragment();
        return fragmentSettings;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Context ctx) {
        super.onAttach(ctx);
        try {
            delegate = (Callback) ctx;
        } catch (ClassCastException e) {
            throw new ClassCastException(ctx.getResources().getString(R.string.settings_no_callback_implementation) + ctx.toString());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        ButterKnife.bind(this, view);
        initViews();
        return view;
    }

    @OnClick({R.id.radioButtonFilterB, R.id.radioButtonSortA, R.id.radioButtonSortC,
            R.id.radioButtonSortG, R.id.radioButtonFilterC, R.id.radioButtonFilterD,
            R.id.radioButtonFilterA, R.id.radioButtonSortE, R.id.radioButtonSortB,
            R.id.radioButtonSortD, R.id.radioButtonSortF, R.id.radioButtonSortH,
            R.id.textViewFilterA, R.id.textViewFilterB, R.id.textViewFilterC,
            R.id.textViewFilterD})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.radioButtonSortA:
                clearSortOptions(radioButtonSortA);
                sortOption = Params.SORT_BY_NAME_ASC;
                break;
            case R.id.radioButtonSortB:
                clearSortOptions(radioButtonSortB);
                sortOption = Params.SORT_BY_NAME_DSC;
                break;
            case R.id.radioButtonSortC:
                clearSortOptions(radioButtonSortC);
                sortOption = Params.SORT_BY_DATE_ASC;
                break;
            case R.id.radioButtonSortD:
                clearSortOptions(radioButtonSortD);
                sortOption = Params.SORT_BY_DATE_DSC;
                break;
            case R.id.radioButtonSortE:
                clearSortOptions(radioButtonSortE);
                sortOption = Params.SORT_BY_SIZE_ASC;
                break;
            case R.id.radioButtonSortF:
                clearSortOptions(radioButtonSortF);
                sortOption = Params.SORT_BY_SIZE_DSC;
                break;
            case R.id.radioButtonSortG:
                clearSortOptions(radioButtonSortG);
                sortOption = Params.SORT_BY_RESOLUTION_ASC;
                break;
            case R.id.radioButtonSortH:
                clearSortOptions(radioButtonSortH);
                sortOption = Params.SORT_BY_RESOLUTION_DSC;
                break;
            case R.id.radioButtonFilterA:
                clearFilterOptions(radioButtonFilterA);
                filterOption = Params.FILTER_BY_ORIENTATION;
                break;
            case R.id.radioButtonFilterB:
                clearFilterOptions(radioButtonFilterB);
                filterOption = Params.FILTER_BY_ISO;
                break;
            case R.id.radioButtonFilterC:
                clearFilterOptions(radioButtonFilterC);
                filterOption = Params.FILTER_BY_MAKER;
                break;
            case R.id.radioButtonFilterD:
                clearFilterOptions(radioButtonFilterD);
                filterOption = Params.FILTER_BY_FLASH;
                break;
            case R.id.textViewFilterA:
                createFilterOrientationDialog();
                break;
            case R.id.textViewFilterB:
                createFilterISODialog();
                break;
            case R.id.textViewFilterC:
                createFilterMakerDialog();
                break;
            case R.id.textViewFilterD:
                createFilterFlashDialog();
                break;
        }
        delegate.onFilterOptionChanged(filterOption, filterConstraint);
        delegate.onSortOptionChanged(sortOption);
    }

    @OnLongClick({R.id.radioButtonFilterA, R.id.radioButtonFilterB, R.id.radioButtonFilterC, R.id.radioButtonFilterD})
    public boolean onViewLongClicked(View view) {
        clearAllFilterOptions();
        delegate.onFilterOptionChanged(filterOption, filterConstraint);
        delegate.onSortOptionChanged(sortOption);
        return true;
    }

    private void initViews(){
        radioButtonSortA.setText(getResources().getString(R.string.settings_by_name_asc));
        radioButtonSortB.setText(getResources().getString(R.string.settings_by_name_desc));
        radioButtonSortC.setText(getResources().getString(R.string.settings_by_date_asc));
        radioButtonSortD.setText(getResources().getString(R.string.settings_by_date_desc));
        radioButtonSortE.setText(getResources().getString(R.string.settings_by_size_asc));
        radioButtonSortF.setText(getResources().getString(R.string.settings_by_size_desc));
        radioButtonSortG.setText(getResources().getString(R.string.settings_by_resolution_asc));
        radioButtonSortH.setText(getResources().getString(R.string.settings_by_resolution_desc));

        radioButtonFilterA.setText(getResources().getString(R.string.settings_by_orientation));
        radioButtonFilterB.setText(getResources().getString(R.string.settings_by_iso));
        radioButtonFilterC.setText(getResources().getString(R.string.settings_by_maker));
        radioButtonFilterD.setText(getResources().getString(R.string.settings_by_flash));
    }

    private void createFilterOrientationDialog(){
        CustomAlertDialog.createAlertDialog(this.getContext(), getResources().getString(R.string.settings_filter_by_orientation_label))
                .setItems(getResources().getStringArray(R.array.orientation_filter_options), (int position, String sortItem) -> {
                    switch(position){
                        case 0:
                            filterConstraint = ExifParams.HORIZONTAL;
                            textViewFilterA.setText(getResources().getStringArray(R.array.orientation_filter_options)[position]);
                            break;
                        case 1:
                            filterConstraint = ExifParams.MIRROR_HORIZONTAL;
                            textViewFilterA.setText(getResources().getStringArray(R.array.orientation_filter_options)[position]);
                            break;
                        case 2:
                            filterConstraint = ExifParams.ROTATE_180;
                            textViewFilterA.setText(getResources().getStringArray(R.array.orientation_filter_options)[position]);
                            break;
                        case 3:
                            filterConstraint = ExifParams.MIRROR_VERTICAL;
                            textViewFilterA.setText(getResources().getStringArray(R.array.orientation_filter_options)[position]);
                            break;
                        case 4:
                            filterConstraint = ExifParams.MIRROR_HORIZONTAL_ROTATE_270;
                            textViewFilterA.setText(getResources().getStringArray(R.array.orientation_filter_options)[position]);
                            break;
                        case 5:
                            filterConstraint = ExifParams.ROTATE_90;
                            textViewFilterA.setText(getResources().getStringArray(R.array.orientation_filter_options)[position]);
                            break;
                        case 6:
                            filterConstraint = ExifParams.MIRROR_HORIZONTAL_ROTATE_90;
                            textViewFilterA.setText(getResources().getStringArray(R.array.orientation_filter_options)[position]);
                            break;
                        case 7:
                            filterConstraint = ExifParams.ROTATE_270;
                            textViewFilterA.setText(getResources().getStringArray(R.array.orientation_filter_options)[position]);
                            break;
                    }
                    delegate.onFilterOptionChanged(filterOption, filterConstraint);
                    delegate.onSortOptionChanged(sortOption);
                }).show();
    }

    private void createFilterISODialog(){
        CustomAlertDialog.createAlertDialog(this.getContext(), getResources().getString(R.string.settings_filter_by_iso_label))
                .setItems(getResources().getStringArray(R.array.iso_filter_options), (int position, String sortItem) -> {
                    switch(position){
                        case 0:
                            filterConstraint = ExifParams.ISO_50;
                            textViewFilterB.setText(getResources().getStringArray(R.array.iso_filter_options)[position]);
                            break;
                        case 1:
                            filterConstraint = ExifParams.ISO_80;
                            textViewFilterB.setText(getResources().getStringArray(R.array.iso_filter_options)[position]);
                            break;
                        case 2:
                            filterConstraint = ExifParams.ISO_100;
                            textViewFilterB.setText(getResources().getStringArray(R.array.iso_filter_options)[position]);
                            break;
                        case 3:
                            filterConstraint = ExifParams.ISO_200;
                            textViewFilterB.setText(getResources().getStringArray(R.array.iso_filter_options)[position]);
                            break;
                        case 4:
                            filterConstraint = ExifParams.ISO_400;
                            textViewFilterB.setText(getResources().getStringArray(R.array.iso_filter_options)[position]);
                            break;
                        case 5:
                            filterConstraint = ExifParams.ISO_800;
                            textViewFilterB.setText(getResources().getStringArray(R.array.iso_filter_options)[position]);
                            break;
                        case 6:
                            filterConstraint = ExifParams.ISO_1600;
                            textViewFilterB.setText(getResources().getStringArray(R.array.iso_filter_options)[position]);
                            break;
                        case 7:
                            filterConstraint = ExifParams.ISO_3200;
                            textViewFilterB.setText(getResources().getStringArray(R.array.iso_filter_options)[position]);
                            break;
                        case 8:
                            filterConstraint = ExifParams.ISO_6400;
                            textViewFilterB.setText(getResources().getStringArray(R.array.iso_filter_options)[position]);
                            break;
                    }
                    delegate.onFilterOptionChanged(filterOption, filterConstraint);
                    delegate.onSortOptionChanged(sortOption);
                }).show();
    }

    private void createFilterMakerDialog(){
        CustomAlertDialog.createAlertDialog(this.getContext(), getResources().getString(R.string.settings_filter_by_maker_label))
                .setItems(getResources().getStringArray(R.array.maker_filter_options), (int position, String sortItem) -> {
                    switch(position){
                        case 0:
                            filterConstraint = ExifParams.SAMSUNG;
                            textViewFilterC.setText(getResources().getStringArray(R.array.maker_filter_options)[position]);
                            break;
                        case 1:
                            filterConstraint = ExifParams.HUAWEI;
                            textViewFilterC.setText(getResources().getStringArray(R.array.maker_filter_options)[position]);
                            break;
                        case 2:
                            filterConstraint = ExifParams.SONY;
                            textViewFilterC.setText(getResources().getStringArray(R.array.maker_filter_options)[position]);
                            break;
                        case 3:
                            filterConstraint = ExifParams.NOKIA;
                            textViewFilterC.setText(getResources().getStringArray(R.array.maker_filter_options)[position]);
                            break;
                        case 4:
                            filterConstraint = ExifParams.HONOR;
                            textViewFilterC.setText(getResources().getStringArray(R.array.maker_filter_options)[position]);
                            break;
                        case 5:
                            filterConstraint = ExifParams.XIAOMI;
                            textViewFilterC.setText(getResources().getStringArray(R.array.maker_filter_options)[position]);
                            break;
                        case 6:
                            filterConstraint = ExifParams.LG;
                            textViewFilterC.setText(getResources().getStringArray(R.array.maker_filter_options)[position]);
                            break;
                    }
                    delegate.onFilterOptionChanged(filterOption, filterConstraint);
                    delegate.onSortOptionChanged(sortOption);
                }).show();
    }

    private void createFilterFlashDialog(){
        CustomAlertDialog.createAlertDialog(this.getContext(), getResources().getString(R.string.settings_filter_by_flash_label))
                .setItems(getResources().getStringArray(R.array.flash_filter_options), (int position, String sortItem) -> {
                    switch(position){
                        case 0:
                            filterConstraint = ExifParams.NO_FLASH;
                            textViewFilterD.setText(getResources().getStringArray(R.array.flash_filter_options)[position]);
                            break;
                        case 1:
                            filterConstraint = ExifParams.FIRED;
                            textViewFilterD.setText(getResources().getStringArray(R.array.flash_filter_options)[position]);
                            break;
                        case 2:
                            filterConstraint = ExifParams.FIRED_RETURN_NOT_DETECTED;
                            textViewFilterD.setText(getResources().getStringArray(R.array.flash_filter_options)[position]);
                            break;
                        case 3:
                            filterConstraint = ExifParams.FIRED_RETURN_DETECTED;
                            textViewFilterD.setText(getResources().getStringArray(R.array.flash_filter_options)[position]);
                            break;
                        case 4:
                            filterConstraint = ExifParams.ON_BUT_NOT_FIRED;
                            textViewFilterD.setText(getResources().getStringArray(R.array.flash_filter_options)[position]);
                            break;
                        case 5:
                            filterConstraint = ExifParams.ON_FIRED;
                            textViewFilterD.setText(getResources().getStringArray(R.array.flash_filter_options)[position]);
                            break;
                        case 6:
                            filterConstraint = ExifParams.ON_RETURN_NOT_DETECTED;
                            textViewFilterD.setText(getResources().getStringArray(R.array.flash_filter_options)[position]);
                            break;
                        case 7:
                            filterConstraint = ExifParams.ON_RETURN_DETECTED;
                            textViewFilterD.setText(getResources().getStringArray(R.array.flash_filter_options)[position]);
                            break;
                        case 8:
                            filterConstraint = ExifParams.OFF_DID_NOT_FIRE;
                            textViewFilterD.setText(getResources().getStringArray(R.array.flash_filter_options)[position]);
                            break;
                        case 9:
                            filterConstraint = ExifParams.OFF_DID_NOT_FIRE_RETURN_NOT_DETECTED;
                            textViewFilterD.setText(getResources().getStringArray(R.array.flash_filter_options)[position]);
                            break;
                        case 10:
                            filterConstraint = ExifParams.AUTO_DID_NOT_FIRE;
                            textViewFilterD.setText(getResources().getStringArray(R.array.flash_filter_options)[position]);
                            break;
                        case 11:
                            filterConstraint = ExifParams.AUTO_FIRED;
                            textViewFilterD.setText(getResources().getStringArray(R.array.flash_filter_options)[position]);
                            break;
                        case 12:
                            filterConstraint = ExifParams.AUTO_FIRED_RETURN_NOT_DETECTED;
                            textViewFilterD.setText(getResources().getStringArray(R.array.flash_filter_options)[position]);
                            break;
                        case 13:
                            filterConstraint = ExifParams.AUTO_FIRED_RETURN_DETECTED;
                            textViewFilterD.setText(getResources().getStringArray(R.array.flash_filter_options)[position]);
                            break;
                        case 14:
                            filterConstraint = ExifParams.NO_FLASH_FUNCTION;
                            textViewFilterD.setText(getResources().getStringArray(R.array.flash_filter_options)[position]);
                            break;
                        case 15:
                            filterConstraint = ExifParams.OFF_NO_FLASH_FUNCTION;
                            textViewFilterD.setText(getResources().getStringArray(R.array.flash_filter_options)[position]);
                            break;
                        case 16:
                            filterConstraint = ExifParams.FIRED_RED_EYE_REDUCTION;
                            textViewFilterD.setText(getResources().getStringArray(R.array.flash_filter_options)[position]);
                            break;
                        case 17:
                            filterConstraint = ExifParams.FIRED_RED_EYE_REDUCTION_RETURN_NOT_DETECTED;
                            textViewFilterD.setText(getResources().getStringArray(R.array.flash_filter_options)[position]);
                            break;
                        case 18:
                            filterConstraint = ExifParams.FIRED_RED_EYE_REDUCTION_RETURN_DETECTED;
                            textViewFilterD.setText(getResources().getStringArray(R.array.flash_filter_options)[position]);
                            break;
                        case 19:
                            filterConstraint = ExifParams.ON_RED_EYE_REDUCTION;
                            textViewFilterD.setText(getResources().getStringArray(R.array.flash_filter_options)[position]);
                            break;
                        case 20:
                            filterConstraint = ExifParams.ON_RED_EYE_REDUCTION_RETURN_NOT_DETECTED;
                            textViewFilterD.setText(getResources().getStringArray(R.array.flash_filter_options)[position]);
                            break;
                        case 21:
                            filterConstraint = ExifParams.ON_RED_EYE_REDUCTION_RETURN_DETECTED;
                            textViewFilterD.setText(getResources().getStringArray(R.array.flash_filter_options)[position]);
                            break;
                        case 22:
                            filterConstraint = ExifParams.OFF_RED_EYE_REDUCTION;
                            textViewFilterD.setText(getResources().getStringArray(R.array.flash_filter_options)[position]);
                            break;
                        case 23:
                            filterConstraint = ExifParams.AUTO_DID_NOT_FIRE_RED_EYE_REDUCTION;
                            textViewFilterD.setText(getResources().getStringArray(R.array.flash_filter_options)[position]);
                            break;
                        case 24:
                            filterConstraint = ExifParams.AUTO_FIRED_RED_EYE_REDUCTION;
                            textViewFilterD.setText(getResources().getStringArray(R.array.flash_filter_options)[position]);
                            break;
                        case 25:
                            filterConstraint = ExifParams.AUTO_FIRED_RED_EYE_REDUCTION_RETURN_NOT_DETECTED;
                            textViewFilterD.setText(getResources().getStringArray(R.array.flash_filter_options)[position]);
                            break;
                        case 26:
                            filterConstraint = ExifParams.AUTO_FIRED_RED_EYE_REDUCTION_RETURN_DETECTED;
                            textViewFilterD.setText(getResources().getStringArray(R.array.flash_filter_options)[position]);
                            break;
                    }
                    delegate.onFilterOptionChanged(filterOption, filterConstraint);
                    delegate.onSortOptionChanged(sortOption);
                }).show();
    }

    private void clearSortOptions(RadioButton checkedRadioButton) {
        if (checkedRadioButton != radioButtonSortA) {
            radioButtonSortA.setChecked(false);
        }
        if (checkedRadioButton != radioButtonSortB) {
            radioButtonSortB.setChecked(false);
        }
        if (checkedRadioButton != radioButtonSortC) {
            radioButtonSortC.setChecked(false);
        }
        if (checkedRadioButton != radioButtonSortD) {
            radioButtonSortD.setChecked(false);
        }
        if (checkedRadioButton != radioButtonSortE) {
            radioButtonSortE.setChecked(false);
        }
        if (checkedRadioButton != radioButtonSortF) {
            radioButtonSortF.setChecked(false);
        }
        if (checkedRadioButton != radioButtonSortG) {
            radioButtonSortG.setChecked(false);
        }
        if (checkedRadioButton != radioButtonSortH) {
            radioButtonSortH.setChecked(false);
        }
    }

    private void clearFilterOptions(RadioButton checkRadioButton) {
        if (checkRadioButton != radioButtonFilterA) {
            radioButtonFilterA.setChecked(false);
        }
        if (checkRadioButton != radioButtonFilterB) {
            radioButtonFilterB.setChecked(false);
        }
        if (checkRadioButton != radioButtonFilterC) {
            radioButtonFilterC.setChecked(false);
        }
        if (checkRadioButton != radioButtonFilterD) {
            radioButtonFilterD.setChecked(false);
        }
    }

    private void clearAllFilterOptions(){
        radioButtonFilterA.setChecked(false);
        radioButtonFilterB.setChecked(false);
        radioButtonFilterC.setChecked(false);
        radioButtonFilterD.setChecked(false);
        filterOption = Params.EMPTY_VALUE;
    }
}