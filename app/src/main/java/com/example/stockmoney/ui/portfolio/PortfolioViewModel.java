package com.example.stockmoney.ui.portfolio;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PortfolioViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public PortfolioViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is dashboard fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}