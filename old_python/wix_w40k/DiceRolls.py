#!/usr/bin/env python
# -*- coding: utf-8 -*-

import numpy 

'''
Represent a hit roll
'''
class HitRoll:
    def __init__(self):
        self.skill = None
    
    '''
    @param skill : a skill "C" (characteristic) like 3 for 3+
    '''
    def setSkill(self, skill):
        self.skill = skill
        
    '''
    @param : numpy.array(7) the weight associated to each dice result
    '''
    def setWeaponProfile(self, ):
    
    
'''
@param (1D-array[7])successTable : the gain affected to each dice result
@param (int) bonus : the dice modifier (-1, +1, ...)
@param (str) reroll : the dice reroll capacities
@return (int) the success ratio for the dice roll
'''   
def d6_rollWithGainTable(successTable, reroll = None):
    if bonus is "-":
        bonus = 0
        
    
    
    firstRoll = numpy.dot(d6_Profile, successTable)
    
    #Reroll all :
    if reroll is "RE":
        failedTable = d6_table_inverse(successTable)
        secondRoll = numpy.dot(d6_Profile, failedTable)*firstRoll
        return firstRoll + secondRoll
    #Reroll ones :
    elif reroll is "R1":
        failedTable = numpy.array([0,1,0,0,0,0,0])
        secondRoll = numpy.dot(d6_Profile, failedTable)*firstRoll
        return firstRoll + secondRoll
    #No reroll
    elif reroll is None:
        return firstRoll
    else:
        assert False

'''
Global var to get the statical table for each dice roll of a d6
   note : firt index is always set to zero to allow slot index to be equal to dice value
'''
d6_Profile = numpy.array([0, 1/6., 1/6., 1/6., 1/6., 1/6., 1/6.])

'''
Inverse the success rate of the table
'''
def d6_table_inverse(table):
    return numpy.array([0,1,1,1,1,1,1]) - table

'''
@param the dice result to reach at least or equal (success roll)
@return the success table (0 failure, 1 success for each dice roll) 
'''
def d6_table_rollAtLeast(successValue):
    profile = numpy.array([0,0,0,0,0,0,0])
    
    #If there is no success value, always fails
    if successValue is "-":
        return profile 
    
    assert isinstance(successValue, int), "value is " + str(successValue) + " of type " + str(type(successValue))
    
    #a 1 is always a failure, thus we begin at 2
    for diceRoll in [2,3,4,5,6]:
        if diceRoll < successValue:
            profile[diceRoll] = 0
        else:
            profile[diceRoll] = 1
    
    return profile


'''
@param successValue : the dice result to reach at least or equal (success roll)
@param (int) bonus : the dice modifier (-1, +1, ...)
@param (str) reroll : the dice reroll capacities
@return (int) the success ratio for the dice roll
'''
def d6_rollAtLeast(successValue, bonus = 0, reroll = None):
    if successValue is "-":
        return 0
    
    assert isinstance(successValue, int), "value is " + str(successValue) + " of type " + str(type(successValue))
    
    if bonus is "-":
        bonus = 0
    
    successTable = d6_table_rollAtLeast(successValue - bonus)
    return d6_rollWithGainTable(successTable, reroll)
    

'''
@param the dice result to reach at most strictly (failed roll)
@return the success table of success 
'''
def d6_table_rollStrictLess(successValue):
    profile = numpy.array([0,0,0,0,0,0,0])
    
    #If there is no success value, always fails
    if successValue is "-":
        return profile 
    
    assert isinstance(successValue, int), "value is " + str(successValue) + " of type " + str(type(successValue))
    
    #a 6 is always a failure, thus we finish at 5
    for diceRoll in [1,2,3,4,5]:
        if diceRoll < successValue:
            profile[diceRoll] = 1
        else:
            profile[diceRoll] = 0
    
    return profile


'''
@param description of the random value (dice ref like : d3, d6, 2d6)
@return the mean value obtained by the random shoot. If input is a flat value, it returns the value
'''
def fromDxToStat(diceRef):
    if diceRef == "d3":
        d = 2
    elif diceRef =="d6":
        d = 3.5
    elif isinstance(diceRef,int):
        d = diceRef
    else:
        #value unmanaged yet
        assert False    
    return d


