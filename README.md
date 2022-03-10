# Mini-SurveyMonkey-4806
A simple knock-off of Survey Monkey for the SYSC 4806 Group Project

### [CircleCI](https://app.circleci.com/pipelines/github/aeiou80/Mini-SurveyMonkey-4806)
### [Frontend Heroku App](https://react-mini-surveymonkey.herokuapp.com/survey)

## State of the Project (Sprint #1)
 - The React frontend portion can be found [in this repo](https://github.com/doodlehead/React-Mini-SurveyMonkey-4806)
   - Locally communicates with the Spring app on port 8080
   - Communicates with the backend Heroku app using [this link](https://mini-surveymonkey-4806.herokuapp.com/)
 - Users can...
   - Create/delete surveys
   - Add/delete MC, text, and range questions
   - See a list of existing surveys

## Plan for Sprint #2
 - Publish surveys and answer them
 - Close surveys
 - Integration testing

### Current Schema Diagram
![DB Schema Diagram](src/images/DB_Schema.png?raw=true "DB Schema Diagram")