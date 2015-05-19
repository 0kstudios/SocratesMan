package com.zerokstudios.socratesman.gameobject;

import android.graphics.Canvas;

import com.zerokstudios.socratesman.Map;
import com.zerokstudios.socratesman.Vector;

import java.util.ArrayList;

/**
 * Created by Kevin on 5/12/2015.
 * <p/>
 * holds a grid of static entities
 */
public class EntityGrid<SE extends StaticEntity> implements Collidable {
    private static final Vector[] DIRECTIONS = {new Vector(0, 0), new Vector(0, 1), new Vector(1, 0), new Vector(0, -1), new Vector(-1, 0)};
    private final GameObjectType TYPE;
    private Map map;
    private SE[][] entities;

    /**
     * constructor
     *
     * @param aMap      pass in map
     * @param aEntities pass in static entities
     * @param tribute   pass in tribute
     */
    @SuppressWarnings("unchecked") //arraylist must accept type SE thus it is safe to cast to SE
    public EntityGrid(Map aMap, ArrayList<SE> aEntities, SE tribute) {
        map = aMap;
        entities = (SE[][]) (new StaticEntity[map.getGridDimensions().X][map.getGridDimensions().Y]);
        for (SE entity : aEntities) {
            entities[toGridPosition(entity.getPosition()).X][toGridPosition(entity.getPosition()).Y] = entity;
        }

        TYPE = tribute.getType(); // tribute properties completely null, only used to obtain type
    }

    /**
     * get entity at position in pixel dimensions
     *
     * @param position
     * @return entity
     */
    public SE getEntity(Vector position) {
        return entities[toGridPosition(position).X][toGridPosition(position).Y];
    }

    /**
     * set entity at given position
     *
     * @param position
     * @param entity
     */
    public void setEntity(Vector position, SE entity) {
        Vector gridPosition = toGridPosition(position);
        entities[gridPosition.X][gridPosition.Y] = entity;
    }

    /**
     * get type of static entities
     *
     * @return type
     */
    @Override
    public GameObjectType getType() {
        return TYPE;
    }

    /**
     * find static entity closest to given entity and check to see if two are colliding
     *
     * @param entity
     * @param time
     * @return is colliding
     */
    @Override
    public boolean isColliding(Entity entity, int time) {
        //System.out.println(entity.getType() + " " + entity.nextPosition(time));
        //System.out.println(getEntity(entity.nextPosition(time)));
//        return entity.isColliding(collisionGetEntity(entity.nextPosition(time), entity.getVelocity()), time); //testing code here for nicer collisions

        return entity.isColliding(getEntity(entity.nextPosition(time)), time);

//        for (Entity e : getSurroundings(entity.nextPosition(time))) {
//            if (e != null && e.isColliding(entity, time)) {
//                return true;
//            }
//        }
//        return false;
    }

    /**
     * on collide, pass on collide event to static entity closest to entity given in event
     *
     * @param event collision event
     */
    @Override
    public void onCollide(CollideEvent event) {
        if (event.collisionMember instanceof Entity) {
//            Entity entityGridObject = collisionGetEntity(((Entity) event.collisionMember).nextPosition(event.elapsedTime), ((Entity) event.collisionMember).getVelocity()); // testing code here for nicer collisions
            Entity entityGridObject = getEntity(((Entity) event.collisionMember).nextPosition(event.elapsedTime));
            if (entityGridObject != null) {
                entityGridObject.onCollide(event);
                garbageCollect(entityGridObject);
            }
        }
    }

//    public SE collisionGetEntity(Vector position, Vector velocity) {
//        double x = position.X*1.0/map.getTileDiameter();
//        double y = position.Y*1.0/map.getTileDiameter();
//        if (velocity.X > 0) {
//            x = Math.ceil(x);
//        }
//        if (velocity.Y > 0) {
//            y = Math.ceil(y);
//        }
//        return entities[(int)x][(int)y];
//    }

    /**
     * draw each individual entity
     *
     * @param canvas
     */
    public void draw(Canvas canvas) {
        for (SE[] ses : entities) {
            for (SE se : ses) {
                if (se != null) {
                    se.draw(canvas);
                }
            }
        }
    }

    /**
     * delete entity at given position if it is dead
     *
     * @param position
     */
    private void garbageCollect(Vector position) {
        garbageCollect(getEntity(position));
    }

    /**
     * delete entity if given entity is dead
     *
     * @param entity
     */
    private void garbageCollect(Entity entity) {
        if (entity.isDead()) {
            setEntity(entity.getPosition(), null);
        }
    }

    /**
     * transforms given position to grid position
     *
     * @param position
     * @return grid position
     */
    private Vector toGridPosition(Vector position) {
        return new Vector(position.X / map.getTileDiameter(), position.Y / map.getTileDiameter());
    }

    /**
     * get 4 adjacent squares of a given position
     *
     * @param position
     * @return
     */
    public ArrayList<SE> getSurroundings(Vector position) {
        ArrayList<SE> surroundings = new ArrayList<>();
        for (Vector direction : DIRECTIONS) {
            SE target = getEntity(position.sum(direction.scale(map.getTileDiameter())));
            if (target != null) {
                surroundings.add(target);
            }
        }
        return surroundings;
    }

    /**
     * passes tick to each static entity
     *
     * @param time
     */
    public void tick(int time) {
        for (Entity[] e : entities) {
            for (Entity ent : e) {
                if (ent != null) {
                    ent.tick(time);
                }
            }
        }
    }
}
