#!/usr/bin/env python
# -*- coding: utf-8 -*-

from wix_w40k.Army import Army, Detachment_Patrol
from external_data.Necrons import Immortals, profile_Immortal
from wix_w40k.ShootingSimulation import simulateUnitShoot

class Necron_Test(Army):
    def __init__(self):
        super().__init__("Test army")
        d1 = Detachment_Patrol()
        self.addDetachment(d1)
        
        self.immo_default = Immortals()
        self.immo_7_tesla = Immortals(7, "tesla")
        self.immo_10_gauss = Immortals(10, "gauss")
        
        d1.addUnit(self.immo_default)
        d1.addUnit(self.immo_7_tesla)
        d1.addUnit(self.immo_10_gauss)

if __name__ == '__main__':
    army = Necron_Test()
    #print(army.exportToText())
    
    target = profile_Immortal
    simulateUnitShoot(army.immo_default, target)
    simulateUnitShoot(army.immo_7_tesla, target)
    simulateUnitShoot(army.immo_10_gauss, target)
    