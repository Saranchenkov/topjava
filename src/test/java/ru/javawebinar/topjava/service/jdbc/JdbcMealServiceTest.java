package ru.javawebinar.topjava.service.jdbc;

import org.junit.Assume;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TestName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.service.AbstractMealServiceTest;

import java.util.stream.Stream;

import static ru.javawebinar.topjava.Profiles.JDBC;

@ActiveProfiles(JDBC)
public class JdbcMealServiceTest extends AbstractMealServiceTest {

    @Autowired
    private Environment environment;

    @Rule
    public TestName test = new TestName();

    @Before
    public void beforeMethod(){
        boolean isJDBC = Stream.of(environment.getActiveProfiles()).anyMatch(JDBC::equals);
        boolean isValidation = "testValidation".equals(test.getMethodName());
        Assume.assumeFalse((isJDBC && isValidation));
    }


}