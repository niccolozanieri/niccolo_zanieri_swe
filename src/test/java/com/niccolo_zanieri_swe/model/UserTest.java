package com.niccolo_zanieri_swe.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UserTest {

    private User u;

    @BeforeEach void setUp() {
        Tags[] tags = new Tags[2];
        tags[0] = Tags.STREET;
        tags[1] = Tags.MOTION_GRAPHIC;

        Specializations[] specs = new Specializations[1];
        specs[0] = Specializations.FILMMAKER;

        u = new Creator("Peregrino Tuc", "peregrino.tuc@hotshire.com", "mushrooms", tags, specs);
    }

    @Test void UserCtorTest() {
        int[] expectedArray = new int[u.getLikesPerTag().values().size()];


        Assertions.assertArrayEquals(expectedArray, u.getLikesIntArray());
        Assertions.assertEquals(Tags.STREET, u.getFavouriteTags().get(0));
        Assertions.assertEquals(Tags.MOTION_GRAPHIC, u.getFavouriteTags().get(1));

    }

    @Test void UserIsFollowedTest() {

        Creator creator = new Creator("Otho Baggins", "otho.baggins@hotshire.com", "baggins");
        Assertions.assertFalse(u.isFollowed(creator));

        u.getFollowed().put("Otho Baggins", creator);
        Assertions.assertTrue(u.isFollowed(creator));
    }

    @Test void UserFollowTest() {
        Creator creator = new Creator("Otho Baggins", "otho.baggins@hotshire.com", "baggins");
        u.follow(creator);

        Assertions.assertTrue(u.isFollowed(creator));

        IllegalArgumentException thrown = Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> {u.follow(creator);},
                "Expected u.follow(other_user) to throw, but it didn't"
        );

        Assertions.assertTrue(thrown.getMessage().equals("Can't follow yourself or someone already followed."));
    }
}
