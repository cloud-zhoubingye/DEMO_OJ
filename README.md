# Online Judge System (OJS)
Project of "Software Engineer" course in 24fall, SCU with supervisor Luo.

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
  
__This is the code repositories for front-end code in our programme.__ 

## Features

#### 1. Diverse Programming Challenges
The system covers basic algorithms and data structure problems, ensuring a wide variety of challenges. The types of problems supported include:
- **Number Theory**: Polynomial roots, factorization, GCD and LCM, modular arithmetic
- **Combinatorics**: Permutations, subsets, order of permutations, next permutation
- **Graph Theory**: Depth-first search, breadth-first search, shortest path algorithms, minimum spanning tree, maximum flow algorithms
- **Dynamic Programming**: General dynamic programming, greedy algorithms, state compression
- **String Processing**: String matching, string operations, string transformations
- **Computational Geometry**: Convex hull algorithms, closest pair of points, line segment intersection, point-to-segment distance
- **Data Structures**: Stacks and queues, linked lists, trees and binary trees, hash tables, heaps and priority queues
- **Sorting**: Bubble sort, selection sort, insertion sort, quick sort, merge sort

The system supports problems of varying difficulty levels, allowing users to practice by problem type or difficulty level.

#### 2. Efficient and Accurate Code Evaluation
The system supports multiple programming languages such as Java, Python, and C/C++. The evaluation mechanism quickly compiles and runs user-submitted code, comparing the results with standard answers. Detailed error messages help users identify and fix issues in their code. Evaluation results include:
- Execution time
- Memory usage
- Number of test cases passed

#### 3. Multi-role User Interface
The system provides user interfaces for both regular users and administrators:
- **User Interface**: Allows users to browse problems, submit code, and view evaluation results. Users can also view their submission history and progress statistics.
- **Admin Interface**: Provides robust management functions, enabling administrators to add, delete, and edit problems, manage user accounts, and modify user passwords. Administrators can maintain the system and ensure the problem database is up-to-date and relevant.


## Functional Modules

#### Module 1: User Management
- **Functions**: Manages user registration, login, and information modification.
- **Input Items**:
  - Username
  - Password
  - Email
  - Verification code (for registration and password reset)
- **Output Items**:
  - User registration success/failure message
  - Login success/failure message
  - Information modification result
- **Related Algorithms**:
  - Hashing algorithm: Encrypts user passwords for storage.
  - Authentication process: Verifies whether the entered username and password match the stored data.
  - Captcha verification: Ensures the entered captcha code is correct.

#### Module 2: Problem Management
- **Functions**: Manages the addition, modification, deletion, and browsing of problems.
- **Input Items**:
  - Problem title
  - Problem description
  - Input/output format
  - Sample input/output
  - Difficulty level
  - Problem type
- **Output Items**:
  - New problem result
  - Problem modification result
  - Deletion confirmation
  - Problem list and detailed information
- **Related Algorithms**:
  - Problem classification algorithm: Categorizes problems by type and difficulty for storage and retrieval.
  - CRUD operations: Performs create, read, update, delete operations on the problem database.

#### Module 3: Code Submission and Evaluation
- **Functions**: Allows users to select problems, write code, and submit for evaluation. The system compiles, runs, and evaluates the submitted code.
- **Input Items**:
  - Selected problem ID
  - Selected programming language (e.g., Java, Python, C/C++)
  - Code content written by the user
- **Output Items**:
  - Evaluation result (passed/failed)
  - Execution time
  - Memory usage
  - Error information (if any)
- **Related Algorithms**:
  - Code compilation and execution: Uses the appropriate compiler or interpreter for the selected programming language to compile and run the submitted code.
  - Test case comparison: Compares the output of the user's code with the standard answers using pre-set test cases.
  - Result feedback:
    - Records execution time and memory usage.
    - Counts the number of passed test cases to generate the evaluation result.
    - Generates detailed error feedback if there are any issues.


## Issues to be Addressed

#### 1. Cross-Origin Routing Issues
Currently, the cross-origin routing issue is unresolved. This can cause problems when the frontend and backend are hosted on different domains or ports, leading to CORS (Cross-Origin Resource Sharing) errors.

**Proposed Solutions**:
- **Configure a Proxy Server with a Public IP**: Set up a proxy server that has a public IP address. This proxy server will handle the requests from the frontend and forward them to the backend, thus mitigating the CORS issues.
- **Deploy Frontend and Backend on the Same Device**: Ensure that both the frontend and backend code are hosted on the same device. This can be achieved by using containerization tools like Docker, which allows for easy deployment and management of both services on the same host.



## Team and Contributions 
- **Yunfan Zhang**: Database design and backend development for CRUD operations. 
- **Liaotian Feng**: Development of the code evaluation engine and backend business logic implementation. 
- **Bingye Zhou**: Frontend development for user and admin interfaces.

## Conclusion
The Online Judge System (OJS) aims to provide an extensive range of programming challenges and an efficient evaluation mechanism, supporting multiple programming languages and user roles. The system is designed to cater to users of different skill levels, helping them enhance their coding skills through a structured and engaging platform.

