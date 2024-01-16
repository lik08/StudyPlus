# Iteration 2 Worksheet

## Paying off technical debt

1. **Changing Navigation Menu**
   
   This technical debt would be considered **inadvertent and reckless**.
   
   Our original navigation menu was only accessible through the hamburger menu in order to select different tabs/pages of the application. We received feedback that this design decision is not intuitive and smooth to non-technical individuals that will use our application. We made the decision to change the navigation to a bottom screen navigation that is always visible.
   
   [change the navigation from side to the bottom](https://code.cs.umanitoba.ca/3350-summer2023/teameleven-11/-/commit/e7e5bc0b83d54c7271d02822e1a3d1f4d40f6105)


2. **Changing Our Home Page To Courses Feature**
   
   This technical debt would be consider **prudent and deliberate**.

   Initially, we had a profile page setup as our main screen upon opening the application. Now we have paid off our debt by setting up the home page to be the courses feature. Since we only expect one user per device, it no longer made sense to implement the profile feature as our home page, and made the courses feature as our main page instead.
   
   [change navigation, remove dead classes](https://code.cs.umanitoba.ca/3350-summer2023/teameleven-11/-/commit/4cdb38e9755cd386b4ad8c1c0774bd2d7bb1a1c1)

## SOLID

We managed to find a code smell in [StarSign.java](https://code.cs.umanitoba.ca/3350-summer2023/team-8-12/-/blob/90940c45160996640c652e2902a01f618ccfb3b7/app/src/main/java/com/example/trueastrology/objects/StarSign.java#L20) file where the star sign is assigned in the object. This is a violation of single responsibility principle and dependency inversion.

This violates the single responsibility principle. DSO objects should typically be dumb and should not have logic like this.

This also violates dependency inversion since the logic depends on the object itself.

One solution to this could be creating a singleton class where parameters (string) could be sent to the singleton and return a value (int). Alternatively, the switch block could also be put in an enum.

[Issue Ticket](https://code.cs.umanitoba.ca/3350-summer2023/team-8-12/-/issues/83)


## Retrospective

We feel like we did not hit our goals in this iteration.

During sprint planning, we felt good about getting a full feature implemented and were optimistic about the number of features that could be complete in iteration 2 due to the break at the end of June. See [Iteration 1](Iteration1.md) for our future planning. However, due to unforeseen circumstances, we were not able to get all the mentioned features (epics) fully implemented due to not realizing the scope of those features.

We also tried a different approach this iteration by allowing individual members taking charge of their features and user stories by having them create the developer task. However, this did not work as well as we hoped. We realize that it was too difficult to leave it to single individuals and needed a team meeting with proper sprint planning in order to create dev takes and create appropriate time estimates.

[Issues Board](https://code.cs.umanitoba.ca/3350-summer2023/teameleven-11/-/issues)

[Iteration 1 Retrospective](Iteration1Retro.jpg)


## Design patterns

1. **Singleton**

   The entirety of the Services class act as a singleton.
   
   Example: [Services](https://code.cs.umanitoba.ca/3350-summer2023/teameleven-11/-/blob/main/app/src/main/java/comp3350/studyplus/application/Services.java)

2. **Iterator**

   Cycling through quiz game.
   
   Example: [QuizHandler](https://code.cs.umanitoba.ca/3350-summer2023/teameleven-11/-/blob/18c8fc99f8115c339ce73b51157d33be2dda3462/app/src/main/java/comp3350/studyplus/logic/QuizHandler.java#L46) and [QuizGameFragment](https://code.cs.umanitoba.ca/3350-summer2023/teameleven-11/-/blob/3a71c8b296038d63afdd7d8f5cb92a37c4a27265/app/src/main/java/comp3350/studyplus/presentation/quiz_game/QuizGameFragment.java#L54)

3. **Observer**
   
   All OnClick button is an observer.
   
   Example: [AddQuestionFragment](https://code.cs.umanitoba.ca/3350-summer2023/teameleven-11/-/blob/3a71c8b296038d63afdd7d8f5cb92a37c4a27265/app/src/main/java/comp3350/studyplus/presentation/question/AddQuestionFragment.java#L41) and [DisplayQuestionFragment](https://code.cs.umanitoba.ca/3350-summer2023/teameleven-11/-/blob/3a71c8b296038d63afdd7d8f5cb92a37c4a27265/app/src/main/java/comp3350/studyplus/presentation/question/DisplayQuestionFragment.java#L47)


## Iteration 1 Feedback fixes

[Bug: Long text get no scroll and they go under the buttons](https://code.cs.umanitoba.ca/3350-summer2023/teameleven-11/-/issues/49)

**Description:**

BUG: allowed overflow of text that went off screen.

**Fix:**

Limit the amount of characters allowed for a cue card to 250 characters. Cue cards should be short trivia questions and answers so having long paragraphs does not make sense. Having 250 character was also in-line with VARCHAR limit in our database. The fix was also related to [Bug: Blank is accepted as question input - No Validation](https://code.cs.umanitoba.ca/3350-summer2023/teameleven-11/-/issues/48) where our solution was to do some text processing by trimming whitespace.

**Commits:**

1. [bug fix - whitespace validator and trim strings](https://code.cs.umanitoba.ca/3350-summer2023/teameleven-11/-/merge_requests/33)

2. [validator to prevent text overflow in cue cards](https://code.cs.umanitoba.ca/3350-summer2023/teameleven-11/-/merge_requests/40)
