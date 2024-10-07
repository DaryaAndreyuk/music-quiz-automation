# Music Quiz UI Automation framework

## This is portfolio project on automation of test scripts using UI tests for  [wro.mzgb.net](https://wro.mzgb.net/)

## Contents:

- [Technology stack used](https://github.com/DaryaAndreyuk/PetStoreAPITesting/tree/fb_DaryaAndreyuk_8_AddReadMe?tab=readme-ov-file#technology-stack-used)
- [Running the Tests from the terminal](https://github.com/DaryaAndreyuk/PetStoreAPITesting/tree/fb_DaryaAndreyuk_8_AddReadMe?tab=readme-ov-file#running-the-tests)
- [Viewing Allure Reports](https://github.com/DaryaAndreyuk/PetStoreAPITesting/tree/fb_DaryaAndreyuk_8_AddReadMe?tab=readme-ov-file#viewing-allure-reports)
- [Build with Github Actions](https://github.com/DaryaAndreyuk/PetStoreAPITesting/tree/fb_DaryaAndreyuk_8_AddReadMe?tab=readme-ov-file#build-with-jenkins)

## Technology stack used

<p align="center" dir="auto">
<a href="https://www.selenium.dev/" rel="nofollow"><img width="9%" title="Selenium" src="images/logo/Selenium.png" alt="Selenium" style="max-width: 100%;"></a>
<a href="https://www.selenium.dev/documentation/grid/" rel="nofollow"><img width="9%" title="Selenium Grid" src="images/logo/Selenium%20Grid.png" alt="Selenium Grid" style="max-width: 100%;"></a>
<a href="https://www.jetbrains.com/idea/" rel="nofollow"><img width="9%" title="IntelliJ IDEA" src="images/logo/Intelij_IDEA.svg" alt="Intellij_IDEA" style="max-width: 100%;"></a>
<a href="https://www.java.com/" rel="nofollow"><img width="9%" title="Java" src="images/logo/Java.svg" alt="Java" style="max-width: 100%;"></a>
<a href="https://allurereport.org/" rel="nofollow"><img width="9%" title="Allure Report" src="images/logo/Allure_Report.svg" alt="Allure_Report" style="max-width: 100%;"></a>
<a href="https://maven.apache.org/" rel="nofollow"><img width="9%" title="Maven" src="images/logo/Maven.svg" alt="Maven" style="max-width: 100%;"></a>
<a href="https://github.com/features/actions" rel="nofollow"><img width="9%" title="Github Actions" src="images/logo/Github%20Actions.svg" alt="Github Actions" style="max-width: 100%;"></a>
<a href="https://github.com/"><img width="9%" title="GitHub" src="images/logo/GitHub.svg" alt="GitHub" style="max-width: 100%;"></a>
<a href="https://junit.org/junit5/" rel="nofollow"><img width="9%" title="JUnit5" src="images/logo/JUnit5.svg" alt="JUnit5" style="max-width: 100%;"></a>

</p>

In this project, automated tests are written in `Java 21` using the `Selenium` framework for UI testing.

- `Maven` is used as the build tool.
- `JUnit5` is the testing framework.
- `Allure` is applied for generating test reports.

## Running the Tests

To execute the tests, run the following command in the CLI:

`mvn clean test`

## Viewing Allure Reports

After running the tests, you can view the Allure reports by executing:

`mvn allureServe`

## Example of Allure Report

<img src="images/AllureExample.png" alt="AllureReportExample" />

## Report with test results



## Build with Github Actions



## See Allure Report from Github Pages




If you have any comments or suggestions, please feel free to contact me
at: [dasha.andreyuk@gmail.com](dasha.andreyuk@gmail.com)


## Instructions to Create a Valid `Sheet.xlsx` File

To create a valid `src/test/resources/data/Sheet.xlsx` file based on a template, follow these steps:

### Step 1: Locate the Template

A template file named `SheetTemplate.xlsx` is provided in the repository at `src/test/resources/data/SheetTemplate.xlsx`.

### Step 2: Copy the Template

Copy the `SheetTemplate.xlsx` file to create a new file named `Sheet.xlsx` in the same directory.

 ### Step 3: Open the Sheet.xlsx file in Excel or any other spreadsheet editor.
Make sure to fill in the required data fields as shown below:

### Example `Sheet.xlsx`

Here’s a detailed view of what the `SheetTemplate.xlsx` file should contain:

| Name                | John              |
| Phone               | 48152829319       |
| Email               | test@testmail.com |
| Team                | DreamTeam         |
| Number of teammates | 7                 |
| Password            | strongpass!@#$%57 |

Make sure each field corresponds to the expected format and content based on your application's requirements.

