# Project Manager - Domain-Driven Design sample
Project Manager is a sample REST service implemented using Domain-Driven Design technique.
Its primary goal is to help me to conduct a Domain-Driven Design workshops.
During the workshop developers implement the application on their own, following the steps in right order.

## Application summary
The Project Manager is a simple application for managing business projects at IT company.
The application is directed to project managers.
Using the application they can add projects, monitor their progress and assign teams to work on the projects.

## Steps to implement
Each branch of the repository represents a subsequent step of the overall task.
The task is to implement each step using Domain-Driven Design rules, so the unit tests can successfully pass.
Checkout the _master_ branch.
Get known with the starting code, it contains the parts that are not focused on Domain-Driven Design modelling but are necessary for application to work properly.

### Step 1 - Create a team
The user can create new **teams**.
Every team is **named**.
To manage teams more efficient the user will need an information on how busy a team currently is.
To fulfil this requirement the application must display **how many projects a team is currently implementing**.
An unnamed team cannot be created.
A team cannot be created if it already exists.

##### To do
Checkout the _step-1-start_ branch.
Implement the `TeamsEndpoint.createTeam(...)` method, so the unit tests can successfully pass.
Compare your solution with the _step-1-done_ branch.

### Step 2 - Assign an employee to a team
Every team consists of **members** that are company's **employees**.
It is important for a team member that, besides of having a **first** and **last name**, he has a known **job position**.
Job position can be one of: developer, scrum master or product owner.

##### To do
Checkout the _step-2-start_ branch.
Implement the `TeamsEndpoint.addMemberToTeam(...)` method, so the unit tests can successfully pass.
Compare your solution with the _step-2-done_ branch.
