# Reflection Questions

Answer these questions thoroughly after completing the assignment, using examples from your code. Good answers will be 1-2 paragraphs that cite specific code examples and show a meaningful reflection on how your development went, and how it could be improved in the future.

## Question 1

Compare the difficulty integrating (that is, connecting new code into the existing structure) the new apportionment features (Hamilton and Huntington-Hill) to code in HW3 vs. integrating Excel support to your code in HW1 Part 2. Which change was easier? Justify your answer. Answer this question honestly! If you think Prof. McBurney's design sucks and is hard to work with, we want to know! (If members of your HW3 group was on different teams for HW1, then they should answer this question separately.)

## Answer 1
* (Karen, Rex) In our opinion it was easier to integrate excel in HW1 because all we did was make another class and read it. We had already made
csv file reader so the format was fairly similar. In the other classes we only changed a little to handle the xlsx files, so it wasn't too bad
there either. We also wrote the other code, so we understood quicker what was needed to be done. Integrating to someone else's code although was
fairly simple, we still had to look at the other classes to see what was going on. Overall it was a hard choice to pick which one, but the
familiarity with our own code made us lean towards the implementation in HW 1.
* (Herin) The difficulty in integrating the new apportionment features to code in HW3 was higher than integrating Excel support to the code in HW1 Part2. Both were equal in the sense that existing code was not modified extensively but new code for the new features had to be added to handle the different instances. For example, implementing the Excel support required a new class to read in Excel files while allowing the command line arguments to have the xlcs file extension. This required the command lines to be able to handle the two types of files. However, in order to integrate the two new apportionment features we had to account for a diverse range of command line inputs with arguments or without (which would automatically point to default values for the ones not defined). Since this required more accommodation to existing code, the integration of new apportionment features was a little harder in difficulty.

 
## Question 2

What is the benefit of the Factory classes that handle instantiating the relevant objects? How this could make future changes easier? Be specific about what could you could expect to change.

## Answer 2