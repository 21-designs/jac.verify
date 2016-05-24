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

import org.junit.Assert;
import org.junit.Test;

import de.drost.annotation.AVerification.Result;
import de.drost.annotation.prove.Interval;
import de.drost.annotation.prove.Max;
import de.drost.annotation.prove.Min;

public class CombinedTest
{
	@Interval(min=10, max=100)
	@Max(100)
	@Min(10)
	int validArg;
	
	@Interval(min=10, max=100)
	@Max(0)
	@Min(30)
	int invalidArg;
	
	@Test
	public void testValidMinMaxInterval() throws InstantiationException, IllegalAccessException, NoSuchFieldException, SecurityException
	{		
		validArg = 10;
		Result result = AVerification.verifyField( this, "validArg" );
		Assert.assertTrue(result.passedAll( ));	
		
		validArg = 50;
		result = AVerification.verifyField( this, "validArg" );
		Assert.assertTrue(result.passedAll( ));	
		
		validArg = 100;
		result = AVerification.verifyField( this, "validArg" );
		Assert.assertTrue(result.passedAll( ));	
	}
	
	
	@Test
	public void testInvalidMinMaxInterval() throws InstantiationException, IllegalAccessException, NoSuchFieldException, SecurityException
	{
		invalidArg = 10;
		Result result = AVerification.verifyField( this, "invalidArg" );
		Assert.assertFalse(result.passedAll( ));
		
		invalidArg = 100;
		result = AVerification.verifyField( this, "invalidArg" );
		Assert.assertFalse(result.passedAll( ));
		
		invalidArg = 20;
		result = AVerification.verifyField( this, "invalidArg" );
		Assert.assertFalse(result.passedAll( ));		
	}
}
