# Send SMS to FTP Application Overview

This repository contains the source code of send-sms-ftp application. The goal of application is to monitor a folder and upload new files in it(which suit selection criteria) to ftp server.

## Project Structure

The source code in this project are divided into 3 packages:

* core package: provides the core utilities that performs file selection, upload and backup features.
* engine package: provides a runnable class that performs the main features under command line mode.
* ui package: provides a GUI(graphic interface) based on the engine package.

For more information on project structure, please refers to the class diagram provided at the project root. For more information on class definition and feature implementation, please read the java document provided at the site/apidocs under each package's target folder.
 
## How to build

To build this project, please install following dependencies:

* jdk 1.6+
* maven 2+
(Internet access is required for the first-time build)

To build the project, run below code at your shell:
```cmd
cd C:\path\to\the\project\root
mvn clean package
mvn install
mvn javadoc:javadoc
```

## How to run

### Run in command line mode

To run, double click the send-sms-ftp-engine.bat at the project root or run below command in shell(config.properties and log4j.properties are required at the same folder):
`java -jar /path/to/engine/jar/file/send-sms-ftp-engine-1.0-SNAPSHOT.jar`

### Run in Graphic User Interface mode

To run, double click the send-sms-ftp-ui.bat at the project root or run below command in shell(config.properties and log4j.properties are required at the same folder):
`java -jar /path/to/ui/jar/file/send-sms-ftp-ui-1.0-SNAPSHOT.jar`

## How to version control

This is a git project. Install git and perform below features with these commands:

| feature | command |
| ------- | ------- |
| Check project log | `git log` |
| Check project one-line log | `git log --oneline` |
| Check project release | `git tag` |
| Check project changes since last version | `git status` |
| Confirm project changes since last version | `git add *` |
| Save changes to a version | `git commit -m "Version description message."` |

For more information on git usage, google it.

## Work Log

- *2017-02-17* Reach the MVP(minimal valid product)
- *2017-03-10* Develope the package structure
- *2017-08-31* Release v1.0

## Future-proof Features & Guidance

Considering future possible changes varies, classes are designed in a way that follows suggested programming principles so that most of codes is re-usable. 

For package engine, here lists some demo cases and suggested solutions:

* If back up strategy changes from storing at local file system to remote ftp server:
    - add new backup ftp related info to config.properties
    - update package<engine>:SendSMSJob.java, replace MessageBackuper with MessageFtpUploader(grab props from jobConfig)
    - rebuild the project!

* If the input source change from local file folder to remote ftp server:
    - add new ftp related info to config.properties
    - update package<engine>:SendSMSJob.java the way that it feeds data to FileManipulators
    - rebuild the project!

* If we need to add an convertor for messages before they are sent:
    - create new implementee of interface FileManipulator named MessageConvertor
    - update package<engine>:SendSMSJob.java push MessageConvertor into SendSMSJob's process chain
    - rebuild the project!

* If messages should be process by priority according to a newly introduced algorithm:
    - update package<engine>:SendSMSJob.java the way that it feeds data to FileManipulators
    - rebuild the project!

* If messages processing changes from ftp uploading to web-service posting:
    - create new implementee of interface FileManipulator named MessageWebPoster
    - update package<engine>:SendSMSJob.java, replace MessageFtpUploader with MessageWebPoster(grab props from jobConfig)
    - rebuild the project!
    
For package ui, mostly add/remove gui components for new changes, few logic changes involve.
