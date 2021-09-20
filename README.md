# FetchRewardTask_Mina

What I did:

1- I used Retrofit 2 to call the endpoint API 

2- I filtered out all the items where "name" is blank or null.

3- I used the feature of the TreeMap (is always sorted based on keys) class to sort by List ID (keys)

4- I used the Comparator interface to order each value (each list of items) in my Tree Map by name.
(I sorted by Item Id (Int) instead of name (String) for more accurate resutls)

5- I used the Exbandable List to display the items in groups.
