Feature: Register and Update Guide (p9)
  As a guide, I want to register and update my guide account so that I can work on bike tours

  Background: 
    Given the following BikeTourPlus system exists: (p9)
      | startDate  | nrWeeks | priceOfGuidePerWeek |
      | 2023-03-13 |      10 |                 100 |
    Given the following guides exist in the system: (p9)
      | email          | password | name | emergencyContact |
      | jeff@email.com | pass1    | Jeff | (555)555-5555    |
      | john@email.com | pass2    | John | (444)444-4444    |
    Given the following participants exist in the system: (p9)
      | email           | password | name  | emergencyContact | nrWeeks | weeksAvailableFrom | weeksAvailableUntil | lodgeRequired |
      | peter@email.com | pass1    | Peter | (666)555-5555    |       1 |                  1 |                   2 | true          |
      | tyler@email.com | pass2    | Tyler | (777)444-4444    |       2 |                  2 |                   5 | false         |

  Scenario Outline: Register a guide successfully
    When a new guide attempts to register with email "<email>", password "<password>", name "<name>", and emergency contact "<emergencyContact>" (p9)
    Then a guide account shall exist with email "<email>", password "<password>", name "<name>", and emergency contact "<emergencyContact>" (p9)
    Then the number of guides shall be "3" (p9)

    Examples: 
      | email          | password | name | emergencyContact |
      | lisa@email.com | pass1    | Lisa | (666)666-6666    |
      | liam@email.com | pass2    | Liam | (777)777-7777    |

  Scenario Outline: Register a guide unsuccessfully
    When a new guide attempts to register with email "<email>", password "<password>", name "<name>", and emergency contact "<emergencyContact>" (p9)
    Then a guide account shall not exist with email "<email>", password "<password>", name "<name>", and emergency contact "<emergencyContact>" (p9)
    Then the number of guides shall be "2" (p9)
    Then the system shall raise the error "<error>" (p9)

    Examples: 
      | email            | password | name  | emergencyContact | error                                         |
      | manager@btp.com  | pass1    | Paul  | (111)111-1111    | Email cannot be manager@btp.com               |
      | jeff@email.com   | pass2    | Jeff  | (111)777-7777    | Email already linked to a guide account       |
      | peter@email.com  | pass3    | Peter | (555)555-5555    | Email already linked to a participant account |
      | bart @ email.com | pass3    | Bart  | (444)666-6666    | Email must not contain any spaces             |
      | don@email@y.com  | pass4    | Dony  | (777)555-7777    | Invalid email                                 |
      | kyle@email.      | pass5    | Kyle  | (666)777-6666    | Invalid email                                 |
      | greg.email@com   | pass6    | Greg  | (777)888-7777    | Invalid email                                 |
      | @email.com       | pass7    | Otto  | (111)777-6666    | Invalid email                                 |
      | karl@.com        | pass8    | Karl  | (111)777-6661    | Invalid email                                 |
      |                  | pass9    | Vino  | (777)888-5555    | Email cannot be empty                         |
      | luke@email.com   |          | Luke  | (999)888-5555    | Password cannot be empty                      |
      | owen@email.com   | pass10   |       | (888)888-5555    | Name cannot be empty                          |
      | noah@email.com   | pass11   | Noah  |                  | Emergency contact cannot be empty             |

  Scenario Outline: Update a guide account successfully
    When the guide with email "<email>" attempts to update their account information to password "<newPassword>", name "<newName>", and emergency contact "<newEmergencyContact>" (p9)
    Then a guide account shall exist with email "<email>", password "<newPassword>", name "<newName>", and emergency contact "<newEmergencyContact>" (p9)
    Then a guide account shall not exist with email "<email>", password "<password>", name "<name>", and emergency contact "<emergencyContact>" (p9)
    Then the number of guides shall be "2" (p9)

    Examples: 
      | email          | password | name | emergencyContact | newPassword | newName | newEmergencyContact |
      | jeff@email.com | pass1    | Jeff | (555)555-5555    | pass5       | Jake    | (111)111-1111       |
      | john@email.com | pass2    | John | (444)444-4444    | pass6       | Johnny  | (111)777-7777       |

  Scenario Outline: Update a guide account unsuccessfully
    When the guide with email "<email>" attempts to update their account information to password "<newPassword>", name "<newName>", and emergency contact "<newEmergencyContact>" (p9)
    Then a guide account shall exist with email "<email>", password "<password>", name "<name>", and emergency contact "<emergencyContact>" (p9)
    Then a guide account shall not exist with email "<email>", password "<newPassword>", name "<newName>", and emergency contact "<newEmergencyContact>" (p9)
    Then the number of guides shall be "2" (p9)
    Then the system shall raise the error "<error>" (p9)

    Examples: 
      | email          | password | name | emergencyContact | newPassword | newName | newEmergencyContact | error                             |
      | jeff@email.com | pass1    | Jeff | (555)555-5555    |             | Jeff    | (555)666-5555       | Password cannot be empty          |
      | john@email.com | pass2    | John | (444)444-4444    | pass2       |         | (444)444-7777       | Name cannot be empty              |
      | john@email.com | pass2    | John | (444)444-4444    | pass2       | John    |                     | Emergency contact cannot be empty |

  Scenario Outline: Unsuccessfully update a guide account that does not exist
    When the guide with email "<email>" attempts to update their account information to password "<newPassword>", name "<newName>", and emergency contact "<newEmergencyContact>" (p9)
    Then a guide account shall not exist with email "<email>", password "<password>", name "<name>", and emergency contact "<emergencyContact>" (p9)
    Then a guide account shall not exist with email "<email>", password "<newPassword>", name "<newName>", and emergency contact "<newEmergencyContact>" (p9)
    Then the number of guides shall be "2" (p9)
    Then the system shall raise the error "<error>" (p9)

    Examples: 
      | email          | password | name | emergencyContact | newPassword | newName | newEmergencyContact | error                            |
      | jane@email.com | pass1    | Jane | (333)333-3333    | pass2       | Jeff    | (555)666-4444       | The guide account does not exist |
