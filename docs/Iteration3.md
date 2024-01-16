# Iteration 3

## This Iteration
This iteration, we decided to only take on one feature that would allow us to categorize our
questions to courses. When we create a question in a course, the question will automatically be
assigned a tag with that course. The feature was large enough in scope that we did not feel the need
to take on extra features and focus on finishing up and improving any outstanding ones. We also 
spent a lot of time cleaning up some of our technical debt, writing unit, integration, and acceptance
tests for our application.

NOTE: DisplayQuestionTextIterator is a class that supports the UI of our cue card system. It has logic to iterate through the question list. It is untestable as it's only purpose is to support the UI layer and act as a getter.

## Features Finished:
- Question Organization

## Features in Progress:
- Quiz Game

## User Stories Finished:
- Edit Question
- Add Tags to Question
- View Questions by Tag
- Display Menu Before Quiz

## User Stories in Progress:
- Add Quiz
- Delete Quiz

### Architecture Document
We updated our [Architecture](Architecture.md) document.

## Next Iteration
For future iterations we would like to :
- Implement the following features:
  - Progress report
  - Filter and Search for items by tag
- Polish the following features:
  - Quiz Game
    - Being able to add/delete different quizzes
