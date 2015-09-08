# University Registrar

##### _Join Statements for Epicodus, 1 September 2015_

#### By **Kallen Millner & Molly Waggett**

## Description

This app allows a user to view students and courses in a university registrar. From the 'View Students' page, the user may add a new student or view details about a particular student, including name, enrollment date, and courses they're registered for, by clicking on their name. The user may also edit or delete a student. The 'View Courses' page is similar - the user may add a new course or view details about a particular course, including name, course number, and enrolled students. The user may also edit or delete a course.

## Setup

* Set up the database in PostgreSQL by running the following commands in your terminal:
```
  psql
  CREATE DATABASE registrar;
  \c registrar;
  CREATE TABLE students (id serial PRIMARY KEY, name varchar, enrollment_year int, enrollment_month int, enrollment_day int);
  CREATE TABLE courses (id serial PRIMARY KEY, name varchar, course_number varchar);
  CREATE TABLE students_courses (id serial PRIMARY KEY, student_id int, course_id int);
  
```
* If you wish to run tests, create a test database:
```
  CREATE DATABASE registrar_test WITH TEMPLATE registrar;
```
* Clone this repository.
* Using the command line, navigate to the top level of the cloned directory.
* Make sure you have gradle installed. Then run the following command in your terminal:
```
  gradle run
```
* Go to localhost:4567.
* Go!

## Technologies Used

* Java
* PostgreSQL
* Spark
* Velocity
* Gradle
* JUnit
* FluentLenium

### Legal

Copyright (c) 2015 **Molly Waggett**

This software is licensed under the MIT license.

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
