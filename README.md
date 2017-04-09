# Project Manager - Domain-Driven Design workshop application
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

The following describes steps to implement using an ubiquitous language.
The description contains hints on how to design a model from the business requirements.
A hint is a bold part of the text with a suggestion on what domain building block should be used:
 - _(E)_ - entity
 - _(EP)_ - entity property
 - _(VO)_ - value object
 - _(VOP)_ - value object property
 - _(DS)_ - domain service
 - _(?)_ - try to guess :-)
 
The `layers` package contains examples on how to properly implement and organize Domain-Driven Design building blocks and other component types. 

### Step 1 - Create a team
The user can create a new **team**<sub>_(E)_</sub>.
Every team is **named**<sub>_(EP)_</sub>.
To manage teams more efficient the user will need an information on how busy a team currently is.
To fulfil this requirement the application must display **how many projects a team is currently implementing**<sub>_(EP)_</sub>.
An unnamed team cannot be created.
A team cannot be created if it already exists.

##### To do
Checkout the _step-1-start_ branch.
Implement the `TeamController.createTeam(...)` method, so the unit tests can successfully pass.
Compare your solution with the _step-1-finish_ branch.

### Step 2 - Add a member to a team
The user can add a member to a team.
Every team consists of **members**<sub>_(EP)_</sub> that are company's **employees**<sub>_(VO)_</sub>.
It is important for an employee that, besides of having a **first**<sub>_(VOP)_</sub> and **last name**<sub>_(VOP)_</sub>, he has a known **job position**<sub>_(VOP)_</sub>.
Job position can be one of: developer, scrum master or product owner.
An employee without first name, last name or job position cannot be added.

##### To do
Checkout the _step-2-start_ branch.
Implement the `TeamController.addMemberToTeam(...)` method, so the unit tests can successfully pass.
Compare your solution with the _step-2-finish_ branch.

### Step 3 - Show teams
The user can browse teams.
He can see their members and on how many projects teams are working right now.
If a team is working on more than 3 projects the user sees it as a busy team.

##### To do
Checkout the _step-3-start_ branch.
Implement the `TeamController.getTeams(...)` method, so the unit tests can successfully pass.
Compare your solution with the _step-3-finish_ branch.

### Step 4 - Create a project draft
The user can create **project**<sub>_(E)_</sub> drafts.
A project draft includes minimum information about the project.
An application must automatically **generate an unique project identifier**<sub>_(DS)_</sub>.
A draft also requires a project **name**<sub>_(EP)_</sub>.
A newly created project has a "to do" **status**<sub>_(EP)_</sub>.
An unnamed project draft cannot be created.

##### To do
Checkout the _step-4-start_ branch.
Implement the `ProjectController.createProject(...)` method, so the unit tests can successfully pass.
Compare your solution with the _step-4-finish_ branch.

### Step 5 - Create a full project
The user can also create a full project.
A full project is a project draft with extra information.
It must contain a list of features that are required to implement within the project.
Every **feature**<sub>_(VO)_</sub> has to be **named**<sub>_(VOP)_</sub> and it must have **status**<sub>_(VOP)_</sub> and **requirement**<sub>_(VOP)_</sub> defined.
Status can be one of: to do, in progress or done.
Requirement can be one of: optional, recommended or necessary.
An unnamed project cannot be created.
A project cannot be created if it already exists.

##### To do
Checkout the _step-5-start_ branch.
Implement the `ProjectController.createProject(...)` method, so the unit tests can successfully pass.
Compare your solution with the _step-5-finish_ branch.

### Step 6 - Edit a project
The user can edit created projects.
He can update its name, features and he can **assign a team**<sub>_(DS)_</sub> to work on a project.
If a team is assigned to project then the project counts as a project implemented by the team.

##### To do
Checkout the _step-6-start_ branch.
Implement the `ProjectController.updateProject(...)` method, so the unit tests can successfully pass.
Compare your solution with the _step-6-finish_ branch.

### Step 7 - Show projects
The user can browse projects.
He can see their list.
Every item on the list contains a project name and status.

##### To do
Checkout the _step-7-start_ branch.
Implement the `ProjectController.getProjects(...)` method, so the unit tests can successfully pass.
Compare your solution with the _step-7-finish_ branch.

### Step 8 - Show a specific project
The user can also browse a specific project when he clicks on it in the project list.
He can see all information about the project.

##### To do
Checkout the _step-8-start_ branch.
Implement the `ProjectController.getProject(...)` method, so the unit tests can successfully pass.
Compare your solution with the _step-8-finish_ branch.

### Step 9 - Start a project
The user can start a project only if the project is assigned to a team.
When the project starts its status is changed to "in progress".

##### To do
Checkout the _step-9-start_ branch.
Implement the `ProjectController.startProject(...)` method, so the unit tests can successfully pass.
Compare your solution with the _step-9-finish_ branch.

### Step 10 - End a project
The user can end a started project when **all of the features in the project are done**<sub>_(?)_</sub>.
Sometimes the users manager can give him a permission to end a project when **only the necessary features are done**<sub>_(?)_</sub>.
Ended projects needs to be reported in the company's end year review.
To do that the application needs to **inform a Reporting Service**<sub>_(?)_</sub> about the ended project by sending its **identifier**<sub>_(?)_</sub>.

##### To do
Checkout the _step-10-start_ branch.
Implement the `ProjectController.endProject(...)` method, so the unit tests can successfully pass.
Compare your solution with the _step-10-finish_ branch.
