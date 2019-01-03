#!/usr/bin/env python
# -*- coding: utf-8 -*-

class Army:
    def __init__(self, name):
        self.name = name
        self.detachments = list()
        
    def addDetachment(self, detachment):
        self.detachments.append(detachment)
        
    def exportToText(self, tabs = ""):
        '''
        Export the army in a simple human readable format
        '''
        s = "Army name: " + self.name
        tabs += "  "
        for d in self.detachments:
            s += "\n"
            s += d.exportToText(tabs) 
        return s
        
class Detachment:
    def __init__(self, name, CP):
        self.name = name
        self.CP = CP
        self.units = list()
        
    def addUnit(self, unit):
        self.units.append(unit)
        
    def exportToText(self, tabs = ""):
        s = tabs + "Detachement name : " + self.name
        tabs += "  "
        for u in self.units:
            s += "\n"
            s += u.exportToText(tabs)
        return s
        
class Detachment_Patrol(Detachment):
    def __init__(self):
        super().__init__("Patrol", 0)
             

        