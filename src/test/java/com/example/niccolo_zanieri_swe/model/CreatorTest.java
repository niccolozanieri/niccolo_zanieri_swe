package com.example.niccolo_zanieri_swe.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.util.Map;

public class CreatorTest {

    @Test void CreatorCtorTest() {
        Tags[] tags = new Tags[2];
        tags[0] = Tags.STREET;
        tags[1] = Tags.MOTION_GRAPHIC;

        Specializations[] specs = new Specializations[1];
        specs[0] = Specializations.FILMMAKER;

        Creator u = new Creator("Peregrino Tuc", "peregrino.tuc@hotshire.com", "mushrooms", tags, specs);

        Assertions.assertEquals(Specializations.FILMMAKER, u.getSpecializations().get(0));
    }
}
