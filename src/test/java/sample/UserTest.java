package sample;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserTest {

    @Test
    public void getId() {
        User user = new User(2, "Krzysztof");
        Assert.assertEquals(user.getId(), 2);
        user = new User("Krzysztof");
        Assert.assertEquals(user.getId(), 0);
    }

    @Test
    public void setId() {
    }

    @Test
    public void getName() {
    }

    @Test
    public void setName() {
    }
}