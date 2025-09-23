# Project 1 Summary and Requirements
Build a fullstack application using Spring Boot & React. Back your data with a SQL database, expose with an HTTP API, and a webapp UI.

## Technology Requirements
- Spring Boot
- Spring Web, Spring JPA (or JDBC)
- SQL (H2 embedded, Postgres, etc)
- React
- Maven
- GitHub

 ## Deadline & Presentation
- First Checkpoint: 9/26
- Final Presentation: 10/10

## Example Project - Employee Reimbursement System
A system for employees to submit reimbursement tickets, and for managers to view and approve/deny them.

Employee Users can:
- Create an account
- Create a new reimbursement ticket
- See all reimbursement tickets
- See their reimbursement tickets
- See only pending reimbursement tickets (filter)
- Edit a reimbursement ticket

Manager Users can:
- See all reimbursements
- See all pending reimbursements
- Resolve (approve/deny) a reimbursement
- See all users
- Delete a user

Optional Ideas:
- Users who are not logged in can only attempt to log in or register for a new account
- Logging of the service layer
- Test suites for the service layer
- Logging out functionality

Dependencies:
- JPA
- Spring Web
- Devtools? (for live reloading)

Then use:
npx create-react-app frontend