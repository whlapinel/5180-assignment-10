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
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import edu.uncc.assignment10.databinding.FragmentBillsBinding;

public class BillsFragment extends Fragment {
    public BillsFragment() {
        // Required empty public constructor
    }

    String sortAttribute = "Date", sortOrder = "ASC";

    public void setSortItems(String sortAttribute, String sortOrder) {
        this.sortAttribute = sortAttribute;
        this.sortOrder = sortOrder;
    }

    FragmentBillsBinding binding;

    private ArrayList<Bill> mBills = new ArrayList<>();

    BillAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentBillsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mBills.clear();
        mBills.addAll(mListener.getAllBills());

        binding.textViewSortedBy.setText("Sorted By " + sortAttribute + " (" + sortOrder + ")");

        adapter = new BillsFragment.BillAdapter();
        binding.billList.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.billList.setAdapter(adapter);

        binding.buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.clearAllBills();
            }
        });

        binding.buttonNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.gotoCreateBill();
            }
        });

        /*binding.buttonSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.gotoSortSelection();
            }
        });*/
    }

    BillsListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof BillsListener) {
            mListener = (BillsListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement BillsListener");
        }
    }

    interface BillsListener {
        void goToBillSummary(Bill bill);
        void goToEditBill(Bill bill);
        ArrayList<Bill> getAllBills();
        void gotoCreateBill();
        void deleteBill(Bill bill);
        void clearAllBills();
    }

    class BillAdapter extends RecyclerView.Adapter<BillsFragment.BillAdapter.BillViewHolder>{
            @NonNull
            @Override
            public BillAdapter.BillViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = getLayoutInflater().inflate(R.layout.bill_row_item, parent, false);
                return new BillViewHolder(view);
            }

            @Override
            public void onBindViewHolder(@NonNull BillAdapter.BillViewHolder holder, int position) {
                holder.setUpUI(mBills.get(position));
            }

            @Override
            public int getItemCount() {
                return mBills.size();
            }

            class BillViewHolder extends RecyclerView.ViewHolder {
                TextView billAmount , billListItemTitle ,billDiscount,billTotal,billDate,billCategory;
                Bill bill;
                public BillViewHolder(@NonNull View itemView) {
                    super(itemView);

                    billListItemTitle = itemView.findViewById(R.id.billListItemTitle);
                    billAmount = itemView.findViewById(R.id.billAmount);
                    billDiscount = itemView.findViewById(R.id.billDiscount);
                    billTotal = itemView.findViewById(R.id.billTotal);
                    billDate = itemView.findViewById(R.id.billDate);
                    billCategory = itemView.findViewById(R.id.billCategory);


                    itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mListener.goToBillSummary(bill);
                        }
                    });

                    itemView.findViewById(R.id.deleteBill).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mListener.deleteBill(bill);
                            mBills.clear();
                            mBills.addAll(mListener.getAllBills());
                        }
                    });

                    itemView.findViewById(R.id.editBill).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mListener.goToEditBill(bill);
                            mBills.clear();
                            mBills.addAll(mListener.getAllBills());
                        }
                    });
                }

                void setUpUI(Bill currentBill) {

                    this.bill = currentBill;

                    Double discount = currentBill.getDiscount();
                    Double amount = currentBill.getAmount();
                    String amountString = String.format("%.2f", amount);
                    Double total = amount - (discount * amount);
                    String totalString = String.format("%.2f", total);
                    String dollarDiscount = String.format("%.2f", discount * amount);
                    String discountPcString = String.format("%.0f", discount * 100);
                    String discountString = (discountPcString + "%" + " ($" + dollarDiscount + ")");


                    billListItemTitle.setText(currentBill.getName());

                    billAmount.setText("Bill Amount: $" + amountString);

                    billDiscount.setText("Discount: " + discountString);

                    billTotal.setText("Total: $" + totalString);

                    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                    billDate.setText("Bill Date: " + sdf.format(currentBill.getBillDate()));

                    billCategory.setText("Category: " + currentBill.getCategory());
                }

            }
    }

}

