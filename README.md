This is a simple Android application that lets you set and use a pattern to unlock. You can draw a pattern by connecting at least four different dots on a 3x3 grid.

How the Code Works?
1. Setting the Pattern:
When you open the app for the first time, isSettingPattern is true. You draw a pattern by connecting at least four dots. If the pattern has at least four points, it is saved in savedPattern. The app shows "Pattern is saved". IsSettingPattern becomes false.
2. Unlocking with the Pattern:
Now, isSettingPattern is false. You draw your pattern to unlock. The app compares your pattern with savedPattern. If they are the same, it shows "Correct, device is open now!". If not, it shows "Wrong pattern!".

Important Functions:
1. onCreate():
Called when the app starts. Sets the layout to activity_main.xml. Finds the PatternView in the layout. Defines what happens when a pattern is completed.
2. patternView.onPatternCompleted:
Runs when you finish drawing a pattern. Checks if you are setting a new pattern or unlocking. 

Future Improvements:
1. Make the lines smoother when drawing.
2. Improve the design to look nicer.





