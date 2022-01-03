package com.example.sleepyhead.data;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({DatabaseWriteTest.class, DatabaseQueryTest.class})
public final class DatabaseSuite {}
