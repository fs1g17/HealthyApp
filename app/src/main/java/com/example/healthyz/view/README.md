# View in Android 

The main entry point for an Android application is an [Activity](https://developer.android.com/reference/android/app/Activity). The Activity can be split up into related elements that can be grouped into [Fragments](https://developer.android.com/guide/fragments). Fragments can be nested in Activities. Here there are 2 main Fragments that are contained in the Main Activity, the Food Diary Fragment, and the Summary Fragment. The user can navigate between them using the navigation drawer by swiping from the left of the screen, as shown in the images below (left is a screenshot, and right is the [Navigation Graph](https://developer.android.com/guide/navigation/navigation-getting-started))

<img src="/images/navigation_drawer.jpg" width=28.9%> <img src="/images/nav_graph.png" width=70.3%>

Below are images of the Meal Fragment and Food Fragment, where Food Fragments can be added to the Meal Fragments. A Food Fragment should contain the name of the food.

<img src="/images/meal_fragment.png" width=99.5%> <img src="/images/food_fragment.png" width=99.5%>

The HEI score is displayed as a Radar Chart (labels correspond to categories which are listed in the scoring table). 

<img src="/images/my_input_hei_radar_graph.jpg" width=61%>  <img src="/images/my_input_hei_table.jpg" width=38%>
