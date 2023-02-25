package com.redbadger.runner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


/**
 * @author rahulsudhanaboina
 *
 */
public class ProcessRobotInstructions {
	
	/**
	 * This list is used to map direction (char) to integer and vice versa.
	 * Using this list has helped us to replace large if else ladders.
	 */
	private final static List<Character> directionsMap = new ArrayList<>(Arrays.asList('N', 'E', 'S', 'W'));
	
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		
		//Creat max coordinates of grid by scanning the first line
		String maxCoordinateString = scanner.nextLine().toUpperCase();
		Point maxPoint = new Point(Character.getNumericValue(maxCoordinateString.charAt(0)), Character.getNumericValue(maxCoordinateString.charAt(1)));
		
		//Process each robot instruction
		while(scanner.hasNextLine()) {
			String line = scanner.nextLine().toUpperCase();
			if(line != "") {
				
				//Get point and direction of the robot
				Point currentPoint = new Point(Character.getNumericValue(line.charAt(0)), Character.getNumericValue(line.charAt(1)));				
				Direction direction = new Direction(directionsMap.indexOf(line.charAt(2)));
				
				//Get the instructions
				String instructions = scanner.nextLine().toUpperCase();
				
				//process instructions of the robot with particular point and direction
				moveRobotAsPerInstructions(currentPoint, direction, maxPoint, instructions);	
			}
		}
		
	}

	private static void moveRobotAsPerInstructions(Point currentPoint, Direction direction, Point maxPoint,
			String instructions) {
		
		//flag used to check whether robot is lost. If its lost, the flag is set as false and loop is terminated.
		boolean flag = true;
		
		for (char instruction : instructions.toCharArray())
			if(processEachInstruction(currentPoint, direction, maxPoint, instruction) == false) {
				flag=false;
				break;
			}
		
		System.out.println(currentPoint.getX()+" "+currentPoint.getY() + " " + directionsMap.get(direction.getFacing()) + ((flag == false)? " LOST": ""));
	}
	
	/**
	 * @return true if the robot moves forward (location is updated), false if robot is fallen of the edge.
	 */
	private static boolean processEachInstruction(Point currentPoint, Direction direction, Point maxPoint, char instruction) {
		if(instruction == 'L' || instruction == 'R' )
			changeRobotFacing(direction, instruction);
		else
			return moveRobotForward(currentPoint, direction, maxPoint);
		return true;
	}
	
	/**
	 * @return true if the robot moves forward (location is updated), false if robot is fallen of the edge.
	 */
	private static boolean moveRobotForward(Point currentPoint, Direction direction, Point maxPoint) {
		int currentFacing = direction.getFacing();
		if(currentFacing == 0) {
			if(currentPoint.getY()+1>maxPoint.getY()) {
				return false;
			}
			currentPoint.increment('y');
		}
		else if(currentFacing == 1) {
			if(currentPoint.getX()+1>maxPoint.getX()) {
				return false;
			}
			currentPoint.increment('x');
		}
		else if(currentFacing == 2) {
			if(currentPoint.getY()-1<0) {
				return false;
			}
			currentPoint.decrement('y');
		}
		else if(currentFacing == 3) {
			if(currentPoint.getX()-1<0) {
				return false;
			}
			currentPoint.decrement('x');
		}
		return true;
	}

	/**
	 * Changes facing of the robot, the method uses int value as facing of the robot. 
	 * If the instruction is L, the decrement the facing value. If the instruction is R, increment the value and perform mod 4 on the new value. 
	 */
	private static void changeRobotFacing(Direction direction, char instruction) {
		
		int currentFacing = direction.getFacing();
		int newFacing = 0;
		
		if(instruction == 'L') {
			if(currentFacing==0)
				newFacing=3;
			else 
				newFacing = currentFacing - 1;
		}
		else if(instruction == 'R') 
			newFacing = (currentFacing+1) % 4;
		
		direction.setFacing(newFacing);
	}
}

class Point{
	private int x;
	private int y;
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	
	public void increment(char coordinate) {
		if(coordinate == 'x' || coordinate == 'X')
			setX(getX() + 1);
		else 
			setY(getY() + 1);
	}
	
	public void decrement(char coordinate) {
		if(coordinate == 'x' || coordinate == 'X')
			setX(getX() - 1);
		else 
			setY(getY() - 1);
	}
	@Override
	public String toString() {
		return "Point [x=" + x + ", y=" + y + "]";
	}
	
	
}

class Direction {
	int facing;
	
	public Direction(int direction) {
		this.facing = direction;
	}
	
	public int getFacing() {
		return facing;
	}
	
	public void setFacing(int facing) {
		this.facing = facing;
	}
}