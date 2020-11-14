# MyLoop

## Table of Contents
1. [Overview](#Overview)
1. [Product Spec](#Product-Spec)
1. [Wireframes](#Wireframes)
2. [Schema](#Schema)

## Overview
### Description
This app helps users to find a path of specified length that starts and ends at the same position.

### App Evaluation

- **Category:** Health and Fitness
- **Mobile:** Uses maps and location feature that allows users to find loop paths. It also has a camera that is used to capture and share scenery.
- **Story:** Say you are in a new town or unfamiliar place and you want to find a cycling/ jogging path of a certain length, would not it be nice to find a cyclic path? This app will help the user to find his/her desired path to enjoy.
- **Market:** Anyone who likes to run/jog, walk or cycle would enjoy this app. If a user enjoys discovering new places and wants to explore new paths via a loop this app will be perfect.
- **Habit:** Users will check for new paths/trails daily. Users can explore endless new trails and take as many photos as they wish. 
- **Scope:** Given that we have to interact with the Maps and Strava API's and work with GPS navigation, this app will be quite challenging to implement. However, a stripped down version of this app is nonetheless interesting to build. Furthermore, I'd say that our product is well fleshed out.

## Product Spec

### 1. User Stories (Required and Optional)

**Required Must-have Stories**

* User can sign up for a new account
* User can login
* User can logout
* User can search for a trail
* User can view detailed information about a trail
* User can get a path that has the same start and destination (Cyclic path)
* User can take a photo of the scenery 
* User can view all photos taken


**Optional Nice-to-have Stories**

* Keep track of path completed (competition)
* Favorite a path
* Suggest trails based on favorites
* Count calories
* Leaderboards
* Trail Ranks (based on scenery, convenience, number of people completed the path)
* Weather forecast

### 2. Screen Archetypes

* Login Screen
   * user should be able to login
* Register Screen
   * user create or login to their account
* Explore Screen
   * shows top rated nearby paths 
   * shows suggested trails
   * user can search for a trail/path
   * user can favorite a trail or path
* Detail Screen
   * user can view detailed information about the selected path or trail 
* Navigation Screen
   * user can use a map to navigate to the selected trail or path
   * display weather
* Camera Screen
   * user can take a photo of a path/scenery 
* Profile Screen
   * user can view past and favorited trails 
   * user can view photos taken
   * health profile (includes calories burnt, total length traveled)

### 3. Navigation

**Tab Navigation** (Tab to Screen)

* Explore
* Navigate
* Camera
* Profile

**Flow Navigation** (Screen to Screen)

* Login Screen
   * Explore Screen
* Register Screen
   * Explore Screen
* Explore Screen
   * Detail Screen
   * Profile Screen
   * Camera Screen
* Camera Screen
   * Profile Screen 
* Profile Screen
   * Explore Screen 

## Wireframes
[Add picture of your hand sketched wireframes in this section]
<img src="YOUR_WIREFRAME_IMAGE_URL" width=600>

### [BONUS] Digital Wireframes & Mockups

### [BONUS] Interactive Prototype

## Schema 
[This section will be completed in Unit 9]
### Models
[Add table of models]
### Networking
- [Add list of network requests by screen ]
- [Create basic snippets for each Parse network request]
- [OPTIONAL: List endpoints if using existing API such as Yelp]
