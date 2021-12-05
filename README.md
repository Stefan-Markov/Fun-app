# Fun-app
Full stack project with React, Spring, MySQL.
Back end
- MySQL DB
- 5 independent entities
- 3 type of roles - ROOT, ADMIN, USER
- Managable roles
- Spring security
- Custom security methods
- JPQL and navite queries
- Spring SpEl
- Secure routes, controllers, password, internal state of app
- 5 Repositories
- @Async is used - create new thread for the operation, while the DB still indexing and returning the result
- JWT projection
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
- Hooks approach
- Components segregation
- Consume JWT
- App on parts - private part, part for guest, part for admin

Functionality
- Login, Logout, Register
- Create User
- Create Joke
- Manage joke
- Edit joke
- Delete joke
- Add comment 
- Delete comment 
- Read comments
- Add likes
- Check who already like the joke
- Over four different search engines for: edit data, search data, manage roles, user and jokes
- Admin panel
- Consume DB information based on search by keywords and show dynamic content
- Validate user data inputs and show proper message, if needed
- Search engines to fetch data from DB and show dynamic content based on input
- Rendering items based on colletions, ids,data or inputs
