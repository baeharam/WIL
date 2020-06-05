package HW1;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Scanner;

public class DFACombined {

    public static void main(String[] args) {
        DFA dfa = new DFA();
        dfa.initialize();
        dfa.execute();
    }
}

class TransitionFunction {
    private Map<String, HashMap<String,String>> transition =
            new HashMap<String, HashMap<String,String>>();

    void transitionInitialize(String startState, String input, String nextState) {
        transition.putIfAbsent(startState, new HashMap<>());
        if(transition.get(startState)!=null) {
            transition.get(startState).put(input, nextState);
        }
    }

    String getState(String currentState, String input) {
        if(transition.get(currentState)!=null) {
            return transition.get(currentState).get(input);
        }
        return null;
    }
}

class DFA {
    private static final String initialState = "";
    private TransitionFunction tf = new TransitionFunction();
    private Set<String> acceptingStates = new HashSet<String>();

    void initialize() {
        // HW1.DFA 1
        tf.transitionInitialize(initialState,"a","a");
        tf.transitionInitialize("a","a","aa");
        tf.transitionInitialize("a","b","ab");
        tf.transitionInitialize("ab","a","aba");
        tf.transitionInitialize("aa","a","aa");
        tf.transitionInitialize("aba","b","ab");

        acceptingStates.add(initialState);
        acceptingStates.add("a");
        acceptingStates.add("aa");
        acceptingStates.add("aba");

        // HW1.DFA 2
        tf.transitionInitialize(initialState,"#","#");
        tf.transitionInitialize(initialState,"$","$");
        tf.transitionInitialize("#","a","ab");
        tf.transitionInitialize("#","b","ab");
        tf.transitionInitialize("$","x","xy");
        tf.transitionInitialize("$","y","xy");

        for(int i=0; i<10; i++) {
            String num = String.valueOf(i);
            tf.transitionInitialize("ab",num,"#a0");
            tf.transitionInitialize("#a0",num,"#a0");
            tf.transitionInitialize("xy",num,"$x0");
            tf.transitionInitialize("$x0",num,"$x0");
        }

        acceptingStates.add("#a0");
        acceptingStates.add("$x0");

        // HW1.DFA 3
        tf.transitionInitialize(initialState,"s","s");
        tf.transitionInitialize(initialState,"x","x");
        tf.transitionInitialize("s","t","st");
        tf.transitionInitialize("s","u","stu");
        tf.transitionInitialize("x","y","xy");
        tf.transitionInitialize("x","z","xz");
        tf.transitionInitialize("xy","s","s");
        tf.transitionInitialize("xy","x","xyx");
        tf.transitionInitialize("st","t","st");
        tf.transitionInitialize("st","u","stu");
        tf.transitionInitialize("xyx","y","xyxy");
        tf.transitionInitialize("xyx","z","xz");
        tf.transitionInitialize("xyxy","x","xyx");

        acceptingStates.add("stu");
        acceptingStates.add("xz");
    }

    private String executeMachine(String inputString) {
        String currentState = initialState;

        if(!inputString.isEmpty()) {
            currentState = String.valueOf(inputString.charAt(0));
            int i = 1;

            while(i<inputString.length()) {
                String input = String.valueOf(inputString.charAt(i++));
                currentState = tf.getState(currentState,input);
                if(currentState==null) {
                    break;
                }
            }
        }
        return acceptingStates.contains(currentState) ? "Accepted" : "Rejected";
    }

    void execute() {
        Scanner sc = new Scanner(System.in);

        System.out.println("Type E or e to exit");
        while(true) {
            System.out.print("Input: ");
            String input = sc.nextLine();
            if(input.compareToIgnoreCase("E")==0) {
                break;
            }
            String state = executeMachine(input);
            System.out.println(state);
            if(state.compareTo("Rejected")==0) {
                break;
            }
        }
        sc.close();
    }
}