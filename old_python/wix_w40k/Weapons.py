#!/usr/bin/env python
# -*- coding: utf-8 -*-

from wix_w40k.DiceRolls import d6_table_rollAtLeast
import numpy

''' 
Weapons 
'''    
class ShootingWeapon():
    def __init__(self, name, _range, _type, S, AP, D, cost): 
        self.name = name
        self.range = _range
        assert isinstance(_type, WeaponType)
        self.type = _type
        self.S = S
        assert AP == "-" or AP <= 0 
        self.AP = AP
        self.D = D
        self.cost = cost
        
        #the hit profile is a table that associate for each d6 value, a hit count.
        #typically it allows to create tesla weapon with a [0,1,1,1,1,1,3] table.
        #   note : firt index is always set to zero to allow slot index to be equal to dice value
        #It is handy when handling hit modificators
        self.hitProfile = [0, 1,1,1,1,1,1]
        
    def debugText(self):
        print("Weapon : " + self.name + " " + str(self.range) +"\" " + str(self.type) +
               " S" + str(self.S) + " AP" + str(self.AP) + " D" + str(self.D) + " at " + str(self.cost) + "pts.")

    def exportToText(self, tabs = ""):
        s = tabs + self.name
        return s
    
    def getShoots(self):
        return self.type.A
    
    #@return the strength carac of the weapon
    def getS(self):
        return self.S

    #@return the armor penetration carac of the weapon
    def getAP(self):
        return self.AP
        
    #@return the damage carac of the weapon
    def getD(self):
        return self.D
    
    #@return point cost of the weapon
    def getCost(self):
        return self.cost
    
    #Configure the weapon to create additional hits on some results
    #@param hitProc : minimal score to reach with the dice
    #@param additionalHits : number of hits added
    def setHitProcs(self, hitProc, additionalHits):
        for diceRoll in [1,2,3,4,5,6]:
            if hitProc <= diceRoll:
                self.hitProfile[diceRoll] = 1 + additionalHits
                
    def getHitProfile(self):
        return self.hitProfile

class WeaponType():
    def __init__(self, name, A):
        self.typeName = name
        self.A = A
        
    def __str__(self):
        return self.typeName + "_" + str(self.A)
    
class RapidFire(WeaponType):
    def __init__(self, A):
        super().__init__("RapideFire", 2*A)
        
class Assault(WeaponType):
    def __init__(self, A):
        super().__init__("Assault", A)
        
class Heavy(WeaponType):
    def __init__(self, A):
        super().__init__("Heavy", A)