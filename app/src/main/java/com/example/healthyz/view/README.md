# View in Android 

The main entry point for an Android application is an [Activity](https://developer.android.com/reference/android/app/Activity). The Activity can be splpit up into related elements that can be grouped into [Fragments](https://developer.android.com/guide/fragments). Fragments can be nested in Activities. Here there are 2 main Fragments that are contained in the Main Activity, the Food Diary Fragment, and the Summary Fragment. The user can navigate between them using the navigation drawer by swiping from the left of the screen, as shown in the images below (left is a screenshot, and right is the [Navigation Graph](https://developer.android.com/guide/navigation/navigation-getting-started))

<img src="/images/navigation_drawer.jpg" height=500> <img src="/images/nav_graph.png" height=500>

Below are images of the Meal Fragment and Food Fragment, where Food Fragments can be added to the Meal Fragments. A Food Fragment should contain the name of the food.

<img src="/images/meal_fragment.png" width=700> <img src="/images/food_fragment.png" width=700>
