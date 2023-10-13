[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-24ddc0f5d75046c5622901739e7c5dd533143b0c8e959d652212380cedb1ea36.svg)](https://classroom.github.com/a/ma-cxrlC)
# Homework 3 - Incremental Development

## Authors
1) Rex Wang, xfg7ab, RexW999
2) Herin Seo, aww5kx, herinseo
3) Karen Wang, vxd4qa, wkailun

## To Run

Run ./gradlew build in the terminal to create the .jar file.
Once the .jar is created, cd build/libs, then run, java -jar Apportionment.jar (.csv or .xlsx) (The rest/look under)
For the rest you could use long arguments with (--representation (number) / --method (huntington,jefferson,adam) / --format (alphabetical,benefit,population)) / --ascending / --descending
You could also leave those blank for default options
Short flags can also be used as following: -r / -m / -f / -a / -d
when using short flags you can also combine such as -rfma (arguments) for easier use
## Contributions

List the primary contributions of each author. It is recommended to update this with your contributions after each coding session.:

### Rex Wang

* Implemented HuntingtonHill
* Made the 3 factory classes

### Herin Seo

* Created and implemented BenefitFormat class
* Answered part of question 1

### Karen Wang

* Modified the Argument class to offload work to factories 
* Implemented and supported long and short argument names 
* Handled combined Arguments

## Issues

List any known issues (bugs, incorrect behavior, etc.) at the time of submission.