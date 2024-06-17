package edu.uncc.assignment10;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.Date;

import edu.uncc.assignment10.databinding.FragmentCreateBillBinding;
import edu.uncc.assignment10.databinding.FragmentEditBillBinding;


public class EditBillFragment extends Fragment {
    private static final String ARG_PARAM_BILL = "ARG_PARAM_BILL";
    private Bill mBill;

    String selectedCategory;
    Date selectedBillDate;
    Double selectedDiscount;

    public EditBillFragment() {
        // Required empty public constructor
    }

    public static EditBillFragment newInstance(Bill bill) {
        EditBillFragment fragment = new EditBillFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM_BILL, bill);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mBill = (Bill) getArguments().getSerializable(ARG_PARAM_BILL);
            //initialize the selections
            selectedBillDate = mBill.getBillDate();
            selectedCategory = mBill.getCategory();
            selectedDiscount = mBill.getDiscount();
        }
    }

    public void setSelectedCategory(String selectedCategory) {
        this.selectedCategory = selectedCategory;
    }

    public void setSelectedBillDate(Date selectedBillDate) {
        this.selectedBillDate = selectedBillDate;
    }

    public void setSelectedDiscount(Double selectedDiscount) {
        this.selectedDiscount = selectedDiscount;
    }

    FragmentEditBillBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentEditBillBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(selectedBillDate == null){
            binding.textViewBillDate.setText("N/A");
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
            binding.textViewBillDate.setText(sdf.format(selectedBillDate));
        }

        if(selectedCategory == null){
            binding.textViewCategory.setText("N/A");
        } else {
            binding.textViewCategory.setText(selectedCategory);
        }

        if(selectedDiscount == null){
            binding.textViewDiscount.setText("N/A");
        } else {
            binding.textViewDiscount.setText(selectedDiscount.toString() + "%");
        }

        binding.buttonBillDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.gotoSelectDate();
            }
        });

        binding.buttonCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.gotoSelectCategory();
            }
        });

        binding.buttonDiscount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.gotoSelectDiscount();
            }
        });

        binding.buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = binding.editTextName.getText().toString();
                String billAmountStr = binding.editTextBill.getText().toString();
                double billAmount = 0;
                if(name.isEmpty()){
                    Toast.makeText(getActivity(), "Enter valid bill name!", Toast.LENGTH_SHORT).show();
                    return;
                }

                try{
                    billAmount = Double.parseDouble(billAmountStr);
                } catch (NumberFormatException e){
                    Toast.makeText(getActivity(), "Enter valid bill amount !", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(selectedDiscount == null){
                    Toast.makeText(getActivity(), "Select bill discount!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(selectedBillDate == null){
                    Toast.makeText(getActivity(), "Select bill date!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(selectedCategory == null){
                    Toast.makeText(getActivity(), "Select bill category!", Toast.LENGTH_SHORT).show();
                    return;
                }
                mBill.setCategory(selectedCategory);
                mBill.setDiscount(selectedDiscount);
                mBill.setBillDate(selectedBillDate);
                mBill.setAmount(billAmount);
                mBill.setName(name);
                mListener.editBillSuccessful(mBill);
            }
        });

        binding.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.editBillCancel();
            }
        });
    }

    EditBillListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof EditBillListener){
            mListener = (EditBillListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement CreateBillListener");
        }
    }

    interface EditBillListener {
        void editBillSuccessful(Bill bill);
        void editBillCancel();
        void gotoSelectCategory();
        void gotoSelectDate();
        void gotoSelectDiscount();
    }


}