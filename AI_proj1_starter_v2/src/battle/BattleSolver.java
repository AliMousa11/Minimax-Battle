package battle;



public class BattleSolver {


    public Node initialNode; // this attribute MUST be used to store the initial node in the search tree
    int nodesExpanded=0;

    


    public Node executeAction(Node node, Action action) {
        String currentTurn = node.getTurn();
        if(currentTurn.equals("A")) {
            int attackerIndex = action.attackerIndex;
            int defenderIndex = action.defenderIndex;
            int[] newArmyBHealths = node.armyBHealths.clone();
            newArmyBHealths[defenderIndex] -= node.armyADamages[attackerIndex];
            if (newArmyBHealths[defenderIndex] < 0) {
                newArmyBHealths[defenderIndex] = 0;
            }
            node.setActionExecuted(action);
            return new Node(node.parent, node.armyAHealths, newArmyBHealths, node.armyADamages, node.armyBDamages, "B");

        } else if(currentTurn.equals("B")) {
            int attackerIndex = action.attackerIndex;
            int defenderIndex = action.defenderIndex;

            int[] newArmyAHealths = node.armyAHealths.clone();
            newArmyAHealths[defenderIndex] -= node.armyBDamages[attackerIndex];
            if (newArmyAHealths[defenderIndex] < 0) {
                newArmyAHealths[defenderIndex] = 0;
            }
            node.setActionExecuted(action);
            return new Node(node.parent, newArmyAHealths, node.armyBHealths, node.armyADamages, node.armyBDamages, "A");
        }

        // If the turn is neither "A" nor "B", return the original node unchanged.
        return node;
    }


    public void generateTree(Node node){
        node.getActions();
        if(node.actions.isEmpty()){
            node.setLeafValue();
            node.updateParentValue();
            return;
        }
        for(Action action : node.actions){
            Node childNode = executeAction(node, action);
            nodesExpanded++;
            generateTree(childNode);
            childNode.updateParentValue();

        }

    }
    

    public String  solve(String initialStateString, boolean ab, boolean visualize){
        // TODO: implement this function
        String[] parts = initialStateString.split(";");
        String turn = parts[2];
        String[] armyAString = parts[0].split(",");
        String[] armyBString = parts[1].split(",");
        int[] armyAHealths= new int[armyAString.length/2];
        int[] armyADamages= new int[armyAString.length/2];
        int[] armyBHealths= new int[armyBString.length/2];
        int[] armyBDamages= new int[armyBString.length/2];
        for(int i=0; i< armyAString.length;i+=2){
            armyAHealths[i/2]= Integer.parseInt(armyAString[i]);
            armyADamages[i/2]= Integer.parseInt(armyAString[i+1]);
        }
        for(int i=0; i< armyBString.length;i+=2){
            armyBHealths[i/2]= Integer.parseInt(armyBString[i]);
            armyBDamages[i/2]= Integer.parseInt(armyBString[i+1]);
        }
        if(!ab){

            this.initialNode= new Node (null, armyAHealths, armyBHealths, armyADamages, armyBDamages, turn);
        }else{
            this.initialNode= new Node (null, armyAHealths, armyBHealths, armyADamages, armyBDamages, turn, -1, 1);
        }
        
        generateTree(this.initialNode);
        String sol="";
        String plan = this.initialNode.planSoFarToString();
        sol+= plan + " ";
        sol+= this.initialNode.getValue();
        sol+= " " + nodesExpanded;
        
        

        return sol;

    }





}