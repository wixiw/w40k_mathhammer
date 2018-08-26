#!/usr/bin/env python
# -*- coding: utf-8 -*-

from wix_w40k.Army import Army, Detachment_Patrol
from external_data.Necrons import Immortals

class Army_Test(Army):
    def __init__(self):
        super().__init__("Test army")
        d1 = Detachment_Patrol()
        self.addDetachment(d1)
        
        unit1 = Immortals()
        unit2 = Immortals(7, "tesla")
        unit3 = Immortals(10, "gauss")
        
        d1.addUnit(unit1)
        d1.addUnit(unit2)
        d1.addUnit(unit3)