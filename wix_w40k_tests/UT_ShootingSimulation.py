'''
Created on 30 août 2018

@author: WLA
'''
import unittest

from external_data.Necrons import *
from wix_w40k.W40KModel import ModelProfile
from wix_w40k.ShootingSimulation import *


class UT_ShootingSimulation(unittest.TestCase):
    
    def testGaussVsMEQ(self):
        target = profile_Immortal
        # 2 attacks, hit on x+, wound on 3+, svg 5+ :
        self.assertRaises(Exception, simulateWeaponShoot, s_weapon_gaussBlaster, 1, target)
        self.assertAlmostEqual(simulateWeaponShoot(s_weapon_gaussBlaster, 2, target), 2*5/6*2/3*2/3)
        self.assertAlmostEqual(simulateWeaponShoot(s_weapon_gaussBlaster, 3, target), 2*4/6*2/3*2/3)
        self.assertAlmostEqual(simulateWeaponShoot(s_weapon_gaussBlaster, 4, target), 2*3/6*2/3*2/3)
        self.assertAlmostEqual(simulateWeaponShoot(s_weapon_gaussBlaster, 5, target), 2*2/6*2/3*2/3)
        self.assertAlmostEqual(simulateWeaponShoot(s_weapon_gaussBlaster, 6, target), 2*1/6*2/3*2/3)
        self.assertRaises(Exception, simulateWeaponShoot, s_weapon_gaussBlaster, 7, target)

    def testTeslaVsMEQ(self):
        target = profile_Immortal
        # 2 attacks, hit on x+/3hits on six, wound on 3+, svg 3+ :
        self.assertRaises(Exception, simulateWeaponShoot, s_weapon_teslaCarabine, 1, target)
        self.assertAlmostEqual(simulateWeaponShoot(s_weapon_teslaCarabine, 2, target), 2*(4/6 + 1/6*3)*2/3*1/3)
        self.assertAlmostEqual(simulateWeaponShoot(s_weapon_teslaCarabine, 3, target), 2*(3/6 + 1/6*3)*2/3*1/3)
        self.assertAlmostEqual(simulateWeaponShoot(s_weapon_teslaCarabine, 4, target), 2*(2/6 + 1/6*3)*2/3*1/3)
        self.assertAlmostEqual(simulateWeaponShoot(s_weapon_teslaCarabine, 5, target), 2*(1/6 + 1/6*3)*2/3*1/3)
        self.assertAlmostEqual(simulateWeaponShoot(s_weapon_teslaCarabine, 6, target), 2*(      1/6*3)*2/3*1/3)
        self.assertRaises(Exception, simulateWeaponShoot, s_weapon_teslaCarabine, 7, target)

    #Expected results are catch from http://www.dice-hammer.com/
    def testImmortals(self):
        self.assertAlmostEqual(simulateUnitShoot(Immortals(          ), profile_Immortal), 2.96, 2)
        self.assertAlmostEqual(simulateUnitShoot(Immortals(7, "tesla"), profile_Immortal), 3.11, 2)
        self.assertAlmostEqual(simulateUnitShoot(Immortals(10, "gauss"), profile_Immortal),5.93, 2)

if __name__ == "__main__":
    #import sys;sys.argv = ['', 'Test.testName']
    unittest.main()