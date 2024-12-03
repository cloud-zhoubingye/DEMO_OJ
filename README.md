# Online Judge System (OJS)
Project of "Software Engineer" course in 24fall, SCU with supervisor Luo.  
__This is the code repositories for front-end code in our programme.__ 

## Install
```shell
npm install
```
## Run
```shell
npm run serve
npm run dev
```

## Introduction
The Online Judge System (OJS) is a comprehensive platform that provides a wide range of programming challenges and an efficient code evaluation mechanism. It supports multiple programming languages and offers user-friendly interfaces for both users and administrators.
  


## Issues to be Addressed

#### Cross-Origin Routing Issues
Currently, the cross-origin routing issue is unresolved. This can cause problems when the frontend and backend are hosted on different domains or ports, leading to CORS (Cross-Origin Resource Sharing) errors.

**Proposed Solutions**:
- **Configure a Proxy Server with a Public IP**: Set up a proxy server that has a public IP address. This proxy server will handle the requests from the frontend and forward them to the backend, thus mitigating the CORS issues.
- **Deploy Frontend and Backend on the Same Device**: Ensure that both the frontend and backend code are hosted on the same device. This can be achieved by using containerization tools like Docker, which allows for easy deployment and management of both services on the same host.



## Team and Contributions 
- **Yunfan Zhang**: Database design and backend development for CRUD operations. 
- **Liaotian Feng**: Development of the code evaluation engine and backend business logic implementation. 
- **Bingye Zhou**: Frontend development for user and admin interfaces.


## Interface
#### 1. Fetch Problem Details
- **URL**: `/api/problem_detail`
- **Method**: `GET`
- **Incoming Parameters**: None
- **Return Parameters**: `data` (list of problem details)

#### 2. Delete Problem
- **URL**: `/api/problem_delete`
- **Method**: `POST`
- **Incoming Parameters**: 
  - `params`: { `id`: index }
- **Return Parameters**: `response.data` (status of the delete operation)

#### 3. Add New Problem
- **URL**: `/api/add_new_problem`
- **Method**: `POST`
- **Incoming Parameters**:
  - `name`: `this.problemNameInput`
  - `description`: `this.problemDescriptionInput`
  - `difficulty`: `this.difficultyLevelInput`
  - `category`: `this.problemCategoryInput`
  - `inputDescription`: `this.problemInputDescriptionInput`
  - `outputDescription`: `this.problemOutputDescriptionInput`
  - `inputOutputSample`: `this.problemInputOutputSampleInput`
  - `timeLimit`: `this.timeLimitInput`
  - `memoryLimit`: `this.memoryLimitInput`
- **Return Parameters**: `response.data` (status of the add operation)

#### 4. Admin Login
- **URL**: `/api/admin_login`
- **Method**: `POST`
- **Incoming Parameters**:
  - `username`: this.username
  - `password`: this.password
- **Return Parameters**: `response` (status of the login operation)

#### 5. Fetch Captcha
- **URL**: `/api/getCaptcha`
- **Method**: `GET`
- **Incoming Parameters**: None
- **Return Parameters**: `captcha` (randomly generated captcha image)

#### 6. Reset Password
- **URL**: `/api/reset_password`
- **Method**: `POST`
- **Incoming Parameters**:
  - `resetPasswordAdmin`: true
- **Return Parameters**: `response` (status of the password reset operation)

#### 7. Fetch Problems List
- **URL**: `/api/problems_list`
- **Method**: `GET`
- **Incoming Parameters**: None
- **Return Parameters**: `probList` (list of problem details)

#### 8. Show Problem Detail
- **URL**: `/api/show_problem_detail`
- **Method**: `GET`
- **Incoming Parameters**:
  - `problem_name`: this.currentChoose
- **Return Parameters**: 
  - `problemDescription`
  - `problemInputDescription`
  - `problemOutputDescription`
  - `problemInputOutputSample`
  - `timeLimit`
  - `memoryLimit`
  - `difficultyLevel`
  - `problemCategory`

#### 9. Save Problem
- **URL**: `/api/save_problem`
- **Method**: `POST`
- **Incoming Parameters**:
  - `problem_name`: this.currentChoose
  - `problemDescription`: this.problemDescription
  - `problemInputDescription`: this.problemInputDescription
  - `problemOutputDescription`: this.problemOutputDescription
  - `problemInputOutputSample`: this.problemInputOutputSample
  - `timeLimit`: this.timeLimit
  - `memoryLimit`: this.memoryLimit
  - `difficultyLevel`: this.difficultyLevel
  - `problemCategory`: this.problemCategory
- **Return Parameters**: `response` (status of the save operation)

#### 10. Get User Management List
- **URL**: `/api/userManagement`
- **Method**: `GET`
- **Incoming Parameters**: None
- **Return Parameters**: `data` (list of user details)

#### 11. Save User Data
- **URL**: `/api/userManagement`
- **Method**: `POST`
- **Incoming Parameters**: 
  - `name`: `this.editName`
  - `mail`: `this.editEmail`
  - `password`: `this.editPassword`
  - `type`: `this.editType`
- **Return Parameters**: `response` (status of the save operation)

#### 12. Get Categorized Problems
- **URL**: `/api/category_problem`
- **Method**: `GET`
- **Incoming Parameters**: 
  - `catogory`: `this.catogory_choose`
- **Return Parameters**: `ProblemList` (list of categorized problems)

#### 13. Get Hard-Based Problems
- **URL**: `/api/get_hard_based_problems`
- **Method**: `GET`
- **Incoming Parameters**:
  - `hard_choose`: this.hard_choose
- **Return Parameters**: `ProblemList` (list of hard-based problems)

#### 14. Get User History
- **URL**: `/api/history`
- **Method**: `GET`
- **Incoming Parameters**: None
- **Return Parameters**: `data` (list of user history details)

#### 15. Admin Login
- **URL**: `/api/admin_login`
- **Method**: `POST`
- **Incoming Parameters**:
  - `username`: `this.username`
  - `password`: `this.password`
- **Return Parameters**: `response.data.result` (status of the login operation)

#### 16. Fetch Captcha
- **URL**: `/api/getCaptcha`
- **Method**: `GET`
- **Incoming Parameters**: None
- **Return Parameters**: `captcha` (randomly generated captcha image)

#### 17. Get Personal Information
- **URL**: `/api/getPersonInfo`
- **Method**: `GET`
- **Incoming Parameters**: 
  - `username`: `this.username`
- **Return Parameters**: 
  - `totalQuestions`
  - `completedQuestions`
  - `avg_score`
  - `max_score`

#### 18. Get Problem Details
- **URL**: `/api/getProblem`
- **Method**: `GET`
- **Incoming Parameters**: 
  - `prob`: `this.problemName`
- **Return Parameters**: 
  - `problemDescription`
  - `problemInputDescription`
  - `problemOutputDescription`
  - `problemInputOutputSample`
  - `timeLimit`
  - `memoryLimit`
  - `difficultyLevel`
  - `problemCategory`

#### 19. Submit Code
- **URL**: `/api/sendData`
- **Method**: `POST`
- **Incoming Parameters**: 
  - `prob`: `this.problemName`
  - `language`: `this.language`
  - `code`: `this.code`
- **Return Parameters**: 
  - `timeCost`
  - `memoryCost`
  - `resultScore`
  - `resultInformation`

#### 20. Register User
- **URL**: `/api/register`
- **Method**: `POST`
- **Incoming Parameters**: 
  - `email`: this.email
  - `username`: this.username
  - `password`: this.password
- **Return Parameters**: `response.result` (status of the registration operation)

#### 21. Get Captcha
- **URL**: `/api/getCaptcha`
- **Method**: `GET`
- **Incoming Parameters**: None
- **Return Parameters**: `captcha` (randomly generated captcha image)

#### 22. Send Email Captcha
- **URL**: `/api/email_captcha`
- **Method**: `GET`
- **Incoming Parameters**: 
  - `email`: this.email
- **Return Parameters**: `response.result` (status of the email captcha sending operation)

#### 23. Reset Password
- **URL**: `/api/reset_password`
- **Method**: `GET`
- **Incoming Parameters**: 
  - `email`: this.email
  - `username`: this.username
  - `password`: this.password
  - `email_captcha`: this.email_captcha
- **Return Parameters**: `response.result` (status of the password reset operation)
