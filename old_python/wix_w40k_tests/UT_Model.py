'''
Created on 02 jan 2019

@author: WLA
'''
import unittest

from external_data.Necrons import *
from wix_w40k.W40KModel import *


class UT_Characteristic(unittest.TestCase):

    def testConstructor(self):
        #Check that no assert are rised
        c1 = C(1)
        c2 = C(2,"D3")
        c3 = C(1,"D6")
        c4 = C(6,"R1")
        c5 = C(3,"RE")
        c6 = C(0,"-")
        #Check asserts
        self.assertRaises(IncorrectProfile, C, 3,"D4")
        self.assertRaises(IncorrectProfile, C, 0,"D3")
        self.assertRaises(IncorrectProfile, C, 0,"D6")
        self.assertRaises(IncorrectProfile, C, 1,"-")
        self.assertRaises(IncorrectProfile, C, 0,"R1")
        self.assertRaises(IncorrectProfile, C, 0,"RE")
        self.assertRaises(IncorrectProfile, C, 1,"R1")
        self.assertRaises(IncorrectProfile, C, 1,"RE")
        self.assertRaises(IncorrectProfile, C, 7,"R1")
        self.assertRaises(IncorrectProfile, C, 7,"RE")
       
        
        
        
if __name__ == "__main__":
    #import sys;sys.argv = ['', 'Test.testName']
    unittest.main()