/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swe.life.objects;

import swe.life.objects.enumerations.Goal;

/**
 * This represents the current events of a {@link Animal animal}.
 * @author Roy
 */
public class Activity {
    private Object target;
    private Goal goal;

    public Activity() {
        this.target = null;
        this.goal = Goal.None;
    }

    /**
     * Gets the current target of the event.
     * @return Will return null when there is no target.
     */
    public Object getTarget() {
        return target;
    }

    public void setTarget(Object target) {
        this.target = target;
    }

    /**
     * Gets the current goal of the activity.
     * @return A value from the enum {@link Goal}.
     */
    public Goal getGoal() {
        return goal;
    }

    public void setGoal(Goal goal) {
        this.goal = goal;
        this.target = null;
    }
}
