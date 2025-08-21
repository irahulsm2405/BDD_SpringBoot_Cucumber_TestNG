Feature: Delete Product Service

  Scenario: Delete product with valid ID
    Given Product delete method in service class is available
    When Requesting to delete product with ID 1
    Then Product with ID 1 should be deleted successfully

  Scenario: Delete product with invalid ID
    Given Product delete method in service class is available
    When Requesting to delete product with ID 99
    Then Throw new ProductDoesNotExistException
