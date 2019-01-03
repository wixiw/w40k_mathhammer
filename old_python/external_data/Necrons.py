#!/usr/bin/env python
# -*- coding: utf-8 -*-

from wix_w40k.W40KModel import *
from wix_w40k.Weapons import *

'''
Standard profiles
'''
profile_Immortal = ModelProfile(5,3,3,4,4,1,1,10,3)
profile_Destroyer = ModelProfile(10,3,3,4,5,3,2,10,3)

'''
Distance Weapon
'''
s_weapon_gaussBlaster = ShootingWeapon("Gauss blaster", 
                        24, RapidFire(1), 5, -2, 1, cost=9)
s_weapon_gaussCannon = ShootingWeapon("Gauss cannon", 
                        24, Heavy(3), 6, -3, "D3", cost=20)
s_weapon_gaussFlayer = ShootingWeapon("Gauss flayer", 
                        24, RapidFire(1), 4, -1, 1, cost=0)
s_weapon_heavyGaussCannon = ShootingWeapon("Heavy gauss cannon", 
                        36, Heavy(1), 9, -4, "D6", cost=20)
s_weapon_teslaCarabine = ShootingWeapon("Tesla carabine", 
                        24, Assault(2), 5, 0, 1, cost=9)
s_weapon_teslaCarabine.setHitProcs(6,2)

'''''''''''''''''''''
Troops
'''''''''''''''''''''
       
class Immortal(Figurine):
    def __init__(self):
        super().__init__("Immortal", profile_Immortal, 8)
            
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
            
'''''''''''''''''''''
Fast Attack
'''''''''''''''''''''
       
class Destroyer(Figurine):
    def __init__(self):
        super().__init__("Destroyer", profile_Immortal, 8)
            
class Destroyers(Unit):
    def __init__(self, nb = 1, weapon = "normal"):
        super().__init__("Destroyers")
        
        #Insert figurines into the unit
        for i in range (0,nb):
            self.figs.append(Destroyer())
            
        #Add a weapon option
        self.addOption("weapon", 
                        Option(["normal", "heavy"], weapon, self)
                        )
        
    def opt_weapon(self, option):
        '''
        Change the weapon of one figurine
        '''
        assert option in self.options["weapon"].choices
        if option == "normal":
            pass
        if option == "heavy":
            self.figs[0].setShootingWeapon("weapon", s_weapon_heavyGaussCannon)
        else:
            assert False