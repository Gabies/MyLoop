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
- **Scope:** Given that we have to interact with Strava API's and work with GPS navigation, this app will be quite challenging to implement. However, a stripped down version of this app is nonetheless interesting to build. The app will start out by just allowing the user to log in and out of the app, find a trail then based on desired length, find a path that begins and end in the same position. 

## Product Spec

### 1. User Stories (Required and Optional)

**Required Must-have Stories**

* User can sign up for a new account
* User can login
* User can logout
* User can search for a trail
* User can view detailed information about a trail
* User can get a path on the trail which has the same start and destination (Cyclic path)
* User can select the desired length of the path

**Optional Nice-to-have Stories**

* Keep track of path completed (competition)
* Draw a desired path on the map and save it
* Favorite a path
* Suggest trails based on favorites
* Show top rated nearby paths 
* User can take a photo of the scenery 
* User can view all photos taken
* Count calories
* Leaderboards
* Trail Ranks (based on scenery, convenience, number of people completed the path)
* Weather forecast

### 2. Screen Archetypes

* Login Screen
   * user should be able to login
* Register Screen
   * user create or login to their account
* Stream - Explore Screen
   * shows suggested trails
   * user can search for a trail/path
   * user can favorite a trail or path
* Detail Screen
   * user can view detailed information about the selected path or trail 
* Map - Navigation Screen
   * user can search for a trail
   * user can use a map to navigate to the selected trail or path
* Creation - Camera Screen
   * user can take a photo of a path/scenery 
* Profile Screen
   * user can view profile information
   * health profile (includes calories burnt, total length traveled)
* Stream - Favorites Screen
   * user can view a list of all his/her favorite trails
* Stream - Scenery Screen
   * user can view a gallery of photos taken

### 3. Navigation

**Tab Navigation** (Tab to Screen)

* Explore
* Navigate
* Camera
* Profile
* Favorites
* Scenery

**Flow Navigation** (Screen to Screen)

* Login Screen
   * Explore Screen
* Register Screen
   * Explore Screen
* Explore Screen
   * Detail Screen
   * Profile Screen
   * Navigation Screen
   * Favorites Screen
   * Scenery Screen
* Detail Screen
   * Navigation Screen
   * Explore Screen
* Navigation Screen
   * Camera Screen
   * Favorites Screen
   * Scenery Screen
* Camera Screen
   * Scenery Screen 
   * Navigation Screen
* Profile Screen
   * Explore Screen 
   * Navigation Screen
   * Favorites Screen
   * Scenery Screen

## Wireframes
<img src='Wireframe.jpg' width=600>

### [BONUS] Digital Wireframes & Mockups
<img src='Digital Wireframe.jpg' width=600>

### [BONUS] Interactive Prototype
<img src='My Loop Initial Prototype.gif' title='Video Walkthrough' width='250' />
GIF created with [LiceCap](http://www.cockos.com/licecap/).

## Schema 
### Models
  #### Trail
  
  | Property  | Type  | Description |
  | --------- | ----- | ----------- |
  | userID    | String | unique id for a user |
  | username  | Pointer to user | name of the user |
  
  
  #### User


### Networking
- [Add list of network requests by screen ]
- [Create basic snippets for each Parse network request]
- [OPTIONAL: List endpoints if using existing API such as Yelp]
