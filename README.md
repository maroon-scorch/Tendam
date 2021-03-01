# cs0320 Term Project 2021

 _Fill this in with three unique ideas! (Due by March 1)_
# Project Idea(s):
## Team Members:
- Edward Yan
- Heon Lee
- Matthew Ji
- Ria Rajesh
- Ricky (Wenqi) Zhong

## Team Strengths and Weaknesses: 
#### Edward Yan:
- Strength: Broad understanding of popular APIs
- Weakness: Documentation is totally incomprehensible, hates (paying) money
#### Heon Lee: 
- Strength: Communication; keeping promises with other students (to finish assigned tasks by an assigned date)
- Weaknesses: Not fully aware of all the tools Java has; graphic design/UI/UX
#### Matthew Ji:
- Strengths: Cloud-based (AWS) Services (Solutions Architect), Serverless Framework, REACT
- Weaknesses: Traditional Backend Services (ex. PHP, SQL), Machine Learning
#### Ria Rajesh:
- Strength: starts things prior to the night before they are due
- Weaknesses: bad at testing, no prior experience with object oriented programming
#### Ricky Zhong:
- Strength: Quick Googler - my queries are fast and I enjoy writing documentation
- Weaknesses:
  - New to such long-term projects
  - HTML and anything that has to do with GUI (Does anyone have any artistic skills?)

## Project Idea(s): Fill this in with three unique ideas! (Due by March 1) 

### Idea 1: Matching Platform

#### What problem does this solve?
Apps like Tinder often leave out groups by its use of collaborative filtering to aggregate opinions; we want to make sure that everyone is equally included (Our app might not solve this, but we will sure try to). Most of these apps are geared towards dating and specifically towards dating for young adults.

#### How does this solve the problem?
- Less focus on dating, if at all
- More interactive and fun
- Dating to be more inclusive and include minority groups.

#### Critical features + why they’re included + challenging aspect of each feature
- User Profile Creation:
  - The ability to freely customize one's profile and persona is instrumental to the functioning of a Dating App as it is the window from which others can peek, the light that is the key to understanding, grasping, the surface of their inner nature. A user profile provides the customizability that clients would desire. This could be challenging in the sense on how much degree of freedom should be relinquished to the user (and some difficulty in implementing them), perhaps what to add in the user profile or what to include in the potential options for the user. These are all interesting design decisions to ponder about. Of course, with profiles, we should also think about implementing private chatting feature, blocklist, etc.
- Capacity to find Similar Matches (Recommendation):
  - The capacity to find similar matches include not only geographical locations but also similar interests, likes, favors, hobbies, etc. These matching algorithms would be built based on analyzing the more factors than just pure geographical location (maybe consider the Levenshtein Distance, etc.) to recommend the user. The ability to feed the information to the user is critical in the information age as everything is going so fast, by giving the user all the consumption of data that they would need, they could be met with people without even needing to search themselves. They can consume information and learn with only a single swipe. The challenge of this would definitely be how to break down these abstract concepts such as interests, hobbies, etc. in algorithmics and data perspective to recommend the users.
- In-app Search Algorithm:
  - Of course, no recommendation algorithms are perfect. Therefore, we also need to enable search features so that users can search/filter to find users on their own if so desired. This is important because it would enable an extra layer of freedom for the user. Of course, there's also a layer of challenge here as determining what to be displayed in the search afterwards means that the designer would have to make the decision for the user at some level of priority, and this needs to be as objective as possible.
- Security and Privacy of Each User:
  - The security and privacy of each user are crtical. One user couldn't just simply switch to another link and obtain access to another user's account. The challeneg of security though is that there's often a tradeoff between security vs privacy as more security often means a tradeoff in privacy. Our program should aim to find the equilibrium between the two.
- Minigames:
  - Minigames are a large part of it.
  - More engaging, more interesting from a data perspective
  - Challenging because each mini-game can take time to design and produce.
  - Platform of each mini-game is also ambiguous as well as how they’re going to be written
- (Nice to have) Video Call Feature:
  - Zoom is getting very popular as the alternative to gathering during Quarantine, so our app should also follow the trend. This is done to bring people together and for matches to meet each other face to face virtually. The challenege is that incorporating video call feature would be extremely difficult.

### Idea 2: A social hangout location recommender
Think Trip Advisor but focus on domestic casual activities rather than catering towards international tourists. An existing app that is comparable would be something like DianPing, a social media app that in China that aggregates massive amounts of data and user opinion for use in making all kinds of “restaurant”, social hangout, shopping mall, etc. recommendations.

#### Problem:
Lots of people try to find places to meet and hangout with friends, but the decision making process in many places simply involves performing a google search or looking up simple terms like “restaurants” in google maps. Apps that are focused on making these recommendations as well as existing with intent to gather thousands of user comments are largely nonexistent in North America. 
Trip advisor is the only really existing app for something like that in the U.S. and Canada, but it’s for tourists and nobody wants to feel like a foreigner in their own country.

#### Challenges:
Integrating existing location-based APIs (like Google Maps data) and however that will work
Getting data
Making use of massive databases could pose large challenges.

#### Critical Features:
- Data, More data
- Focus on user-generated reviews and images
- As many filters as possible (distance, type of activity, type of food, etc.)
- Machine-generated recommendations based on these factors


### Idea 3: Some kind of card or turn-based strategy game.
Some examples that come to mind to think about (disregarding the far larger scale): Sid Meier’s Civilization games, Hearthstone, Slay the Spire, Gwent

#### Problem:
Boredom.

#### Challenges:
Any game, even the most basic, will feature a pretty significant non-programming component that relates to game mechanics, game design, asset production, music, and art, which we may or may not be good at.
Development platform will certainly be another issue. Is this to be made in Unity? Unity code is written in C#, which is similar to Java but not the same. Developing on an engine not all of us have experience in may also pose difficulties of its own

#### Critical Features:
Not entirely sure, but likely some turn-based multiplayer strategy environment with a simple interface.


**Mentor TA:** _Put your mentor TA's name and email here once you're assigned one!_

## Meetings
_On your first meeting with your mentor TA, you should plan dates for at least the following meetings:_

**Specs, Mockup, and Design Meeting:** _(Schedule for on or before March 15)_

**4-Way Checkpoint:** _(Schedule for on or before April 5)_

**Adversary Checkpoint:** _(Schedule for on or before April 12 once you are assigned an adversary TA)_

## How to Build and Run
_A necessary part of any README!_
