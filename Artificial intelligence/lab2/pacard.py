
"""
In search.py, you will implement generic search algorithms which are called by
Pacman agents (in searchAgents.py).
"""

import util
from logic import * 

class SearchProblem:
    """
    This class outlines the structure of a search problem, but doesn't implement
    any of the methods (in object-oriented terminology: an abstract class).

    You do not need to change anything in this class, ever.
    """

    def getStartState(self):
        """
        Returns the start state for the search problem.
        """
        util.raiseNotDefined()

    def isGoalState(self, state):
        """
          state: Search state

        Returns True if and only if the state is a valid goal state.
        """
        util.raiseNotDefined()

    def getSuccessors(self, state):
        """
        state: Search state

        For a given state, this should return a list of triples, (successor,
        action, stepCost), where 'successor' is a successor to the current
        state, 'action' is the action required to get there, and 'stepCost' is
        the incremental cost of expanding to that successor.
        """
        util.raiseNotDefined()

    def getCostOfActions(self, actions):
        """
        actions: A list of actions to take

        This method returns the total cost of a particular sequence of actions.
        The sequence must be composed of legal moves.
        """
        util.raiseNotDefined()


def miniWumpusSearch(problem): 
    """
    A sample pass through the miniWumpus layout. Your solution will not contain 
    just three steps! Optimality is not the concern here.
    """
    from game import Directions
    e = Directions.EAST 
    n = Directions.NORTH
    return  [e, n, n]


#using global var for easier function calls
stench={}
chemicals={}
glow = {}
poison= {}
wumpus = {}
teleporter={}
ordinary = {}

def logicBasedSearch(problem):
    """

    To get started, you might want to try some of these simple commands to
    understand the search problem that is being passed in:

    print "Start:", problem.getStartState()
    print "Is the start a goal?", problem.isGoalState(problem.getStartState())
    print "Start's successors:", problem.getSuccessors(problem.getStartState())

    print "Does the Wumpus's stench reach my spot?", 
               \ problem.isWumpusClose(problem.getStartState())

    print "Can I sense the chemicals from the pills?", 
               \ problem.isPoisonCapsuleClose(problem.getStartState())

    print "Can I see the glow from the teleporter?", 
               \ problem.isTeleporterClose(problem.getStartState())
    
    (the slash '\\' is used to combine commands spanning through multiple lines - 
    you should remove it if you convert the commands to a single line)
    
    Feel free to create and use as many helper functions as you want.

    A couple of hints: 
        * Use the getSuccessors method, not only when you are looking for states 
        you can transition into. In case you want to resolve if a poisoned pill is 
        at a certain state, it might be easy to check if you can sense the chemicals 
        on all cells surrounding the state. 
        * Memorize information, often and thoroughly. Dictionaries are your friends and 
        states (tuples) can be used as keys.
        * Keep track of the states you visit in order. You do NOT need to remember the
        tranisitions - simply pass the visited states to the 'reconstructPath' method 
        in the search problem. Check logicAgents.py and search.py for implementation.
    """
    # array in order to keep the ordering
    """
    ####################################
    ###                              ###
    ###        YOUR CODE HERE        ###
    ###                              ###
    ####################################
    """
    visitedStates = []
    startState = problem.getStartState()
    #visitedStates.append(startState)

    safeStates=util.PriorityQueue()
    safeStatesSet=set()
    dangerousStates=set()
    unsureStates=util.PriorityQueue()
    unsureStatesSet=set()

    currentState=startState
    poison[currentState] = False
    wumpus [currentState] = False
    teleporter [currentState] = False
    ordinary [currentState] = True

    safeStates.push(currentState,0)
    safeStatesSet.add(currentState)
    while True:
        if not safeStates.isEmpty():
            currentState = safeStates.pop()
            print currentState
            safeStatesSet.remove(currentState)
        elif not unsureStates.isEmpty():
            currentState = unsureStates.pop()
            unsureStatesSet.remove(currentState)
        else:
            print "I can't go anywhere"
            return problem.reconstructPath(visitedStates)

        if currentState in visitedStates: continue
        visitedStates.append(currentState)
        print "Visiting: ", currentState
        if problem.isPoisonCapsule(currentState) or problem.isWumpus(currentState):
            print "Game over: Pacard is dead!"
            return problem.reconstructPath(visitedStates)
        if problem.isGoalState(currentState) :
            print "Game over: Teleported home!"
            return problem.reconstructPath(visitedStates)

        print "safe states: ", safeStatesSet
        print "unsure stats: ", unsureStatesSet
        print "dangerus state: ", dangerousStates
        stench[currentState]=problem.isWumpusClose(currentState)
        chemicals[currentState] = problem.isPoisonCapsuleClose(currentState)
        glow[currentState] = problem.isTeleporterClose(currentState)

        print "Sensed s: ", stench[currentState]
        print "Sensed c: ", chemicals[currentState]
        print "Sensed g: ", glow[currentState]

        successors=problem.getSuccessors(currentState)
        for succ in successors:
            state=succ[0]

            if state not in dangerousStates and state not in visitedStates:
                clauses = getClauses(successors, currentState)
                if clauses is None: continue
                #print "clauses for state ",state, clauses
                poison [state] = conclude(clauses, state, "poison")
                wumpus [state] = conclude(clauses, state, "wumpus")
                teleporter [state] = conclude(clauses, state, "teleporter")
                ordinary [state] = conclude(clauses, state, "ordinary")

                print "concluded for ",state," P: ",poison[state]," W: ",wumpus[state]," T: ",teleporter[state]," O: ",ordinary[state]

                if teleporter[state]:
                    currentState = state
                    visitedStates.append(currentState)
                    return problem.reconstructPath(visitedStates)

                elif ordinary[state]:
                    if state not in safeStatesSet:
                        if state in unsureStatesSet:
                            newUnsureStates = util.PriorityQueue()
                            while not unsureStates.isEmpty():
                                unsureState=unsureStates.pop()
                                if unsureState==state:
                                    print "debug"
                                    print unsureStatesSet
                                    unsureStatesSet.remove(state)
                                    continue
                                newUnsureStates.push(unsureState, 20*unsureState[0]+unsureState[1])
                            unsureStates=newUnsureStates
                        safeStates.push(state,20*state[0]+state[1])
                        safeStatesSet.add(state)

                elif wumpus[state]:
                    if state in unsureStatesSet:
                        newUnsureStates = util.PriorityQueue()
                        while not unsureStates.isEmpty():
                            unsureState = unsureStates.pop()
                            if unsureState == state:
                                unsureStatesSet.remove(state)
                                continue
                            newUnsureStates.push(unsureState, 20 * unsureState[0] + unsureState[1])
                        unsureStates = newUnsureStates
                    dangerousStates.add(state)

                elif poison[state]:
                    if state in unsureStatesSet:
                        newUnsureStates = util.PriorityQueue()
                        while not unsureStates.isEmpty():
                            unsureState = unsureStates.pop()
                            if unsureState == state:
                                unsureStatesSet.remove(state)
                                continue
                            newUnsureStates.push(unsureState, 20 * unsureState[0] + unsureState[1])
                        unsureStates = newUnsureStates
                    dangerousStates.add(state)

                else:
                    if state not in unsureStatesSet:
                        unsureStates.push(state, 20 * state[0] + state[1])
                        unsureStatesSet.add(state)


def conclude (clauses, state, name):
    if name == "poison":
        label='p'
        if poison.has_key(state):
            return poison[state]
    elif name == "wumpus":
        label='w'
        if wumpus.has_key(state):
            return wumpus[state]
    elif name == "teleporter":
        label='t'
        if teleporter.has_key(state):
            return teleporter[state]
    elif name == "ordinary":
        label='o'
        if wumpus.has_key(state) and poison.has_key(state):
            if wumpus[state]== False and  poison[state] == False:
                ordinary[state]=True
        if ordinary.has_key(state):
            return ordinary[state]

    '''
    conclusion = resolution(clauses, Clause((Literal(label, state))))
    if conclusion==True:
       #print "Debug1"
        return conclusion
    elif conclusion != resolution(clauses, Clause((Literal(label, state, True)))):
       #print "DEBUGGGGGGGGGGGG"
        return conclusion
    else:
        return None'''


def getClauses(succesors, currentState):
    wumpusClauses=set()
    poisonClauses = set()
    teleporterClauses = set()
    ordinaryClauses = set()

    if stench.has_key(currentState):
        if stench[currentState]:
            literal=set()
            for succ in succesors:
                state=succ[0]
                literal.add(Literal('W', state, False))

            wumpusClauses.add(Clause(literal))
        elif not stench[currentState]:
            for succ in succesors:
                state = succ[0]
                wumpusClauses.add(Clause(Literal('W', state, True)))
                wumpus[state]=False

    if chemicals.has_key(currentState):
        if chemicals[currentState]:
            literal = set()
            for succ in succesors:
                state = succ[0]
                literal.add(Literal('P', state, False))

            poisonClauses.add(Clause(literal))
        elif not chemicals[currentState]:
            for succ in succesors:
                state = succ[0]
                poisonClauses.add(Clause(Literal('P', state, True)))
                poison[state] = False

    if glow.has_key(currentState):
        if glow[currentState]:
            literal = set()
            for succ in succesors:
                state = succ[0]
                literal.add(Literal('T', state, False))

            teleporterClauses.add(Clause(literal))
        elif not glow[currentState]:
            for succ in succesors:
                state = succ[0]
                teleporterClauses.add(Clause(Literal('T', state, True)))
                teleporter[state] = False

    if wumpus[currentState]:
        wumpus.clear()
        wumpus[currentState]=True

    if stench.has_key(currentState) and chemicals.has_key(currentState):
        if not stench[currentState] and not chemicals[currentState]:
            for succ in succesors:
                state = succ[0]
                ordinaryClauses.add(Clause(Literal('O', state, False)))
                ordinary[succ]=True

    clauses=wumpusClauses | poisonClauses | teleporterClauses | ordinaryClauses

    if not wumpus[currentState] and not poison[currentState]:
        clauses.add(Clause(Literal('O', currentState, False)))
        ordinary[currentState] = True

    return clauses

# Abbreviations
lbs = logicBasedSearch
