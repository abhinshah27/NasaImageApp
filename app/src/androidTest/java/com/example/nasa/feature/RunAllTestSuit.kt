package com.example.nasa.feature

import org.junit.runner.RunWith
import org.junit.runners.Suite

/**
 * Created by Abhin.
 */
@RunWith(Suite::class)
@Suite.SuiteClasses(
    ImageScreenTest::class,
    ImageDetailScreenTest::class
)
class RunAllTestSuit
