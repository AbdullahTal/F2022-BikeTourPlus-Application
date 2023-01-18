Feature: Update Manager (p5)
  As a manager, I want to update my manager account so that the account is accurate

  Background: 
    Given the following BikeTourPlus system exists: (p5)
      | startDate  | nrWeeks | priceOfGuidePerWeek |
      | 2023-03-13 |      10 |                 100 |
    Given the following manager exists in the system: (p5)
      | email           | password |
      | manager@btp.com | manager  |

  Scenario Outline: Update the manager successfully
    When the manager attempts to update their password to "<password>" (p5)
    Then a manager account shall exist with email "manager@btp.com" and with password "<password>" (p5)
    Then the number of managers shall be "1" (p5)

    Examples: 
      | password |
      | P!p1     |
      | p#2P     |
      | $aA3     |

  Scenario Outline: Update a manager unsuccessfully
    When the manager attempts to update their password to "<password>" (p5)
    Then a manager account shall exist with email "manager@btp.com" and password "manager" (p5)
    Then the number of managers shall be "1" (p5)
    Then the system shall raise the error "<error>" (p5)

    Examples: 
      | password | error                                          |
      | P!p      | Password must be at least four characters long |
      | p2P2     | Password must contain one character out of !#$ |
      |          | Password cannot be empty                       |
      | !2P2     | Password must contain one lower-case character |
      | !2p2     | Password must contain one upper-case character |
