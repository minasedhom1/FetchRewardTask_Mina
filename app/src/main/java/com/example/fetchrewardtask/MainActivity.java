package com.example.fetchrewardtask;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    ApiInterface apiInterface=ApiClient.getClient().create(ApiInterface.class);
    ExpandableListView expandableListViewExample;
    ExpandableListAdapter expandableListAdapter;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_main);

        expandableListViewExample = findViewById(R.id.expandableListViewSample);
        progressBar = findViewById(R.id.progress_bar);

        Call<List<Item>> call = apiInterface.getItemList();
        call.enqueue(new Callback<List<Item>>() {
            @Override
            public void onResponse(@NonNull Call<List<Item>> call, @NonNull Response<List<Item>> response) {
                if (response.isSuccessful() && response.code() == 200) {
                    List<Item> itemsList = response.body();
                    if (itemsList != null) {
                        Map<Integer, List<Item> > hm = groupAndFilterItems(itemsList);
                        expandableListAdapter = new CustomizedExpandableListAdapter(MainActivity.this, hm);
                        expandableListViewExample.setAdapter(expandableListAdapter);
                        progressBar.setVisibility(View.GONE);
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Something went wrong! Please try again later",
                            Toast.LENGTH_SHORT).show();
                    Log.d("error message", response.errorBody().toString());
                    progressBar.setVisibility(View.GONE);
                }
            }
            @Override
            public void onFailure(@NonNull Call<List<Item>> call, @NonNull Throwable t) {
                Toast.makeText(MainActivity.this, "Something went wrong! Please try again later",
                        Toast.LENGTH_SHORT).show();
                t.printStackTrace();
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    // Here, I used Tree Map to group the items by their list ID while keep them ordered by list ID
    //@RequiresApi(api = Build.VERSION_CODES.N)
    private Map<Integer, List<Item> > groupAndFilterItems (List<Item> apiList) {
        // (Sort by List ID) I used TreeMap because its already sorted by key
        // Another solution using Comparator interface included bellow (Commented)
        Map<Integer, List<Item> > itemsMap = new TreeMap<>();
        final long startTime = System.currentTimeMillis();
        for (Item item : apiList) {
            if (item.getName() != null && !item.getName().equals("")) {
                List <Item> itemsList = itemsMap.get(item.getList_id());
                if(itemsList == null) {
                    itemsList = new ArrayList<>();
                    itemsList.add(item);
                    itemsMap.put(item.getList_id(), itemsList);
                } else {
                    // add if item is not already in list
                    if(!itemsList.contains(item)) itemsList.add(item);
                }
            //itemsMap.computeIfAbsent(item.getList_id(), k -> new ArrayList<>()).add(item);
            }
        }
        // Sort by name - I used the item id for more accurate results
        // comparing integers instead of comparing strings
       for (Map.Entry<Integer, List<Item>> set : itemsMap.entrySet()) {
             Collections.sort(set.getValue(), new Comparator<Item>() {
                  @Override
                  public int compare(Item t1, Item t2) {
                      return Integer.compare(t1.getId(),t2.getId());
                  }
              });
        }
        final long endTime = System.currentTimeMillis();
        System.out.println("Total execution time: " + (endTime - startTime));
        return itemsMap;
    }




    // Another way if I am targeting a list instead of a Map to display my data
    //I used the sort() method from the collection class

   /* @RequiresApi(api = Build.VERSION_CODES.N)
    private  List<Item> groupAndFilterItems2 (List<Item> apiList) {
        List<Item> filteredList = new ArrayList<>();

        for (Item item : apiList) {
            if (item.getName() != null && !item.getName().equals("")) {
                filteredList.add(item);
            }
        }
        // Comparator<Item> compareByListId = (Item i1, Item i2) -> Integer.compare(i1.getList_id(),i2.getList_id());
         Collections.sort(filteredList, new Comparator<Item>() {
             @Override
             public int compare(Item item, Item t1) {
                 return 0;
             }
         });

        // If the needed result is one list instead of the hashmap using this :
        Comparator<Item> compareByName = Comparator
                .comparing(Item::getList_id)
                .thenComparing(Item::getId);
        Collections.sort(filteredList,compareByName);
        return filteredList;
    }*/
}