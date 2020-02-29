# javaassignment2

Introduction
This quiz application was designed as a teaching tool for users who want to learn the important facts about Arrowtown. Arrowtown is charming and quirky – a delightful gold rush village nestled below the beautiful peaks that surround the sparkling Arrow River. Visitors will be enchanted by Historic Arrowtown, a New Zealand treasure.


Design
The quiz application has 3 main pages:
1.	Landing page
2.	Question pages
3.	Result page
 
 
There will be 10 questions provided in the application. Each question page will have 3 states from loading to submitting the question before transitioning to the next question. The users can see the result and score immediately in the question page after they submit the answer. The submit button will be enabled when the users select the choice and the next button will show after the users click the submit button.
 

DataGenerator Class
The DataGenerator.java will load all questions from choice.json into ArrayList. This class is called by QuestionActivity.java.
 

Model Classes
There are 2 model classes for question and answer.
1.	Quiz.java
2.	Choice.java


Activity Classes
There are 3 Activity classes implemented in this quiz application.
1.	LandingActivity.java
a.	Provide a landing page and start button to play the quiz.
2.	QuestionActivity.java
a.	Load all questions and answers from the json file and display them on the screen. This activity class will be looped until the users reach to the last question.
3.	ResultActivity.java
a.	Display the overall score that the users have from all answered questions.


Background Images
In each question, the background images will be loaded from the resource folder and displayed behind the question.
 

SharedPreference
The application uses shared preference to store the index of question and current score data across the activities.
