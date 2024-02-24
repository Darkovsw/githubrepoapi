# Github Repository API

## Purpose

The purpose of this Application is to present Github informations of a specific user.
It includes informations such as Github repositories (which are not forks),
owner login, branch name and last commit sha.

## Requirements

For building and running the application you need:

- [JDK 21.0] https://www.oracle.com/java/technologies/javase/jdk21-archive-downloads.html

## Installation

Use `git clone` to download this project to your repository

Open your console and type `cd your-repisitory-directory-path`

`mvn clean package`

## Running the application

Open your console and type `java -jar target/github-repo-api-1.0.jar`

In the place of username type the name of user you want to check `http://localhost:8080/api/repositories/username`

## Copyright

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details