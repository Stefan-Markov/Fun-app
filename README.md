# Fun-app
Full stack project with React, Spring, MySQL.

Back end

- MySQL DB
- over 6 independent entities
- 3 type of roles - ROOT, ADMIN, USER
- Manageable roles
- Spring security
- Custom security methods
- JPQL and native queries
- Spring SpEl
- Secure routes, controllers, password, internal state of app
- 6 Repositories
- JPA
- @Async is used - create new thread for the operation, while the DB still indexing and returning the result
- JWT projection with claims: username, roles
- JWT validate, authenticate and refresh procedures
- Error handling
- Validate data consuming
- Data quality protect
- Binding entities for validation
- REST controllers architecture to communicate on frond end with React

Front end

- React functional approach
- CSS local manage strategy
- Route guards
- Context 
- Error boundary
- Hooks approach
- HOC
- Components segregation
- Consume JWT for Auth
- Consume JSON from Spring 
- App on parts - private part, part for guest, part for admin

Functionality

- Login
- Register
- Logout
- Create user
- Delete user
- Add or remove admin role
- Create Joke
- Manage joke
- Edit joke
- Delete joke
- Add joke to favourites
- Remove joke from favourites
- Add comment
- Delete comment 
- Read comments
- Add likes
- Check who already like the joke
- Admin panel
- Consume DB information based on search by keywords and show dynamic content
- Validate user data inputs and show proper message, if needed
- Over four different search engines for: edit data, search data, manage roles, user and jokes
- Search engines to fetch data from DB and show dynamic content based on input
- Search user by username, roles
- Search joke by keyword (by regex)
- Rendering items based on colletions, ids, data or inputs
- Dynamic component interaction based on user actions
- Private part, user part, admin part
- Navigation bar changed based on roles
- Proper error(data quality requirements from inputs) fallback and render to user


To run the app, please provide yours username and password to MySQL db in the app.props.

