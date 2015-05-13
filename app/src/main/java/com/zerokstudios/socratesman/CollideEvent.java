package com.zerokstudios.socratesman;

import java.util.ArrayList;

/**
 * Created by Kevin on 5/12/2015.
 */
public class CollideEvent {
    public final ArrayList<Entity> COLLISION_MEMBERS;

    public CollideEvent(ArrayList<Entity> A_COLLISION_MEMBERS){
        COLLISION_MEMBERS = A_COLLISION_MEMBERS;
    }
}
