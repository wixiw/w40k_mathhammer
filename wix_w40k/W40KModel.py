#!/usr/bin/env python
# -*- coding: utf-8 -*-

from wix_w40k.Weapons import ShootingWeapon

class IncorrectProfile(Exception):
    pass

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
    def __init__(self, M, WS, BS, S, T, W, A, Ld, Sv, Inv = "-", FnP = "-"):
        self.M = M
        self.WS = WS
        self.BS = BS
        self.S = S
        self.T = T
        self.W = W
        self.A = A
        self.Ld = Ld
        self.Sv = Sv
        self.Inv = Inv
        self.FnP = FnP
        
    def exportToText(self, tabs=""):
        s = "|M\t|WS\t|BS\t|S\t|T\t|W\t|A\t|Ld\t|Sv\t|Inv\t|Fnp\t|\n"
        s += ("|"+ str(self.M) + "\t|" +
              str(self.WS) + "\t|" +
              str(self.BS) + "\t|" +
              str(self.S) + "\t|" +
              str(self.T) + "\t|" +
              str(self.W) + "\t|" +
              str(self.A) + "\t|" +
              str(self.Ld) + "\t|" +
              str(self.Sv) + "\t|" +
              str(self.Inv) + "\t|" +
              str(self.FnP) )
        return s
            
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
    
    #@return the dictionnary of weapons of the fig
    def getShootingWeapons(self):
        return self.shootingWeapons
    
    #@return : get mouvement caracteristic    
    def getM(self):
        return self.profile.M

    #@return : get close combat skill caracteristic    
    def getWS(self):
        return self.profile.WS

    #@return : get balistic skill caracteristic    
    def getBS(self):
        return self.profile.BS
    
    #@return : get strength caracteristic    
    def getS(self):
        return self.profile.S

    #@return : get toughness caracteristic
    def getT(self):
        return self.profile.T 
      
    #@return : get wounds caracteristic    
    def getW(self):
        return self.profile.W

    #@return : get attack caracteristic    
    def getA(self):
        return self.profile.A
    
    #@return : get leadership caracteristic    
    def getLd(self):
        return self.profile.Ld

    #@return : get save caracteristic
    def getSv(self):
        return self.profile.Sv

    #@return : get invulnerable save caracteristic
    def getInv(self):
        return self.profile.Inv 
        
    #@return : get feel no pain caracteristic
    def getFnP(self):
        return self.profile.FnP  
    
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
    
    def getFigs(self):
        return self.figs
        

    
