package com.redbadger.runner;

import java.util.Scanner;


public class ProcessRobotInstructions {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		
		//Creat max coordinates by scanning the first line
		String maxCoordinateString = scanner.nextLine().toUpperCase();
		Point maxPoint = new Point(Character.getNumericValue(maxCoordinateString.charAt(0)), Character.getNumericValue(maxCoordinateString.charAt(1)));
		
		StringBuilder sb = new StringBuilder();
		
		while(scanner.hasNextLine()) {
			String line = scanner.nextLine().toUpperCase();
			if(line != "") {
				Point currentPoint = new Point(Character.getNumericValue(line.charAt(0)), Character.getNumericValue(line.charAt(1)));				
				Direction direction = new Direction(line.charAt(2));
				String instructions = scanner.nextLine().toUpperCase();
				
				moveRobotAsPerInstructions(currentPoint, direction, maxPoint, instructions);	
			}
		}
		
	}

	private static void moveRobotAsPerInstructions(Point currentPoint, Direction direction, Point maxPoint,
			String instructions) {
		
		boolean flag = true;
		for (char instruction : instructions.toCharArray())
			if(processInstruction(currentPoint, direction, maxPoint, instruction) == false) {
				flag=false;
				break;
			}
		
		
		System.out.println(currentPoint + " " + direction.getFacing() + ((flag == false)? " LOST": ""));
	}

	private static boolean processInstruction(Point currentPoint, Direction direction, Point maxPoint, char instruction) {
		if(instruction == 'L' || instruction == 'R' )
			changeRobotFacing(direction, instruction);
		else
			return moveRobotForward(currentPoint, direction, maxPoint);
		return true;
			
	}
	
	/**
	 * @param currentPoint
	 * @param direction
	 * @param maxPoint
	 * @return boolean false if the robot moves into an edge, true if robot moves into a valid position in grid
	 */
	private static boolean moveRobotForward(Point currentPoint, Direction direction, Point maxPoint) {
		char currentFacing = direction.getFacing();
		if(currentFacing == 'N') {
			if(currentPoint.getY()+1>maxPoint.getY()) {
				return false;
			}
			currentPoint.increment('y');
		}
		else if(currentFacing == 'E') {
			if(currentPoint.getX()+1>maxPoint.getX()) {
				return false;
			}
			currentPoint.increment('x');
		}
		else if(currentFacing == 'S') {
			if(currentPoint.getY()-1<0) {
				return false;
			}
			currentPoint.decrement('y');
		}
		else if(currentFacing == 'W') {
			if(currentPoint.getX()-1<0) {
				return false;
			}
			currentPoint.decrement('x');
		}
		return true;
	}

	private static void changeRobotFacing(Direction direction, char instruction) {
		char currentFacing = direction.getFacing();
		char newFacing = 'O';
		if(instruction == 'L') {
			switch (currentFacing) {
			case 'N':
				newFacing = 'W';
				break;
			case 'E':
				newFacing = 'N';
				break;
			case 'S':
				newFacing = 'E';
				break;
			case 'W':
				newFacing = 'S';
				break;

			}
		}
		else if(instruction == 'R') {
			switch (currentFacing) {
			case 'N':
				newFacing = 'E';
				break;
			case 'E':
				newFacing = 'S';
				break;
			case 'S':
				newFacing = 'W';
				break;
			case 'W':
				newFacing = 'N';
				break;

			}
		}
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
	char facing;
	
	public Direction(char direction) {
		this.facing = direction;
	}
	
	public char getFacing() {
		return facing;
	}
	
	public void setFacing(char facing) {
		this.facing = facing;
	}
}