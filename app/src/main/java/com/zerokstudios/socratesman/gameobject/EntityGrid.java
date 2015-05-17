package com.zerokstudios.socratesman.gameobject;

import android.graphics.Canvas;

import com.zerokstudios.socratesman.Map;
import com.zerokstudios.socratesman.Vector;

import java.util.ArrayList;

/**
 * Created by Kevin on 5/12/2015.
 */
public class EntityGrid<SE extends StaticEntity> implements Collidable {
    private final GameObjectType TYPE;
    private Map map;
    private SE[][] entities;

    //private static final Vector[] DIRECTIONS = {new Vector(0,0), new Vector(0,1), new Vector(1,1), new Vector(1,0), new Vector(1,-1), new Vector(0,-1), new Vector(-1,-1), new Vector(-1,0), new Vector(-1,1)};

    @SuppressWarnings("unchecked") //arraylist must accept type SE thus it is safe to cast to SE
    public EntityGrid(Map aMap, ArrayList<SE> aEntities, SE tribute) {
        map = aMap;
        entities = (SE[][]) (new StaticEntity[map.getGridDimensions().X][map.getGridDimensions().Y]);
        for (SE entity : aEntities) {
            entities[toGridPosition(entity.getPosition()).X][toGridPosition(entity.getPosition()).Y] = entity;
        }

        TYPE = tribute.getType(); // tribute properties completely null, only used to obtain type
        //System.out.println(Arrays.deepToString(entities));
    }

    public SE getEntity(Vector position) {
        return entities[toGridPosition(position).X][toGridPosition(position).Y];
    }

    public void setEntity(Vector position, SE entity) {
        Vector gridPosition = toGridPosition(position);
        entities[gridPosition.X][gridPosition.Y] = entity;
    }

    @Override
    public GameObjectType getType() {
        return TYPE;
    }

    @Override
    public boolean isColliding(Entity entity, int time) {
        //System.out.println(entity.getType() + " " + entity.nextPosition(time));
        //System.out.println(getEntity(entity.nextPosition(time)));
        //return entity.isColliding(collisionGetEntity(entity.nextPosition(time), entity.getVelocity()), time); //testing code here for nicer collisions
        return entity.isColliding(getEntity(entity.nextPosition(time)), time);
    }

    @Override
    public void onCollide(CollideEvent event) {
        if (event.collisionMember instanceof Entity) {
            //Entity entityGridObject = collisionGetEntity(((Entity) event.collisionMember).nextPosition(event.elapsedTime), ((Entity) event.collisionMember).getVelocity()); // testing code here for nicer collisions
            Entity entityGridObject = getEntity(((Entity) event.collisionMember).nextPosition(event.elapsedTime));
            if (entityGridObject != null) {
                entityGridObject.onCollide(event);
                garbageCollect(entityGridObject);
            }
        }
    }

    private Entity collisionGetEntity(Vector position, Vector velocity) {
        Vector roundedCenterPosition = roundToGridPosition(position, velocity);
        return entities[roundedCenterPosition.X][roundedCenterPosition.Y];
    }

//    private ArrayList<SE> getSurroundings(Vector position) {
//        position = toGridPosition(position);
//        ArrayList<SE> surroundings = new ArrayList<SE>();
//        for (Vector direction : DIRECTIONS) {
//            Vector target = new Vector(position.X + direction.X, position.Y + direction.Y) ;
//            if (isInBounds(target)) {
//                surroundings.add(getEntity(target));
//            }
//        }
//        return surroundings;
//    }
//
//    private boolean isInBounds(Vector gridposition) {
//        int x = gridposition.X;
//        int y = gridposition.Y;
//        return x > -1 && x < entities.length && y > -1 && y < entities[0].length;
//    }

    public void draw(Canvas canvas) {
        for (SE[] ses : entities) {
            for (SE se : ses) {
                if (se != null) {
                    se.draw(canvas);
                }
            }
        }
    }

    public void garbageCollect() {

    }

    private void garbageCollect(Vector position) {
        garbageCollect(getEntity(position));
    }

    private void garbageCollect(Entity entity) {
        if (entity.isDead()) {
            setEntity(entity.getPosition(), null);
        }
    }

    private Vector toGridPosition(Vector position) {
        return new Vector(position.X / map.getTileDiameter(), position.Y / map.getTileDiameter());
    }

    private Vector roundToGridPosition(Vector position, Vector velocity) {
        boolean right = velocity.X > 0;
        boolean down = velocity.Y > 0;
        double x = position.X * 1.0 / map.getTileDiameter();
        double y = position.Y * 1.0 / map.getTileDiameter();
        if (right) {
            x = Math.ceil(x);
        } else {
            x = Math.floor(x);
        }
        if (down) {
            y = Math.ceil(y);
        } else {
            y = Math.floor(y);
        }
        return new Vector((int) x, (int) y);
    }
}
