package sample;

import org.junit.Assert;
import org.junit.Test;
import sample.utilities.User;

public class UserTest {

    @Test
    public void getId() {
        User user = new User(2, "Krzysztof");
        Assert.assertEquals(2, user.getId());
        user = new User("Krzysztof");
        Assert.assertEquals(0, user.getId());
    }

    @Test
    public void setId() {
        User user = new User("Karolina");
        user.setId(987);
        Assert.assertEquals(987, user.getId());
    }

    @Test
    public void getName() {
        User user = new User("Kasia");
        Assert.assertEquals("Kasia", user.getName());
        user = new User(123, "Mariusz");
        Assert.assertEquals("Mariusz", user.getName());
    }

    @Test
    public void setName() {
        User user = new User("Tomasz");
        user.setName("Kamil");
        Assert.assertEquals("Kamil", user.getName());
    }
}