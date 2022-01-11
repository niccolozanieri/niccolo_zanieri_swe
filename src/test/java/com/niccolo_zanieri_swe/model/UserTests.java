package com.niccolo_zanieri_swe.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UserTests {

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

        User other_user = new Creator("Otho Baggins", "otho.baggins@hotshire.com", "baggins");
        Assertions.assertFalse(u.isFollowed(other_user));

        u.getFollowed().put("Otho Baggins", other_user);
        Assertions.assertTrue(u.isFollowed(other_user));
    }

    @Test void UserFollowTest() {
        User other_user = new Creator("Otho Baggins", "otho.baggins@hotshire.com", "baggins");
        u.follow(other_user);

        Assertions.assertTrue(u.isFollowed(other_user));

        IllegalArgumentException thrown = Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> {u.follow(other_user);},
                "Expected u.follow(other_user) to throw, but it didn't"
        );

        Assertions.assertTrue(thrown.getMessage().equals("Can't follow yourself or someone already followed."));
    }
}