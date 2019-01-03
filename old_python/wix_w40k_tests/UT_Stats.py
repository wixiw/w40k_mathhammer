'''
Created on 30 août 2018

@author: WLA
'''
import unittest

from external_data.Necrons import *
from wix_w40k.W40KModel import ModelProfile
from wix_w40k.ShootingSimulation import *


class UT_ShootingStats(unittest.TestCase):

    def testToHit(self):
        self.assertAlmostEqual(chanceToHit([0,1,1,1,1,1,1]), 1)
        self.assertAlmostEqual(chanceToHit([0,0,1,1,1,1,1]), 5/6)
        self.assertAlmostEqual(chanceToHit([0,0,0,1,1,1,1]), 4/6)
        self.assertAlmostEqual(chanceToHit([0,0,0,0,1,1,1]), 3/6)
        self.assertAlmostEqual(chanceToHit([0,0,0,0,0,1,1]), 2/6)
        self.assertAlmostEqual(chanceToHit([0,0,0,0,0,0,1]), 1/6)
        self.assertAlmostEqual(chanceToHit([0,0,0,0,0,0,0]), 0)
        #tesla case :
        self.assertAlmostEqual(chanceToHit([0,0,0,1,1,1,3]), 3*1/6+1*1/6*3)

#TODO
#     def testSuccessTable(self):
#         hit3p = C(3)
#         self.assertEqual(hit3p.getSuccessTable(), [0,0,0,1,1,1,1])
#         self.assertEqual(hit3p.getSuccessTable(+10), [0,0,1,1,1,1,1])
#         self.assertEqual(hit3p.getSuccessTable(+2), [0,0,1,1,1,1,1])
#         self.assertEqual(hit3p.getSuccessTable(+1), [0,0,1,1,1,1,1])
#         self.assertEqual(hit3p.getSuccessTable(-1), [0,0,0,0,1,1,1])
#         self.assertEqual(hit3p.getSuccessTable(-2), [0,0,0,0,0,1,1])
#         self.assertEqual(hit3p.getSuccessTable(-3), [0,0,0,0,0,0,1])
#         self.assertEqual(hit3p.getSuccessTable(-4), [0,0,0,0,0,0,0])
#         self.assertEqual(hit3p.getSuccessTable(-10), [0,0,0,0,0,0,0])
        
    def testToHurt(self):
        self.assertRaises(IncorrectProfile, chanceToHurt, 4, 0)
        
        for F in range(1,10):
            self.assertEqual(chanceToHurt(0, F), 0)
        
        self.assertEqual(chanceToHurt(4, 1), 5/6)
        self.assertEqual(chanceToHurt(4, 2), 5/6)
        self.assertEqual(chanceToHurt(4, 3), 4/6)
        self.assertEqual(chanceToHurt(4, 4), 1/2)
        self.assertEqual(chanceToHurt(4, 5), 2/6)
        self.assertEqual(chanceToHurt(4, 6), 2/6)
        self.assertEqual(chanceToHurt(4, 7), 2/6)
        self.assertEqual(chanceToHurt(4, 8), 1/6)
        self.assertEqual(chanceToHurt(4, 9), 1/6)
        self.assertEqual(chanceToHurt(4,10), 1/6)
        
    def testToTruSavesNoInv(self):
        
        #No saves, al AP
        for AP in range (-6, 0):
            self.assertAlmostEqual(chanceToGoThruSave(AP, "-"), 1)

        #No AP, all saves
        self.assertAlmostEqual(chanceToGoThruSave("-", 0), 1/6)
        self.assertAlmostEqual(chanceToGoThruSave("-", 1), 1/6)
        self.assertAlmostEqual(chanceToGoThruSave("-", 2), 1/6)
        self.assertAlmostEqual(chanceToGoThruSave("-", 3), 2/6)
        self.assertAlmostEqual(chanceToGoThruSave("-", 4), 3/6)
        self.assertAlmostEqual(chanceToGoThruSave("-", 5), 4/6)
        self.assertAlmostEqual(chanceToGoThruSave("-", 6), 5/6)
        self.assertAlmostEqual(chanceToGoThruSave("-", 7), 1)
        
        #AP -1, all saves
        self.assertAlmostEqual(chanceToGoThruSave(-1, 2), 2/6)
        self.assertAlmostEqual(chanceToGoThruSave(-1, 3), 3/6)
        self.assertAlmostEqual(chanceToGoThruSave(-1, 4), 4/6)
        self.assertAlmostEqual(chanceToGoThruSave(-1, 5), 5/6)
        self.assertAlmostEqual(chanceToGoThruSave(-1, 6), 1)        

        #AP -6, all saves
        for Sv in range (2,6):
            self.assertAlmostEqual(chanceToGoThruSave(-6, Sv), 1)
        
    def testToTruSavesInv5(self):
        
        #5++, all AP
        for AP in range (-6, 0):
            self.assertAlmostEqual(chanceToGoThruSave(AP, "-", 5), 4/6)

        #No AP, all saves
        self.assertAlmostEqual(chanceToGoThruSave("-", 2, 5), 1/6)
        self.assertAlmostEqual(chanceToGoThruSave("-", 3, 5), 2/6)
        self.assertAlmostEqual(chanceToGoThruSave("-", 4, 5), 3/6)
        self.assertAlmostEqual(chanceToGoThruSave("-", 5, 5), 4/6)
        self.assertAlmostEqual(chanceToGoThruSave("-", 6, 5), 4/6)
        
        #AP -1, all saves
        self.assertAlmostEqual(chanceToGoThruSave(-1, 2, 5), 2/6)
        self.assertAlmostEqual(chanceToGoThruSave(-1, 3, 5), 3/6)
        self.assertAlmostEqual(chanceToGoThruSave(-1, 4, 5), 4/6)
        self.assertAlmostEqual(chanceToGoThruSave(-1, 5, 5), 4/6)
        self.assertAlmostEqual(chanceToGoThruSave(-1, 6, 5), 4/6)        

        #AP -6, all saves
        for Sv in range (2,6):
            self.assertAlmostEqual(chanceToGoThruSave(-6, Sv, 5), 4/6)        

    def testToTruSavesInv3(self):
        
        #3++, all AP
        for AP in range (-6, 0):
            self.assertAlmostEqual(chanceToGoThruSave(AP, "-", 3), 2/6)

        #No AP, all saves
        self.assertAlmostEqual(chanceToGoThruSave("-", 2, 3), 1/6)
        self.assertAlmostEqual(chanceToGoThruSave("-", 3, 3), 2/6)
        self.assertAlmostEqual(chanceToGoThruSave("-", 4, 3), 2/6)
        self.assertAlmostEqual(chanceToGoThruSave("-", 5, 3), 2/6)
        self.assertAlmostEqual(chanceToGoThruSave("-", 6, 3), 2/6)
        
        #AP -1, all saves
        self.assertAlmostEqual(chanceToGoThruSave(-1, 2, 3), 2/6)
        self.assertAlmostEqual(chanceToGoThruSave(-1, 3, 3), 2/6)
        self.assertAlmostEqual(chanceToGoThruSave(-1, 4, 3), 2/6)
        self.assertAlmostEqual(chanceToGoThruSave(-1, 5, 3), 2/6)
        self.assertAlmostEqual(chanceToGoThruSave(-1, 6, 3), 2/6)        

        #AP -6, all saves
        for Sv in range (2,6):
            self.assertAlmostEqual(chanceToGoThruSave(-6, Sv, 3), 2/6)  
               
               
    def testDmg(self):
        self.assertRaises(IncorrectProfile, chanceToWound, 0, 1, "-")
        self.assertRaises(IncorrectProfile, chanceToWound, 1, 0, "-")
        
        #No FnP
        for W in range(1, 10):
            for D in range(1, 10):
                self.assertAlmostEqual(chanceToWound(1, W, "-"), 1)
                if D < W:
                    self.assertAlmostEqual(chanceToWound(D, W, "-"), D)
                else:
                    self.assertAlmostEqual(chanceToWound(D, W, "-"), W) 
                self.assertAlmostEqual(chanceToWound(D, 1, "-"), 1)
        
        #FnP D=1 
        for W in range(1, 10):
            self.assertAlmostEqual(chanceToWound(1, 1, 5), 4/6)
            self.assertAlmostEqual(chanceToWound(1, 1, 3), 2/6) 
        
        #FnP W="inf"
        for D in range(1, 10):
            self.assertAlmostEqual(chanceToWound(D, 20, 5), D*4/6)
            self.assertAlmostEqual(chanceToWound(D, 20, 3), D*2/6)
        
        #Fnp complex
        self.assertAlmostEqual(chanceToWound(4, 2, 5), 2)
        self.assertAlmostEqual(chanceToWound(4, 2, 3), 4*2/6)                   
             
             
    def testWeaponShoot(self):
        #Bolter like
        weapon = ShootingWeapon("Test weapon", 
                        24, RapidFire(1), 4, "-", 1, 0)
         
        #Meq like attacker
        WS = 3
        
        #MeqLike target
        target = ModelProfile(6,3,3,4,4,1,1,10,3)
 
        #stimulation at rapid fire range
        self.assertAlmostEqual(simulateWeaponShoot(weapon, WS, target), 2*2/3*1/2*1/3)
        
    def testGaussImmortals(self):
        #Firing 5 immortals on MEQs
        unit = Immortals()
        target = profile_Immortal
        wounds = simulateUnitShoot(unit, target)
    
        self.assertAlmostEqual(wounds, 2.96, 0, 0.01)
        

if __name__ == "__main__":
    #import sys;sys.argv = ['', 'Test.testName']
    unittest.main()