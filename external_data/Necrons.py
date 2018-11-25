#!/usr/bin/env python
# -*- coding: utf-8 -*-

from wix_w40k.W40KModel import *
from wix_w40k.Weapons import *

'''
Standard profiles
'''
profile_Immortal = ModelProfile(5,3,3,4,4,1,1,10,3)

'''
Distance Weapon
'''
s_weapon_gaussBlaster = ShootingWeapon("Gauss blaster", 
                        24, RapidFire(1), 5, -2, 1, 9)
s_weapon_teslaCarabine = ShootingWeapon("Tesla carabine", 
                        24, Assault(2), 5, 0, 1, 9)
s_weapon_teslaCarabine.setHitProcs(6,2)

'''''''''''''''''''''
Troops
'''''''''''''''''''''
       
class Immortal(Figurine):
    def __init__(self):
        super().__init__("Immortal", profile_Immortal)
            
class Immortals(Unit):
    def __init__(self, nb = 5, weapon = "gauss"):
        super().__init__("Immortals")
        
        #Insert figurines into the unit
        for i in range (0,nb):
            self.figs.append(Immortal())
            
        #Add a weapon option
        self.addOption("weapon", 
                        Option(["gauss", "tesla"], weapon, self)
                        )
        
    def opt_weapon(self, option):
        '''
        Change the principal weapon of the immortal
        '''
        assert option in self.options["weapon"].choices
        if option == "gauss":
            for f in self.figs:
                f.setShootingWeapon("weapon", s_weapon_gaussBlaster)
        elif option == "tesla":
            for f in self.figs:
                f.setShootingWeapon("weapon", s_weapon_teslaCarabine)
        else:
            assert False