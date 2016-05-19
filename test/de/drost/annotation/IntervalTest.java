package de.drost.annotation;
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

import org.junit.Assert;
import org.junit.Test;

import de.drost.Verificator;
import de.drost.Verificator.Result;
import de.drost.annotation.prove.Interval;

public class IntervalTest
{
	@Interval(min=0, max=20)
	int value1;
	
	@Test
	public void testValidValue() throws InstantiationException, IllegalAccessException
	{
		value1 = 0;
		Result result = Verificator.verify( this );
		Assert.assertTrue(result.passedAll( ));
		
		value1 = 10;
		result = Verificator.verify( this );
		Assert.assertTrue(result.passedAll( ));
		
		value1 = 20;
		result = Verificator.verify( this );
		Assert.assertTrue(result.passedAll( ));
	}
	
	@Test
	public void testInvalidValue() throws InstantiationException, IllegalAccessException
	{
		value1 = 50;
		
		Result result = Verificator.verify( this );
		
		Assert.assertFalse(result.passedAll( ));
	}
}
