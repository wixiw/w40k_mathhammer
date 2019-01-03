#!/usr/bin/env python
# -*- coding: utf-8 -*-

from wix_w40k.W40KModel import *
from wix_w40k.DiceRolls import * 
from numpy import dot
 
class IncorrectWeaponProfile(Exception):
    pass

'''
Compute the statistical wounds that a unit can make to a target
@return wounds
'''
def simulateUnitShoot(firingUnit, targetProfile, verbose = False):
    assert isinstance(firingUnit, Unit)
    assert isinstance(targetProfile, ModelProfile)
    
    if verbose:
        print("Firing unit : " + firingUnit.exportToText())
        print("Target profile : \n" + targetProfile.exportToText())
        
        print("----------------")
        print("Results : ")
    
    wounds = 0
    for fig in firingUnit.getFigs():
        wounds += simulateFigShoot(fig, targetProfile)
        
    if verbose:
        print("Wounds : " + str(wounds))
        print("----------------")
    
    return wounds
    
'''
Compute the statistical wounds that a figurine can make to a target
@return wounds
'''
def simulateFigShoot(fig, targetProfile):
    assert isinstance(fig, Figurine)
    assert isinstance(targetProfile, ModelProfile)
    
    wounds = 0
    for weapon in fig.getShootingWeapons().values():
        wounds += simulateWeaponShoot(weapon, fig.getWS(), targetProfile)
        
    return wounds

'''
Compute the statistical wounds that a weapon can make to a target
@return wounds
'''
def simulateWeaponShoot(weapon, WS, targetProfile):
    assert isinstance(weapon, ShootingWeapon)
    assert isinstance(WS, int) and 1 < WS and WS < 7
    assert isinstance(targetProfile, ModelProfile)
    
    
    d6_table_rollAtLeast(WS, weapon.getHitProfile())
    
    touched = weapon.getShoots() * chanceToHit((WS))
    hurts = touched * chanceToHurt(weapon.getS(), targetProfile.T)
    damaged = hurts * chanceToGoThruSave(weapon.getAP(), targetProfile.Sv, targetProfile.Inv)
    wound = damaged * chanceToWound(weapon.getD(), targetProfile.W, targetProfile.FnP)                                 
    
    return wound
 
'''
@param weapon skill of attacker
@param hit profile of the weapon (table : each slot represent a dice result. Contained value is the number of hits)
@return touchs made by an attack
'''
def chanceToHit(hitProfile):
    return numpy.dot(hitProfile, d6_Profile)
       
'''
@param strength of attacker
@param toughness of defender
@return damage made by a hit
'''
def chanceToHurt(S, T):
    if not isinstance(S,int):
        raise IncorrectWeaponProfile
    
    if not isinstance(T, int) or T < 1:
        raise IncorrectProfile
    
    if S == 0:
        return 0
    if S == T:
        return 3/6
    if 2*S <= T:
        return 1/6
    if 2*T <= S:
        return 5/6
    if S < T:
        return 2/6
    if T < S :
        return 4/6
    assert False


'''
@param Armor penetration of the weapon
@param profile of the target
@return damage roll produced
'''
def chanceToGoThruSave(AP, Sv, Inv = "-"):
    save = d6_rollAtLeast(Sv, AP)
    invu = d6_rollAtLeast(Inv)
    
    thruSave = 0
    if save < invu:
        thruSave = 1 - invu
    else:
        thruSave = 1 - save
    
    return thruSave


'''
@param D damage carac of the weapon
@param W wounds of the target
@param FnP feel no pain carac
@return wounds inflicted (taking wound cap into account)
'''
def chanceToWound(D, W, FnP):
    if not isinstance(D,int): 
        if D != "D3" and D != "D6":  
            raise IncorrectProfile
    else:
        if D < 1 or W < 1:
            raise IncorrectProfile
    if not isinstance(W,int):
        raise IncorrectProfile

    
    d = fromDxToStat(D)

    #Feel no pain rolls, damage success when roll is failed
    
    if FnP != "-":
        d = d * (1 - d6_rollAtLeast(FnP))
    
    #Saturates by wounds
    if W < d:
        d = W 
        
    return d
    
