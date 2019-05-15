# csmub499.MasterScheduler-Generate

## Table of Contents
1. [Overview](#Overview)
2. [Product Spec](#Product-Spec)
3. [Schema](#Schema)
4. [Networking](#Networking)

## Overview
### Description
California State University Capstone Project, Salinas Union High School District Sponsored. Micro service for the Master Scheduler App.
The Master scheduler app is a tool implemented for the Administrative team. This app makes it easier for the admin team to generate the school schedule. 

This administrative micro services handels the generation process for the admin team. It takes all of the current entries in the database and creates a schedule based on the classes, teachers, and students that have been entered. 
The first step of the process is mapping the students and the classes in order to decide how many sections will be created. Then each section gets assign a period and a teacher. Finally, Teachers get assign a prep period and students get assign to sections.
Micro service is deployed on Heroku. https://master-scheduler-generate.herokuapp.com/

## Product Spec

### 1. User Stories (Required and Optional)

**Required Must-have Stories**
  - [X] User can generate a schedule 
  - [X] User can tell if a schedule has been generated

## Schema 

### Models
#### Student
|Property|Type|Description|
|---|---|---|
|id|String|Id of the student. Database Primary key|
|name|String|Name of student|
|grade|int|Grade of student|
|preferredClasses|List of String|List of classes student wants to take|
|preferred|List of Boolean|Maps to the classes student want to take and determines if a student has priority to take the class|
|academy|String|Student's academy they are part of [none, green, fast]|
|schedule|List of String|Student's schedule that was generated. A list of the class names|
|scheduleId|List of String|Student's schedule that was generated. A list of class Ids that map to class names|

#### Teacher
|Property|Type|Description|
|---|---|---|
|id|String|Id of the teacher. Database primary key|
|name|String|Name of the teacher|
|department|String|Department teacher is apart of|
|prep|int|Teacher's prep period|
|preferre_room|String|Room teacher wants to teach in|
|is80Percent|boolean|If true teacher teaches 5 sections. If false teacher teaches 4 sections.|
|academy|String|Teacher's academy they are part of [none, green, fast]|
|maxNumStudent|int|Maximum number of students the teacher can teach|
|currentNumStudent|int|Number of students teacher currently has|
|sections|List of Section|Teachers schedule|
|className|String|1rst class teacher can teach|
|className2|String|2nd class teacher can teach|
|className3|String|3rd class teacher can teach|

#### Class
|Property|Type|Description|
|---|---|---|
|id|String|Id of the Class. Database primary key|
|className|String|Name of the class|
|maxNumSections|int|Max number of sections that can be created form this class|
|maxNumStudentPerSection|int|Max number of students for each section|
|numStudentRegistered|int|how many students have registed for the class (in progress)|
    
#### Section extends Class
|Property|Type|Description|
|---|---|---|
|section_num|int|Section number. Section_num gets used with class id to create primary key|
|periodNum|int|Period when section will be held|
|roster|List of Par of string , string|List of student name and id that will take the class|
|teacherID|String|Teache ID who is teaching the class|
|room|String|how many students have registed for the class (in progress)|

## Networking
## List of network requests
- Generate 
    - (Read/Get) Generates the schedule for the school
    - Required: NA
    - Response: NA
     ``` java
        @CrossOrigin(origins = "*")
        @GetMapping("generate")
        public void generate(){
          ...
        }
    ```
- remove generation 
  - (Read/Get) remove generation 
  - Required: NA
  - Response: boolean
   ``` java
       @CrossOrigin(origins = "*")
       @GetMapping("remove")
       public boolean remove() {
        isGenerated = false;
        return false;
       }
  ```

- isGenerated 
  - (Read/Get) Checks whether or not the schedule has been generated
  - Required: NA
  - Response: boolean
   ``` java
        @CrossOrigin(origins = "*")
        @GetMapping("isgenerated")
        public boolean isGenerate(){
           return isGenerated;
       }
  ```
  
- Get Schedule 
  - (Read/Get) Returns the schedule for the teachers. Generation must happen first in order for this to work
  - Required: NA
  - Response: List of teachers sorted based on department
   ``` java
        @CrossOrigin(origins = "*")
        @GetMapping("/getschedule")
        public List<List<Teacher>> getSchedule(){
           ...
        }
  ```


