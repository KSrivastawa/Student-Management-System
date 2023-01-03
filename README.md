# Student-Management-System

![smt](https://user-images.githubusercontent.com/103960690/210166517-7f0f3ae2-62bf-4e07-9b7d-4f7c1f5099c8.png)


Modules
----------------
1. User Module
2. Student Module

Project Type[Sole]:
-----------------------
- Rest API using Spring Boot and Mysql
- Authentication applied with token based using spring security

Technologies:
-------------------
- Spring Boot
- Spring Data JPA (Hibernate)
- MySQL
- Spring Security


------------------------------------------

ER-Diagram
--------------------------------------
![smsapp-ER_Daigram](https://user-images.githubusercontent.com/103960690/210269024-d2efb76f-8f0b-4629-a702-ef55502b2880.png)

----------------------------------------------------

API's [BaseURL:=> localhost:8889/api]
-----------------------------------------
User:
- /addrole  (here need to insert the role into database before register like: ROLE_ADMIN)
- /register
- /login
- /getuser/{email}


Student:
- /addstudent  (Authentication applied)
- /getallstudents  
- /updatestudent/{id}  (Authentication applied)
- /deletestudent/{id}  (Authentication applied)
- /getstudent/{id}
- /getstudents/{firstName}


