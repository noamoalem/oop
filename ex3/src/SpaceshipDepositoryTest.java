import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * The SpaceshipDepositoryTest class unit all the tests of locker, long-term storage and spaceship class.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        LockerTest.class,
        LongTermTest.class,
        SpaceshipTest.class
})
public class SpaceshipDepositoryTest{}


