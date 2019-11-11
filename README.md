# organization
organization-user relationship with spring data Jpa

Coding Challenge (Java)
Consider the following details of an Organization and a User

Organization:
 - Name
 - Address
 - Phone

User:
 - First Name
 - Last Name
 - Email
 - Address
 - Phone

Instructions:

Design and implement a simple application serving a RESTful API to perform operations on Organization as well as
Users. Please implement the challenge in any of the Java Web Frameworks. We are looking to see how you design and
implement your model as well as your application. We expect to be able to trace an endpoint down to the data
transfer objects (DTO) that represent Organizations and Users. Feel free to provide additional documentation (UML,
SQL scripts, comments, etc) that may communicate your design choices. We expect your API to support the following

operations:
 - Create a single Organization
 - Create a single User
 - Add a User to an Organization
 - Delete a User from an Organization
 - Read all Users who belong to a specific Organization
 - Read all Organizations to which a User belongs

SQL queries

CREATE TABLE `dev`.`Organization` (
  `ORG_ID` INT NOT NULL AUTO_INCREMENT,
  `ORG_NAME` VARCHAR(255) NOT NULL,
  `ADDRESS_LINE1` VARCHAR(128) NULL,
  `ADDRESS_LINE2` VARCHAR(128) NULL,
  `CITY` VARCHAR(32) NULL,
  `STATE` VARCHAR(32) NULL,
  `COUNTRY` VARCHAR(32) NULL,
  `ZIPCODE` VARCHAR(32) NULL,
  `PHONE_NO` VARCHAR(32) NULL,
  `CREATED_DATE` DATETIME NOT NULL,
  `UPDATED_DATE` DATETIME NOT NULL,
  `CREATED_BY` VARCHAR(64) NOT NULL,
  `UPDATED_BY` VARCHAR(64) NOT NULL,
  PRIMARY KEY (`ORG_ID`),
  UNIQUE INDEX `ORG_NAME_UNIQUE` (`ORG_NAME` ASC) VISIBLE);
  
  CREATE TABLE `dev`.`User` (
  `USER_ID` INT NOT NULL AUTO_INCREMENT,
  `ORG_ID` INT NULL,
  `FIRSTNAME` VARCHAR(64) NULL,
  `LASTNAME` VARCHAR(64) NULL,
  `EMAIL` VARCHAR(64) NULL,
  `PHONE_NO` VARCHAR(32) NULL,
  `CREATED_DATE` DATETIME NOT NULL,
  `UPDATED_DATE` DATETIME NOT NULL,
  `CREATED_BY` VARCHAR(64) NOT NULL,
  `UPDATED_BY` VARCHAR(64) NOT NULL,
  PRIMARY KEY (`USER_ID`));


CREATE TABLE `dev`.`Address` (
  `ADDRESS_ID` INT NOT NULL AUTO_INCREMENT,
  `USER_ID` INT NOT NULL,
  `ADDRESS_LINE1` VARCHAR(128) NULL,
  `ADDRESS_LINE2` VARCHAR(128) NULL,
  `CITY` VARCHAR(32) NULL,
  `STATE` VARCHAR(32) NULL,
  `COUNTRY` VARCHAR(32) NULL,
  `ZIPCODE` VARCHAR(32) NULL,
  `CREATED_DATE` DATETIME NOT NULL,
  `UPDATED_DATE` DATETIME NOT NULL,
  `CREATED_BY` VARCHAR(64) NOT NULL,
  `UPDATED_BY` VARCHAR(64) NOT NULL,
  PRIMARY KEY (`ADDRESS_ID`));
  
 CREATE TABLE `dev`.`ORG_USER_MAPPING` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `ORG_ID` INT NULL,
  `USER_ID` INT NULL,
  PRIMARY KEY (`ID`));


  
  
