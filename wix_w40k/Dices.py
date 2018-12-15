#!/usr/bin/env python
# -*- coding: utf-8 -*-

'''
Represent an exception due to an impossible dice roll
'''
class ImpossibleRoll(Exception):
    def __init__(self, rollTarget):
        super().__init__( "A roll of " + str(rollTarget) + "+ is impossible")

'''
Global var to get the statical table for each dice roll of a d6
   note : firt index is always set to zero to allow slot index to be equal to dice value
'''
d6_Profile = [0, 1/6., 1/6., 1/6., 1/6., 1/6., 1/6.]

'''
@param the dice result to reach at least or equal (success roll)
@return the statistics of success (between 0 and 1)
'''
def d6_rollMoreThan(successValue):
    assert isinstance(successValue, int), "value is " + str(successValue) + " of type " + str(type(successValue))
    
    if successValue < 2 or 6 < successValue:
        raise ImpossibleRoll(successValue)
         
    return (7 - successValue)/6

'''
@param the dice result to reach at most strictly (failed roll)
@return the statistics of success (between 0 and 1)
'''
def d6_rollStrictLess(succesValue):
    return 1 - d6_rollMoreThan(succesValue)


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