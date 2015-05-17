package com.zerokstudios.socratesman.gameobject;

import com.zerokstudios.socratesman.gameobject.Entity;

import java.util.ArrayList;

/**
 * Created by Kevin on 5/12/2015.
 */
public class CollideEvent {
    public final GameObjectType TYPE;
    public final Collidable collisionMember;
    public final int elapsedTime;

    public CollideEvent(Collidable aCollisionMember, int time){
        TYPE = aCollisionMember.getType();
        collisionMember = aCollisionMember;
        elapsedTime = time;
    }
}
