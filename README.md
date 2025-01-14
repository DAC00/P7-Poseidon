# P7-Poseidon

# bdd.env
The application need a file named "bdd.env" in the project root directory, with four variables : DB_URL, DB_URL_TEST, DB_USERNAME and DB_PASSWORD

example : 

DB_URL=jdbc:mysql://localhost:3306/p7_poseidon

DB_URL_TEST=jdbc:mysql://localhost:3306/p7_poseidon_test

DB_USERNAME=username

DB_PASSWORD=password

# SQL
Two scripts below for the database, one for the app and the other one for testing.

[Script for the app](sql/p7_poseidon_script.sql)

[Script for testing](sql/p7_poseidon_test_script.sql)

# USER in Database
There is two users in the database with different role :

USER 1 :

username : admin

password : 123456

role : ADMIN

USER 2 :

username : user

password : 123456

role : USER