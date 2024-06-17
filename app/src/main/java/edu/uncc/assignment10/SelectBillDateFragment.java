package edu.uncc.assignment10;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

import edu.uncc.assignment10.databinding.FragmentSelectBillDateBinding;


public class SelectBillDateFragment extends Fragment {

    public SelectBillDateFragment() {
        // Required empty public constructor
    }

    FragmentSelectBillDateBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSelectBillDateBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    Date selectedDate = null;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Calendar now = Calendar.getInstance();
        binding.datePicker.setMaxDate(now.getTimeInMillis());
        selectedDate = now.getTime();

        binding.datePicker.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar selected = Calendar.getInstance();
                selected.set(year, monthOfYear, dayOfMonth);
                selectedDate = selected.getTime();
            }
        });

        binding.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onCancelSelectBillDate();
            }
        });

        binding.buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedDate != null) {
                    mListener.onBillDateSelected(selectedDate);
                }
                else {
                    Toast.makeText(getActivity(), "Select a bill date!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    SelectDateBillListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof SelectDateBillListener) {
            mListener = (SelectDateBillListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement SelectDateBillListener");
        }
    }

    interface SelectDateBillListener {
        void onBillDateSelected(Date date);
        void onCancelSelectBillDate();
    }
}