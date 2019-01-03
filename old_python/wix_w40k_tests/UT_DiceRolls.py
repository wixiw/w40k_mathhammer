'''
Created on 02 jan 2019

@author: WLA
'''
import unittest
import numpy.testing as nt
from wix_w40k.DiceRolls import *


class UT_DiceRolls(unittest.TestCase):

    def testInverse(self):
        nt.assert_array_equal(d6_table_inverse([0,0,0,0,0,0,0]), [0,1,1,1,1,1,1])
        nt.assert_array_equal(d6_table_inverse([0,1,1,1,1,1,1]), [0,0,0,0,0,0,0])
        nt.assert_array_equal(d6_table_inverse(d6_table_inverse([0,1,0,1,0,1,0])), [0,1,0,1,0,1,0])
        nt.assert_array_equal(d6_table_inverse([0,0,1,1,1,1,1]), [0,1,0,0,0,0,0])
        
    def test_d6_table_rollAtLeast(self):
        nt.assert_array_equal(d6_table_rollAtLeast(0), [0,0,1,1,1,1,1])
        nt.assert_array_equal(d6_table_rollAtLeast(1), [0,0,1,1,1,1,1])
        nt.assert_array_equal(d6_table_rollAtLeast(2), [0,0,1,1,1,1,1])
        nt.assert_array_equal(d6_table_rollAtLeast(3), [0,0,0,1,1,1,1])
        nt.assert_array_equal(d6_table_rollAtLeast(4), [0,0,0,0,1,1,1])
        nt.assert_array_equal(d6_table_rollAtLeast(5), [0,0,0,0,0,1,1])
        nt.assert_array_equal(d6_table_rollAtLeast(6), [0,0,0,0,0,0,1])
        nt.assert_array_equal(d6_table_rollAtLeast(7), [0,0,0,0,0,0,0])
        nt.assert_array_equal(d6_table_rollAtLeast(10),[0,0,0,0,0,0,0])
        nt.assert_array_equal(d6_table_rollAtLeast("-"),[0,0,0,0,0,0,0])
   
    def test_d6_table_rollStrictLess(self):
        nt.assert_array_equal(d6_table_rollStrictLess("-"), [0,0,0,0,0,0,0])
        nt.assert_array_equal(d6_table_rollStrictLess(0), [0,0,0,0,0,0,0])
        nt.assert_array_equal(d6_table_rollStrictLess(1), [0,0,0,0,0,0,0])
        nt.assert_array_equal(d6_table_rollStrictLess(2), [0,1,0,0,0,0,0])
        nt.assert_array_equal(d6_table_rollStrictLess(3), [0,1,1,0,0,0,0])
        nt.assert_array_equal(d6_table_rollStrictLess(4), [0,1,1,1,0,0,0])
        nt.assert_array_equal(d6_table_rollStrictLess(5), [0,1,1,1,1,0,0])
        nt.assert_array_equal(d6_table_rollStrictLess(6), [0,1,1,1,1,1,0])
        nt.assert_array_equal(d6_table_rollStrictLess(7), [0,1,1,1,1,1,0])
        nt.assert_array_equal(d6_table_rollStrictLess(10),[0,1,1,1,1,1,0])
  
class UT_DiceRolls_HitRoll(unittest.TestCase):      
    def test_HitRoll(self):
        roll1 = HitRoll()
        
if __name__ == "__main__":
    #import sys;sys.argv = ['', 'Test.testName']
    unittest.main()