#!/usr/bin/env python
# -*- coding: utf-8 -*-

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
        assert AP <= 0
        self.AP = AP
        self.D = D
        self.cost = cost
        
    def debugText(self):
        print("Weapon : " + self.name + " " + str(self.range) +"\" " + str(self.type) +
               " S" + str(self.S) + " AP" + str(self.AP) + " D" + str(self.D) + " at " + str(self.cost) + "pts.")

    def exportToText(self, tabs = ""):
        s = tabs + self.name
        return s

class WeaponType():
    def __init__(self, name, A):
        self.typeName = name
        self.A = A
        
    def __str__(self):
        return self.typeName + "_" + str(self.A)
        
class RapidFire(WeaponType):
    def __init__(self, A):
        super().__init__("RapideFire", A)
        
class Assault(WeaponType):
    def __init__(self, A):
        super().__init__("Assault", A)
        
class Heavy(WeaponType):
    def __init__(self, A):
        super().__init__("Heavy", A)