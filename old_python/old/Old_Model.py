#!/usr/bin/env python
# -*- coding: utf-8 -*-

class Codex():
    '''
    classdocs
    '''


    def __init__(self, name):
        '''
        Constructor
        '''
        
        #the name of the codex as displayed to the user
        self.name = name
        
        #the list of codex entries
        self.entries = list()
        
    def getName(self):
        return self.name
    
    def debugText(self):
        print("Codex instanciated : " + self.name)
        for e in self.entries:
            e.debugText()
        
        
class CodexEntry():
    def __init__(self, name, metatype):
        #the name of the entry as shown in the codex
        self.name = name

        #The metatype of the unit among HQ, Elite, ...
        assert metatype in ("HQ", "Elite", "Troop", "FastAttack", "Support", "LoW", "Transport", "Flyer")
        self.metatype = metatype

        #the list of models types that the unit can have
        self.models = list()

    def getName(self):
        return self.name
    
    def appendModel(self, model):
        assert isinstance(model, EntryModel)
        self.models.append(model)
    
    def debugText(self):
        print(self.metatype + " entry : " + self.name)
        for m in self.models:
            m.debugText()
            
class EntryModel():
    def __init__(self, name, profile, minCount, maxCount, cost):
        #the name of the entry as shown in the codex
        self.name = name
        self.profile = profile
        self.min = minCount
        self.max = maxCount
        self.cost = cost
        self.weapons = list()
        
    def addShootingWeapon(self, weapon):
        assert isinstance(weapon, DistanceWeapon)
        assert not weapon in self.weapons
        self.weapons.append(weapon)
            
    def debugText(self):
        print("Model : [" + str(self.min) + ", " + str(self.max) + "] " + self.name + ", at " + str(self.cost) + "pts each.")
        self.profile.debugText()
        for w in self.weapons:
            w.debugText() 
        
        

        

            