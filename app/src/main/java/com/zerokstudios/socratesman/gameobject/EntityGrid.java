package com.zerokstudios.socratesman.gameobject;

import android.graphics.Canvas;

import com.zerokstudios.socratesman.Map;
import com.zerokstudios.socratesman.Vector;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Kevin on 5/12/2015.
 */
public class EntityGrid<SE extends StaticEntity> implements Collidable {
    private Map map;

    private final GameObjectType TYPE;

    private SE[][] entities;

    //private static final Vector[] DIRECTIONS = {new Vector(0,0), new Vector(0,1), new Vector(1,1), new Vector(1,0), new Vector(1,-1), new Vector(0,-1), new Vector(-1,-1), new Vector(-1,0), new Vector(-1,1)};

    @SuppressWarnings("unchecked") //arraylist must accept type SE thus it is safe to cast to SE
    public EntityGrid(Map aMap, ArrayList<SE> aEntities, SE tribute) {
        map = aMap;
        entities = (SE[][])(new StaticEntity[map.getGridDimensions().X][map.getGridDimensions().Y]);
        for (SE entity : aEntities) {
            entities[toGridPosition(entity.getPosition()).X][toGridPosition(entity.getPosition()).Y] = entity;
        }

        TYPE = tribute.getType(); // tribute properties completely null, only used to obtain type
        //System.out.println(Arrays.deepToString(entities));
    }

    public SE getEntity(Vector position) {
        return entities[toGridPosition(position).X][toGridPosition(position).Y];
    }

    public void setEntity(SE entity) {
        entities[toGridPosition(entity.getPosition()).X][toGridPosition(entity.getPosition()).Y] = entity;
    }

    @Override
    public GameObjectType getType() {
        return TYPE;
    }

    @Override
    public boolean isColliding(Entity entity, int time) {
        //System.out.println(entity.getType() + " " + entity.nextPosition(time));
        //System.out.println(getEntity(entity.nextPosition(time)));
        return entity.isColliding(getEntity(entity.nextPosition(time)), time);
    }

    @Override
    public void onCollide(CollideEvent event) {
        if (event.collisionMember instanceof Entity) {
            Entity entityGridObject = getEntity(((Entity) event.collisionMember).nextPosition(event.elapsedTime));
            if (entityGridObject != null) {
                entityGridObject.onCollide(event);
            }
        }
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

    public void garbageCollect(Vector position) {

    }

    private Vector toGridPosition(Vector position) {
        return new Vector(position.X/(map.getTileDiameter()), position.Y/(map.getTileDiameter()));
    }
}
