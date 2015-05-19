package com.zerokstudios.socratesman.gameobject;

/**
 * Created by Kevin on 5/12/2015.
 * <p/>
 * contains event data for a collision occurrence
 */
public class CollideEvent {
    public final GameObjectType TYPE;
    public final Collidable collisionMember;
    public final int elapsedTime;

    /**
     * default constructor
     *
     * @param aCollisionMember
     * @param time
     */
    public CollideEvent(Collidable aCollisionMember, int time) {
        TYPE = aCollisionMember.getType();
        collisionMember = aCollisionMember;
        elapsedTime = time;
    }
}
