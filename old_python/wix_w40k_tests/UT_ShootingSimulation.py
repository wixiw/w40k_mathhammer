'''
Created on 30 août 2018

@author: WLA
'''
import unittest

from external_data.Necrons import *
from wix_w40k.W40KModel import ModelProfile
from wix_w40k.ShootingSimulation import *

profile_IK = ModelProfile(12,3,3,8,8,24,4,10,3,5)

class UT_ShootingSimulation(unittest.TestCase):
    
    def testGaussBlasterVsMEQ(self):
        t = profile_Immortal
        w = s_weapon_gaussBlaster
        # 2 attacks, hit on x+, wound on 3+, svg 5+ :
        self.assertRaises(Exception, simulateWeaponShoot, w, 1, t)
        self.assertAlmostEqual(simulateWeaponShoot(w, 2, t), 2*5/6*2/3*2/3)
        self.assertAlmostEqual(simulateWeaponShoot(w, 3, t), 2*4/6*2/3*2/3)
        self.assertAlmostEqual(simulateWeaponShoot(w, 4, t), 2*3/6*2/3*2/3)
        self.assertAlmostEqual(simulateWeaponShoot(w, 5, t), 2*2/6*2/3*2/3)
        self.assertAlmostEqual(simulateWeaponShoot(w, 6, t), 2*1/6*2/3*2/3)
        self.assertRaises(Exception, simulateWeaponShoot, w, 7, t)

    def testTeslaVsMEQ(self):
        t = profile_Immortal
        w = s_weapon_teslaCarabine
        # 2 attacks, hit on x+/3hits on six, wound on 3+, svg 3+ :
        self.assertRaises(Exception, simulateWeaponShoot, w, 1, t)
        self.assertAlmostEqual(simulateWeaponShoot(w, 2, t), 2*(4/6 + 1/6*3)*2/3*1/3)
        self.assertAlmostEqual(simulateWeaponShoot(w, 3, t), 2*(3/6 + 1/6*3)*2/3*1/3)
        self.assertAlmostEqual(simulateWeaponShoot(w, 4, t), 2*(2/6 + 1/6*3)*2/3*1/3)
        self.assertAlmostEqual(simulateWeaponShoot(w, 5, t), 2*(1/6 + 1/6*3)*2/3*1/3)
        self.assertAlmostEqual(simulateWeaponShoot(w, 6, t), 2*(      1/6*3)*2/3*1/3)
        self.assertRaises(Exception, simulateWeaponShoot, w, 7, t)

    #Expected results are catch from http://www.dice-hammer.com/
    def testImmortals(self):
        self.assertAlmostEqual(simulateUnitShoot(Immortals(          ), profile_Immortal), 2.96, 2)
        self.assertAlmostEqual(simulateUnitShoot(Immortals(7, "tesla"), profile_Immortal), 3.11, 2)
        self.assertAlmostEqual(simulateUnitShoot(Immortals(10, "gauss"), profile_Immortal),5.93, 2)

    def testWeaponsVsIk(self):
        t = profile_IK
        #Warriors
        self.assertAlmostEqual(simulateWeaponShoot(s_weapon_gaussFlayer, 3, t), 2*2/3*1/6*1/2)
        #Destroyers
        self.assertAlmostEqual(simulateWeaponShoot(s_weapon_gaussCannon, 3, t), 3*(2/3+1/6*2/3)*1/3*2/3*2)


if __name__ == "__main__":
    #import sys;sys.argv = ['', 'Test.testName']
    unittest.main()