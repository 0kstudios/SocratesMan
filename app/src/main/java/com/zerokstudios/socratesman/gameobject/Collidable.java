package com.zerokstudios.socratesman.gameobject;

/**
 * Created by Kevin on 5/12/2015.
 *
 * collidable object specification
 */
public interface Collidable {
    boolean isColliding(Entity entity, int time);
    void onCollide(CollideEvent event);
    GameObjectType getType();
}
