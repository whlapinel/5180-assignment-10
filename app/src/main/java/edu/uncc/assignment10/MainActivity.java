package edu.uncc.assignment10;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements BillsFragment.BillsListener,
        BillSummaryFragment.BillSummaryListener, SelectSortFragment.SortListener,
        CreateBillFragment.CreateBillListener, SelectCategoryFragment.SelectCategoryListener,
        SelectDiscountFragment.SelectDiscountListener, SelectBillDateFragment.SelectDateBillListener, EditBillFragment.EditBillListener {
    ArrayList<Bill> mBills = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        mBills.add(new Bill("Groceries", "Milk", new Date(124, 0, 1), 0.10, 25.00));
        mBills.add(new Bill("Entertainment", "Movies", new Date(124, 4, 20), 0.0, 30.00));
        mBills.add(new Bill("Dining", "Restaurant", new Date(124, 3, 10), 0.15, 75.00));
        mBills.add(new Bill("Housing", "Rent", new Date(124, 2, 31), 0.05, 1000.00));
        mBills.add(new Bill("Utilities", "Electricity", new Date(124, 1, 15), 0.0, 50.00));

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main, new BillsFragment(), "bills-fragment")
                .commit();
    }


    @Override
    public void goToBillSummary(Bill bill) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main, BillSummaryFragment.newInstance(bill))
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void goToEditBill(Bill bill) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main, EditBillFragment.newInstance(bill), "edit-bill-fragment")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public ArrayList<Bill> getAllBills() {
        return mBills;
    }

    @Override
    public void gotoCreateBill() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main, new CreateBillFragment(), "create-bill-fragment")
                .addToBackStack(null)
                .commit();
    }

   /* @Override
    public void gotoSortSelection() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main, new SelectSortFragment())
                .addToBackStack(null)
                .commit();
    }*/

    @Override
    public void clearAllBills() {
        mBills.clear();
    }

    @Override
    public void deleteBill(Bill bill) {
        mBills.remove(bill);
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void closeBillSummary() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onSortSelected(String sortAttribute, String sortOrder) {
        BillsFragment billsFragment = (BillsFragment) getSupportFragmentManager().findFragmentByTag("bills-fragment");
        if(billsFragment != null){
            billsFragment.setSortItems(sortAttribute, sortOrder);
        }
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onSortCancel() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void createBillSuccessful(Bill bill) {
        mBills.add(bill);
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void createBillCancel() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void editBillSuccessful(Bill bill) {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void editBillCancel() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void gotoSelectCategory() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main, new SelectCategoryFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void gotoSelectDate() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main, new SelectBillDateFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void gotoSelectDiscount() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main, new SelectDiscountFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void selectCategory(String category) {
        CreateBillFragment createBillFragment = (CreateBillFragment) getSupportFragmentManager().findFragmentByTag("create-bill-fragment");
        if(createBillFragment != null){
            createBillFragment.setSelectedCategory(category);
        }

        EditBillFragment editBillFragment = (EditBillFragment) getSupportFragmentManager().findFragmentByTag("edit-bill-fragment");
        if(editBillFragment != null){
            editBillFragment.setSelectedCategory(category);
        }

        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onCancelSelectCategory() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onDiscountSelected(double discount) {
        CreateBillFragment createBillFragment = (CreateBillFragment) getSupportFragmentManager().findFragmentByTag("create-bill-fragment");
        if(createBillFragment != null){
            createBillFragment.setSelectedDiscount(discount);
        }

        EditBillFragment editBillFragment = (EditBillFragment) getSupportFragmentManager().findFragmentByTag("edit-bill-fragment");
        if(editBillFragment != null){
            editBillFragment.setSelectedDiscount(discount);
        }

        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onCancelSelectDiscount() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onBillDateSelected(Date date) {
        CreateBillFragment createBillFragment = (CreateBillFragment) getSupportFragmentManager().findFragmentByTag("create-bill-fragment");
        if(createBillFragment != null){
            createBillFragment.setSelectedBillDate(date);
        }

        EditBillFragment editBillFragment = (EditBillFragment) getSupportFragmentManager().findFragmentByTag("edit-bill-fragment");
        if(editBillFragment != null){
            editBillFragment.setSelectedBillDate(date);
        }

        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onCancelSelectBillDate() {
        getSupportFragmentManager().popBackStack();
    }
}