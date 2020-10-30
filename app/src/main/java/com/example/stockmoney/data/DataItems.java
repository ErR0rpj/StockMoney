package com.example.stockmoney.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataItems {
    public static List<StockFirebaseColumns> stockmodelList = new ArrayList<>();
    public static List<StockFirebaseColumns> stockmodelListfilterd = new ArrayList<>();
    public static UserDetails currentUser = new UserDetails();
    public static List<StocksOwn> stocksOwnList = new ArrayList<>();
    public static Map<String, Double> currentPriceMap = new HashMap<String, Double>();
}
