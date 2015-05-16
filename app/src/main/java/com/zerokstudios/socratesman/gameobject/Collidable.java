package com.zerokstudios.socratesman.gameobject;

/**
 * Created by Kevin on 5/12/2015.
 */
public interface Collidable {
    public boolean isColliding(Entity entity, int time);
    public void onCollide(CollideEvent event);
    public GameObjectType getType();
}
