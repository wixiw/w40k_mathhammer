#!/usr/bin/env python
# -*- coding: utf-8 -*-

from wix_w40k.Weapons import ShootingWeapon

class Option:
    def __init__(self, choices, value, objectRef):
        assert isinstance(choices, list)
        self.choices = choices
        assert value in self.choices
        self.value = value
        #A reference to the model owning the option
        assert objectRef != None
        self.object = objectRef
            
class ConfigurableObject:
    def __init__(self, name):
        #The name of the object as displayed to the user
        self.name = name
        
        #The list of configurable options of the figurine
        self.options = dict()
        
    def addOption(self, name, option):
        self.options[name] = option
        cb = getattr(option.object, "opt_" + name)
        cb(option.value)
        
class ModelProfile():
    def __init__(self, M, WS, BS, S, T, W, A, Ld, Sv):
        self.M = M
        self.WS = WS
        self.BS = BS
        self.S = S
        self.T = T
        self.W = W
        self.A = A
        self.Ld = Ld
        self.Sv = Sv
        
    def debugText(self):
        print(str(self.M) + " " +
              str(self.WS) + " " +
              str(self.BS) + " " +
              str(self.S) + " " +
              str(self.T) + " " +
              str(self.W) + " " +
              str(self.A) + " " +
              str(self.Ld) + " " +
              str(self.Sv) + " " )
            
class Figurine(ConfigurableObject):
    def __init__(self, name, profile):
        super().__init__(name)
        
        #The list of caracteristics of the figurine
        self.profile = profile
        
        #The list of weapons of the figurine
        self.shootingWeapons = dict()
    
    def addEquipment(self):
        assert False
        
    def setShootingWeapon(self, name, weapon):
        assert isinstance(weapon, ShootingWeapon)
        self.shootingWeapons[name] = weapon
        
    def addCloseWeapon(self):
        assert False
    
    def addRule(self):
        assert False

    def exportToText(self, tabs =""):
        s = tabs + self.name
        tabs += "  "
        for w in self.shootingWeapons.values():
            s += "\n"
            s += w.exportToText(tabs)
        return s

class Unit(ConfigurableObject):
    '''
    A unit is a collection of Figurine
    '''
    def __init__(self, name):
        super().__init__(name)
        self.figs = list()
        
    def exportToText(self, tabs =""):
        s = tabs + self.name + " (" + str(len(self.figs)) + ")"
        tabs += "  "
        for f in self.figs:
            s += "\n"
            s += f.exportToText(tabs) 
        return s
        

    
