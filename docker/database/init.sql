create database if not exists `gamificator_db`;

USE `gamificator_db`;

grant all on gamificator_db.* to 'springuser'@'%'; -- Gives all privileges to the new user on the newly created database