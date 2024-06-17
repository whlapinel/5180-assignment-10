package edu.uncc.assignment10;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import edu.uncc.assignment10.databinding.FragmentSelectDiscountBinding;


public class SelectDiscountFragment extends Fragment {
    public SelectDiscountFragment() {
        // Required empty public constructor
    }

    FragmentSelectDiscountBinding binding;
    ArrayList<String> discounts = new ArrayList<>();

    DiscountAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSelectDiscountBinding.inflate(inflater, container, false);
        discounts.add("10%");
        discounts.add("15%");
        discounts.add("18%");
        discounts.add("Custom");
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.seekBar.setMax(50);
        binding.seekBar.setProgress(25);

        binding.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                binding.textViewSeekBarProgress.setText(progress + "%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        binding.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onCancelSelectDiscount();
            }
        });

        adapter = new SelectDiscountFragment.DiscountAdapter();
        binding.listView.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.listView.setAdapter(adapter);
        

    }

    SelectDiscountListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof SelectDiscountListener) {
            mListener = (SelectDiscountListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement SelectDiscountListener");
        }
    }

    interface SelectDiscountListener {
        void onDiscountSelected(double discount);
        void onCancelSelectDiscount();
    }

    class DiscountAdapter extends RecyclerView.Adapter<SelectDiscountFragment.DiscountAdapter.DiscountViewHolder>{
        @NonNull
        @Override
        public SelectDiscountFragment.DiscountAdapter.DiscountViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.discount_row_item, parent, false);
            return new SelectDiscountFragment.DiscountAdapter.DiscountViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull SelectDiscountFragment.DiscountAdapter.DiscountViewHolder holder, int position) {
            holder.setUpUI(discounts.get(position));
        }

        @Override
        public int getItemCount() {
            return discounts.size();
        }

        class DiscountViewHolder extends RecyclerView.ViewHolder {

            double discount;
            TextView discountText;
            public DiscountViewHolder(@NonNull View itemView) {
                super(itemView);

                discountText = itemView.findViewById(R.id.discountText);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mListener.onDiscountSelected(discount);
                    }
                });


            }

            void setUpUI(String discountString) {

                if (!discountString.equalsIgnoreCase("Custom")) {
                    discountString = discountString.substring(0, discountString.length() - 1);
                    discount = Double.parseDouble(discountString);
                } else {
                    discount = binding.seekBar.getProgress();
                }
                discountText.setText(discountString);


            }

        }
    }
}