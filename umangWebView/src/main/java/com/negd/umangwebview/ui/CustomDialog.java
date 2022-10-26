package com.negd.umangwebview.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.negd.umangwebview.R;
import com.negd.umangwebview.databinding.CustomDialogViewBinding;

public class CustomDialog extends DialogFragment implements CustomDialogCallback{

    private static final String TAG = CustomDialog.class.getSimpleName();

    private DialogButtonClickListener buttonClickListener;

    private CustomDialogViewBinding binding;

    public static final String TITLE="title";
    public static final String MESSAGE="msg";
    public static final String YES_TXT="yes";
    public static final String NO_TXT="no";
    public static final String TYPE="type";

    private String dialogTitle,dialogMsg,dialogYesTxt,dilogNoTxt,dialogFor;

    public CustomDialog() {
    }

    public static CustomDialog newInstance(String title,String msg,String yesBtn,String noBtn,String forType) {
        CustomDialog fragment = new CustomDialog();
        Bundle bundle = new Bundle();
        bundle.putString(TITLE,title);
        bundle.putString(MESSAGE,msg);
        bundle.putString(YES_TXT,yesBtn);
        bundle.putString(NO_TXT,noBtn);
        bundle.putString(TYPE,forType);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        LayoutInflater inflater2 = (LayoutInflater)
                getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
         binding= CustomDialogViewBinding.inflate(inflater2);
        View view=binding.getRoot();
        setData();
        return view;
    }

    @Override
    public void onYesClick() {
        if(this.buttonClickListener !=null)
            buttonClickListener.onOkClick(dialogFor);

        dismiss();
    }

    @Override
    public void onNoClick() {
        if(this.buttonClickListener !=null)
            buttonClickListener.onCancelClick(dialogFor);

        dismiss();
    }

    public void setData(){
        if(getArguments()!=null) {
            dialogTitle = getArguments().getString(TITLE);
            dialogMsg = getArguments().getString(MESSAGE);
            dialogYesTxt = getArguments().getString(YES_TXT);
            dilogNoTxt = getArguments().getString(NO_TXT);
            dialogFor = getArguments().getString(TYPE);

            if(dialogTitle!=null && dialogTitle.length()>0){
                binding.txtTitle.setText(dialogTitle);
            }else{
                binding.txtTitle.setVisibility(View.GONE);
                binding.divider.setVisibility(View.GONE);
            }

            if(dialogMsg!=null && dialogMsg.length()>0){
                binding.contentText.setText(dialogMsg);
            }else{
                binding.contentText.setVisibility(View.GONE);
            }


            if(dialogYesTxt!=null && dialogYesTxt.length()>0){
                binding.btnYes.setText(dialogYesTxt);
            }

            if(dilogNoTxt!=null && dilogNoTxt.length()>0){
                binding.btnNo.setText(dilogNoTxt);
            }else{
                binding.btnNo.setVisibility(View.GONE);
                binding.space.setVisibility(View.GONE);
            }
        }
    }

    public void setDialogButtonClickListener(DialogButtonClickListener clickListener){
        this.buttonClickListener=clickListener;
    }

    public void show(FragmentManager fragmentManager) {
        try{
            super.show(fragmentManager, TAG);
        }catch (Exception ex){

        }

    }


    public interface DialogButtonClickListener{
        void onOkClick(String type);
        void onCancelClick(String type);
    }
}
