package edu.uncc.assignment10;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.uncc.assignment10.databinding.FragmentSelectSortBinding;

public class SelectSortFragment extends Fragment {

    public SelectSortFragment() {
        // Required empty public constructor
    }

    FragmentSelectSortBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSelectSortBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onSortCancel();
            }
        });

        binding.imageViewDateAsc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onSortSelected("Date", "ASC");
            }
        });

        binding.imageViewDateDesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onSortSelected("Date", "DESC");
            }
        });

        binding.imageViewCategoryAsc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onSortSelected("Category", "ASC");
            }
        });

        binding.imageViewCategoryDesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onSortSelected("Category", "DESC");
            }
        });

        binding.imageViewDiscountAsc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onSortSelected("Discount", "ASC");
            }
        });

        binding.imageViewDiscountDesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onSortSelected("Discount", "DESC");
            }
        });
    }

    SortListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof SortListener) {
            mListener = (SortListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement SortListener");
        }
    }

    interface SortListener {
        void onSortSelected(String sortAttribute, String sortOrder);
        void onSortCancel();
    }
}