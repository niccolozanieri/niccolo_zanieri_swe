package com.example.niccolo_zanieri_swe.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.util.Map;

public class ClientTest {

    private Client c;

    @BeforeEach
    void setUp() {
        Tags[] tags = new Tags[2];
        tags[0] = Tags.STREET;
        tags[1] = Tags.MOTION_GRAPHIC;

        Specializations[] specs = new Specializations[1];
        specs[0] = Specializations.FILMMAKER;

        c = new Client("Peregrino Tuc", "peregrino.tuc@hotshire.com", "mushrooms");
    }

    @Test void SendEmploymentProposalTest() {
        Creator creator = new Creator("Gandalf The Grey", "gandalf.thegrey@hotshire.com", "youshallnotpassword", 1);

        Assertions.assertTrue(c.sendEmploymentProposal("test_proposal", 1, 1, creator));
        Assertions.assertFalse(c.sendEmploymentProposal("another_test_proposal", 1, 1, creator));
    }
}

