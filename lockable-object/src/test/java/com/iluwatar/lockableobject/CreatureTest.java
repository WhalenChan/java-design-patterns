package com.iluwatar.lockableobject;

import com.iluwatar.lockableobject.domain.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CreatureTest {

    private Creature orc;
    private Creature elf;
    private Lockable sword;

    @BeforeEach
    void init() {
        elf = new Elf("Elf test");
        orc = new Orc("Orc test");
        sword = new SwordOfAragorn();
    }

    @Test
    void baseTest() {
        Assertions.assertEquals("Elf test", elf.getName());
        Assertions.assertEquals(CreatureType.ELF, elf.getType());
        Assertions.assertThrows(NullPointerException.class, () -> new Elf(null));
        Assertions.assertThrows(NullPointerException.class, () -> elf.acquire(null));
        Assertions.assertThrows(NullPointerException.class, () -> elf.attack(null));
        Assertions.assertThrows(IllegalArgumentException.class, () -> elf.hit(-10));
    }

    @Test
    void hitTest() {
        elf.hit(CreatureStats.ELF_HEALTH.getValue() / 2);
        Assertions.assertEquals(CreatureStats.ELF_HEALTH.getValue() / 2, elf.getHealth());
        elf.hit(CreatureStats.ELF_HEALTH.getValue() / 2);
        Assertions.assertFalse(elf.isAlive());

        Assertions.assertEquals(0, orc.getInstruments().size());
        Assertions.assertTrue(orc.acquire(sword));
        Assertions.assertEquals(1, orc.getInstruments().size());
        orc.kill();
        Assertions.assertEquals(0, orc.getInstruments().size());
    }

    @Test
    void testFight() throws InterruptedException {
        killCreature(elf, orc);
        Assertions.assertTrue(elf.isAlive());
        Assertions.assertFalse(orc.isAlive());
        Assertions.assertTrue(elf.getHealth() > 0);
        Assertions.assertTrue(orc.getHealth() <= 0);
    }

    @Test
    void testAcqusition() throws InterruptedException {
        Assertions.assertTrue(elf.acquire(sword));
        Assertions.assertEquals(elf.getName(), sword.getLocker().getName());
        Assertions.assertTrue(elf.getInstruments().contains(sword));
        Assertions.assertFalse(orc.acquire(sword));
        killCreature(orc, elf);
        Assertions.assertTrue(orc.acquire(sword));
        Assertions.assertEquals(orc, sword.getLocker());
    }

    void killCreature(Creature source, Creature target) throws InterruptedException {
        while (target.isAlive()) {
            source.attack(target);
        }
    }

    @Test
    void invalidDamageTest() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> elf.hit(-50));
    }
}
