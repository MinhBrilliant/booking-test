# Booking Management Guide
 In this project we are implementing the functions as below:
1. Register new user function: http://localhost:8081/register
2. Login function: http://localhost:8081/login
3. List Seat Types, Update Seat Type, Create Seat Type, Duplicate Seat Type, Delete Seat Type
   http://localhost:8081/book-type/page/1
   All function do not need to login
4. Seat Type list to book, Book a Seat Type. this functions need to login to use and has Role User or Admin
Note: When user login successfully. the system redirect to Seat Type list to book function.
   
How to run project
1. Pre-condition setup

   1.1. download and install git Guide: https://github.com/git-guides/install-git.
   
   1.2 download and setup maven enviroment for windown: https://www.testingdocs.com/setting-up-maven_home-on-windows-10/.
   
   1.3 download and istall docker: https://docs.docker.com/desktop/setup/install/windows-install/

   1.4 update windows subsystem for linux: run cmd: wsl --install
3. clone project from github: https://github.com/MinhBrilliant/booking-test
4. in folder of project. run cmd: docker-compose up -d --build (pre-condition you need setup docker in your PC firstly)
5. Restart service

   5.1. if restart db-> run cmd: docker restart oracle-db

   5.1. if restart app-> run cmd: docker restart booking-management-app
6. Using the functions above

7. if stop app: docker stop booking-management-app
