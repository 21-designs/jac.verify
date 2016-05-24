/*
 * This file is part of the application library that simplifies common
 * initialization and helps setting up any java program.
 * 
 * Copyright (C) 2016 Yannick Drost, all rights reserved.
 * 
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
package de.drost.annotation;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import de.drost.annotation.AVerification.Result;
import de.drost.annotation.prove.MaxSize;

public class MaxSizeTest
{
	// Array
	@MaxSize(3)
	Integer[] array = null;
	
	// Subtypes of Collection
	@MaxSize(3)
	List<Integer> list = null;
	@MaxSize(3)
	Set<Integer> set = null;
	@MaxSize(3)
	Queue<Integer> queue = null;
	
	// String
	@MaxSize(3)
	String string = null;
	
	
	

	@Test
	public void testValidMaxSize() throws InstantiationException, IllegalAccessException
	{
		Integer[] integers = new Integer[] {1};
		set( integers );
		
		Result result = AVerification.verify( this );
		Assert.assertTrue(result.passedAll( ));
		
		integers = new Integer[] {1,2,3};
		set( integers );
		
		result = AVerification.verify( this );
		Assert.assertTrue(result.passedAll( ));
	}
	
	@Test
	public void testInvalidMaxSize() throws InstantiationException, IllegalAccessException
	{
		Integer[] integers = new Integer[] {};
		set( integers );
		
		Result result = AVerification.verify( this );
		Assert.assertFalse(result.passedAll( ));
		
		integers = new Integer[] {1,2,3,4};
		set( integers );
		
		result = AVerification.verify( this );
		Assert.assertFalse(result.passedAll( ));
	}
	
	/**
	 * Initializes all different types.
	 * @param integers
	 */
	public void set(Integer... integers)
	{
		if(integers == null || integers.length == 0)
			return;
		
		array = integers;
		
		// Subtypes of Collection
		list = Arrays.asList( array );
		set = new HashSet<Integer>(list);
		queue = new LinkedList<Integer>(list);
		
		// String
		string = "";
		for(int i : integers)
		{
			string += i;
		}
	}
}
