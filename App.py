#!/usr/bin/env python
# -*- coding: utf-8 -*-

from player_armies import Necron_Test
from external_data.Necrons import Immortals, profile_Immortal
from wix_w40k.W40KModel import ModelProfile
from wix_w40k.ShootingSimulation import simulateUnitShoot

if __name__ == '__main__':
#    army = Necron_Test()
#    print(army.exportToText())

    #Firing 5 immortals on MEQs
    unit = Immortals()
    target = profile_Immortal
    simulateUnitShoot(unit, target, True)
    
