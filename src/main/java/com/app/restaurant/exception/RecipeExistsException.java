package com.app.restaurant.exception;

/**
 * Recipe exists exception
 * @author Anitha Manoharan
 *
 */
public class RecipeExistsException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public RecipeExistsException(final String message) {
		super(message);
	}

}
