package com.zerokstudios.socratesman.gameobject;

import com.zerokstudios.socratesman.gameobject.Entity;

import java.util.ArrayList;

/**
 * Created by Kevin on 5/12/2015.
 */
public class CollideEvent {
    public final GameObjectType TYPE;

    public CollideEvent(Entity collisionMember){
        TYPE = collisionMember.getType();
    }

    public CollideEvent(EntityGrid collisionMember) {
        TYPE = collisionMember.getType();
    }
}
