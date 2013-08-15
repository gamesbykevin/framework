package com.gamesbykevin.framework.ai;

import com.gamesbykevin.framework.base.Cell;

import java.util.ArrayList;
import java.util.List;

public class AStar 
{
    private Node start, goal;
    
    private List<Node> openList, closedList, path;
    
    private boolean foundGoal = false;
    
    public AStar()
    {
        openList = new ArrayList<>();
        closedList = new ArrayList<>();
        path = new ArrayList<>();
    }
    
    public AStar(Cell start, Cell goal)
    {
        this(start.getCol(), start.getRow(), goal.getCol(), goal.getRow());
    }
    
    public AStar(int startX, int startY, int goalX, int goalY)
    {
        start = new Node(startX, startY, null);
        goal  = new Node(goalX,  goalY, null);
    }
    
    public void dispose()
    {
        start = null;
        goal  = null;

        if (closedList != null)
        {
            for (Object o : closedList)
            {
                o = null;
            }

            closedList.clear();
        }

        if (openList != null)
        {
            for (Object o : openList)
            {
                o = null;
            }

            openList.clear();
        }

        if (path != null)
        {
            for (Object o : path)
            {
                o = null;
            }

            path.clear();
        }

        closedList = null;
        openList   = null;
        path       = null;
    }
    
    public void setStart(Cell cell)
    {
        if (start != null)
        {
            start.dispose();
            start = null;
        }
        
        if (cell == null)
        {
            start = null;
        }
        else
        {
            start = new Node(cell.getCol(), cell.getRow(), null);
        }
        
        cell = null;
    }
    
    public void setGoal(Cell cell)
    {
        if (goal != null)
        {
            goal.dispose();
            goal = null;
        }
        
        if (cell == null)
        {
            goal = null;
        }
        else
        {
            goal = new Node(cell.getCol(), cell.getRow(), null);
        }
        
        cell = null;
    }
    
    public void calculateShortestPath(boolean[][] map, ArrayList otherObstacles)
    {
        calculateShortestPath(map, otherObstacles, false);
    }
    
    public void calculateShortestPath(boolean[][] map, ArrayList otherObstacles, boolean allowDiagnolMovement)  
    {   //anything true in boolean[][] map will be a valid path, 
        //ArrayList otherObstacles are any other cells in the way note: can be null if no obstacles
        
        if (openList == null)
            openList = new ArrayList<>();
        
        if (closedList == null)
            closedList = new ArrayList<>();
        
        start.setBounds(0, map[0].length -  1, 0, map.length - 1);  //set boundaries for the start cell
        start.setGoalScore(getGoalScore(start));
        start.setStartScore(0.0);
        
        addOpenList(start);
        
        Node current = null;
        Node node = null;
        
        while(hasOpenNode())
        {
            //get lowest cost node from the open list
            current = getLowestTotalScore();

            removeOpenNode(current);
            addClosedList(current);
            
            if (current.getGoalScore() == 0)
            {   //we found the goal
                foundGoal = true;
                break;
            }

            //add nodes surrounding the current node
            List<Node> possibleNewNodes = new ArrayList<>();
            
            for (int x=-1; x <= 1; x++)
            {
                for (int y=-1; y <= 1; y++)
                {
                    if (x == 0 && y == 0)
                        continue;

                    if (!allowDiagnolMovement && x != 0 && y != 0)
                        continue;

                    possibleNewNodes.add(new Node(current.getCol() + x, current.getRow() + y, current));
                }
            }
            
            for (int i=0; i < possibleNewNodes.size(); i++)
            {
                node = possibleNewNodes.get(i);

                if (current.getCol() != node.getCol() && current.getRow() != node.getRow())
                {
                    node.setStartScore(current.getStartScore() + 1.4);
                }
                else
                {
                    node.setStartScore(current.getStartScore() + 1.0);
                }

                node.setGoalScore(getGoalScore(node));

                if (isValidNode(map, node, otherObstacles))
                {
                    if (getOpenNode(node) != null)
                    {
                        if (node.getStartScore() < getOpenNode(node).getStartScore())
                        {   //if node parent score is less than existing set new parent for the node
                            addOpenList(node);//new parent was already set so just update it
                        }
                    }
                    else
                    {
                        addOpenList(node);
                    }
                }
            }
            
            possibleNewNodes.clear();
            possibleNewNodes = null;
        }
        
        //after we found the goal backtrack the closed list to create the path until we reach the start
        if (closedList.size() > 0 && foundGoal)
        {
            current = closedList.get(closedList.size() - 1);

            while(true)
            {   //start at goal and finish at the start
                if (path.size() < 1)
                {
                    path.add(new Node(current));
                }
                else
                {
                    if (current.getParent() != null)    //the start node will not have a parent node
                    {
                        current = getClosedNode(current.getParent());   //get parent node
                        path.add(new Node(current));
                    }

                    if (current.getCol() == start.getCol() && current.getRow() == start.getRow())
                        break;
                }
            }
        }
        
        openList.clear();
        closedList.clear();
        
        current = null;
        node    = null;
    }
    
    public boolean hasFoundGoal()
    {
        return foundGoal;
    }
    
    private boolean isValidNode(boolean[][] map, Node node, ArrayList otherObstacles)
    {
        if (otherObstacles != null && otherObstacles.size() > 0)
        {
            for (int i=0; i < otherObstacles.size(); i++)
            {
                Cell tmp = (Cell)otherObstacles.get(i);
                
                if (node.equals(tmp))//if any of the obstacles hit the node it is not valid
                {
                    tmp = null;
                    return false;
                }
                
                tmp = null;
            }
        }
        
        return (node.hasBounds(start.getMinCol(), start.getMaxCol(), start.getMinRow(), start.getMaxRow()) && hasPath(map, node) && getClosedNode(node) == null);
    }
    
    private boolean hasPath(boolean[][] map, Cell test)
    {
        return map[test.getRow()][test.getCol()];
    }
    
    private void addOpenList(Node node)
    {
        if (getOpenNode(node) != null)
        {
            for (int i=0; i < openList.size(); i++)
            {
                Node tmp = openList.get(i);

                if (node.equals(tmp))
                {
                    openList.set(i, node);
                    break;
                }
                
                tmp = null;
            }
        }
        else
        {
            openList.add(node);
        }
    }
    
    private void addClosedList(Node node)
    {
        closedList.add(node);
    }
    
    private Node getLowestTotalScore()
    {   //gets lowest cost node from open list
        double lowestScore = 0;
        int index = -1;
        
        for (int i=0; i < openList.size(); i++)
        {
            Node tmp = openList.get(i);
            
            if (i == 0 || tmp.getTotalScore() < lowestScore)
            {
                index = i;
                lowestScore = tmp.getTotalScore();
            }
            
            tmp = null;
        }
        
        return openList.get(index);
    }
    
    private boolean hasOpenNode()
    {
        return (openList.size() > 0);
    }
    
    private Node getOpenNode(Node testNode)
    {
        for (int i=0; i < openList.size(); i++)
        {
            Node tmp = openList.get(i);
            
            if (testNode.equals(tmp))
                return tmp;
            
            tmp = null;
        }
        
        return null;
    }
    
    private Node getClosedNode(Node testNode)
    {
        for (int i=0; i < closedList.size(); i++)
        {
            Node tmp = closedList.get(i);
            
            if (testNode.equals(tmp))
            {
                return tmp;
            }
            
            tmp = null;
        }
        
        return null;
    }
    
    private void removeOpenNode(Node node)
    {
        for (int i=0; i < openList.size(); i++)
        {
            Node tmpNode = openList.get(i);
            
            if (node.equals(tmpNode))
            {
                tmpNode = null;
                openList.remove(i);
                return;
            }
            
            tmpNode = null;
        }
    }
    
    private double getGoalScore(Cell current)
    {
        double diffX = current.getCol() - goal.getCol();
        double diffY = current.getRow() - goal.getRow();
        
        if (diffX < 0)
            diffX = -diffX;
        if (diffY < 0)
            diffY = -diffY;
        
        return diffX + diffY;
    }
    
    public Cell getStart()
    {
        return start;
    }
    
    public Cell getGoal()
    {
        return goal;
    }
    
    public List<Node> getPath()
    {   //returns an arraylist of cell's from the goal to the start
        return path;
    }
    
    public boolean hasPath()
    {   //has any path available
        return path.size() > 0;
    }
    
    public void clearPath()
    {
        path.clear();
    }
    
    public boolean hasExistingPath(int col, int row)
    {
        Cell cell = new Cell(col, row);
        
        for (int i=0; i < path.size(); i++)
        {
            Cell test = (Cell)path.get(i);
            
            if (cell.equals(test))
            {
                test = null;
                cell = null;
                return true;
            }
            
            test = null;
        }
        
        cell = null;
        
        return false;
    }
    
    public boolean hasExistingPath(Cell cell)
    {
        return hasExistingPath(cell.getCol(), cell.getRow());
    }
    
    public boolean hasExistingPath(ArrayList tmpPath)
    {   //ArrayList tmpPath will be checked if it collides with this.path
        for (int i=0; i < tmpPath.size(); i++)
        {
            Cell cell = (Cell)tmpPath.get(i);
            
            if (hasExistingPath(cell))
            {
                cell = null;
                return true;
            }
            
            cell = null;
        }
        
        return false;
    }
}