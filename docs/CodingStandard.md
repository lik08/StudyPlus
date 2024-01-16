# Standards

## Branch Names
All branches for developer tasks should have the feature number followed by ticket number and a short description of the ticket.
```
F1/123/create_question_object
```

## Class and Variable Naming
All inner class variable names (not shared) shall be camel case.
```java
questionId
```

All Test classes shall be have the suffix Test.
```java
QuestionHandlerTest
```

## Format
All opening curly brackets shall sit *beside* the line, not under, followed by a newline.
```java
public boolean doMethod() {
```

All indents are four spaces. All indenting is done with tabs.

Matching braces always line up vertically in the same column as their construct.
```java
if(condition) {
    do thing
}
```

All test methods with have the suffix test.
```java
public void testSomeMethod() {
```

All classes shall be setup as follows:
```java
class Order {
    //fields
    
    //constructors
    
    //methods
    
    @override
    //methods
}
```


## Documentation
As a rule of thumb, code should be self-explanatory 99% of the time. If you feel the need to comment, there's a good chance you can write it in a simpler, better way.

Of course, sometimes you do need to comment to clarify something, or point out something that isn't obvious. However, they should be used minimally.
