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

import java.util.ArrayList;
import java.util.Arrays;

import edu.uncc.assignment10.databinding.FragmentSelectCategoryBinding;


public class SelectCategoryFragment extends Fragment {

    String[] mCategories = {"Housing", "Transportation", "Food", "Health", "Other"};

    public SelectCategoryFragment() {
        // Required empty public constructor
    }

    FragmentSelectCategoryBinding binding;
    CategoryAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSelectCategoryBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onCancelSelectCategory();
            }
        });

        adapter = new SelectCategoryFragment.CategoryAdapter();
        binding.listView.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.listView.setAdapter(adapter);

    }

    SelectCategoryListener mListener;

    ArrayList<String> category = new ArrayList<>(Arrays.asList(mCategories));

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof SelectCategoryListener) {
            mListener = (SelectCategoryListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement SelectCategoryListener");
        }
    }

    interface SelectCategoryListener {
        void selectCategory(String category);
        void onCancelSelectCategory();
    }


    class CategoryAdapter extends RecyclerView.Adapter<SelectCategoryFragment.CategoryAdapter.CategoryViewHolder>{
        @NonNull
        @Override
        public SelectCategoryFragment.CategoryAdapter.CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.category_row_item, parent, false);
            return new SelectCategoryFragment.CategoryAdapter.CategoryViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull SelectCategoryFragment.CategoryAdapter.CategoryViewHolder holder, int position) {
            holder.setUpUI(category.get(position));
        }

        @Override
        public int getItemCount() {
            return category.size();
        }

        class CategoryViewHolder extends RecyclerView.ViewHolder {

            String categorySelected;
            TextView categoryText;
            public CategoryViewHolder(@NonNull View itemView) {
                super(itemView);

                categoryText = itemView.findViewById(R.id.categoryText);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mListener.selectCategory(categorySelected);
                    }
                });


            }

            void setUpUI(String category) {
                categorySelected = category;
                categoryText.setText(category);
            }

        }
    }
}