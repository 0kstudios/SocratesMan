package com.zerokstudios.socratesman;

/**
 * Created by Kevin on 5/12/2015.
 */
public interface Collidable {
    public boolean isColliding(Entity entity);
    public void onCollide(CollideEvent event);
}
