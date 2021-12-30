package com.example.niccolo_zanieri_swe.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.util.Map;

public class CreatorTest {

    private Creator c;

    @BeforeEach
    void setUp() {
        Tags[] tags = new Tags[2];
        tags[0] = Tags.STREET;
        tags[1] = Tags.MOTION_GRAPHIC;

        Specializations[] specs = new Specializations[1];
        specs[0] = Specializations.FILMMAKER;

        c = new Creator("Peregrino Tuc", "peregrino.tuc@hotshire.com", "mushrooms", tags, specs);
    }

    @Test void CreatorCtorTest() {
        Assertions.assertEquals(Specializations.FILMMAKER, c.getSpecializations().get(0));
    }
}
