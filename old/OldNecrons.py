#!/usr/bin/env python
# -*- coding: utf-8 -*-

from wix_w40k.Model import *

'''
Standard profiles
'''
profile_Immortal = ModelProfile(5,3,3,4,4,1,1,10,3)

'''
Distance Weapon
'''
s_weapon_gaussBlaster = DistanceWeapon("Gauss blaster", 
                        24, RapidFire(2), 5, -2, 1, 9)
s_weapon_teslaCarabine = DistanceWeapon("Tesla carabine", 
                        24, Assault(2), 5, 0, 1, 9)
    
        
class Codex_Necrons(Codex):
    '''
    classdocs
    '''


    def __init__(self):
        '''
        Constructor
        '''
        super().__init__("Necrons")
        

        '''
         Troop choices
        '''
        immortals = CodexEntry("Immortals", "Troop")
        immortal = EntryModel("Immortal", profile_Immortal, 5, 10, 8)
        immortal.addShootingWeapon(s_weapon_gaussBlaster)
        immortals.appendModel(immortal)
        self.entries.append(immortals)
        
        
class Immortals(CodexEntry):
    def __init__(self):
        
        
    def opt_switchToTesla(self):
        immortal.addShootingWeapon(s_weapon_teslaCarabine)