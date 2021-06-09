# Project Structure

The application follows a Model-View-ViewModel (MVVM) design architecture, as shown in the diagram below. 

<img src="/images/MVVM Structure.png" height=700>

The Model contains parts that are stored locally (see [here](database)), as well as parts that are stored remotely on the cloud (see [here](server)). This was done to enable indivdual user privacy and collaborative filtering simultaneously). 

The ViewModel (see [here](viewmodel)) contains classes that prepare the data for the View. 

The View (see [here](view)) contains Android classes (namely Activities and Fragments) that display the data.

# Collaborative Filtering Overview

Below is a flow diagram showing the collaborative filtering steps. I ran the backend of this project locally on my PC. 

<img src="/images/collaborative_filtering.png" height=700>
