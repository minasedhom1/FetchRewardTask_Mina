# FetchRewardTask_Mina!

![242220881_453254889256003_3284152970713244090_n](https://user-images.githubusercontent.com/22953976/134126116-772c54e7-aba0-42ab-ad73-f2ef8488bc8b.gif)

The challenges were to sort the data with two different attributes (List ID then name) & display it to the user grouped by the List ID

What I did:

1- I used Retrofit 2 to call the endpoint API and get unfiltered items' list.

2- I filtered out all the items where "name" is blank or null.

3- I used the feature of the TreeMap (is always sorted based on keys) class to sort by List ID (keys)

4- I used the Comparator interface to sort each value (each list of items) in my Tree Map by name.
(I sorted by Item Id (Int) instead of name (String) for more accurate resutls)

5- I used the Exbandable List to display the items in groups.

6- I provided another metho named groupAndFilterItems2() (commented below groupAndFilterItems() ) to filter and sort (by list id then by name) the data in one list instead of a TreeMap.


