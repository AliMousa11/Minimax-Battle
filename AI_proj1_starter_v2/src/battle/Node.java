package battle;

import java.util.ArrayList;
import java.util.List;
import java.util.LinkedList;

public class Node {

    //you can add a state representation attribute or any other attributes you need
    public int value=0;
    public Node parent;
    public int[] armyAHealths;
    public int[] armyBHealths;
    public int[] armyADamages;
    public int[] armyBDamages;
    private String turn;
    public List<Action> actions= new ArrayList<>();
    private Action actionExecuted;
    public int alpha;
    public int beta;
    public boolean ab;
    public LinkedList<Action> planSoFar= new LinkedList<>();

    


    public Node ( Node parent, int[] armyAHealths, int[] armyBHealths, int[] armyADamages, int[] armyBDamages, String turn) {
        this.armyAHealths = armyAHealths;
        this.armyBHealths = armyBHealths;
        this.armyADamages = armyADamages;
        this.armyBDamages = armyBDamages;
        this.turn = turn;
        this.parent= parent;

    }
    public Node ( Node parent, int[] armyAHealths, int[] armyBHealths, int[] armyADamages, int[] armyBDamages, String turn, int alpha, int beta,boolean ab) {
        this.armyAHealths = armyAHealths;
        this.armyBHealths = armyBHealths;
        this.armyADamages = armyADamages;
        this.armyBDamages = armyBDamages;
        this.turn = turn;
        this.ab=ab;
        this.alpha = alpha;
        this.beta = beta;
        this.parent= parent;
    }
    
    public Action getActionExecuted() {
        return actionExecuted;
    }
    public void setActionExecuted(Action actionExecuted) {
        this.actionExecuted = actionExecuted;
    }

    public LinkedList<Action> getPlanSoFar() {
        return planSoFar;
    }
    
    public List<Action> getActions() {
        actions.clear(); 
        
        if(turn.equals("A")){
            
            for(int i = 0; i < armyAHealths.length; i++){
                if(armyAHealths[i] > 0){ 
                    for(int j = 0; j < armyBHealths.length; j++){
                        if(armyBHealths[j] > 0){ 
                            Action action = new Action(turn, i, j); 
                            actions.add(action);
                        }
                    }
                }
            }
        } else if(turn.equals("B")){
            
            for(int i = 0; i < armyBHealths.length; i++){
                if(armyBHealths[i] > 0){ 
                    for(int j = 0; j < armyAHealths.length; j++){
                        if(armyAHealths[j] > 0){ 
                            Action action = new Action(turn, i, j); 
                            actions.add(action);
                        }
                    }
                }
            }
        }
        
        return actions;
    }



    public String getTurn() {
        return turn;
    }
    public void setLeafValue() {
        if(this.turn.equals("A")){
            for (int health : this.armyBHealths) {
                if (health > 0) {
                    this.value += health;
                    
                }
            }
            this.value = -this.value;
        } else {
            for (int health : this.armyAHealths) {
                if (health > 0) {
                    this.value += health;
                    
                }
            }
        }        
    }
    public void updateParentValue(){
        if(this.parent != null && this.value != 0){
            if(this.turn.equals("A")){
                if(this.parent.value > this.value){
                    this.parent.value = this.value;
                    this.parent.planSoFar.addFirst(this.actionExecuted);
                }
            } else {
                if(this.parent.value < this.value){
                    this.parent.value = this.value;
                    this.parent.planSoFar.addFirst(this.actionExecuted);
                }
            }
        }
    }
    public int getValue() {
        // you are allowed to modify this implementation, but it must return the value of this node.
        return this.value;
    }
    public String planSoFarToString(){
        StringBuilder sb = new StringBuilder();
        for(Action action : planSoFar){
            sb.append(action.actionToString()+",");
        }
        return sb.toString();
    }
}

class Action{
    public String turn;
    public int attackerIndex;
    public int defenderIndex;   
    public Action(String turn, int attackerIndex, int defenderIndex){
        this.turn = turn;
        this.attackerIndex = attackerIndex;
        this.defenderIndex = defenderIndex;
    }
    public String actionToString(){
       return this.turn + "(" + this.attackerIndex + "," + this.defenderIndex + ")";
    }

}