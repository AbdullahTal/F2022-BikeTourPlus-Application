Feature: Add and Update Combo (p7)
  As a manager, I want to add and update combos so that participants can rent them

  Background: 
    Given the following BikeTourPlus system exists: (p7)
      | startDate  | nrWeeks | priceOfGuidePerWeek |
      | 2023-03-13 |      10 |                 100 |
    Given the following pieces of gear exist in the system: (p7)
      | name     | pricePerWeek |
      | helmet   |           25 |
      | e-bike   |          150 |
      | bike bag |           19 |
    Given the following combos exist in the system: (p7)
      | name        | discount | items                  | quantity |
      | small combo |       10 | e-bike,helmet          |      1,2 |
      | large combo |       25 | e-bike,helmet,bike bag |    1,2,2 |

  Scenario Outline: Add a combo successfully
    When the manager attempts to add a combo with name "<name>" and discount "<discount>" (p7)
    Then a combo shall exist with name "<name>" and discount "<discount>" (p7)
    Then the number of pieces of gear for the combo with name "<name>" shall be "0" (p7)
    Then the number of combos shall be "3" (p7)

    Examples: 
      | name         | discount |
      | deluxe combo |       40 |
      | combo plus   |       10 |

  Scenario Outline: Add a combo unsuccessfully
    When the manager attempts to add a combo with name "<name>" and discount "<discount>" (p7)
    Then a combo shall not exist with name "<name>" and discount "<discount>" (p7)
    Then the number of combos shall be "2" (p7)
    Then the system shall raise the error "<error>" (p7)

    Examples: 
      | name         | discount | error                                             |
      | deluxe combo |       -1 | Discount must be at least 0                       |
      | combo plus   |      101 | Discount must be no more than 100                 |
      |              |        0 | The name must not be empty                        |
      | helmet       |       35 | A piece of gear with the same name already exists |
      | small combo  |       30 | A combo with the same name already exists         |

  Scenario Outline: Update a combo successfully
    When the manager attempts to update the combo with name "<oldName>" to have name "<newName>" and discount "<newDiscount>" (p7)
    Then a combo shall exist with name "<newName>" and discount "<newDiscount>" (p7)
    Then the number of pieces of gear for the combo with name "<newName>" shall be "<numberOfGearInCombo>" (p3)
    Then a combo shall not exist with name "<oldName>" and discount "<oldDiscount>" (p7)
    Then the number of combos shall be "2" (p7)

    Examples: 
      | oldName     | oldDiscount | newName      | newDiscount | numberOfGearInCombo |
      | small combo |          10 | deluxe combo |          40 |                   2 |
      | large combo |          25 | combo plus   |          10 |                   3 |

  Scenario Outline: Update a combo unsuccessfully
    When the manager attempts to update the combo with name "<oldName>" to have name "<newName>" and discount "<newDiscount>" (p7)
    Then a combo shall exist with name "<oldName>" and discount "<oldDiscount>" (p7)
    Then the number of pieces of gear for the combo with name "<oldName>" shall be "<numberOfGearInCombo>" (p3)
    Then a combo shall not exist with name "<newName>" and discount "<newDiscount>" (p7)
    Then the number of combos shall be "2" (p7)
    Then the system shall raise the error "<error>" (p7)

    Examples: 
      | oldName     | oldDiscount | newName      | newDiscount | numberOfGearInCombo | error                                             |
      | small combo |          10 | deluxe combo |          -1 |                   2 | Discount must be at least 0                       |
      | small combo |          10 | combo plus   |         101 |                   2 | Discount must be no more than 100                 |
      | small combo |          10 |              |           0 |                   2 | The name must not be empty                        |
      | large combo |          25 | helmet       |          35 |                   3 | A piece of gear with the same name already exists |
      | large combo |          25 | small combo  |          30 |                   3 | A combo with the same name already exists         |

  Scenario Outline: Unsuccessfully update a combo that does not exist
    When the manager attempts to update the combo with name "<oldName>" to have name "<newName>" and discount "<newDiscount>" (p7)
    Then a combo shall not exist with name "<oldName>" and discount "<oldDiscount>" (p7)
    Then a combo shall not exist with name "<newName>" and discount "<newDiscount>" (p7)
    Then the number of combos shall be "2" (p7)
    Then the system shall raise the error "<error>" (p7)

    Examples: 
      | oldName    | oldDiscount | newName      | newDiscount | error                    |
      | combo plus |          10 | deluxe combo |          15 | The combo does not exist |
