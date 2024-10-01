import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mockStatic;

public class HorseTest {

    @Test
    public void nullNameException() {
        assertThrows(IllegalArgumentException.class, ()-> new Horse(null,1,1));

    }
    @Test
    public void nullNameMessage() {
        try {
            new Horse(null, 1, 1);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Name cannot be null.", e.getMessage());
        }

    }
    @ParameterizedTest
    @ValueSource(strings = {"","  ", "\t\t", "\n\n\n\n\n\n"})
    public void blankNameException(String name) {
        assertThrows(IllegalArgumentException.class, () -> new Horse(name,1,1));

    }
    @ParameterizedTest
    @ValueSource(strings = {"","  ", "\t\t", "\n\n\n\n\n\n"})
    public void blankNameMassage(String name){
        try {
            new Horse(name,1,1);
            fail();
        }catch (IllegalArgumentException e) {
            assertEquals("Name cannot be blank.", e.getMessage());
        }
    }
    @Test
    public void speedException() {
        assertThrows(IllegalArgumentException.class, ()-> new Horse("test name",-1,1));

    }
    @Test
    public void speedMassage() {
        try {
            new Horse("test name",-1,1);
            fail();
        }catch (IllegalArgumentException e) {
            assertEquals("Speed cannot be negative.", e.getMessage());
        }
    }
    @Test
    public void distanceException() {
        assertThrows(IllegalArgumentException.class, ()-> new Horse("test name",1,-1));

    }
    @Test
    public void distanceMassage() {
        try {
            new Horse("test name",1,-1);
            fail();
        }catch (IllegalArgumentException e) {
            assertEquals("Distance cannot be negative.", e.getMessage());
        }
    }


    @Test
    public void getNameTest()  {
        Horse horse = new Horse("pony", 1,1);

        assertEquals("pony", horse.getName());
    }
    @Test
    public void getSpeed() {
        Horse horse = new Horse("pony", 400,1);

        assertEquals(400, horse.getSpeed());

    }
    @Test
    public void getDistance() {
        Horse horse = new Horse("pony", 1, 500);

        assertEquals(500, horse.getDistance());

    }
    @Test
    public void zeroDistanceTest() {
        Horse horse = new Horse("pony", 1 );

        assertEquals(0, horse.getDistance());

    }

    @Test
    void moveUsesGetRandom(){
        try (MockedStatic<Horse> mockedStatic = mockStatic(Horse.class)) {
            new Horse("pony", 1,1).move();

            mockedStatic.verify(()-> Horse.getRandomDouble(0.2, 0.9));
        }
    }
    @ParameterizedTest
    @ValueSource(doubles = {0.1, 0.2, 0.5, 1.0, 999.999, 0.0})
    void moveTest(double random){
        try (MockedStatic<Horse> mockedStatic = mockStatic(Horse.class)){
            Horse horse = new Horse("pony", 31, 250);
            mockedStatic.when(()-> Horse.getRandomDouble(0.2, 0.9)).thenReturn(random);

            horse.move();

            assertEquals(250 + 31 * random, horse.getDistance());
        }
    }

}
