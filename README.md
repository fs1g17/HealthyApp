# HealthyZ: A Healthy Eating App


# About

HealthyZ is an Android cloud app that uses collaborative filtering to suggest food items that will maximise the user’s Healthy Eating Index (HEI). 


# How it Works

Many industries have seen great success with recommender systems. Netflix suggests movies, Spotify suggests songs. So, why don’t we use the same approach to help people develop healthy habits? This is what inspired me to create HealthyZ. 

The premise is simple, people are more likely to stick to a diet that is more enjoyable. The burden of forcing down chicken that is drier than the Sahara desert and broccoli that tastes like TV static becomes increasingly difficult with the ever-growing temptation of indulgence in sugary sweets and savoury snacks. 

It is difficult to define what makes a diet healthy, so in this project I reference HEI, which is the degree to which a diet complies with the Dietary Guidelines for Americans (DGA). HEI is measured across 13 different components, and an overall score is indicative of the quality of the diet. It is important to note that HEI assesses the quality of the diet, not considering the quantity of food consumed (there are many calorie tracking apps available). 


## Food Diary 

HealthyZ learns about a user by allowing them to keep a record of the foods they eat. The UI is simple to use and very intuitive. The user can add meals, add food items to meals, edit and delete food items from meals and meals altogether. Previous days of eating can be accessed with a calendar widget. Images below show how the food diary looks like in the application.

<img src="/images/food_diary_fragment.png" height=500 title="hello"> <img src="/images/date_picker.jpg" height=500> <img src="/images/my_input_food_diary.jpg" height=500>

## Analysis 
The application analyses the user input to produce an estimate of the user's HEI, which is displayed as a graph and a table in the Summary section of the app. In the same section the user can view their suggestions. Below are images showing the application in action, based on my personal food diary.

<img src="/images/hei_table_visible.jpg" height=500> <img src="/images/my_input_recommendations.jpg" height=500>

# Report 
A detailed report discussing the project and the development process can be found [here](/images/Report_fs1g17.pdf). A brief overview of the inner workings of the application can be found here.
