package edu.uncc.assignment10;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.SimpleDateFormat;

import edu.uncc.assignment10.databinding.FragmentBillSummaryBinding;

public class BillSummaryFragment extends Fragment {
    private static final String ARG_PARAM_BILL = "ARG_PARAM_BILL";
    private Bill mBill;

    public BillSummaryFragment() {
        // Required empty public constructor
    }

    public static BillSummaryFragment newInstance(Bill bill) {
        BillSummaryFragment fragment = new BillSummaryFragment();
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
        }
    }

    FragmentBillSummaryBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentBillSummaryBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.closeBillSummary();
            }
        });

        binding.buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.deleteBill(mBill);
            }
        });

        binding.textViewBillName.setText(mBill.getName());
        binding.textViewBillAmount.setText("$" + String.format("%.2f", mBill.getAmount()));
        binding.textViewBillDiscountPercent.setText(String.valueOf(mBill.getDiscount()) + "%");
        double discountAmount = mBill.getAmount() * mBill.getDiscount() / 100;
        binding.textViewBillDiscount.setText("$" + String.format("%.2f", discountAmount));
        binding.textViewBillTotal.setText("$" + String.format("%.2f", mBill.getAmount() - discountAmount));
        binding.textViewBillCategory.setText(mBill.getCategory());
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        binding.textViewBillDate.setText(sdf.format(mBill.getBillDate()));
    }



    BillSummaryListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof BillSummaryListener) {
            mListener = (BillSummaryListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement BillSummaryListener");
        }
    }

    interface BillSummaryListener {
        void deleteBill(Bill bill);
        void closeBillSummary();
    }
}