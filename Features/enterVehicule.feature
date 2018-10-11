Feature: The web-page enter vehicule
As a user
I want to enter a vehicule

Background:
Given the user is on the page vehicules

Scenario: the user should be able to enter vehicule automovil
When the user enters car plate and choose automovil
Then the user should be able to enter vehicule

#Scenario: the user should not to enter vehicule automovil without car plate
#When the user choose automovil and don't typing car plate
#Then the user should not be able to enter vehicule
#And the user should get an invalid message

#Scenario: the user should not be able to enter vehicule motorcycle
#When the user enters moto plate and choose moto
#And the user enters displacement greater than zero
#Then the user should be able to enter vehicule
#And the user should get an valid message

#Scenario: the user should not be able to enter vehicule motorcycle with displacement equals zero 
#When the user enters moto plate and choose moto
#And the user enters displacement equals to zero
#Then the user should not be able to enter vehicule
#And the user should get an invalid message