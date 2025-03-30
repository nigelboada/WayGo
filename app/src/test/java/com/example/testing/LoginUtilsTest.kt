package com.example.testing

import com.example.testing.utils.LoginUtils
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

/**
 * LoginUtils test class
 */
class LoginUtilsTest {

    private lateinit var loginUtils: LoginUtils

    @Before
    fun initLoginUtilsTest(){
        loginUtils = LoginUtils()
    }

    @Test
    fun validEmailPasses(){
        assertTrue(loginUtils.isValidEmailAddress("user@udl.cat"))
    }

    @Test
    fun validEmailFails(){
        assertFalse(loginUtils.isValidEmailAddress("xxx"))
    }
}