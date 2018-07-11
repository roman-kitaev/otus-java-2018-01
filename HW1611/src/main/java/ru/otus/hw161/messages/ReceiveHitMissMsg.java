package ru.otus.hw161.messages;

import ru.otus.hw161.app.HitMiss;
import ru.otus.hw161.app.Msg;

public class ReceiveHitMissMsg extends Msg {
    private HitMiss hitMiss;

    public ReceiveHitMissMsg(HitMiss hitMiss) {
        super(ReceiveHitMissMsg.class);
        this.hitMiss = hitMiss;
    }

    public HitMiss getHitMiss() {
        return hitMiss;
    }

    @Override
    public String toString() {
        return "ReceiveHitMissMsg{" +
                "hitMiss=" + hitMiss +
                '}';
    }
}
